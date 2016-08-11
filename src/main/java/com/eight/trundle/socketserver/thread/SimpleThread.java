package com.eight.trundle.socketserver.thread;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;
import com.eight.trundle.Constants;
import com.eight.trundle.common.DateUtils;

/**
 * 所有处理线程的基类，必须被继承
 * @author weijl
 *
 */
public abstract class SimpleThread implements Runnable {
	
	private Logger logger = Logger.getLogger(SimpleThread.class);
	private Socket socket = null;
	private DataOutputStream out = null;
	private DataInputStream in = null;
	private int bufferSize = 1024;
	private String isLongConnection = Constants.CONSTNATS_YES;
	private long starttime = System.currentTimeMillis();
	
	/**
	 * 构造函数，接受socket
	 * @param socket
	 */
	public  SimpleThread(Socket socket, String isLongConnection){
		this.socket = socket;
		this.isLongConnection = isLongConnection;
	}
	/**
	 * 执行主函数
	 */
	public void run() {  
        try {  
        	System.out.println(DateUtils.getCurFormatDateTime()+": 处理数据中...");
        	logger.info("处理数据中...");
        	
            if(socket != null){
				out = new DataOutputStream(socket.getOutputStream());
				in = new DataInputStream(socket.getInputStream());
				do{
					doThread();
				}while(Constants.CONSTNATS_YES.equals(isLongConnection));
				out.close();
				in.close();
				socket.close();
    		}
            
        } catch (Exception e) { 
            logger.error(e.toString());
        } finally {  
        	System.out.println(DateUtils.getCurFormatDateTime()+": 处理数据 [完毕]，关闭此线程。线程生命周期[ " + (System.currentTimeMillis()-starttime)/1000 + "S ]");
            logger.info("处理数据 [完毕]，关闭此线程。线程生命周期[ " + (System.currentTimeMillis()-starttime)/1000 + "S ]");
            try {  
                if(socket != null && !socket.isClosed()) socket.close();  
            } catch (IOException e) {}  
        }  
    }  
	
	/**
	 * 必须实现的执行内容函数
	 */
	public abstract void doThread();
	
	
	/**
	 * 获取socket
	 * @return
	 */
	public Socket getSocket() {
		return socket;
	}
	/**
	 * 获取out
	 * @return
	 */
	public DataOutputStream getOut() {
		return out;
	}
	/**
	 * 获取in
	 * @return
	 */
	public DataInputStream getIn() {
		return in;
	}
	/**
	 * 向out发送数据
	 * @param msg
	 */
	protected void writeToClient(byte[] msg) throws IOException{
		if(out != null && msg != null && msg.length > 0){
			out.write(msg);
			out.flush();
		}
	}
	/**
	 * 向out发送数据
	 * @param msg
	 */
	protected void writeToClient(String msg) throws IOException{
		if(out != null && msg != null && msg.length() > 0){
			out.writeUTF(msg);
			out.flush();
		}
	}
	/**
	 * 从流中读取数据
	 * @return
	 */
	protected byte[] readFromIn() throws IOException{
		byte[] reciveBytes = new byte[0]; 
		if(in != null){
			byte[] onceRead = new byte[bufferSize]; 
			int inLength = in.available();
			int currentLength = 0;
			int temp = 0;
			while(inLength > 0 && currentLength < inLength && temp != -1){
				temp = in.read(onceRead,0,bufferSize);
				if(temp != -1){
					reciveBytes = ArrayUtils.addAll(reciveBytes, ArrayUtils.subarray(onceRead, 0, temp));
					currentLength += temp;
				}
			}			
		}
		return reciveBytes;
	}
	/**
	 * 从流中读取数据
	 * @return
	 */
	protected String readFromInUTF() throws IOException{
		String reciveStr = ""; 
		if(in != null){
			reciveStr = in.readUTF();
		}
		return reciveStr;
	}

}

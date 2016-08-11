package com.eight.trundle.socketserver.thread;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import org.apache.log4j.Logger;

import com.eight.trundle.common.DateUtils;

/**
 * 所有处理线程的基类，必须被继承
 * @author weijl
 *
 */
public abstract class SimpleUDPThread implements Runnable {
	public Logger logger = Logger.getLogger(SimpleUDPThread.class);
	private DatagramSocket server = null;
	private DatagramPacket packet = null;
	
	public  SimpleUDPThread(DatagramSocket server, DatagramPacket packet){
		this.server = server;
		this.packet = packet;
	}
	
	

	public void run() {
		System.out.println(DateUtils.getCurFormatDateTime()+": 处理数据中...");
		logger.info("处理数据中...");
		long starttime = System.currentTimeMillis();
		if(packet != null){
			doThread();
		}else{
			System.out.println(DateUtils.getCurFormatDateTime()+": 收到无效数据，放弃本次处理");
			logger.info("收到无效数据，放弃本次处理");
		}	
		System.out.println(DateUtils.getCurFormatDateTime()+": 处理数据 [完毕]，关闭此线程。线程生命周期[ " + (System.currentTimeMillis()-starttime)/1000 + "S ]");
		logger.info("处理数据 [完毕]，关闭此线程。线程生命周期[ " + (System.currentTimeMillis()-starttime)/1000 + "S ]");
	}
	
	public abstract void doThread();
	
	/**
	 * 获取DatagramSocket
	 * @return
	 */
	public DatagramSocket getServer() {
		return server;
	}
	/**
	 * 获取DatagramPacket
	 * @return
	 */
	public DatagramPacket getPacket() {
		return packet;
	}
	/**
	 * 从流中读取数据
	 * @return
	 */
	protected byte[] readFromIn() throws IOException{
		return packet.getData();
	}
	/**
	 * 从流中读取数据
	 * @return
	 */
	protected String readFromInUTF() throws IOException{
		return new String(packet.getData());
	}
	/**
	 * 从流中写数据
	 * @return
	 */
	protected void writeToClient(byte[] msg) throws IOException{
		DatagramPacket sendpacket = new DatagramPacket(new byte[msg.length], msg.length,packet.getSocketAddress());
		sendpacket.setData(msg);
		server.send(sendpacket);
	}
	/**
	 * 从流中写数据
	 * @return
	 */
	protected void writeToClient(String msg) throws IOException{
		writeToClient(msg.getBytes());
	}

}

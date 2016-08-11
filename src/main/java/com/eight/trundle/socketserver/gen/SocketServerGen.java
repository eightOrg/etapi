package com.eight.trundle.socketserver.gen;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import org.apache.log4j.Logger;
import org.springframework.core.task.TaskExecutor;
import com.eight.trundle.Constants;
import com.eight.trundle.common.DateUtils;
import com.eight.trundle.socketserver.factory.ThreadFactory;

public class SocketServerGen {
	
	private static Logger logger = Logger.getLogger(SocketServerGen.class);
	//服务端类型
	private String type = "TCP";
	//服务端socket占用端口
	private int port = 8899;
	//线程池
	private TaskExecutor taskExecutor;
	//执行的目标线程
	private String className;
	//数据缓冲区大小
	private int bufferSize = 1024;
	//TCP连接：是否长连接
	private String isLongConnection = Constants.CONSTNATS_YES;
	
	
	/**
	 * 开启服务
	 */
	public void startServiceGen() {
		String strConnection = "";
		if("TCP".equals(type)){
			strConnection = "[" + (Constants.CONSTNATS_YES.equals(isLongConnection) ? "长连接" : "短连接") + "]";
		}
		System.out.println(DateUtils.getCurFormatDateTime()+": 启动socket服务器...[端口："+port+"] [协议："+type+"] " + strConnection);
		logger.info("启动socket服务器...[端口："+port+"] [协议："+type+"]");
		try {	
			if("TCP".equals(type)){
				ServerSocket server = new ServerSocket(port);
				System.out.println(DateUtils.getCurFormatDateTime()+": [成功]");
				logger.info("[成功]");
				Socket socket = null;
				System.out.println(DateUtils.getCurFormatDateTime()+": 等待接收数据...");
				logger.info("等待接收数据...");
				while (true) {  
					// 接受客户端的请求  
		            socket = server.accept();
		            System.out.println(DateUtils.getCurFormatDateTime()+": 接受请求  IP:"+ socket.getInetAddress() + " port:" + socket.getPort());
		            logger.info("接受请求  IP:"+ socket.getInetAddress() + " port:" + socket.getPort());
		            // 为支持多用户并发访问，采用线程池管理每一个用户的连接请求  
		            taskExecutor.execute((Runnable)ThreadFactory.getInstance(className, socket, isLongConnection));
		        }
			}else if("UDP".equals(type)){
				DatagramSocket server = new DatagramSocket(port);
				System.out.println(DateUtils.getCurFormatDateTime()+": [成功]");
				logger.info("[成功]");
				DatagramPacket packet = new DatagramPacket(new byte[bufferSize], bufferSize); 
				System.out.println(DateUtils.getCurFormatDateTime()+": 等待接收数据...");
				logger.info("等待接收数据...");
				while (true) {
					server.receive(packet); 
					System.out.println(DateUtils.getCurFormatDateTime()+": 接受请求  IP:"+ packet.getAddress() + " port:" + packet.getPort());
					logger.info("接受请求  IP:"+ packet.getAddress() + " port:" + packet.getPort());
					taskExecutor.execute((Runnable)ThreadFactory.getUDPInstance(className, server, packet));// 启动一个线程来处理请求
					//申明新的对象，防止不同线程重复使用
					packet = new DatagramPacket(new byte[bufferSize], bufferSize);  
				}
			}else{
				System.out.println(DateUtils.getCurFormatDateTime()+": [失败]");
				System.out.println(DateUtils.getCurFormatDateTime()+": 服务端类型错误");
				logger.info("[失败]");
				logger.info("服务端类型错误");
			}
		} catch (IOException e) {
			if (e.getMessage().indexOf("Address already in use") > -1) {
				System.out.println(DateUtils.getCurFormatDateTime()+": [失败]");
				System.out.println(DateUtils.getCurFormatDateTime()+": 该端口在本台计算机上已经被占用，可能本程序已经启动！");
				logger.info("[失败]");
				logger.info("该端口在本台计算机上已经被占用，可能本程序已经启动！");
			}
			System.exit(0);
			logger.error(e);
		}
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public int getPort() {
		return port;
	}


	public void setPort(int port) {
		this.port = port;
	}


	public TaskExecutor getTaskExecutor() {
		return taskExecutor;
	}


	public void setTaskExecutor(TaskExecutor taskExecutor) {
		this.taskExecutor = taskExecutor;
	}


	public String getClassName() {
		return className;
	}


	public void setClassName(String className) {
		this.className = className;
	}


	public int getBufferSize() {
		return bufferSize;
	}


	public void setBufferSize(int bufferSize) {
		this.bufferSize = bufferSize;
	}


	public String getIsLongConnection() {
		return isLongConnection;
	}


	public void setIsLongConnection(String isLongConnection) {
		this.isLongConnection = isLongConnection;
	}
	

}

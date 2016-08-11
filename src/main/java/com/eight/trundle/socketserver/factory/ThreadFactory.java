package com.eight.trundle.socketserver.factory;

import java.lang.reflect.Constructor;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;
import org.apache.log4j.Logger;

/**
 * 线程工厂
 * @author weijl
 *
 */
public class ThreadFactory {
	private static Logger logger = Logger.getLogger(ThreadFactory.class);

	/**
	 * 产生TCP连接的服务端
	 * @param className
	 * @param socket
	 * @return
	 */
	synchronized public static Object getInstance(String className,Socket socket,String isLongConnection) {
		Object object = null;
		try {
			Class c = Class.forName(className);
			Constructor factory = c.getDeclaredConstructor(new Class[]{Socket.class,String.class});
			object = factory.newInstance(new Object[]{socket,isLongConnection});
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return object;
	}
	/**
	 * 产生UDP连接的服务端
	 * @param className
	 * @param server
	 * @param packet
	 * @return
	 */	
	synchronized public static Object getUDPInstance(String className,DatagramSocket server,DatagramPacket packet) {
		Object object = null;
		try {
			Class c = Class.forName(className);
			Constructor factory = c.getDeclaredConstructor(new Class[]{DatagramSocket.class,DatagramPacket.class});
			object = factory.newInstance(new Object[]{server,packet});
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return object;
	}	

}

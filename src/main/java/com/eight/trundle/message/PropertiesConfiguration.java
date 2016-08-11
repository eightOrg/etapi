package com.eight.trundle.message;

import java.io.IOException;
import java.util.*;
import java.io.*;

import org.apache.log4j.Logger;

/**
 * 配置文件读取创建类
 * @author weijl
 */
public class PropertiesConfiguration extends Configuration {
	
	private Logger logger = Logger.getLogger(PropertiesConfiguration.class);
	
	/**
	 * CLASSPATH
	 */
	private static final String CLASSPATH = Path.getClasspath();
	private static final String MESSAGECONFIG_PROPERTES = "messageconfig.properties";
	private static final String KEY_BASEPATH = "basepath";
	public static String BASE_PATH = "messages";
	
	
	private static Map map = new HashMap();

	private static Map fileTime = new HashMap();
	static {
		File file = new File(CLASSPATH + System.getProperty("file.separator")
				+ MESSAGECONFIG_PROPERTES);
		if (file != null) {
			InputStream fis = null;
			try {
				fis = new FileInputStream(file);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
				fis = null;
			}
			Properties pro = new Properties();
			try {
				if (fis != null) {
					pro.load(fis);
					BASE_PATH = pro.getProperty(KEY_BASEPATH);
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}


	/*
	 * 获取信息
	 * 
	 * @see cn.simpleroom.simple.message.Configuration#getMessages()
	 */
	public Map getMessages() throws IOException {
		File file = new File(CLASSPATH + System.getProperty("file.separator")
				+ BASE_PATH);
		File[] files = file.listFiles();

		if ((files == null) || (files.length == 0)) {
			//System.err.println("can't found the message folder '" + BASE_PATH + "'");
			logger.error("can't found the message folder '" + BASE_PATH + "'");
			return map;
		}

		for (int i = 0; i < files.length; i++) {
			file = files[i];

			if (file.getName().indexOf(".properties") != -1) {

				//判断是否reload
				if (fileTime.get(file.getName()) == null) {
					//System.out.println("init");
					fileTime.put(file.getName(), new Long(file.lastModified()));
					InputStream fis = new FileInputStream(file);
					Properties pro = new Properties();
					pro.load(fis);
					map.putAll(pro);
				} else {
					if (file.lastModified() != ((Long) fileTime.get(file
							.getName())).longValue()) {
						//reload
						InputStream fis = new FileInputStream(file);
						Properties pro = new Properties();
						pro.load(fis);
						map.putAll(pro);
						fileTime.put(file.getName(), new Long(file
								.lastModified()));
						//System.out.println("messages has been updated ! reload messages");
						logger.info("messages has been updated ! reload messages");
					}
				}

			}
		}

		return map;
	}
	
	

}

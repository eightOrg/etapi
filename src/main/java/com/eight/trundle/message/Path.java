package com.eight.trundle.message;

import java.net.URL;

/**
 * 配置文件读取路径类
 * @author weijl
 */
public class Path {
	
	public static String getClasspath() {
		URL url = Path.class.getClassLoader().getResource(
				"messages/config.properties");
		if (url == null) {
			System.err
					.println("can't found the file config.properties under the classes/");
		}
		String path = url.getFile();
		//去掉空格
		path = path.replaceAll("%20", " ");

		path = path.substring(0, path.indexOf("/messages"));

		return path;
	}

	public static String getClassPath() {
		return Path.class.getClassLoader().getResource(
				"com/hesc/trundle/message/Path.class").getPath();
	}

	public static void main(String args[]) {
		
	}

}

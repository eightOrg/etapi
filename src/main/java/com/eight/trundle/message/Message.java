package com.eight.trundle.message;

import java.io.*;
import java.util.*;

/**
 * 操作配置文件类，主要是读取
 * @author weijl
 */
public class Message {
	/**
	 * 判断是否reload配置
	 */
	private static boolean isreload = true;
	/**
	 * 读取文件时候的编码
	 */
	private static final String ZH_ENCODE = "UTF-8";
	/**
	 * 消息池
	 */
	private static Map messages = null;

	/**
	 * init the messages
	 */
	static {
		init();
	}

	/**
	 * 初始化消息
	 */
	private static void init() {
		try {
			messages = Configuration.getDefaultConfiguration().getMessages();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 默认的locale
	 */
	private Locale locale = Locale.getDefault();

	/**
	 * 构造函数
	 * @param locale Locale
	 */
	public Message(Locale locale) {
		if (locale != null) {
			this.locale = locale;
		}

	}

	/**
	 * get the message from specified key and locale
	 * @param key String
	 * @param locale Locale
	 * @return String
	 */
	public String get(String key, Locale locale) {
		return getMessage(key, locale);
	}

	/**
	 *
	 * @param key String
	 * @param param1 String
	 * @return String
	 */
	public String get(String key, String param1) {
		return get(key, param1, null);
	}

	/**
	 *
	 * @param key String
	 * @param param1 String
	 * @param param2 String
	 * @return String
	 */
	public String get(String key, String param1, String param2) {
		return get(key, param1, param2, null);
	}

	/**
	 *
	 * @param key String
	 * @param param1 String
	 * @param param2 String
	 * @param param3 String
	 * @return String
	 */
	public String get(String key, String param1, String param2, String param3) {
		String[] params = { param1, param2, param3 };

		return get(key, params);
	}

	/**
	 *
	 * @param key String
	 * @param params String[]
	 * @return String
	 */
	public String get(String key, String[] params) {
		String value = get(key);
		if (params != null) {
			for (int i = 0; i < params.length; i++) {
				if (params[i] != null) {
					value = value.replaceAll("(" + i + ")", params[i]);
				}
			}
		}
		return value;
	}

	/**
	 *
	 * @param key String
	 * @return String
	 */
	public String get(String key) {
		return get(key, locale);
	}

	/**
	 * 根据key来获取消息值
	 * @param key String
	 * @return String
	 */
	public static String getMessage(String key) {
		return getMessage(key, Locale.getDefault());
	}

	/**
	 * 根据key来获取消息值
	 * @param key String
	 * @param param1 String 第一个替换变量
	 * @return String
	 */
	public static String getMessage(String key, String param1) {
		return getMessage(key, param1, null);
	}

	/**
	 * 根据key来获取消息值<br>
	 * error1=(0) is (1) error
	 * @param key String
	 * @param param1 String 第一个替换变量(0)
	 * @param param2 String 第二个替换变量(1)
	 * @return String
	 */
	public static String getMessage(String key, String param1, String param2) {
		return getMessage(key, param1, param2, null);
	}

	/**
	 * 根据key来获取消息值
	 * @param key String
	 * @param param1 String 第一个替换变量
	 * @param param2 String 第二个替换变量
	 * @param param3 String 第三个替换变量
	 * @return String
	 */
	public static String getMessage(String key, String param1, String param2,
			String param3) {
		String[] params = { param1, param2, param3 };

		return getMessage(key, params);
	}

	/**
	 * @param key String
	 * @param params String[] 替换数组
	 * @return String
	 */
	public static String getMessage(String key, String[] params) {
		String value = getMessage(key);
		if (params != null) {
			for (int i = 0; i < params.length; i++) {
				if (params[i] != null) {
					value = value.replaceAll("\\(" + i + "\\)", params[i]);
				}
			}
		}
		return value;
	}

	/**
	 * 输入key 和local获取消息属性
	 * @param key String
	 * @param locale Locale
	 * @return String
	 */
	public static String getMessage(String key, Locale locale) {
		String rs = processMessage(key, locale);
		rs = parseValue(rs);
		return rs;
	}

	private static String processMessage(String key, Locale locale) {
		if (locale == null) {
			locale = Locale.getDefault();
		}

		if (isreload) {
			init();
		}
		String rs = "?" + key + "?";

		if (messages != null) {
			String value = null;
			if (locale.equals(Locale.getDefault())) {
				value = (String) messages.get(key);
				if (value == null) {
					value = (String) messages.get(locale.getLanguage() + "."
							+ key);
				}

			} else {
				value = (String) messages.get(locale.getLanguage() + "." + key);
			}

			if (value != null) {				
				try {					
					String tvalue = new String(value.getBytes("ISO8859-1"),
							ZH_ENCODE);
					if (tvalue.length() != value.length()) {
						value = tvalue;
					}					
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}

				return value;
			}
		}
		return rs;
	}

	/**
	 * 将ab${value}c - >abcvaluec
	 * @param value String
	 * @return String
	 */
	private static String parseValue(String value) {
		if (value == null)
			return null;
		value = value.replaceAll("\\$\\{", "!@");
		value = value.replaceAll("\\}", "@!");
		int indexs = value.indexOf("!@");
		if (indexs != -1) {
			int indexe = value.indexOf("@!", indexs);
			if (indexe != -1 && indexs < indexe) {
				String tv = value.substring(indexs + 2, indexe);
				String tv1 = getMessage(tv);
				tv1 = tv1.replaceAll("\\$\\{", "!@");
				tv1 = tv1.replaceAll("\\}", "@!");

				//String tp = "\\$\\{"+tv+"\\}";
				value = value.replaceAll("!@" + tv + "@!", tv1);

				value = parseValue(value);
			}
		}
		return value;
	}

	//获取config.properties中配置属性,允许default
	public static String getConfig(String key, String defaultValue) {
		if (null == key) {
			return defaultValue;
		}
		String value = getMessage(key);
		if (value.startsWith("?")) {
			return defaultValue;
		}
		return value;
	}

	/**
	 *
	 * @return Map
	 */
	public static Map getMessages() {
		return messages;
	}

	/**
	 *
	 * @param key String
	 * @param message String
	 */
	public static void putMessages(String key, String message) {
		messages.put(key, message);
	}

	/**
	 *
	 * @param args String[]
	 */
	public static void main(String[] args) {		

	}

}

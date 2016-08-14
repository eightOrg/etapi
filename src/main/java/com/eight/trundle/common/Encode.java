package com.eight.trundle.common;

import java.io.UnsupportedEncodingException;

/**
 * 转换编码类
 * @author weijl
 */
public class Encode {
	
	public static String isoToGbk(String s) {
		if (s == null)
			return null;
		try {
			byte[] c = s.getBytes("ISO8859-1");
			String tm = new String(c, "GBK");
			if (tm.length() < s.length()) {
				return tm;
			} else {
				return s;
			}
		} catch (UnsupportedEncodingException ex) {
			return "";
		}

	}
	
	public static String gbkToIso(String s) {
		if (s == null)
			return null;
		try {
			byte[] c = s.getBytes("GBK");
			String tm = new String(c, "ISO8859-1");
			if (tm.length() < s.length()) {
				return tm;
			} else {
				return s;
			}
		} catch (UnsupportedEncodingException ex) {
			return "";
		}

	}

	public static String isoToUTF8(String s) {
		if (s == null)
			return null;
		try {
			byte[] c = s.getBytes("ISO8859-1");
			String tm = new String(c, "UTF-8");
			if (tm.length() < s.length()) {
				return tm;
			} else {
				return s;
			}
		} catch (UnsupportedEncodingException ex) {
			return "";
		}

	}

	public static String utf8ToGbk(String s) {
		if (s == null)
			return null;
		try {
			byte[] c = s.getBytes("UTF-8");
			String tm = new String(c, "GBK");

			return tm;
		} catch (UnsupportedEncodingException ex) {
			return "";
		}
	}
}

package com.eight.trundle;
import java.util.TreeMap;

/**
 * 系统常量类，用以保存系统中经常用到的常量值
 * 经常被工程实际使用的Constants类继承
 * @author weijl
 */
public class Constants {
	//常量
	public static final int PORT = 8000;
	public static final String ROUTE_REFLECTIONS = "com.eight.controller";
	public static final String CONSTANTROOT = "root";
	//状态
	public static final TreeMap<String, String> STATEMAP = new TreeMap<String, String>();
	public static final String STATE_OK = "0";//正常
	public static final String STATE_DELETE = "1";//删除
	public static final String STATE_LOCKED = "2";//锁定
	//session里保存用户对象
	public static final String SESSION_USER = "SESSION_USER";
	//session里保存用户的资源
	public static final String SESSION_USER_RESOURSE = "SESSION_USER_RESOURSE";
	//session里保存用户的角色
	public static final String SESSION_USER_ROLE = "SESSION_USER_ROLE";
	//cookie里保存的用户对象ID
	public static String COOKUSERID = "COOKUSERID";
	//session保存验证码ID
	public static final String VALIDATE_CODE_ID = "VALIDATE_CODE_ID";
	//url返回值代码参数
	public static final String URL_RETURN_KEY = "urk";
	//url返回值代码参数集合
	public static final String URL_RETURN_PAM = "pam";
	//request中返回值key
	public static final String RETURN_KEY = "rkey";
	//选择时是与否
	public static final String CONSTNATS_YES = "1";//是
	public static final String CONSTNATS_NO = "0";//否
	//上传图片存放地址和访问地址
	public static final String IMAGEUPLOADPATH = "IMAGEUPLOADPATH";//存放地址 
	public static final String IMAGEUPLOADURL = "IMAGEUPLOADURL";//访问地址
	
	static{
		//状态
		STATEMAP.put(STATE_OK, "正常");
		STATEMAP.put(STATE_LOCKED, "锁定");
		STATEMAP.put(STATE_DELETE, "删除");
	}

}

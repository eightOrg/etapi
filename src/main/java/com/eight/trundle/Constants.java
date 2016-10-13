package com.eight.trundle;
import java.util.TreeMap;

/**
 * 系统常量类，用以保存系统中经常用到的常量值
 * 经常被工程实际使用的Constants类继承
 * @author weijl
 */
public class Constants {
	//vertx常量
	public static final int PORT = 8888; //监听端口
	public static final String ROUTE_REFLECTIONS = "com.eight.controller"; //扫描位置注册vertx的服务
    //JsonObject常量
    public static final int DEFAULT_LIMIT = 10; //DEFAULT_LIMIT
    public static final int DEFAULT_PAGE = 1; //DEFAULT_PAGE
    //ob
	public static final String RESULT_OB_FLAG = "flag"; //flag
	public static final String RESULT_OB_MSG = "msg"; //msg
	public static final String RESULT_OB_CODE = "code"; //code
	public static final String RESULT_OB_OB = "ob"; //ob
    //params
    public static final String PARAMS_OBJLIST = "objList";//传入列表
    public static final String PARAMS_IDLIST = "idList";//传入id列表
    public static final String PARAMS_LIMIT = "limit";//select_limit
    public static final String PARAMS_PAGE = "page";//select_page
	//服务常量
	public static final String SERVICE_SYSTEM = ""; //系统服务 不需要session即可调用
	public static final String SERVICE_USER = "/user"; //user服务
	public static final String SERVICE_DEMO = "/demo"; //demo服务
	//pojo常量
	public static final String POJO_ID = "id";
	public static final String POJO_STATE = "state";
	public static final String POJO_CREATETIME = "createTime";
	public static final String POJO_CHANGETIME = "changeTime";
	//状态
	public static final TreeMap<String, String> STATEMAP = new TreeMap<String, String>();
	public static final String STATE_OK = "0";//正常
	public static final String STATE_DELETE = "1";//删除
	public static final String STATE_LOCKED = "2";//锁定
	//服务器响应code
	public static final TreeMap<Integer, String> CODEMAP = new TreeMap<Integer, String>();
	public static final int CODE_OK = 200;//正常
	public static final int CODE_LOSE = 403;//验证失败
	public static final int CODE_FAILD = 500;//请求失败
	static{
		//状态
		STATEMAP.put(STATE_OK, "正常");
		STATEMAP.put(STATE_LOCKED, "锁定");
		STATEMAP.put(STATE_DELETE, "删除");
		//服务器响应code
		CODEMAP.put(CODE_OK, "请求成功");
		CODEMAP.put(CODE_LOSE, "验证失败");
		CODEMAP.put(CODE_FAILD, "请求失败");
	}

}

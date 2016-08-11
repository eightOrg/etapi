package com.eight.trundle.funmonitor;

public class FunItem implements Comparable<FunItem>{
	
	//方法名
	private String fun;
	//调用参数
	private String param;
	//调用IP
	private String ip;
	//调用时间
	private long createtime;
	//耗时
	private Long time;
	
	
	
	public FunItem(String fun, String param, String ip, long createtime,
			long time) {
		super();
		this.fun = fun;
		this.param = param;
		this.ip = ip;
		this.createtime = createtime;
		this.time = time;
	}
	public String getFun() {
		return fun;
	}
	public void setFun(String fun) {
		this.fun = fun;
	}
	public String getParam() {
		return param;
	}
	public void setParam(String param) {
		this.param = param;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public long getCreatetime() {
		return createtime;
	}
	public void setCreatetime(long createtime) {
		this.createtime = createtime;
	}
	public Long getTime() {
		return time;
	}
	public void setTime(Long time) {
		this.time = time;
	}
	
	@Override
	public int compareTo(FunItem arg0) {
		return arg0.getTime().compareTo(this.getTime());
	}
	
	

}

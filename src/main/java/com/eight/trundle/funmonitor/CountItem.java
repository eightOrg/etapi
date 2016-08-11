package com.eight.trundle.funmonitor;

import java.util.List;


public class CountItem implements Comparable<CountItem>{
	
	private String fun;
	private Integer count;
	private long max;
	private long min;
	private long avr;
	
	
	public String getFun() {
		return fun;
	}
	public void setFun(String fun) {
		this.fun = fun;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public long getMax() {
		return max;
	}
	public void setMax(long max) {
		this.max = max;
	}
	public long getMin() {
		return min;
	}
	public void setMin(long min) {
		this.min = min;
	}
	public long getAvr() {
		return avr;
	}
	public void setAvr(long avr) {
		this.avr = avr;
	}
	
	@Override
	public int compareTo(CountItem arg0) {
		return arg0.getCount().compareTo(this.getCount());
	}
	
	

}

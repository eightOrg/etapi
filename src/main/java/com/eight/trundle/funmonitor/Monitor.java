package com.eight.trundle.funmonitor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

import com.eight.trundle.message.Message;

public class Monitor {
	
	//队列长度
	private static int COUNTSIZE = Integer.valueOf(Message.getConfig("fun_monitor_countsize", "100000"));
	//初始过滤，只有当执行时间大于该值的数字才列入监控
	private static long INITTIME = Long.valueOf(Message.getConfig("fun_monitor_inittime", "500"));
	//所有历史记录
	private static Queue<FunItem> HISTORY = new ArrayBlockingQueue<FunItem>(COUNTSIZE);
	
	//统计概况
	private static List<CountItem> countList = new ArrayList<CountItem>();
	//历史记录
	private static List<FunItem> historyList = new ArrayList<FunItem>();
	
	/**
	 * 增加记录
	 * @param item
	 */
	public static void add(FunItem item){
		if(item.getTime() <= INITTIME) return;
		if(!HISTORY.offer(item)){
			HISTORY.poll();
			Monitor.add(item);
		}
	}
	
	/**
	 * 获取展示概况
	 * @return
	 */
	public static void flushData(){
		countList.clear();
		historyList.clear();
		Map<String,CountItem> counts = new HashMap<String,CountItem>();
		Queue<FunItem> tempqueue = new ArrayBlockingQueue<FunItem>(COUNTSIZE);
		tempqueue.addAll(HISTORY);
		FunItem funItem = null;
		while((funItem = tempqueue.poll()) != null){
			historyList.add(funItem);
			if(counts.keySet().contains(funItem.getFun())){
				CountItem count = counts.get(funItem.getFun());
				count.setAvr( (count.getAvr() * count.getCount() + funItem.getTime()) / (count.getCount() + 1) );
				if(funItem.getTime() > count.getMax()){
					count.setMax(funItem.getTime());
				}else if(funItem.getTime() < count.getMin()){
					count.setMin(funItem.getTime());
				}
				count.setCount(count.getCount() + 1);
			}else{
				CountItem count = new CountItem();
				count.setFun(funItem.getFun());
				count.setCount(1);
				count.setAvr(funItem.getTime());
				count.setMax(funItem.getTime());
				count.setMin(funItem.getTime());
				counts.put(funItem.getFun(), count);
			}
		}
		
		Iterator<String> it = counts.keySet().iterator();
		while(it.hasNext()){
			countList.add(counts.get(it.next().toString()));
		}
		Collections.sort(countList);
		
	}

	public static List<CountItem> getCountList() {
		return countList;
	}

	public static List<FunItem> getHistoryList() {
		return historyList;
	}
	
	public static long getINITTIME() {
		return INITTIME;
	}
	

	public static int getCOUNTSIZE() {
		return COUNTSIZE;
	}

	/**
	 * 初始化队列
	 * @param countSize 队列长度
	 * @param initTime 初始过滤，只有当执行时间大于该值的数字才列入监控
	 */
	public static void initQueue(int countSize, long initTime){
		COUNTSIZE = countSize;
		INITTIME = initTime;
		HISTORY = new ArrayBlockingQueue<FunItem>(COUNTSIZE);
	}
	
	
	
	
	

}

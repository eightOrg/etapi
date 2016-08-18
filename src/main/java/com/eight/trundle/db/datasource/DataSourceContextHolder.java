package com.eight.trundle.db.datasource;


public class DataSourceContextHolder {

	@SuppressWarnings("rawtypes")
	private static final ThreadLocal contextHolder = new ThreadLocal();// 线程本地环境
	
	/**
	 * 设置数据源类型
	 * @param dataSourceType
	 */
	@SuppressWarnings("unchecked")
	public static void setDataSourceType(String dataSourceType) { 
		 contextHolder.set(dataSourceType); 
	 } 
	
	/**
	   * 　获取当前数据源类型 
	 * @return
	 */
	public static String getDataSourceType() { 
	    return (String) contextHolder.get();
	} 
	
	/**
	 * 清除数据源类型 
	 */
	public static void clearDataSourceType () { 
	    contextHolder.remove();
	}
	

}

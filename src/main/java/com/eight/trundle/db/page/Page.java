package com.eight.trundle.db.page;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 分页结果
 * @author weijl
 */
public class Page<T> {
	//查询总条数
	private long count = 0;
	//当前第几页
	private int curPage = 1;
	//每页多少条
	private int pageSize = 10;
	//当前页列表
	private List<T> queryList;
	//总页数
	private int pageCount = 0;
	//是否需要计算总条数，默认计算
	private boolean executeCount = true;
	//结果页跳转ID
	private String target = "";
	//其他的参数
	private Map<String, Object> params = new HashMap<String, Object>();
	
	
	public long getCount() {
		return count;
	}
	public void setCount(long count) {		
		this.count = count;
		int pageCount = (int) (count % pageSize == 0 ? count / pageSize : count / pageSize + 1);  
	    this.setPageCount(pageCount);
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public List<T> getQueryList() {
		return queryList;
	}
	public void setQueryList(List<T> queryList) {
		this.queryList = queryList;
	}
	public int getCurPage() {
		return curPage;
	}
	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}	
	public int getPageCount() {
		return pageCount;
	}
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	public Map<String, Object> getParams() {
		return params;
	}
	public void setParams(Map<String, Object> params) {
		this.params = params;
	}
	public boolean getExecuteCount() {
		return executeCount;
	}
	public void setExecuteCount(boolean executeCount) {
		this.executeCount = executeCount;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	/**
	 * 获取分页脚本
	 * @param postUrl 
	 * @return string <上一页>...567...<下一页><共几条><分几页>
	 */
	public String printPageString(String postUrl){
		return PageHtml.printPageString(postUrl,target, String.valueOf(count).toString(), String.valueOf(curPage).toString(), String.valueOf(pageSize).toString());
	}
	/**
	 * 获取分页脚本
	 * @param postUrl 
	 * @param curpageTag 分页标识
	 * @return string <上一页>...567...<下一页><共几条><分几页>
	 */
	public String printPageString(String postUrl,String curpageTag){
		return PageHtml.printPageString(postUrl,target, String.valueOf(count).toString(), String.valueOf(curPage).toString(), String.valueOf(pageSize).toString(),curpageTag);
	}
	
	/**
	 * 获取分页脚本
	 * @param postUrl 
	 * @return string <上一页>...567...<下一页>
	 */
	public String printPageStr(String postUrl){
		return PageHtml.printPageStr(postUrl,target, String.valueOf(count).toString(), String.valueOf(curPage).toString(), String.valueOf(pageSize).toString());
	}
	/**
	 * 获取分页脚本
	 * @param postUrl 
	 * @param curpageTag 分页标识
	 * @return string <上一页>...567...<下一页>
	 */
	public String printPageStr(String postUrl,String curpageTag){
		return PageHtml.printPageStr(postUrl,target, String.valueOf(count).toString(), String.valueOf(curPage).toString(), String.valueOf(pageSize).toString(),curpageTag);
	}
	
	/**
	 * 获取分页脚本
	 * @param postUrl 
	 * @return string < ...567... >
	 */
	public String printSimPageStr(String postUrl){
		return PageHtml.printSimPageStr(postUrl,target, String.valueOf(count).toString(), String.valueOf(curPage).toString(), String.valueOf(pageSize).toString());
	}
	/**
	 * 获取分页脚本
	 * @param postUrl 
	 * @param curpageTag 分页标识
	 * @return string < ...567... >
	 */
	public String printSimPageStr(String postUrl,String curpageTag){
		return PageHtml.printSimPageStr(postUrl,target, String.valueOf(count).toString(), String.valueOf(curPage).toString(), String.valueOf(pageSize).toString(),curpageTag);
	}
	
	/**
	 * 获取分页脚本
	 * @param postUrl 
	 * @return string <首页><上一页>...567...<下一页><尾页>
	 */
	public String printPageString2(String postUrl){
		return PageHtml.printPageString2(postUrl,target, String.valueOf(count).toString(), String.valueOf(curPage).toString(), String.valueOf(pageSize).toString());
	}
	/**
	 * 获取分页脚本
	 * @param postUrl 
	 * @param curpageTag 分页标识
	 * @return string <首页><上一页>...567...<下一页><尾页>
	 */
	public String printPageString2(String postUrl,String curpageTag){
		return PageHtml.printPageString2(postUrl,target, String.valueOf(count).toString(), String.valueOf(curPage).toString(), String.valueOf(pageSize).toString(),curpageTag);
	}
	
	/**
	 * 获取分页脚本
	 * @param postUrl 
	 * @return string << < ...567... > >>
	 */
	public String printSimPageString2(String postUrl){
		return PageHtml.printSimPageString2(postUrl,target, String.valueOf(count).toString(), String.valueOf(curPage).toString(), String.valueOf(pageSize).toString());
	}
	/**
	 * 获取分页脚本
	 * @param postUrl 
	 * @param curpageTag 分页标识
	 * @return string << < ...567... > >>
	 */
	public String printSimPageString2(String postUrl,String curpageTag){
		return PageHtml.printSimPageString2(postUrl,target, String.valueOf(count).toString(), String.valueOf(curPage).toString(), String.valueOf(pageSize).toString(),curpageTag);
	}
	
	
	@Override
	public String toString() {
		return "Page [count=" + count + ", curPage=" + curPage + ", pageSize="
				+ pageSize  + ", pageCount=" + pageCount + ", params=" + params + ", queryList=" + queryList + "]";
	}
	
//	public static void main(String[] arg){
//		Page page = new Page();
//		page.setCurPage(8);
//		page.setCount(230l);
//		System.out.println(page.printSimPageStr("baidu.com"));
//		
//	}

}

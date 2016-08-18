package com.eight.trundle.db.page;

import com.mysql.jdbc.StringUtils;

/**
 * html操作类,主要功能是分页页码
 * @author weijl
 */
public class PageHtml {
	// 默认的当前页字符串
	private static String CURPAGE_TAG = "curpage";

	/**
	 * 检查当前页的页码
	 * @param curpage
	 * @return
	 */
	public static String checkCurPage(String curpage){
		if(curpage == null || curpage.trim().length() == 0){
			curpage = "1";
		}else{
			try{
				Integer.parseInt(curpage);
			}catch(Exception ex){
				curpage = "1";
			}
		}
		return curpage;
	}
	
	/**
	 * 打印分页页码栏
	 * 
	 * @param postUrl
	 *            链接地址
	 * @param count
	 *            记录总数
	 * @param curPage
	 *            当前页码
	 * @param pageSize
	 *            单页显示记录数
	 * @return <上一页>...567...<下一页><共几条><分几页>
	 */
	public static String printPageString(String postUrl,String target, String count,
			String curPage, String pageSize) {
		return printPageString(postUrl, target, count, curPage, pageSize,
				CURPAGE_TAG);
	}

	/**
	 * 打印分页页码栏
	 * 
	 * @param postUrl
	 *            链接地址
	 * @param count
	 *            记录总数
	 * @param curPage
	 *            当前页码
	 * @param pageSize
	 *            单页显示记录数
	 * @param curpageTag
	 *            当前页URL参数名 /xxx.jsp?curpage=1
	 * @return <上一页>...567...<下一页><共几条><分几页>
	 */
	public static String printPageString(String postUrl,String target, String count,
			String curPage, String pageSize, String curpageTag) {
		if ("0".equals(count))
			return "";// 若记录总数为0，则无需PageBottom了
		int totalsize = Integer.parseInt(count);
		int curpage = Integer.parseInt(curPage);
		int pagesize = Integer.parseInt(pageSize);
		if (postUrl.indexOf("?") >= 0)
			postUrl += "&";
		else
			postUrl += "?";
		int totalpage = (totalsize - 1) / pagesize + 1;
		int curtenpage = (curpage - 1) / 10;

		StringBuffer bottomLink = new StringBuffer();
		if ("1".equals(curPage)) {// 如果当前页是第一页
			bottomLink.append("<li><a>上一页</a></li>");
		} else {// 如果当前页不是第一页
			bottomLink.append("<li><a onclick='return getPageBtn(this);' href='");
			bottomLink.append(postUrl);
			bottomLink.append(curpageTag);
			bottomLink.append("=");
			bottomLink.append(String.valueOf(curpage - 1));
			bottomLink.append("'");
			if(!StringUtils.isNullOrEmpty(target)){
				bottomLink.append(" target='");
				bottomLink.append(target);
				bottomLink.append("'");
			}
			bottomLink.append(">上一页</a></li>");
		}
		// 增加当前页前面的页数
		int startpage = curpage - 5;
		if (startpage < 1) {
			startpage = 1;
		} else {
			bottomLink.append("<li><a>...</a></li>");
		}
		for (int i = startpage; i < curpage; i++) {
			bottomLink.append("<li><a onclick='return getPageBtn(this);' href='");
			bottomLink.append(postUrl);
			bottomLink.append(curpageTag);
			bottomLink.append("=");
			bottomLink.append(String.valueOf(i));
			bottomLink.append("'");
			if(!StringUtils.isNullOrEmpty(target)){
				bottomLink.append(" target='");
				bottomLink.append(target);
				bottomLink.append("'");
			}
			bottomLink.append(">");
			bottomLink.append(String.valueOf(i));
			bottomLink.append("</a></li>");
		}
		// 加入当前页
		bottomLink.append("<li class='active'><a>");
		bottomLink.append(String.valueOf(curpage));
		bottomLink.append("</a></li>");
		// 加入当前页后面的页数
		int endpage = curpage + 5;
		if (endpage > totalpage) {
			endpage = totalpage;
		}
		for (int i = curpage + 1; i <= endpage; i++) {
			bottomLink.append("<li><a onclick='return getPageBtn(this);' href='");
			bottomLink.append(postUrl);
			bottomLink.append(curpageTag);
			bottomLink.append("=");
			bottomLink.append(String.valueOf(i));
			bottomLink.append("'");
			if(!StringUtils.isNullOrEmpty(target)){
				bottomLink.append(" target='");
				bottomLink.append(target);
				bottomLink.append("'");
			}
			bottomLink.append(">");
			bottomLink.append(String.valueOf(i));
			bottomLink.append("</a></li>");
		}
		if (endpage < totalpage) {
			bottomLink.append("<li><a>...</a></li>");
		}
		// 最后一页
		if (totalpage == curpage) {// 如果当前页是最后一页
			bottomLink.append("<li><a>下一页</a></li>");
		} else {
			bottomLink.append("<li><a onclick='return getPageBtn(this);' href='");
			bottomLink.append(postUrl);
			bottomLink.append(curpageTag);
			bottomLink.append("=");
			bottomLink.append(String.valueOf(curpage + 1));
			bottomLink.append("'");
			if(!StringUtils.isNullOrEmpty(target)){
				bottomLink.append(" target='");
				bottomLink.append(target);
				bottomLink.append("'");
			}
			bottomLink.append(">下一页</a></li>");
		}
		bottomLink.append("<li><a>共");
		bottomLink.append(totalsize);
		bottomLink.append("条</a></li>");
		bottomLink.append("<li><a>分");
		bottomLink.append(totalpage);
		bottomLink.append("页</a></li>");
		return bottomLink.toString();
	}

	/**
	 * 打印分页页码栏(简洁，没有首尾页)
	 * 
	 * @param postUrl
	 *            链接地址
	 * @param count
	 *            记录总数
	 * @param curPage
	 *            当前页码
	 * @param pageSize
	 *            单页显示记录数
	 * @return <上一页>...567...<下一页>
	 */
	public static String printPageStr(String postUrl,String target, String count,
			String curPage, String pageSize) {
		return printPageStr(postUrl,target, count, curPage, pageSize,
				CURPAGE_TAG);
	}

	/**
	 * 打印分页页码栏(简洁，没有首尾页)
	 * 
	 * @param postUrl
	 *            链接地址
	 * @param count
	 *            记录总数
	 * @param curPage
	 *            当前页码
	 * @param pageSize
	 *            单页显示记录数
	 * @param curpageTag
	 *            当前页URL参数名 /xxx.jsp?curpage=1
	 * @return  <上一页>...567...<下一页>
	 */
	public static String printPageStr(String postUrl,String target, String count,
			String curPage, String pageSize, String curpageTag) {
		if ("0".equals(count))
			return "";// 若记录总数为0，则无需PageBottom了
		int totalsize = Integer.parseInt(count);
		int curpage = Integer.parseInt(curPage);
		int pagesize = Integer.parseInt(pageSize);
		if (postUrl.indexOf("?") >= 0)
			postUrl += "&";
		else
			postUrl += "?";
		int totalpage = (totalsize - 1) / pagesize + 1;
		int curtenpage = (curpage - 1) / 10;

		StringBuffer bottomLink = new StringBuffer();
		if ("1".equals(curPage)) {// 如果当前页是第一页
			bottomLink.append("<li><a>上一页</a></li>");
		} else {// 如果当前页不是第一页
			bottomLink.append("<li><a onclick='return getPageBtn(this);' href='");
			bottomLink.append(postUrl);
			bottomLink.append(curpageTag);
			bottomLink.append("=");
			bottomLink.append(String.valueOf(curpage - 1));
			bottomLink.append("'");
			if(!StringUtils.isNullOrEmpty(target)){
				bottomLink.append(" target='");
				bottomLink.append(target);
				bottomLink.append("'");
			}
			bottomLink.append(">上一页</a></li>");
		}
		// 增加当前页前面的页数
		int startpage = curpage - 5;
		if (startpage < 1) {
			startpage = 1;
		} else {
			bottomLink.append("<li><a>...</a></li>");
		}
		for (int i = startpage; i < curpage; i++) {
			bottomLink.append("<li><a onclick='return getPageBtn(this);' href='");
			bottomLink.append(postUrl);
			bottomLink.append(curpageTag);
			bottomLink.append("=");
			bottomLink.append(String.valueOf(i));
			bottomLink.append("'");
			if(!StringUtils.isNullOrEmpty(target)){
				bottomLink.append(" target='");
				bottomLink.append(target);
				bottomLink.append("'");
			}
			bottomLink.append(">");
			bottomLink.append(String.valueOf(i));
			bottomLink.append("</a></li>");
		}
		// 加入当前页
		bottomLink.append("<li class='active'><a>");
		bottomLink.append(String.valueOf(curpage));
		bottomLink.append("</a></li>");
		// 加入当前页后面的页数
		int endpage = curpage + 5;
		if (endpage > totalpage) {
			endpage = totalpage;
		}
		for (int i = curpage + 1; i <= endpage; i++) {
			bottomLink.append("<li><a onclick='return getPageBtn(this);' href='");
			bottomLink.append(postUrl);
			bottomLink.append(curpageTag);
			bottomLink.append("=");
			bottomLink.append(String.valueOf(i));
			bottomLink.append("'");
			if(!StringUtils.isNullOrEmpty(target)){
				bottomLink.append(" target='");
				bottomLink.append(target);
				bottomLink.append("'");
			}
			bottomLink.append(">");
			bottomLink.append(String.valueOf(i));
			bottomLink.append("</a></li>");
		}
		if (endpage < totalpage) {
			bottomLink.append("<li><a>...</a></li>");
		}
		// 最后一页
		if (totalpage == curpage) {// 如果当前页是最后一页
			bottomLink.append("<li><a>下一页</a></li>");
		} else {
			bottomLink.append("<li><a onclick='return getPageBtn(this);' href='");
			bottomLink.append(postUrl);
			bottomLink.append(curpageTag);
			bottomLink.append("=");
			bottomLink.append(String.valueOf(curpage + 1));
			bottomLink.append("'");
			if(!StringUtils.isNullOrEmpty(target)){
				bottomLink.append(" target='");
				bottomLink.append(target);
				bottomLink.append("'");
			}
			bottomLink.append(">下一页</a></li>");
		}
		return bottomLink.toString();
	}
	
	/**
	 * 打印分页页码栏(简洁，没有首尾页)
	 * 
	 * @param postUrl
	 *            链接地址
	 * @param count
	 *            记录总数
	 * @param curPage
	 *            当前页码
	 * @param pageSize
	 *            单页显示记录数
	 * @return < ...567... >
	 */
	public static String printSimPageStr(String postUrl,String target, String count,
			String curPage, String pageSize) {
		return printSimPageStr(postUrl,target, count, curPage, pageSize,
				CURPAGE_TAG);
	}

	/**
	 * 打印分页页码栏(简洁，没有首尾页)
	 * 
	 * @param postUrl
	 *            链接地址
	 * @param count
	 *            记录总数
	 * @param curPage
	 *            当前页码
	 * @param pageSize
	 *            单页显示记录数
	 * @param curpageTag
	 *            当前页URL参数名 /xxx.jsp?curpage=1
	 * @return  <...567...>
	 */
	public static String printSimPageStr(String postUrl,String target, String count,
			String curPage, String pageSize, String curpageTag) {
		if ("0".equals(count))
			return "";// 若记录总数为0，则无需PageBottom了
		int totalsize = Integer.parseInt(count);
		int curpage = Integer.parseInt(curPage);
		int pagesize = Integer.parseInt(pageSize);
		if (postUrl.indexOf("?") >= 0)
			postUrl += "&";
		else
			postUrl += "?";
		int totalpage = (totalsize - 1) / pagesize + 1;
		int curtenpage = (curpage - 1) / 10;

		StringBuffer bottomLink = new StringBuffer();
		if ("1".equals(curPage)) {// 如果当前页是第一页
			bottomLink.append("<li><a><</a></li>");
		} else {// 如果当前页不是第一页
			bottomLink.append("<li><a onclick='return getPageBtn(this);' href='");
			bottomLink.append(postUrl);
			bottomLink.append(curpageTag);
			bottomLink.append("=");
			bottomLink.append(String.valueOf(curpage - 1));
			bottomLink.append("'");
			if(!StringUtils.isNullOrEmpty(target)){
				bottomLink.append(" target='");
				bottomLink.append(target);
				bottomLink.append("'");
			}
			bottomLink.append("><</a></li>");
		}
		// 增加当前页前面的页数
		int startpage = curpage - 5;
		if (startpage < 1) {
			startpage = 1;
		} else {
			bottomLink.append("<li><a>...</a></li>");
		}
		for (int i = startpage; i < curpage; i++) {
			bottomLink.append("<li><a onclick='return getPageBtn(this);' href='");
			bottomLink.append(postUrl);
			bottomLink.append(curpageTag);
			bottomLink.append("=");
			bottomLink.append(String.valueOf(i));
			bottomLink.append("'");
			if(!StringUtils.isNullOrEmpty(target)){
				bottomLink.append(" target='");
				bottomLink.append(target);
				bottomLink.append("'");
			}
			bottomLink.append(">");
			bottomLink.append(String.valueOf(i));
			bottomLink.append("</a></li>");
		}
		// 加入当前页
		bottomLink.append("<li class='active'><a>");
		bottomLink.append(String.valueOf(curpage));
		bottomLink.append("</a></li>");
		// 加入当前页后面的页数
		int endpage = curpage + 5;
		if (endpage > totalpage) {
			endpage = totalpage;
		}
		for (int i = curpage + 1; i <= endpage; i++) {
			bottomLink.append("<li><a onclick='return getPageBtn(this);' href='");
			bottomLink.append(postUrl);
			bottomLink.append(curpageTag);
			bottomLink.append("=");
			bottomLink.append(String.valueOf(i));
			bottomLink.append("'");
			if(!StringUtils.isNullOrEmpty(target)){
				bottomLink.append(" target='");
				bottomLink.append(target);
				bottomLink.append("'");
			}
			bottomLink.append(">");
			bottomLink.append(String.valueOf(i));
			bottomLink.append("</a></li>");
		}
		if (endpage < totalpage) {
			bottomLink.append("<li><a>...</a></li>");
		}
		// 最后一页
		if (totalpage == curpage) {// 如果当前页是最后一页
			bottomLink.append("<li><a>></a></li>");
		} else {
			bottomLink.append("<li><a onclick='return getPageBtn(this);' href='");
			bottomLink.append(postUrl);
			bottomLink.append(curpageTag);
			bottomLink.append("=");
			bottomLink.append(String.valueOf(curpage + 1));
			bottomLink.append("'");
			if(!StringUtils.isNullOrEmpty(target)){
				bottomLink.append(" target='");
				bottomLink.append(target);
				bottomLink.append("'");
			}
			bottomLink.append(">></a></li>");
		}
		return bottomLink.toString();
	}
	
	/**
	 * 打印分页页码栏(简洁，有首尾页)
	 * 
	 * @param postUrl
	 *            链接地址
	 * @param count
	 *            记录总数
	 * @param curPage
	 *            当前页码
	 * @param pageSize
	 *            单页显示记录数
	 * @return  <首页><上一页>...567...<下一页><尾页>
	 */
	public static String printPageString2(String postUrl,String target, String count,
			String curPage, String pageSize) {
		return printPageString2(postUrl,target, count, curPage, pageSize,
				CURPAGE_TAG);
	}

	/**
	 * 打印分页页码栏(简洁,有首尾页),
	 * 
	 * @param postUrl
	 *            链接地址
	 * @param count
	 *            记录总数
	 * @param curPage
	 *            当前页码
	 * @param pageSize
	 *            单页显示记录数
	 * @param curpageTag
	 *            当前页URL参数名 /xxx.jsp?curpage=1
	 * @return <首页><上一页>...567...<下一页><尾页>
	 */
	public static String printPageString2(String postUrl,String target,
			String count, String curPage, String pageSize, String curpageTag) {
		if ("0".equals(count))
			return "";// 若记录总数为0，则无需PageBottom了
		int totalsize = Integer.parseInt(count);
		int curpage = Integer.parseInt(curPage);
		int pagesize = Integer.parseInt(pageSize);
		if (postUrl.indexOf("?") >= 0)
			postUrl += "&";
		else
			postUrl += "?";
		int totalpage = (totalsize - 1) / pagesize + 1;
		int curtenpage = (curpage - 1) / 10;

		StringBuffer bottomLink = new StringBuffer();
		if ("1".equals(curPage)) {// 如果当前页是第一页
			bottomLink.append("<li><a>上一页</a></li>");
		} else {// 如果当前页不是第一页
			bottomLink.append("<li><a onclick='return getPageBtn(this);' href='");
			bottomLink.append(postUrl);
			bottomLink.append(curpageTag);
			bottomLink.append("=1");
			bottomLink.append("'");
			if(!StringUtils.isNullOrEmpty(target)){
				bottomLink.append(" target='");
				bottomLink.append(target);
				bottomLink.append("'");
			}
			bottomLink.append(">首页</a></li>");
			bottomLink.append("<li><a onclick='return getPageBtn(this);' href='");
			bottomLink.append(postUrl);
			bottomLink.append(curpageTag);
			bottomLink.append("=");
			bottomLink.append(String.valueOf(curpage - 1));
			bottomLink.append("'");
			if(!StringUtils.isNullOrEmpty(target)){
				bottomLink.append(" target='");
				bottomLink.append(target);
				bottomLink.append("'");
			}
			bottomLink.append(">上一页</a></li>");
		}
		// 增加当前页前面的页数
		int startpage = curpage - 3;
		if (curpage == totalpage)
			startpage = curpage - 5;
		if (startpage < 1) {
			startpage = 1;
		} else {
			bottomLink.append("<li><a>...</a></li>");
		}
		for (int i = startpage; i < curpage; i++) {
			bottomLink.append("<li><a onclick='return getPageBtn(this);' href='");
			bottomLink.append(postUrl);
			bottomLink.append(curpageTag);
			bottomLink.append("=");
			bottomLink.append(String.valueOf(i));
			bottomLink.append("'");
			if(!StringUtils.isNullOrEmpty(target)){
				bottomLink.append(" target='");
				bottomLink.append(target);
				bottomLink.append("'");
			}
			bottomLink.append(">");
			bottomLink.append(String.valueOf(i));
			bottomLink.append("</a></li>");
		}
		// 加入当前页
		bottomLink.append("<li class='active'><a>");
		bottomLink.append(String.valueOf(curpage));
		bottomLink.append("</a></li>");
		// 加入当前页后面的页数
		int endpage = curpage + 3;
		if (endpage == 4)
			endpage = 6;
		if (endpage > totalpage) {
			endpage = totalpage;
		}
		for (int i = curpage + 1; i <= endpage; i++) {
			bottomLink.append("<li><a onclick='return getPageBtn(this);' href='");
			bottomLink.append(postUrl);
			bottomLink.append(curpageTag);
			bottomLink.append("=");
			bottomLink.append(String.valueOf(i));
			bottomLink.append("'");
			if(!StringUtils.isNullOrEmpty(target)){
				bottomLink.append(" target='");
				bottomLink.append(target);
				bottomLink.append("'");
			}
			bottomLink.append(">");
			bottomLink.append(String.valueOf(i));
			bottomLink.append("</a></li>");
		}
		if (endpage < totalpage) {
			bottomLink.append("<li><a>...</a></li>");
		}
		// 最后一页
		if (totalpage == curpage) {// 如果当前页是最后一页
			bottomLink.append("<li><a>下一页</a></li>");
		} else {
			bottomLink.append("<li><a onclick='return getPageBtn(this);' href='");
			bottomLink.append(postUrl);
			bottomLink.append(curpageTag);
			bottomLink.append("=");
			bottomLink.append(String.valueOf(curpage + 1));
			bottomLink.append("'");
			if(!StringUtils.isNullOrEmpty(target)){
				bottomLink.append(" target='");
				bottomLink.append(target);
				bottomLink.append("'");
			}
			bottomLink.append(">下一页</a></li>");
			bottomLink.append("<li><a onclick='return getPageBtn(this);' href='");
			bottomLink.append(postUrl);
			bottomLink.append(curpageTag);
			bottomLink.append("=");
			bottomLink.append(String.valueOf(totalpage));
			bottomLink.append("'");
			if(!StringUtils.isNullOrEmpty(target)){
				bottomLink.append(" target='");
				bottomLink.append(target);
				bottomLink.append("'");
			}
			bottomLink.append(">尾页</a></li>");
		}
		return bottomLink.toString();
	}
	
	/**
	 * 打印分页页码栏(简洁，有首尾页)
	 * 
	 * @param postUrl
	 *            链接地址
	 * @param count
	 *            记录总数
	 * @param curPage
	 *            当前页码
	 * @param pageSize
	 *            单页显示记录数
	 * @return  << < ...567... > >>
	 */
	public static String printSimPageString2(String postUrl,String target, String count,
			String curPage, String pageSize) {
		return printSimPageString2(postUrl,target, count, curPage, pageSize,
				CURPAGE_TAG);
	}

	/**
	 * 打印分页页码栏(简洁,有首尾页),
	 * 
	 * @param postUrl
	 *            链接地址
	 * @param count
	 *            记录总数
	 * @param curPage
	 *            当前页码
	 * @param pageSize
	 *            单页显示记录数
	 * @param curpageTag
	 *            当前页URL参数名 /xxx.jsp?curpage=1
	 * @return << < ...567... > >>
	 */
	public static String printSimPageString2(String postUrl,String target,
			String count, String curPage, String pageSize, String curpageTag) {
		if ("0".equals(count))
			return "";// 若记录总数为0，则无需PageBottom了
		int totalsize = Integer.parseInt(count);
		int curpage = Integer.parseInt(curPage);
		int pagesize = Integer.parseInt(pageSize);
		if (postUrl.indexOf("?") >= 0)
			postUrl += "&";
		else
			postUrl += "?";
		int totalpage = (totalsize - 1) / pagesize + 1;
		int curtenpage = (curpage - 1) / 10;

		StringBuffer bottomLink = new StringBuffer();
		if ("1".equals(curPage)) {// 如果当前页是第一页
			bottomLink.append("<li><a><</a></li>");
		} else {// 如果当前页不是第一页
			bottomLink.append("<li><a onclick='return getPageBtn(this);' href='");
			bottomLink.append(postUrl);
			bottomLink.append(curpageTag);
			bottomLink.append("=1");
			bottomLink.append("'");
			if(!StringUtils.isNullOrEmpty(target)){
				bottomLink.append(" target='");
				bottomLink.append(target);
				bottomLink.append("'");
			}
			bottomLink.append("><<</a></li>");
			bottomLink.append("<li><a onclick='return getPageBtn(this);' href='");
			bottomLink.append(postUrl);
			bottomLink.append(curpageTag);
			bottomLink.append("=");
			bottomLink.append(String.valueOf(curpage - 1));
			bottomLink.append("'");
			if(!StringUtils.isNullOrEmpty(target)){
				bottomLink.append(" target='");
				bottomLink.append(target);
				bottomLink.append("'");
			}
			bottomLink.append("><</a></li>");
		}
		// 增加当前页前面的页数
		int startpage = curpage - 3;
		if (curpage == totalpage)
			startpage = curpage - 5;
		if (startpage < 1) {
			startpage = 1;
		} else {
			bottomLink.append("<li><a>...</a></li>");
		}
		for (int i = startpage; i < curpage; i++) {
			bottomLink.append("<li><a onclick='return getPageBtn(this);' href='");
			bottomLink.append(postUrl);
			bottomLink.append(curpageTag);
			bottomLink.append("=");
			bottomLink.append(String.valueOf(i));
			bottomLink.append("'");
			if(!StringUtils.isNullOrEmpty(target)){
				bottomLink.append(" target='");
				bottomLink.append(target);
				bottomLink.append("'");
			}
			bottomLink.append(">");
			bottomLink.append(String.valueOf(i));
			bottomLink.append("</a></li>");
		}
		// 加入当前页
		bottomLink.append("<li class='active'><a>");
		bottomLink.append(String.valueOf(curpage));
		bottomLink.append("</a></li>");
		// 加入当前页后面的页数
		int endpage = curpage + 3;
		if (endpage == 4)
			endpage = 6;
		if (endpage > totalpage) {
			endpage = totalpage;
		}
		for (int i = curpage + 1; i <= endpage; i++) {
			bottomLink.append("<li><a onclick='return getPageBtn(this);' href='");
			bottomLink.append(postUrl);
			bottomLink.append(curpageTag);
			bottomLink.append("=");
			bottomLink.append(String.valueOf(i));
			bottomLink.append("'");
			if(!StringUtils.isNullOrEmpty(target)){
				bottomLink.append(" target='");
				bottomLink.append(target);
				bottomLink.append("'");
			}
			bottomLink.append(">");
			bottomLink.append(String.valueOf(i));
			bottomLink.append("</a></li>");
		}
		if (endpage < totalpage) {
			bottomLink.append("<li><a>...</a></li>");
		}
		// 最后一页
		if (totalpage == curpage) {// 如果当前页是最后一页
			bottomLink.append("<li><a>></a></li>");
		} else {
			bottomLink.append("<li><a onclick='return getPageBtn(this);' href='");
			bottomLink.append(postUrl);
			bottomLink.append(curpageTag);
			bottomLink.append("=");
			bottomLink.append(String.valueOf(curpage + 1));
			bottomLink.append("'");
			if(!StringUtils.isNullOrEmpty(target)){
				bottomLink.append(" target='");
				bottomLink.append(target);
				bottomLink.append("'");
			}
			bottomLink.append(">></a></li>");
			bottomLink.append("<li><a onclick='return getPageBtn(this);' href='");
			bottomLink.append(postUrl);
			bottomLink.append(curpageTag);
			bottomLink.append("=");
			bottomLink.append(String.valueOf(totalpage));
			bottomLink.append("'");
			if(!StringUtils.isNullOrEmpty(target)){
				bottomLink.append(" target='");
				bottomLink.append(target);
				bottomLink.append("'");
			}
			bottomLink.append(">>></a></li>");
		}
		return bottomLink.toString();
	}

	public static void main(String[] arg) {
	}

}

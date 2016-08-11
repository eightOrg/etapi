package com.eight.trundle.funmonitor;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.eight.trundle.common.DateUtils;


/**
 * 性能监控
 * @author micktiger
 */
@Controller
@RequestMapping(value="/funmonitor")
public class MonitorController {
	
	/**
	 * 打印结果
     * @param model
	 * @throws IOException 
    */
    @RequestMapping(value="/show", method=RequestMethod.GET)
    public void show(HttpServletRequest request, Writer writer, HttpServletResponse response) throws IOException {
    	StringBuffer sbHtml = new StringBuffer();
        sbHtml.append("<!doctype html><html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
        sbHtml.append("<style type=\"text/css\">");
        sbHtml.append("*{font-size: 12px;padding:0;}table{border-collapse: collapse;border: 1px solid #C7CBD2;width: 100%;margin-top: 5px;}  .trtitle{background-color: #d2d6de;font-weight: bold;} .titleinfo{line-height:30px;background-color: #d2d6de; padding: 5px;}td{text-align: right;line-height: 20px;padding: 0 3px;border: 1px solid #C7CBD2;}td.tleft{text-align: left;}");
        sbHtml.append("</style>");
        sbHtml.append("<title>系统性能监控 Monitor</title>");
        sbHtml.append("</head><body>");
        
        Monitor.flushData();
        
        sbHtml.append("<div class=\"titleinfo\">队列长度：");
        sbHtml.append(Monitor.getHistoryList().size());
        sbHtml.append("/");
        sbHtml.append(Monitor.getCOUNTSIZE());
        sbHtml.append("&nbsp;&nbsp;&nbsp;超时过滤：");
        sbHtml.append(Monitor.getINITTIME());
        sbHtml.append("ms</div>");
    	
        List<CountItem> countList = Monitor.getCountList();
    	sbHtml.append("<table><tr class=\"trtitle\"><td class=\"tleft\">方法名</td><td width=\"100\">累积调用次数</td><td width=\"100\">平均时间(ms)</td><td width=\"100\">最长时间(ms)</td><td width=\"100\">最短时间(ms)</td></tr>");
    	for(CountItem c : countList){
    		sbHtml.append("<tr><td class=\"tleft\">");
    		sbHtml.append(c.getFun());
    		sbHtml.append("</td><td>");
    		sbHtml.append(c.getCount());
    		sbHtml.append("</td><td>");
    		sbHtml.append(c.getAvr());
    		sbHtml.append("</td><td>");
    		sbHtml.append(c.getMax());
    		sbHtml.append("</td><td>");
    		sbHtml.append(c.getMin());
    		sbHtml.append("</td></tr>");
    	}
    	sbHtml.append("</table>");
    	
    	List<FunItem> topHistoryList = new ArrayList<FunItem>();
    	topHistoryList.addAll(Monitor.getHistoryList());
    	Collections.sort(topHistoryList);
    	sbHtml.append("<table><tr class=\"trtitle\"><td class=\"tleft\">方法名(最耗时TOP50)</td><td width=\"100\">用时(ms)</td><td class=\"tleft\">参数</td><td width=\"100\">IP</td><td width=\"120\">发生时间</td></tr>");
    	int topSize = topHistoryList.size() < 50 ? topHistoryList.size() : 50;
    	for(int i = 0; i < topSize; i++){
    		FunItem f = topHistoryList.get(i);
    		sbHtml.append("<tr><td class=\"tleft\">");
    		sbHtml.append(f.getFun());
    		sbHtml.append("</td><td>");
    		sbHtml.append(f.getTime());
    		sbHtml.append("</td><td class=\"tleft\">");
    		sbHtml.append(f.getParam());
    		sbHtml.append("</td><td>");
    		sbHtml.append(f.getIp());
    		sbHtml.append("</td><td>");
    		sbHtml.append(DateUtils.getFormatDateTime(f.getCreatetime(), "yyyy-MM-dd HH:mm:ss"));
    		sbHtml.append("</td></tr>");
    	}
    	sbHtml.append("</table>");
    	
    	List<FunItem> historyList = Monitor.getHistoryList();
    	sbHtml.append("<table><tr class=\"trtitle\"><td class=\"tleft\">方法名(实时记录)</td><td width=\"100\">用时(ms)</td><td class=\"tleft\">参数</td><td width=\"100\">IP</td><td width=\"120\">发生时间</td></tr>");
    	for(int i = historyList.size() - 1; i >= 0; i--){
    		FunItem f = historyList.get(i);
    		sbHtml.append("<tr><td class=\"tleft\">");
    		sbHtml.append(f.getFun());
    		sbHtml.append("</td><td>");
    		sbHtml.append(f.getTime());
    		sbHtml.append("</td><td class=\"tleft\">");
    		sbHtml.append(f.getParam());
    		sbHtml.append("</td><td>");
    		sbHtml.append(f.getIp());
    		sbHtml.append("</td><td>");
    		sbHtml.append(DateUtils.getFormatDateTime(f.getCreatetime(), "yyyy-MM-dd HH:mm:ss"));
    		sbHtml.append("</td></tr>");
    	}
    	sbHtml.append("</table>");
    	
    	sbHtml.append("</body></html>");
    	writer.write(sbHtml.toString());
    }
    
    /**
	 * 设置队列
     * @param model
	 * @throws IOException 
    */
    @RequestMapping(value="/set", method=RequestMethod.GET)
    public void set(HttpServletRequest request, Writer writer, HttpServletResponse response) throws IOException {
    	StringBuffer sbHtml = new StringBuffer();
        sbHtml.append("<!doctype html><html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
        sbHtml.append("<title>设置结果</title></head><body style=\"text-align:center;padding:50px;\">");
        boolean isOK = false;
        
        String countSize = request.getParameter("countSize");
		String initTime = request.getParameter("initTime");
        if(StringUtils.isNotEmpty(countSize) && StringUtils.isNotEmpty(initTime)){
        	try {
				Monitor.initQueue(Integer.valueOf(countSize), Integer.valueOf(initTime));
				isOK = true;
			}catch(Exception e){} 
        }
        if(isOK){
        	sbHtml.append("<h1>OK</h1>");
        }else{
        	sbHtml.append("<h1>请输入正确的参数</h1>");
        }
        
        sbHtml.append("<div>");
        sbHtml.append(DateUtils.getCurFormatDateTime());
        sbHtml.append("</div>");
    	sbHtml.append("</body></html>");
    	writer.write(sbHtml.toString());
    }
	
}

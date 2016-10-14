package com.eight.trundle.common;

import java.util.Random;

/**
 * 字符串操作类
 * @author weijl
 */
public class StringUtils {
	
	  /**
	   * 判断字符串是否null或者""
	   * @param src String
	   * @return boolean
	   */
	  public static boolean isNullOrEmpty(String src) {
	    if(null == src || "".equals(src.trim())) {
	      return true;
	    }
	    return false;
	  }

	  /**
	   * 把没有实例化的串转化为空串
	   * @param str String
	   * @return String
	   */
	  public static String convertNull(String str) {
		  return str == null ? "" : str;
	  }
	 
	  /**
	   * 将textarea输入的文本转化成前台html显示的格式，主要将回车（/r/n）替换成<br>," "替换成&nbsp;
	   * @param text String
	   * @return String
	   */
	  public static String convertTextareaToHtml(String text){
	      if(text != null){
	          return text.replaceAll("\r\n", "<br/>").replaceAll(" ", "&nbsp;");
	      }else{
	          return null;
	      }
	  }
	  
	  /**
	   * 将textarea输入的文本转化成纯文本，主要将回车（/r/n）替换成""," "替换成"";
	   * @param text String
	   * @return String
	   */
	  public static String convertTextareaToText(String text){
	      if(text != null){
	          return text.replaceAll("\r\n", "").replaceAll(" ", "");
	      }else{
	          return null;
	      }
	  }
	  
	  /**
	   * 全数字判断
	   * @param strIn String
	   * @return boolean
	   */
	  public static boolean isNumberString(String strIn) {
	    return isNumberString(strIn, "0123456789");
	  }

	  /**
	   * 全数字判断,参照字符串strRef可以是:"0123456789","23546"或"0123"等等。
	   * @param strIn String
	   * @param strRef String
	   * @return boolean
	   */
	  public static boolean isNumberString(String strIn, String strRef) {
	    if(strIn == null || strIn.length() == 0)
	      return(false);
	    for(int i = 0; i < strIn.length(); i++) {
	      String strTmp = strIn.substring(i, i + 1);
	      if(strRef.indexOf(strTmp) == -1)
	        return(false);
	    }
	    return(true);
	  }
	  
	  /**
	   * 输入带html标签的字符串,返回干净的字符串,其中将(&nbsp;->" "),(<br>->\r\n),(<p>->\r\n\r\n)
	   * @param body String
	   * @return String
	   */
	  public static String getCleanString(String body) {
	    //替换&nbsp;->" ",<br>->\r\n <p>->\r\n\r\n
	    body = body.replaceAll("&[nN][bB][sS][pP];"," ");
	    body = body.replaceAll("<[bB][rR]\\s*>","\r\n");
	    body = body.replaceAll("<[pP]\\s*>","\r\n\r\n");
	    //删除所有标签

	    body = body.replaceAll("<.+?>","");
	    return body;
	  }
	  
	  /**
	   * 根据内容生成摘要。如果是html格式，则自动去除标签
	   * @param content String
	   * @return String
	   */
	  public static String createSummary(String content, int length){
	    if(content == null)return null;
	    content = getCleanString(content);
	    int len = content.length();
	    if(len > length){
	      return content.substring(0, length);
	    }else{
	      return content;
	    }
	  }

	  /**
	   * 返回字符串的前len个字符.例如:输入"abcdefg",3 返回 "abc".
	   * @param value String
	   * @param len int
	   * @return String
	   */
	  public static String getLmtStr(String value, int len) {
	    if(value == null || value.length() <= len)
	      return value;
	    return value.substring(0, len);
	  }
	  /**
	   * 返回字符串的前len个字符.例如:输入"abcdefg",3 返回 "abc...".
	   * @param value String
	   * @param len int
	   * @return String
	   */
	  public static String getLmtString(String value, int len) {
	    if(value == null || value.length() <= len)
	      return value;
	    return value.substring(0, len) + "...";
	  }
	 

	  /**
	   * 返回字符串的len个字符.取前后，去掉中间 例如:输入"abcdefg",3 返回 "ab ... g".
	   * @param value String
	   * @param len int
	   * @return String
	   */
	  public static String getLmtStrx(String value, int len) {
	    if(value == null || value.length() <= len)
	      return value;
	    value = value.substring(0,len/2) + ".." + value.substring(value.length()-len/2);
	    return value;
	  }

	  /**
	   * 给传入的字符串前补足'0'，以使字符串长度为len。例如：输入字符串:"23",4 返回:"0023"。
	   * @param str String
	   * @param len int
	   * @return String
	   */
	  public String getZeroStr(String str, int len) {
	    int strlen = str.length();
	    for(int i = 0; i < len - strlen; i++) {
	      str = "0" + str;
	    }
	    return str;
	  }

	  public static String getCode(int len) {
          Random random = new Random();
		  String code = "";
		  for (int i = 0; i <len ; i++) {
		      code += random.nextInt(10);
		  }
		  return code;
	  }
}

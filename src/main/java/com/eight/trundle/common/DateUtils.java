package com.eight.trundle.common;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * 时间操作类，主要用以对时间的转换，以Long或者java.util.Date为基础
 * 主要有两个格式，Date（yyyy-MM-dd）和DateTime（yyyy-MM-dd HH:mm:ss）
 * 
 * @author weijl
 */
public class DateUtils {
	private static java.util.Calendar calendar = java.util.Calendar
			.getInstance();

	/**
	 * 将Date根据指定格式。(yyyy-MM-dd HH:mm:ss)的任意组合
	 * 
	 * @param date Date
	 * @param format String (yyyy-MM-dd HH:mm:ss)的任意组合
	 * @return String
	 */
	public static final String getFormatDateTime(Date date, String format) {
		SimpleDateFormat mydate = new SimpleDateFormat(format);
		return mydate.format(date);
	}

	/**
	 * 将long类型时间根据指定格式。(yyyy-MM-dd HH:mm:ss)的任意组合
	 * 
	 * @param millis long
	 * @param format String (yyyy-MM-dd HH:mm:ss)的任意组合
	 * @return String
	 */
	public static final String getFormatDateTime(long millis, String format) {
		return getFormatDateTime(new Date(millis), format);
	}
	
	/**
	 * 将long类型时间根据指定格式。yyyy-MM-dd HH:mm
	 * 
	 * @param millis long
	 * @return String
	 */
	public static final String getFormatDateTime(long millis) {
		return getFormatDateTime(new Date(millis), "yyyy-MM-dd HH:mm");
	}
	
	/**
	 * 获取当前时间的指定格式。 (yyyy-MM-dd HH:mm:ss)的任意组合
	 * 
	 * @param format String (yyyy-MM-dd HH:mm:ss)的任意组合
	 * @return String
	 */
	public static final String getCurFormatDateTime(String format) {
		return getFormatDateTime(System.currentTimeMillis(), format);
	}
	
	/**
	 * 获取当前时间的yyyy-MM-dd HH:mm格式。
	 * 
	 * @return String
	 */
	public static final String getCurFormatDateTime() {
		return getFormatDateTime(System.currentTimeMillis(), "yyyy-MM-dd HH:mm");
	}
	
	/**
	 * 获取当前时间的yyyy-MM-dd格式。
	 * 
	 * @return String
	 */
	public static final String getCurFormatDate() {
		return getFormatDateTime(System.currentTimeMillis(), "yyyy-MM-dd");
	}
	
	/**
	 * 将2015-07-23 00:00:00类型转换成long型
	 * 
	 * @param datetime String 格式: 2015-07-23 00:00:00
	 * @return long
	 */
	public static long getLongFromDateTime(String datetime) {
		String args[] = datetime.split(" ");
		String date = args[0];
		String time = args[1];

		String dateArgs[] = date.split("-");
		String timeArgs[] = time.split(":");
		String mm = "00";
		if (timeArgs.length == 3) {
			mm = timeArgs[2];
		}

		long ret = getTimeLong(Integer.parseInt(dateArgs[0]),
				Integer.parseInt(dateArgs[1]), Integer.parseInt(dateArgs[2]),
				Integer.parseInt(timeArgs[0]), Integer.parseInt(timeArgs[1]),
				Integer.parseInt(mm));

		return ret;

	}

	/**
	 * 将2015-07-23类型转换成long型
	 * 
	 * @param date String 格式: 2015-07-23
	 * @return long
	 */
	public static long getLongFromDate(String date) {
		String[] args = date.split("-");
		return getTimeLong(Integer.parseInt(args[0]),
				Integer.parseInt(args[1]), Integer.parseInt(args[2]));
	}
	
	/**
	 * 用于输入年月日小时分秒生成long型的时间格式
	 * 
	 * @param yy int
	 * @param mm int
	 * @param dd int
	 * @param h int
	 * @param m int
	 * @param sec int
	 * @return long
	 */
	public static final long getTimeLong(int yy, int mm, int dd, int h, int m,
			int sec) {
		calendar.clear();
		calendar.set(yy, mm - 1, dd, h, m, sec);
		return calendar.getTimeInMillis();
	}
	
	/**
	 * 用于输入年月日小时分生成long型的时间格式
	 * 
	 * @param yy int
	 * @param mm int
	 * @param dd int
	 * @param h  int
	 * @param m int
	 * @return long
	 */
	public static final long getTimeLong(int yy, int mm, int dd, int h, int m) {
		calendar.clear();
		calendar.set(yy, mm - 1, dd, h, m, 0);
		return calendar.getTimeInMillis();
	}

	/**
	 * 用于只输入年月日生成long型的时间格式
	 * 
	 * @param yy int
	 * @param mm int
	 * @param dd int
	 * @return long
	 */
	public static final long getTimeLong(int yy, int mm, int dd) {
		calendar.clear();
		calendar.set(yy, mm - 1, dd, 0, 0, 0);
		return calendar.getTimeInMillis();
	}

	

	/**
	 * 用于只输入年，月生成long型的时间格式
	 * 
	 * @param yy int
	 * @param mm int
	 * @return long
	 */
	public static final long getTimeLong(int yy, int mm) {
		calendar.clear();
		calendar.set(yy, mm - 1, 0, 0, 0, 0);
		return calendar.getTimeInMillis();
	}

	/**
	 * 根据输入的初始日期和新增的月份到新增月份后的总时间
	 * 
	 * @param startTime Date
	 * @param month long
	 * @return long
	 */
	public static final long getTotalTime(Date startTime, long month) {
		calendar.setTime(startTime);
		calendar.add(calendar.MONTH, (int) month);
		return calendar.getTimeInMillis();
	}

	/**
	 * 根据输入一个时间得到和现在的时间差（3天3小时3分）
	 * 
	 * @param tagTime long
	 * @return String
	 */
	public static final String getLeaveDateTime(long tagTime) {
		long nowTime = System.currentTimeMillis();
		long leaveTime = 0;
		if (nowTime > tagTime)
			leaveTime = (nowTime - tagTime) / 1000;
		else
			leaveTime = (tagTime - nowTime) / 1000;
		;
		long date = 0;
		long hour = 0;
		long minute = 0;
		long dateTime = 0;
		long hourTime = 0;
		String strDate = "";
		String strHour = "";
		String strMinute = "";
		date = leaveTime / 86400;
		dateTime = date * 86400;
		hour = (leaveTime - dateTime) / 3600;
		hourTime = hour * 3600;
		minute = (leaveTime - dateTime - hourTime) / 60;
		if (date > 0)
			strDate = date + "天";
		if (hour > 0 || (minute > 0 && date > 0))
			strHour = hour + "小时";
		if (minute > 0)
			strMinute = minute + "分";
		return strDate + strHour + strMinute;
	}

	/**
	 * 根据输入一个时间得到和现在的时间差(天数)
	 * 
	 * @param tagTime long
	 * @return String
	 */
	public static final String getLeaveDay(long tagTime) {
		long nowTime = System.currentTimeMillis();
		long leaveTime = 0;
		if (nowTime > tagTime)
			leaveTime = (nowTime - tagTime) / (1000 * 24 * 3600);
		else
			leaveTime = (tagTime - nowTime) / (1000 * 24 * 3600);
		return leaveTime + "";
	}

	/**
	 * 查询公元某年某月某日为星期几,0代表星期天
	 * 
	 * @param year int
	 * @param month int
	 * @param day int
	 * @return int
	 * @author meigen 2005-10-18
	 */
	public static int getWeekDay(int year, int month, int day) {
		if (month < 3) {
			month += 13;
			year--;
		} else
			month++;
		return (day + 26 * month / 10 + year + year / 4 - year / 100 + year
				/ 400 + 6) % 7;
	}

	/**
	 * 根据当前时间生成随机ID，yyyyMMddHHmmssSS+三位随机数，如20150723152708485819
	 * 
	 * @return
	 */
	public static String getDateTimeRandomId() {
		return DateUtils.getFormatDateTime(System.currentTimeMillis(),
				"yyyyMMddHHmmssSS") + new Random().nextInt(1000);
	}

}

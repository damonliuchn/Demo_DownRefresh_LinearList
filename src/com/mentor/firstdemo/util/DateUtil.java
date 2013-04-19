package com.mentor.firstdemo.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间辅助
 * @author xudayu
 * 2011-5-24下午05:42:49
 */
public class DateUtil {
	public static final String NO_TIME_DATE_FORMAT = "yyyy/MM/dd";

	public static final String SHORT_DATE_FORMAT = "yyyy/MM/dd HH:mm";
	
	public static final String SHORT_DATE_FORMAT2 = "yyyyMMddHHmm";
	
	public static final String MIDDLE_DATE_FORMAT = "yyyy/MM/dd HH:mm:ss";

	public static final String LONG_DATE_FORMAT = "yyyy/MM/dd HH:mm:ss:SSS";
	
	public static final String SHORT_DATE_DAY_FORMAT = "MM-dd HH:mm";
	
	/**
	 * 根据指定的日期和时间格式取得相应的字符串 <br>
	 * 出错返回null
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String getFormatDateString(Date date,String pattern){
		String formatDateString = null;
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		try {
			formatDateString = sdf.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			sdf = null;
		}
		return formatDateString;
	}
	
	/**
	 * 根据指定的日期字符串和日期格式取得Date实例 <br>
	 * 出错返回 null
	 * 
	 * @param dateString
	 * @param pattern
	 * @return
	 */
	public static Date getDateFromString(String dateString,String pattern){
		Date date = null;
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		try {
			date = sdf.parse(dateString);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			sdf = null;
		}
		return date;
	}
}

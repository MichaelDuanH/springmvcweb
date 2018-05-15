package com.ums.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    private static String TIME_STAM = "yyyy-MM-dd HH:mm:ss";
    
    private static DateFormat dt = new SimpleDateFormat(TIME_STAM);
    private static DateFormat YYYMMMDD = new SimpleDateFormat("yyyy-MM-dd");
    private static DateFormat MMDDYYYY = new SimpleDateFormat("MM-dd-yyyy");
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    static SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
    public static final DateFormat DateFormatYYYYMM = new SimpleDateFormat("yyyy-MM");
    public static String dateToTimeStam(Date date) {
		return dt.format(date);
    }
    
    //计算两个日期的月份差额
    
    public static int getBetweenMonthCount(Date cd1, Date zd2) {
    	if (cd1 == null || zd2 == null) {
    		return 0;
    	}
    	
    	return (cd1.getMonth() + 1) - (zd2.getMonth() + 1);
    }

    // 当前月份
    public static String getCurrMonth() {
    	Calendar c = Calendar.getInstance();
    	c.set(Calendar.DATE, 1);
    	return MMDDYYYY.format(c.getTime());
    	
    }
    
    // 当前月份
    public static Date getCurrMonthDt() {
    	Calendar c = Calendar.getInstance();
    	c.set(Calendar.DATE, 1);
    	return c.getTime();
    	
    }
    
    // 获取当前月份的上个月
    public static String getLastMonth() {
    	Calendar c = Calendar.getInstance();
    	c.add(Calendar.MONTH, -1);
    	c.set(Calendar.DATE, 1);
    	return YYYMMMDD.format(c.getTime());
    }
    
    public static Calendar getNewCalendar() {
    	return Calendar.getInstance();
    }
    
    
    public static Calendar getCalendar(Date date) {
        Calendar calender = Calendar.getInstance();
        calender.setTime(date);
        return calender;
    }
    
    public static Date parse(String dateStr, String formateStrs) {
    	dateStr = dateStr.replace("/", "-");
    	SimpleDateFormat sdf = new SimpleDateFormat(formateStrs);
    	Date date = null;
		try {
			date =  sdf.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
    }
    
    public static String formate(Date date, String formateStrs) {
    	SimpleDateFormat sdf = new SimpleDateFormat(formateStrs);
		return sdf.format(date);
    }
}

package com.wyiwei.commons.date;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
	
	public static String yyyyMMddHHmmss = "yyyyMMddHHmmss";
	public static String yyyyMMddHHmmssSSS = "yyyyMMddHHmmssSSS";
	public static String yyyyMMdd = "yyyyMMdd";
	
	public static String getTyyyyMMddHHmmss() {
		SimpleDateFormat format = new SimpleDateFormat(yyyyMMddHHmmss);
		return format.format(new Date());
	}
	public static String getTyyyyMMddHHmmssSSS() {
		SimpleDateFormat format = new SimpleDateFormat(yyyyMMddHHmmssSSS);
		return format.format(new Date());
	}
	public static String getTyyyyMMddHHmmss(Date date) {
		SimpleDateFormat format = new SimpleDateFormat(yyyyMMddHHmmss);
		return format.format(date);
	}
	public static String getTyyyyMMdd(Date date) {
		SimpleDateFormat format = new SimpleDateFormat(yyyyMMdd);
		return format.format(date);
	}
}

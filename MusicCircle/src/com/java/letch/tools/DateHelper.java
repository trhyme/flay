package com.java.letch.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/***
 * 时间工具类
 * @author WYT
 *
 */
public class DateHelper {
	
	/**获取当前时间：2016-08-08 24:00:00**/
	public static String getNewData(){
		Date date=new Date();   //获取当前时间
		return String.format("%tF %tT", date,date);
	}
	
	/**获取当前时间：2016-08-08**/
	public static String getNewDataYMD(){
		Date date=new Date();   //获取当前时间
		return String.format("%tF", date);
	}
	
	/**获取当前时间：2016-08-08 24:00**/
	public static String getNewDateTwo(){
		Date date=new Date();   //获取当前时间
		String str=String.format("%tF %tT", date,date);
		str=str.substring(0, str.lastIndexOf(":"));
		return String.format("%tF %tT", date,date);
	}
	
	/**获取当前时间: 20160704173752**/
	public static String getSimpDateString(){
		Date date=new Date();   //获取当前时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String str = sdf.format(date);
		return str;
	}
	
	/**返回当前时间戳**/
	public static long getSimpDateMillis(){
		Date date=new Date();
		//return System.currentTimeMillis(); 
		return date.getTime();
	}
	
	/***将时间转换为时间戳***/
	public static long getchangeMillis(String date){
		Long ds=new Long("");
		try {
			String  dit=date;
			dit+=" 00:00:00";
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date dates=sdf.parse(dit);   //将字符串转换为Date
			long lg=new Long(dates.getTime());
			return lg;     //时间戳
		} catch (ParseException e) {}
		return ds;
	}
	
	/****时间戳转换为时间****/
	public static String getchangeMillisDate(String timess){
		try {
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = new Date(new Long(timess));
			String res=sdf.format(date)+" ";
			return res;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}
	
	
	/***字符串日期格式计算出相差天数***/
	public static int getDaysBetween(String begintime,String endstime) throws ParseException{
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
        Calendar cal = Calendar.getInstance();    
        cal.setTime(sdf.parse(begintime));    
        long time1 = cal.getTimeInMillis();                 
        cal.setTime(sdf.parse(endstime));    
        long time2 = cal.getTimeInMillis();         
        long between_days=(time2-time1)/(1000*3600*24);  
        return Integer.parseInt(String.valueOf(between_days));
	}
	
}






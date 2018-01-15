package com.java.letch.tools;

import java.text.SimpleDateFormat;
import java.util.Date;

/***
 * 时间比较根据类
 * @author Administrator
 * 2017-10-17
 */
public class DateCompareTools {
	
	/***
	 * 判断时间是否与当前时间相隔分钟数
	 * @param times 字符格式：yyyy-MM-dd HH:mm:ss
	 * @param num 相差的分钟数
	 * @return	是否在相差的范围之内，TRUE为没有过期，false为过期
	 */
	public static boolean compareOne(String times,int num){
		try {
			Date nowDate=new Date();//当前时间
			long nowTime=nowDate.getTime(); 
			Date date2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(times);
			long lastTime=date2.getTime();//以前的时间
			long time=nowTime-lastTime;//时间相减比较
			if(time>(long)60000*num){		//不在范围，过期
				return false;
			}else{
				return true;
			}
		} catch (Exception e) {
			return false;
		}
		
	}
	
}

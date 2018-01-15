package com.java.letch.tools;

import java.util.Random;

/***
 * 随机数字生成工具包类
 * @author Administrator
 * 2017-09-18
 */
public class NumberCodeTools {
	private static Random random = new Random();
	private static String randString="0123456789";
	/***
	 * 根据位数生成随机验证码
	 * @param num
	 * @return
	 */
	public static String getRandomNumberCode(int num){
		if(num<=0){
			num=1;
		}
		if(num>12){
			num=12;
		}
		StringBuffer stb=new StringBuffer("");
		for(int i=0;i<num;i++){ 	//生成随机数
			String rand = String.valueOf(getRandomString(random.nextInt(randString.length())));
			stb.append(rand);
		}
		return stb.toString();
	}
	
	
	private static String getRandomString(int num) {
        return String.valueOf(randString.charAt(num));
    }
	
}

package com.java.letch.app;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

/***
 * 用户短信验证码发送【中国网建SMS短信通】
 * @author Administrator
 * 2017-09-18
 */
public class ShortMessageCodeHttp {
	
	//常量信息
	private static final String CODENAME="leqtch.me";	  //用户名
	private static final String URL="http://gbk.api.smschinese.cn"; //请求地址
	private static final String KEY="2615b6327b448bea6d4e";  //用户的秘钥
	private static final String CONTENT_TYPE="application/x-www-form-urlencoded;charset=gbk";
	
	/**短信验证码的发送**/
	public static boolean sendCodeMeaage(String phone,String codemessage){
		try {
			HttpClient client = new HttpClient();
			PostMethod post = new PostMethod(URL); 				//设置请求头
			post.addRequestHeader("Content-Type",CONTENT_TYPE); //在头文件中设置转码
			NameValuePair[] data ={ 
					new NameValuePair("Uid", CODENAME),
					new NameValuePair("Key", KEY),
					new NameValuePair("smsMob",phone),   //如：13888888886,13888888887,1388888888 一次最多对100个手机发送
					new NameValuePair("smsText",codemessage)};
			post.setRequestBody(data);

			client.executeMethod(post);
			Header[] headers = post.getResponseHeaders();
			int statusCode = post.getStatusCode();
			System.out.println(statusCode);
			post.releaseConnection();
			if(statusCode==200){
				return true;
			}else{
				return false;
			}
			//System.out.println("statusCode:"+statusCode);
			/*for(Header h : headers){
				System.out.println(h.toString());
			}*/
			/*String result = new String(post.getResponseBodyAsString().getBytes("gbk")); 
			System.out.println(result); //打印返回消息状态
			 */
			
		} catch (Exception e) {
			return false;
		}
	}
	
}

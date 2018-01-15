package test;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.java.letch.app.jpush.JpushClientUtil;

import cn.jpush.api.push.PushResult;

public class TT {
	public static void main(String[] args) {
		/*System.out.println(new Date());
	      System.out.println(isToday(new Date()));*/
		
		String ALERT = "评论通知：你的动态消息被点赞了";    
        PushResult result = JpushClientUtil.push(String.valueOf("24165"),ALERT);
        if(result != null && result.isResultOK()){
            System.out.println("ok");
        }else{
        	System.out.println("no");
        }
	}
	   
	  public static boolean isToday(Date date){
	     SimpleDateFormat fmt=new SimpleDateFormat("yyyy-MM-dd");
	     
	     if(fmt.format(date).toString().equals(fmt.format(new Date()).toString())){//格式化为相同格式
	          return true;
	      }else {
	        return false;
	      }
	         
	           
	  }
}

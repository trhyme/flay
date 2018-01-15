package test;

import com.java.letch.app.jpush.JpushClientUtil;

import cn.jpush.api.push.PushResult;

public class JpushTest {
	public static void main(String[] args) {
		
		//1.0
		/*//JpushClientUtil.sendToAll("测试", "测试标题", "测试内容", "133");
		try {
			JpushClientUtil.sendToRegistrationId("170976fa8a8481ba1e0", "测试", "测试标题", "测试", "24146");
			//JpushClientUtil.sendToAllAndroid("123", "321", "456", "14252");
			//JpushClientUtil.sendToAllIos("123", "321", "456", "14252");
		} catch (Exception e) {
			System.out.println(e);
		}*/
		
		//2.0
		 String alias = "22222";//声明别名
	        //log.info("对别名" + alias + "的用户推送信息");
		 String ALERT = "推送信息1231";    
	        //PushResult result = JpushClientUtil.push(String.valueOf(alias),ALERT);
	        //JpushClientUtil.sendToRegistrationId("22222", "测试", "测试标题", "测试", "22222");
	        //JpushClientUtil.sendToAllAndroid("初遇最新消息[测试]", "初遇即将上线【邀你内测】", "初次遇见你", "5201314");
	        System.out.println(JpushClientUtil.sendToRegistrationId("170976fa8a8481ba1e0", "测试2", "测试标题2", "测试2", "22222"));
	        /*if(result != null && result.isResultOK()){
	            System.out.println("针对别名" + alias + "的信息推送成功！");
	        }else{
	            System.out.println("针对别名" + alias + "的信息推送失败！");
	        }*/
		//JpushClientUtil.push(alias, alert);
	}
}

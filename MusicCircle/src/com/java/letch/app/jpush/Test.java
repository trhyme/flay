package com.java.letch.app.jpush;

public class Test {
	
	public static void main(String[] args) {
		/*int x=JpushClientUtil.sendToAll("测试", "成功了", "123456", "12");
		System.out.println(x);*/
		
		int y=JpushClientUtil.sendToAllAndroid("通过声音初次相遇的地方", "通过声音初次相遇的地方", "124563", "ssjaklas");
		System.out.println(y);
	}
	
}

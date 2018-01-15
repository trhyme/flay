package com.java.letch.serviceimp;

import com.java.letch.model.SayHelloInfo;

/***
 * 用户打招呼的业务操作接口
 * @author Administrator
 * 2017-12-12
 */
public interface SayHelloInfoServiceImp {
	
	//1.添加信息
	public boolean addSayHelloToDB(SayHelloInfo say);
	
	//2.查询单条数据
	public SayHelloInfo selectSayHelloByID(int userid,int sayuserid);
	
}

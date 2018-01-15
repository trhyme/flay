package com.java.letch.daoimp;

import com.java.letch.model.SayHelloInfo;

/***
 * 打招呼的数据操作接口
 * @author Administrator
 * 2017-12-12
 */
public interface SayHelloInfoDaoImp {
	
	//1.添加信息
	public boolean addSayHelloToDB(SayHelloInfo say);
	
	//2.查询单条数据
	public SayHelloInfo selectSayHelloByID(int userid,int sayuserid);
	
}

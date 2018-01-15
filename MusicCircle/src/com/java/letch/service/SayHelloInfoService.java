package com.java.letch.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.java.letch.dao.SayHelloInfoDao;
import com.java.letch.model.SayHelloInfo;
import com.java.letch.serviceimp.SayHelloInfoServiceImp;

/***
 * 用户的数据操作
 * @author Administrator
 * 2017-12-12
 */
@Repository(value="SayHelloInfoService")
public class SayHelloInfoService implements SayHelloInfoServiceImp{

	@Resource(name="SayHelloInfoDao")
	private SayHelloInfoDao sayhelloinfoDao;	//数据操作
	
	//1.添加信息
	@Override
	public boolean addSayHelloToDB(SayHelloInfo say) {
		return this.sayhelloinfoDao.addSayHelloToDB(say);
	}
	
	//2.查询单条数据
	@Override
	public SayHelloInfo selectSayHelloByID(int userid, int sayuserid) {
		return this.sayhelloinfoDao.selectSayHelloByID(userid, sayuserid);
	}
	
}

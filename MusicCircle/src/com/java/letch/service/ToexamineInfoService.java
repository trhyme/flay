package com.java.letch.service;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.java.letch.dao.ToexamineInfoDao;
import com.java.letch.model.ToexamineInfo;
import com.java.letch.serviceimp.ToexamineInfoServiceImp;

/***
 * 用户认证信息的业务请求类
 * @author Administrator
 * 2017-09-29
 */
@Repository(value="ToexamineInfoService")
public class ToexamineInfoService implements ToexamineInfoServiceImp {

	@Resource(name="ToexamineInfoDao")
	private ToexamineInfoDao toexamineinfoDao;		//认证信息数据处理
	
	/*分页查询*/
	@Override
	public Map<String, Object> selectToexamineList(Map<String, Object> map) {
		return this.toexamineinfoDao.selectToexamineList(map);
	}

	/*修改认证状态的方法*/
	@Override
	public boolean updateToexamineStratus(int id, int stratus) {
		return this.toexamineinfoDao.updateToexamineStratus(id, stratus);
	}

	/*添加认证信息的方法*/
	@Override
	public boolean insertToexamOne(ToexamineInfo toexa) {
		return this.toexamineinfoDao.insertToexamOne(toexa);
	}
	
	/*添加修改认证的消息*/
	@Override
	public boolean updatePiclistBy(String userid, String photo) {
		return this.toexamineinfoDao.updatePiclistBy(userid, photo);
	}
	/*添加修改认证的消息*/
	
	/***1.根据用户编号查询认证信息***/
	@Override
	public ToexamineInfo selectToexamByUserid(int userid) {
		return this.toexamineinfoDao.selectToexamByUserid(userid);
	}
	
	/***2.根据用户编号查询认证信息***/
	@Override
	public ToexamineInfo selectToexamByUseridAll(int userid) {
		return this.toexamineinfoDao.selectToexamByUseridAll(userid);
	}
	
}

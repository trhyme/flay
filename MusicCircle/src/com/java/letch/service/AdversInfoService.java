package com.java.letch.service;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.java.letch.dao.AdversInfoDao;
import com.java.letch.model.AdversInfo;
import com.java.letch.serviceimp.AdversInfoServiceImp;

/***
 * 弹窗广告的业务操作信息类
 * @author Administrator
 * 2017-09-22
 */
@Repository(value="AdversInfoService")
public class AdversInfoService implements AdversInfoServiceImp {

	@Resource(name="AdversInfoDao")
	private AdversInfoDao adversInfoDao;
	
	//分页查看信息
	@Override
	public Map<String, Object> selectPageList(Map<String, Object> map) {
		return this.adversInfoDao.selectPageList(map);
	}

	//添加入库的方法
	@Override
	public boolean insertIntoAdversDB(AdversInfo adver) {
		return this.adversInfoDao.insertIntoAdversDB(adver);
	}

	//根据编号查询信息
	@Override
	public AdversInfo selectByIds(int ids) {
		return this.adversInfoDao.selectByIds(ids);
	}

	/**修改信息的方法**/
	@Override
	public boolean updateAdvers(AdversInfo adver) {
		return this.adversInfoDao.updateAdvers(adver);
	}

	
	
}

package com.java.letch.serviceimp;

import java.util.Map;

import com.java.letch.model.AdversInfo;

/***
 * 弹窗广告的业务操作信息类接口
 * @author Administrator
 * 2017-09-22
 */
public interface AdversInfoServiceImp {

	//后台分页查看信息
	public Map<String, Object> selectPageList(Map<String, Object> map);
	
	//添加入库的方法
	public boolean insertIntoAdversDB(AdversInfo adver);
	
	//根据编号查询信息
	public AdversInfo selectByIds(int ids);
	
	/**修改信息的方法**/
	public boolean updateAdvers(AdversInfo adver);
}

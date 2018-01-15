package com.java.letch.service;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.java.letch.dao.BroadcastInfoDao;
import com.java.letch.serviceimp.BroadcastInfoServiceImp;

/***
 * 广播信息的业务操作类
 * @author Administrator
 * 2017-09-27
 */
@Repository(value="BroadcastInfoService")
public class BroadcastInfoService implements BroadcastInfoServiceImp {

	@Resource(name="BroadcastInfoDao")
	private BroadcastInfoDao broadcastinfoDao;			//广播信息数据操作类
	
	/*分页查询*/
	@Override
	public Map<String, Object> selectBroadcastPage(Map<String, Object> map) {
		return this.broadcastinfoDao.selectBroadcastPage(map);
	}

	/*修改是否屏蔽信息*/
	@Override
	public boolean updateBroadcastStratus(int id, int stratus) {
		return this.broadcastinfoDao.updateBroadcastStratus(id, stratus);
	}

}

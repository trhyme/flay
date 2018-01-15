package com.java.letch.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.java.letch.dao.FollowsInfoDao;
import com.java.letch.model.FollowsInfo;
import com.java.letch.serviceimp.FollowsInfoServiceImp;

/***
 * APP用户关注数据的业务操作类
 * @author Administrator
 * 2017-09-26
 */ 
@Repository(value="FollowsInfoService")
public class FollowsInfoService implements FollowsInfoServiceImp {

	@Resource(name="FollowsInfoDao")
	private FollowsInfoDao followsinfoDao;			//关注数据操作
	
	/*APP用户添加关注信息*/
	@Override
	public boolean insertAddFollows(FollowsInfo follow) {
		return this.followsinfoDao.insertAddFollows(follow);
	}

	/**1.根据关注者编号以及被关注者编号查询是否被关注**/
	@Override
	public FollowsInfo selectFollowsByTwoID(int idA, int idB) {
		return this.followsinfoDao.selectFollowsByTwoID(idA, idB);
	}

	/**2.修改互相关注信息**/
	@Override
	public boolean updateFollowsMutualnumByTwoID(int idA, int idB,int stratus) {
		return this.followsinfoDao.updateFollowsMutualnumByTwoID(idA, idB,stratus);
	}

	/***3.根据编号分页查询该用户的关注信息***/
	@Override
	public Map<String, Object> selectFollowsByID(Map<String, Object> map,int uid) {
		return this.followsinfoDao.selectFollowsByID(map, uid);
	}

	/***4.根据编号分页查询该用户的粉丝信息***/
	@Override
	public Map<String, Object> selectFansByID(Map<String, Object> map, int uid) {
		return this.followsinfoDao.selectFansByID(map, uid);
	}
	
	/***5.取消关注***/
	@Override
	public boolean deleteFansByFollows(FollowsInfo follow,int idA,int idB) {
		return this.followsinfoDao.deleteFansByFollows(follow,idA,idB);
	}
	
	/***【心动我的1】***/
	@Override
	public Map<String, Object> selectFollowsMePage(Map<String, Object> map, int userid) {
		return this.followsinfoDao.selectFollowsMePage(map, userid);
	}
	
	/***【我的心动3】***/
	@Override
	public Map<String, Object> selectMyFollowsPage(Map<String, Object> map, int userid) {
		return this.followsinfoDao.selectMyFollowsPage(map, userid);
	}

	/***【相互心动2】***/
	@Override
	public Map<String, Object> selectFollowMutualPage(Map<String, Object> map, int userid) {
		return this.followsinfoDao.selectFollowMutualPage(map, userid);
	}
	
	/***查询心动信息***/
	@Override
	public List<FollowsInfo> selectFollowList(int userid, int followid) {
		return this.followsinfoDao.selectFollowList(userid, followid);
	}
	
}

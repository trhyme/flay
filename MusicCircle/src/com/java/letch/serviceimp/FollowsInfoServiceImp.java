package com.java.letch.serviceimp;

import java.util.List;
import java.util.Map;

import com.java.letch.model.FollowsInfo;

/***
 * APP用户的关注信息业务操作类
 * @author Administrator
 * 2017-09-26
 */
public interface FollowsInfoServiceImp {

	/*APP用户添加关注信息*/
	public boolean insertAddFollows(FollowsInfo follow);
	
	/**1.根据关注者编号以及被关注者编号查询是否被关注**/
	public FollowsInfo selectFollowsByTwoID(int idA,int idB);
	
	/**2.修改互相关注信息**/
	public boolean updateFollowsMutualnumByTwoID(int idA,int idB,int stratus);
	
	
	/***3.根据编号分页查询该用户的关注信息***/
	public Map<String, Object> selectFollowsByID(Map<String, Object> map,int uid);
	
	/***4.根据编号分页查询该用户的粉丝信息***/
	public Map<String, Object> selectFansByID(Map<String, Object> map,int uid);
	
	/***5.取消关注***/
	public boolean deleteFansByFollows(FollowsInfo follow,int idA,int idB);
	
	/***【心动我的1】***/
	public Map<String, Object> selectFollowsMePage(Map<String, Object> map,int userid);
	
	/***【我的心动3】***/
	public Map<String, Object> selectMyFollowsPage(Map<String, Object> map,int userid);
	
	/***【相互心动2】***/
	public Map<String,Object> selectFollowMutualPage(Map<String, Object> map,int userid);
	
	/***查询心动信息***/
	public List<FollowsInfo> selectFollowList(int userid,int followid);
	
}

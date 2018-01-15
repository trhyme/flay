package com.java.letch.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.java.letch.dao.RecentVisitInfoDao;
import com.java.letch.model.RecentVisitInfo;
import com.java.letch.model.view.RecentVisitView;
import com.java.letch.serviceimp.RecentVisitInfoServiceImp;

/***
 * 来访信息的业务操作类
 * @author Administrator
 * 2017-11-14
 */
@Repository(value="RecentVisitInfoService")
public class RecentVisitInfoService implements RecentVisitInfoServiceImp{

	//数据业务操作
	@Resource(name="RecentVisitInfoDao")
	private RecentVisitInfoDao recentvisitinfoDao;
	
	//会员用户的业务操作类
	@Resource(name="MemberInfoService")
	private MemberInfoService memberinfoService;
	
	/**1.新增来访纪录**/
	@Override
	public boolean insertIntoRecentInfo(RecentVisitInfo receninfo) {
		return this.recentvisitinfoDao.insertIntoRecentInfo(receninfo);
	}

	/**2.查询单条用户的来访信息**/
	@Override
	public List<RecentVisitView> selectOneRecentVisitView(int userid) {
		return this.recentvisitinfoDao.selectOneRecentVisitView(userid);
	}

	/**3.分页查询查询来访信息**/
	@Override
	public Map<String, Object> selectRecentVisitView(Map<String, Object> map,int userid) {
		return this.recentvisitinfoDao.selectRecentVisitView(map,userid);
	}

}

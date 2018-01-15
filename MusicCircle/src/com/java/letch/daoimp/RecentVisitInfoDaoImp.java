package com.java.letch.daoimp;

import java.util.List;
import java.util.Map;

import com.java.letch.model.RecentVisitInfo;
import com.java.letch.model.view.RecentVisitView;

/***
 * 最近来访数据操作类
 * @author Administrator
 * 2017-11-14
 */
public interface RecentVisitInfoDaoImp {
	
	/**1.新增来访纪录**/
	public boolean insertIntoRecentInfo(RecentVisitInfo receninfo);
	
	/**2.查询单条用户的来访信息**/
	public List<RecentVisitView> selectOneRecentVisitView(int userid);
	
	/**3.分页查询查询来访信息**/
	public Map<String, Object> selectRecentVisitView(Map<String, Object> map,int userid);
	
}

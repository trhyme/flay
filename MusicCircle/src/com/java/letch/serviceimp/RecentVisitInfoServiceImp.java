package com.java.letch.serviceimp;

import java.util.List;
import java.util.Map;

import com.java.letch.model.RecentVisitInfo;
import com.java.letch.model.view.RecentVisitView;

/***
 * 来访数据信息业务处理接口
 * @author Administrator
 * 2017-11-14
 */
public interface RecentVisitInfoServiceImp {
	
	/**1.新增来访纪录**/
	public boolean insertIntoRecentInfo(RecentVisitInfo receninfo);
	
	/**2.查询单条用户的来访信息**/
	public List<RecentVisitView> selectOneRecentVisitView(int userid);
	
	/**3.分页查询查询来访信息**/
	public Map<String, Object> selectRecentVisitView(Map<String, Object> map,int userid);
	
}

package com.java.letch.serviceimp;

import java.util.Map;

import com.java.letch.model.ReportInfo;

/***
 * 用户举报信息的业务操作类接口
 * @author Administrator
 * 2017-10-09
 */
public interface ReportInfoServiceImp {
	
	/*分页查询信息*/
	public Map<String, Object> selectReportByPage(Map<String, Object> map);
	
	/*根据编号查询单条信息*/
	public ReportInfo selectReportByID(int id); 
	
	/**修改回复信息**/
	public boolean updateReplytieByID(int id,String reply,int stra);
	
	/***1.添加举报信息***/
	public boolean insertReportInfo(ReportInfo rep);
}

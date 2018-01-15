package com.java.letch.service;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.java.letch.dao.ReportInfoDao;
import com.java.letch.model.ReportInfo;
import com.java.letch.serviceimp.ReportInfoServiceImp;

/***
 * 用户举报信息的业务操作类接口
 * @author Administrator
 * 2017-10-09
 */
@Repository(value="ReportInfoService")
public class ReportInfoService implements ReportInfoServiceImp{

	@Resource(name="ReportInfoDao")
	private ReportInfoDao reportinfoDao;		//用户举报信息的数据操作类
	
	/*分页查询信息*/
	@Override
	public Map<String, Object> selectReportByPage(Map<String, Object> map) {
		return this.reportinfoDao.selectReportByPage(map);
	}

	/*根据编号查询单条信息*/
	@Override
	public ReportInfo selectReportByID(int id) {
		return this.reportinfoDao.selectReportByID(id);
	}

	/**修改回复信息**/
	@Override
	public boolean updateReplytieByID(int id, String reply, int stra) {
		return this.reportinfoDao.updateReplytieByID(id, reply, stra);
	}

	/***1.添加举报信息***/
	@Override
	public boolean insertReportInfo(ReportInfo rep) {
		return this.reportinfoDao.insertReportInfo(rep);
	}
	
}

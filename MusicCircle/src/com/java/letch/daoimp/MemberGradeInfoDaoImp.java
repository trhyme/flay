package com.java.letch.daoimp;

import java.util.List;
import java.util.Map;

import com.java.letch.model.MemberGradeInfo;

/***
 * 会员类别的数据操作类接口
 * @author Administrator
 * 2017-09-28
 */
public interface MemberGradeInfoDaoImp {
	
	/*分页查询信息*/
	public Map<String, Object> selectMemberGradePage(Map<String, Object> map);
	
	//添加数据信息
	public boolean insertMemberGradeToDB(MemberGradeInfo membergrd);
	
	/*修改会员等级信息的状态*/
	public boolean updateMemberGradeStratus(int id,int stra);
	
	/***1.查询出所有的会员等级类别信息***/
	public List<MemberGradeInfo> selectAllMemberGrade();
	
	/*2.根据编号查询单条信息*/
	public MemberGradeInfo selectMemberGradeOneByID(int id);
	
	/*3.修改会员的信息*/
	public boolean updateMemberGradeByOne(MemberGradeInfo membergrade);
	
}

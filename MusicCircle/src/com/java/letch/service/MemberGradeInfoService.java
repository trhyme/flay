package com.java.letch.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.java.letch.dao.MemberGradeInfoDao;
import com.java.letch.model.MemberGradeInfo;
import com.java.letch.serviceimp.MemberGradeInfoServiceImp;

/***
 * 会员类别的数据操作类
 * @author Administrator
 * 2017-09-28
 */
@Repository(value="MemberGradeInfoService")
public class MemberGradeInfoService implements MemberGradeInfoServiceImp {

	@Resource(name="MemberGradeInfoDao")
	private MemberGradeInfoDao membergradeinfoDao;		//会员类别信息的数据操作类
	
	/*分页查询信息*/
	@Override
	public Map<String, Object> selectMemberGradePage(Map<String, Object> map) {
		return this.membergradeinfoDao.selectMemberGradePage(map);
	}

	/*添加信息*/
	@Override
	public boolean insertMemberGradeToDB(MemberGradeInfo membergrd) {
		return this.membergradeinfoDao.insertMemberGradeToDB(membergrd);
	}

	/*修改会员等级信息的状态*/
	@Override
	public boolean updateMemberGradeStratus(int id, int stra) {
		return this.membergradeinfoDao.updateMemberGradeStratus(id, stra);
	}

	/***1.查询出所有的会员等级类别信息***/
	@Override
	public List<MemberGradeInfo> selectAllMemberGrade() {
		return this.membergradeinfoDao.selectAllMemberGrade();
	}

	/*2.根据编号查询单条信息*/
	@Override
	public MemberGradeInfo selectMemberGradeOneByID(int id) {
		return this.membergradeinfoDao.selectMemberGradeOneByID(id);
	}

	/*3.修改会员的信息*/
	@Override
	public boolean updateMemberGradeByOne(MemberGradeInfo membergrade) {
		return this.membergradeinfoDao.updateMemberGradeByOne(membergrade);
	}
	
	

}

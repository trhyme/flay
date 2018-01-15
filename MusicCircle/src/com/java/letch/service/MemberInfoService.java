package com.java.letch.service;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.java.letch.dao.MemberInfoDao;
import com.java.letch.model.MemberInfo;
import com.java.letch.serviceimp.MemberInfoServiceImp;
import com.java.letch.tools.DateHelper;

/***
 * 用户会员信息的业务操作类
 * @author Administrator
 * 2017-09-28
 */
@Repository(value="MemberInfoService")
public class MemberInfoService implements MemberInfoServiceImp {

	@Resource(name="MemberInfoDao")
	private MemberInfoDao memberinfoDao;
	
	/**分页查询数据信息**/
	@Override
	public Map<String, Object> selectMemberPage(Map<String, Object> map) {
		return this.memberinfoDao.selectMemberPage(map);
	}

	/**1.根据用户的编号查询会员信息**/
	@Override
	public MemberInfo selectMemberOneByID(int userid) {
		return this.memberinfoDao.selectMemberOneByID(userid);
	}
	
	//查询出会员的总数量
	@Override
	public int selectMemberCount(int strtus) {
		return this.memberinfoDao.selectMemberCount(strtus);
	}

	/***2.添加数据***/
	@Override
	public boolean insertIntoMemberInfoDB(MemberInfo member) {
		return this.memberinfoDao.insertIntoMemberInfoDB(member);
	}
	
	
	
	/***判断用户是否为会员***/
	@Override
	public int checkMemberByUserID(int userid) throws Exception{
		MemberInfo member=this.memberinfoDao.selectMemberOneByID(userid);
		if(member==null){
			return 0;
		}else{
			//判断时间是否到期
			//1.会员开始时间--->到当前时间的天数
			int num1=DateHelper.getDaysBetween(member.getBegintime(), DateHelper.getNewData());
			//2.会员开始时间--->会员到期时间
			int num2=DateHelper.getDaysBetween(member.getBegintime(), member.getEndestime());
			if(num1<=num2){ //还未到期
				return member.getMegid();		//返回会员的编号ID
			}else{			//已到期
				//设置会员信息
				member.setStrutus(0);
				member.setDisabls(0);
				this.memberinfoDao.insertIntoMemberInfoDB(member);
				return 0;
			}
		}
	}
	/***判断用户是否为会员***/
}

package com.java.letch.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.java.letch.dao.TypesVoiceInfoDao;
import com.java.letch.model.TypesVoiceInfo;
import com.java.letch.serviceimp.TypesVoiceInfoServiceImp;

/***
 * 标签类型数据操作
 * @author Administrator
 * 2017-09-20
 */
@Repository(value="TypesVoiceInfoService")
public class TypesVoiceInfoService implements TypesVoiceInfoServiceImp {

	@Resource(name="TypesVoiceInfoDao")
	private  TypesVoiceInfoDao typeVoicesInfoDao;
	
	//查询全部标签信息
	@Override
	public List<TypesVoiceInfo> selectAllTypesList() {
		return this.typeVoicesInfoDao.selectAllTypesList();
	}

	//分页查询信息
	@Override
	public Map<String, Object> selectTypesPageList(Map<String, Object> map) {
		return this.typeVoicesInfoDao.selectTypesPageList(map);
	}

	//添加信息的方法
	@Override
	public boolean insertIntoTypes(TypesVoiceInfo types) {
		return this.typeVoicesInfoDao.insertIntoTypes(types);
	}

}

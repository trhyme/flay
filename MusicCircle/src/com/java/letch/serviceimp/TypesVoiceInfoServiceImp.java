package com.java.letch.serviceimp;

import java.util.List;
import java.util.Map;

import com.java.letch.model.TypesVoiceInfo;

/***
 * 类型标签的业务处理接口
 * @author Administrator
 * 2017-09-20
 */
public interface TypesVoiceInfoServiceImp {
	//查询全部标签信息
	public List<TypesVoiceInfo> selectAllTypesList();
	
	//分页查询信息
	public Map<String, Object> selectTypesPageList(Map<String, Object> map);
	
	//添加信息的方法
	public boolean insertIntoTypes(TypesVoiceInfo types);
	
}

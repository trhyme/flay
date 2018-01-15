package com.java.letch.dao.tools;

import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

/***
 * 数据库信息分页查询工具
 * @author Administrator
 * 2017-09-20
 */
@Repository(value="DataPageTools")
public class DataPageTools {
	@Resource(name="sessionFactory")
	private SessionFactory session;		//数据源
	
	
}

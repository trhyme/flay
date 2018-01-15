package com.java.letch.serviceimp;

import java.util.Map;

/***
 * 广播墙的业务操作类接口
 * @author Administrator
 * 2017-09-27
 */
public interface BroadcastInfoServiceImp {
	
	/*分页查询*/
	public Map<String, Object> selectBroadcastPage(Map<String, Object> map);
	
	/*修改是否屏蔽信息*/
	public boolean updateBroadcastStratus(int id,int stratus);
	
}

package com.java.letch.serviceimp;

import java.util.List;
import java.util.Map;

import com.java.letch.model.BannerInfo;

/***
 * Banner图业务操作接口
 * @author Administrator
 * 2014-09-19
 */
public interface BannerInfoServiceImp {

	/*后台分页查看信息*/
	public Map<String, Object> selectBannerPage(Map<String, Object> map);
	
	/*添加Banner的方法*/
	public boolean insertBannerToDB(BannerInfo banner);
	
	/*根据编号查询信息*/
	public BannerInfo selectBannerById(String id);
	
	/*修改信息*/
	public boolean updateBannerByBanner(BannerInfo banner);
	
	/**1.APP查询出最新四张Banner图片**/
	public List<BannerInfo> selectBannerList(int num);
	
}

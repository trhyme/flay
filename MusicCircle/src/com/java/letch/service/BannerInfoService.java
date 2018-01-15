package com.java.letch.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.java.letch.dao.BannerInfoDao;
import com.java.letch.model.BannerInfo;
import com.java.letch.serviceimp.BannerInfoServiceImp;

/***
 * Banner图数据操作类
 * @author Administrator
 * 2017-09-19
 */
@Repository(value="BannerInfoService")
public class BannerInfoService implements BannerInfoServiceImp {

	@Resource(name="BannerInfoDao")
	private BannerInfoDao bannerInfoDao;	//Banner图操作
	
	/*后台分页查看信息*/
	@Override
	public Map<String, Object> selectBannerPage(Map<String, Object> map) {
		return this.bannerInfoDao.selectBannerPage(map);
	}

	/*添加Banner的方法*/
	@Override
	public boolean insertBannerToDB(BannerInfo banner) {
		return this.bannerInfoDao.insertBannerToDB(banner);
	}

	/*根据编号查询信息*/
	@Override
	public BannerInfo selectBannerById(String id) {
		return this.bannerInfoDao.selectBannerById(id);
	}

	/*修改信息*/
	@Override
	public boolean updateBannerByBanner(BannerInfo banner) {
		return this.bannerInfoDao.updateBannerByBanner(banner);
	}

	/**1.APP查询出最新四张Banner图片**/
	@Override
	public List<BannerInfo> selectBannerList(int num) {
		return this.bannerInfoDao.selectBannerList(num);
	}

}

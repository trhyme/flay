package com.java.letch.model;

import java.io.Serializable;

/***
 * 设置信息表类  sets 
 * @author Administrator
 * 2017-09-21
 */
@SuppressWarnings("serial")
public class SetsInfo implements Serializable{
	public SetsInfo(){}
	/*属性*/
	private int id;					//主键
	private String loginnb;			//登录送NB数
	private String ratenb;			//NB和RNB的兑换率
	private String rewardnb;		//发单奖励NB数
	private String rechargearr;		//充值数值固定数组（逗号隔开）
	private String tapnb;			//偷听音频价格
	private String keywords;		//搜索关键字段
	private int isopen;				//
	
	/**新加字段**/
	private String bluebrick;		//蓝砖与1元人民币
	private String redsbrick;		//红砖与1元人民币
	private String bluerates;		//蓝砖与红砖的联合比率
	private String redsrates;		//蓝砖与红砖的联合比率
	/**新加字段**/
	public int getId() {
		return id;
	}
	public String getBluerates() {
		return bluerates;
	}
	public void setBluerates(String bluerates) {
		this.bluerates = bluerates;
	}
	public String getRedsrates() {
		return redsrates;
	}
	public void setRedsrates(String redsrates) {
		this.redsrates = redsrates;
	}
	public String getBluebrick() {
		return bluebrick;
	}
	public void setBluebrick(String bluebrick) {
		this.bluebrick = bluebrick;
	}
	public String getRedsbrick() {
		return redsbrick;
	}
	public void setRedsbrick(String redsbrick) {
		this.redsbrick = redsbrick;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLoginnb() {
		return loginnb;
	}
	public void setLoginnb(String loginnb) {
		this.loginnb = loginnb;
	}
	public String getRatenb() {
		return ratenb;
	}
	public void setRatenb(String ratenb) {
		this.ratenb = ratenb;
	}
	public String getRewardnb() {
		return rewardnb;
	}
	public void setRewardnb(String rewardnb) {
		this.rewardnb = rewardnb;
	}
	public String getRechargearr() {
		return rechargearr;
	}
	public void setRechargearr(String rechargearr) {
		this.rechargearr = rechargearr;
	}
	public String getTapnb() {
		return tapnb;
	}
	public void setTapnb(String tapnb) {
		this.tapnb = tapnb;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public int getIsopen() {
		return isopen;
	}
	public void setIsopen(int isopen) {
		this.isopen = isopen;
	}
	
}

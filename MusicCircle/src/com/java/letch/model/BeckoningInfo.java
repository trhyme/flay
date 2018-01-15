package com.java.letch.model;

import java.io.Serializable;

/***
 * 心动信息配置设置类
 * @author Administrator
 * 22017-09-26
 */
@SuppressWarnings("serial")
public class BeckoningInfo implements Serializable{
	public  BeckoningInfo(){}
	
	/*属性*/
	private int id;				//主键ID
	private int beginnum;		//心动开始值
	private int endnumes;		//心动值范围结束
	private String beckurls;	//效果图
	private String contents;	//可见部分
	private String remark;		//备注
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getBeginnum() {
		return beginnum;
	}
	public void setBeginnum(int beginnum) {
		this.beginnum = beginnum;
	}
	public int getEndnumes() {
		return endnumes;
	}
	public void setEndnumes(int endnumes) {
		this.endnumes = endnumes;
	}
	public String getBeckurls() {
		return beckurls;
	}
	public void setBeckurls(String beckurls) {
		this.beckurls = beckurls;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}

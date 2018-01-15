package com.java.letch.model;

import java.io.Serializable;

/***
 * 广告弹窗信息表
 * @author Administrator
 * 2017-09-22
 */
@SuppressWarnings("serial")
public class AdversInfo implements Serializable{
	public AdversInfo(){}
	//属性
	private int id;				//主键
	private String name;		//名称
	private String pic;			//广告图片
	private String url;			//链接
	private int work;			//是否可用
	private int sort;			//
	
	/**新版字段**/
	private String begintime;	//开始时间
	private String endsstime;	//结束时间
	private int straus;			//谁可见
	
	public int getStraus() {
		return straus;
	}
	public void setStraus(int straus) {
		this.straus = straus;
	}
	public String getBegintime() {
		return begintime;
	}
	public void setBegintime(String begintime) {
		this.begintime = begintime;
	}
	public String getEndsstime() {
		return endsstime;
	}
	public void setEndsstime(String endsstime) {
		this.endsstime = endsstime;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getWork() {
		return work;
	}
	public void setWork(int work) {
		this.work = work;
	}
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
	
}

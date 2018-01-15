package com.java.letch.model;

import java.io.Serializable;

/***
 * 礼物信息数据类
 * @author Administrator
 * 2017-09-20
 */
@SuppressWarnings("serial")
public class GiftsInfo implements Serializable{
	public GiftsInfo(){}
	
	/*属性*/
	private int id;			//主键
	private String name;	//名称
	private String price;	//价格
	private String pic;		//图片
	private int work;		//是否可用
	private int sort;		//
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
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
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

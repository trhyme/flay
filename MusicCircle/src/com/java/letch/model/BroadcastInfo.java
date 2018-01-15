package com.java.letch.model;

import java.io.Serializable;

/***
 * 广播墙信息类
 * @author Administrator
 * 2017-09-27
 */
@SuppressWarnings("serial")
public class BroadcastInfo implements Serializable{
	public BroadcastInfo(){}
	//属性
	private int id;				//主键ID
	private int userid;			//用户ID
	private String datetime;	//添加时间
	private String contents;	//广播墙内容
	private int stratus;		//是否屏蔽：0为屏蔽，1为未屏蔽
	private int delstratu;		//是否删除：0为删除，1为未删除
	private int browsenum;		//浏览量：默认为0
	private String remarks;		//备注
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public String getDatetime() {
		return datetime;
	}
	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public int getStratus() {
		return stratus;
	}
	public void setStratus(int stratus) {
		this.stratus = stratus;
	}
	public int getDelstratu() {
		return delstratu;
	}
	public void setDelstratu(int delstratu) {
		this.delstratu = delstratu;
	}
	public int getBrowsenum() {
		return browsenum;
	}
	public void setBrowsenum(int browsenum) {
		this.browsenum = browsenum;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
}

package com.java.letch.model;

import java.io.Serializable;

/***
 * APP用户的设置消息推送信息
 * @author Administrator
 * 2017-11-07
 */
@SuppressWarnings("serial")
public class UserSetupInfo implements Serializable{
	public UserSetupInfo(){}
	//属性
	private int id;				//主键ID
	private int uid;			//用户的主键ID，外键
	private int setid;			//设置点赞、评论、收藏的消息推送为关闭(1为开启，0为关闭)
	private String addtime;		//添加时间
	private String remarks;		//备注
	
	/*新增字段*/
	private int settype;		//设置提示类型  1表示心动，2表示回复、点赞提醒，3表示签到提醒
	
	
	public int getSettype() {
		return settype;
	}
	public void setSettype(int settype) {
		this.settype = settype;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public int getSetid() {
		return setid;
	}
	public void setSetid(int setid) {
		this.setid = setid;
	}
	public String getAddtime() {
		return addtime;
	}
	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
}

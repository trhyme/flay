package com.java.letch.model;

import java.io.Serializable;

/***
 * 用户举报信息类
 * @author Administrator
 * 2017-10-09
 */
@SuppressWarnings("serial")
public class ReportInfo implements Serializable{
	public ReportInfo(){}
	/*属性*/
	private int id; 			//主键ID
	private int uid;			//举报用户主键
	private String uuid;		//举报用户唯一标示ID
	private String uphone;		//举报用户电话号码
	private String unames;		//举报用户昵称
	private int beuid;			//被举报用户主键
	private String beuuid;		//被举报用户唯一标示ID
	private String beuphone;	//被举报用户电话号码
	private String beunames;	//被举报用户昵称
	private String reptypes;	//举报类型
	private String repconte;	//举报内容(不得超过200个字符)
	private String reppices;	//举报图片集合
	private String addtimes;	//举报时间
	private String replytie;	//后台回复内容
	private String updtimes;	//回复时间
	private int stratues;		//处理结果（0未处理，1已经处理）
	private String remark;		//备注
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
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getUphone() {
		return uphone;
	}
	public void setUphone(String uphone) {
		this.uphone = uphone;
	}
	public String getUnames() {
		return unames;
	}
	public void setUnames(String unames) {
		this.unames = unames;
	}
	public int getBeuid() {
		return beuid;
	}
	public void setBeuid(int beuid) {
		this.beuid = beuid;
	}
	public String getBeuuid() {
		return beuuid;
	}
	public void setBeuuid(String beuuid) {
		this.beuuid = beuuid;
	}
	public String getBeuphone() {
		return beuphone;
	}
	public void setBeuphone(String beuphone) {
		this.beuphone = beuphone;
	}
	public String getBeunames() {
		return beunames;
	}
	public void setBeunames(String beunames) {
		this.beunames = beunames;
	}
	public String getReptypes() {
		return reptypes;
	}
	public void setReptypes(String reptypes) {
		this.reptypes = reptypes;
	}
	public String getRepconte() {
		return repconte;
	}
	public void setRepconte(String repconte) {
		this.repconte = repconte;
	}
	public String getReppices() {
		return reppices;
	}
	public void setReppices(String reppices) {
		this.reppices = reppices;
	}
	public String getAddtimes() {
		return addtimes;
	}
	public void setAddtimes(String addtimes) {
		this.addtimes = addtimes;
	}
	public String getReplytie() {
		return replytie;
	}
	public void setReplytie(String replytie) {
		this.replytie = replytie;
	}
	public String getUpdtimes() {
		return updtimes;
	}
	public void setUpdtimes(String updtimes) {
		this.updtimes = updtimes;
	}
	public int getStratues() {
		return stratues;
	}
	public void setStratues(int stratues) {
		this.stratues = stratues;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}

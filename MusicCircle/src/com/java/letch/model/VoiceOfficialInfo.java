package com.java.letch.model;

import java.io.Serializable;

/***
 * 声音录制文案信息类
 * @author Administrator
 * 2017-10-31
 */
@SuppressWarnings("serial")
public class VoiceOfficialInfo implements Serializable{
	
	public VoiceOfficialInfo(){}
	//属性
	private int id;					//主键ID
	private String title;			//标题
	private String infos;			//内容
	private String times;			//时间
	private int strus;				//是否可用（1可用，0不可用）
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getInfos() {
		return infos;
	}
	public void setInfos(String infos) {
		this.infos = infos;
	}
	public String getTimes() {
		return times;
	}
	public void setTimes(String times) {
		this.times = times;
	}
	public int getStrus() {
		return strus;
	}
	public void setStrus(int strus) {
		this.strus = strus;
	}
	
}

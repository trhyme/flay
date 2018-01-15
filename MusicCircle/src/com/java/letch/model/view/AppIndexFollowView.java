package com.java.letch.model.view;

import java.io.Serializable;

/***
 * APP数据的关注动态逻辑视图
 * @author Administrator
 * 2017-10-12
 */
@SuppressWarnings("serial")
public class AppIndexFollowView implements Serializable{
	public AppIndexFollowView(){}
	/*属性*/
	private int id;				//主键
	private String voiceurl;	//音频视频URL 
	private String pic;			//封面
	private String info;		//说明
	private int playcount;		//播放量
	private int sort;			//排序
	private int work;			//是否可用
	private int categorys_id;	//
	private String types_id;		//动态类型（1为文字动态，2为语音动态，3为图片文字）
	private int users_id;		//用户ID
	private String date;		//发布日期
	private String length;		//声音，视频长度 2.0
	private String title;		//标题 2.0
	
	//新加字段
	private String photo;		//photo照片组,JSON数据格式
	private String address;		//新增发动态的位置信息
	
	private int fabulous;		//赞数
	private int collection;		//收藏数
	private int commentnum;		//评论数
	
	public int whether=0;		//该用户是否点赞
	public int storeup=0;		//是否收藏
	
	private String username;	//昵称
	private String headpic;		//头像
	private String sex;			//性别
	
	//新增是否会员字段
	public int checkmember=0;	//是否会员字段
	
	private int becknum;	//新增心动值字段
	private int mutualnum;	//是否相互关注(1表示互相关注，0表示未互相关注,2表示我关注了对方，3表示对方关注了我)
	
	private int fl;			//关注者的ID
	
	public int getFl() {
		return fl;
	}
	public void setFl(int fl) {
		this.fl = fl;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getFabulous() {
		return fabulous;
	}
	public void setFabulous(int fabulous) {
		this.fabulous = fabulous;
	}
	public int getCollection() {
		return collection;
	}
	public void setCollection(int collection) {
		this.collection = collection;
	}
	public int getCommentnum() {
		return commentnum;
	}
	public void setCommentnum(int commentnum) {
		this.commentnum = commentnum;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getHeadpic() {
		return headpic;
	}
	public void setHeadpic(String headpic) {
		this.headpic = headpic;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public int getBecknum() {
		return becknum;
	}
	public void setBecknum(int becknum) {
		this.becknum = becknum;
	}
	public int getMutualnum() {
		return mutualnum;
	}
	public void setMutualnum(int mutualnum) {
		this.mutualnum = mutualnum;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getVoiceurl() {
		return voiceurl;
	}
	public void setVoiceurl(String voiceurl) {
		this.voiceurl = voiceurl;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public int getPlaycount() {
		return playcount;
	}
	public void setPlaycount(int playcount) {
		this.playcount = playcount;
	}
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
	public int getWork() {
		return work;
	}
	public void setWork(int work) {
		this.work = work;
	}
	public int getCategorys_id() {
		return categorys_id;
	}
	public void setCategorys_id(int categorys_id) {
		this.categorys_id = categorys_id;
	}
	public String getTypes_id() {
		return types_id;
	}
	public void setTypes_id(String types_id) {
		this.types_id = types_id;
	}
	public int getUsers_id() {
		return users_id;
	}
	public void setUsers_id(int users_id) {
		this.users_id = users_id;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getLength() {
		return length;
	}
	public void setLength(String length) {
		this.length = length;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	
}

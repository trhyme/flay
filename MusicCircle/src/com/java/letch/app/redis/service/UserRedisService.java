package com.java.letch.app.redis.service;


import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;

import com.java.letch.app.redis.tools.RedisUtil;
import com.java.letch.app.redis.tools.SerializeUtil;
import com.java.letch.app.redis.view.UserRedisView;

import redis.clients.jedis.Jedis;

/***
 * 用户对REDIS的操作类
 * @author Administrator
 * 2017-10-17
 */
public class UserRedisService {
	
	/**1.将用户信息存入REDIS，Key为用户的APPID**/
	public static boolean insertRedisUser(UserRedisView user,String key){
		try {
			Jedis redis=RedisUtil.getJedis();
			//设置KEY的过期时间
			redis.expire(key.getBytes(), 30*60*60);
			//将对象存储到缓
			Long num=redis.sadd(key.getBytes(), SerializeUtil.serialize(user));
			if(Integer.parseInt(num.toString())>0){
				//将用户的在线状态添加到REDIS
				redis.set(user.getApponlyid(),user.getVoicesurl());
				redis.disconnect();
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}
	
	/****1.1.添加配对信息****/
	
	
	/**2.通过Key值随机获取指定条数的**/
	public static UserRedisView selectRedisUserByKey(String key){
		try {
			Jedis redis=RedisUtil.getJedis();
			UserRedisView uu=(UserRedisView) SerializeUtil.unserialize(redis.srandmember(key.getBytes()));
			//查询获取信息之后，移除
			//deleteRedisViewByKey(key);
	        return uu;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}
	
	/**3.通过KEY删除指定数据**/
	public static boolean deleteRedisViewByKey(String key){
		try {
			Jedis redis=RedisUtil.getJedis();
			Long num=redis.del(key);
			if(Integer.parseInt(num.toString())>0){
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}
	
	
	/*************2.0 52Hz的匹配人数添加到REDIS******************************************/
	public static boolean hertzAddUserView(UserRedisView user,String key){
		try {
			Jedis redis=RedisUtil.getJedis();
			//设置KEY的过期时间
			redis.expire(key.getBytes(), 30*60*60);
			//将对象存储到缓
			Long num=redis.sadd(key.getBytes(), SerializeUtil.serialize(user));
			if(Integer.parseInt(num.toString())>0){
				//将用户的在线状态添加到REDIS
				redis.set(user.getApponlyid(),user.getVoicesurl());
				redis.disconnect();
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}
	
	//2.0 存放单个用户的录音信息
	public static boolean hertzAddUserViewOneUser(UserRedisView user,String key){
		try {
			Jedis redis=RedisUtil.getJedis();
			//设置KEY的过期时间
			redis.expire(key.getBytes(), 30*60*60);
			//将对象存储到缓
			String num=redis.set(key.getBytes(), SerializeUtil.serialize(user));
			redis.disconnect();
			if(num.equals("OK")){
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}
	/**************2.0 52Hz的匹配人数添加REDIS*****************************************/
	
	
	/*************2.0 52Hz的根据编号获取用户数据******************************************/
	public static UserRedisView hertzNewUserSelect(String key){
		try {
			Jedis redis=RedisUtil.getJedis();
			byte[] person = redis.get(key.getBytes());
			//查询获取信息之后，移除
			//deleteRedisViewByKey(key);
			redis.disconnect();
			return (UserRedisView) SerializeUtil.unserialize(person);
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}
	/*************2.0 52Hz的匹配人数添加到REDIS******************************************/
	
	
	public static void main(String[] args) {
		UserRedisView view=hertzNewUserSelect("24156");
		//UserRedisView view=selectRedisUserByKey("女");
		System.out.println(view.getVoicesurl());
		String sex="男";
		UserRedisView view2=selectRedisUserByKey(sex);
		System.out.println(view2);
	}
}

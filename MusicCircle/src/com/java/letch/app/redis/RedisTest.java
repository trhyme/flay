package com.java.letch.app.redis;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Iterator;
import java.util.Set;

import com.java.letch.app.redis.tools.RedisUtil;
import com.java.letch.app.redis.tools.SerializeUtil;
import com.java.letch.model.UsersInfo;
import com.mchange.v2.ser.SerializableUtils;

import redis.clients.jedis.Jedis;

/***
 * REDIS数据库测试连接
 * @author Administrator
 * 2017-10-16
 */ 
public class RedisTest {

	public static void main(String[] args){
		try {
			Jedis jedis = new Jedis("120.77.62.203", 6379);
			jedis.auth("leqtch147852369");
			jedis.set("name", "LEQTCH");
			

			/*System.out.println(jedis.get("name"));
			
			UsersInfo user=new UsersInfo();
			user.setId(123);
			user.setUsername("测试");*/
			/*System.out.println(RedisUtil.getString("name"));
			RedisUtil.setString("one", SerializeUtil.serialize(user).toString());
			System.out.println(RedisUtil.getString("one"));*/
			
			
			//测试
			Jedis redis=RedisUtil.getJedis();
			/*System.out.println(redis.set(SerializeUtil.serialize("one2"), SerializeUtil.serialize(user)));*/
			//System.out.println(RedisUtil.getString("one2"));
			
			
			/*UsersInfo user=new UsersInfo();
			user.setId(12355);
			user.setUsername("测试666");*/
			//System.out.println(redis.sadd("t1".getBytes(), SerializeUtil.serialize(user)));
			//redis.expire("t1".getBytes(), 60*1);
			//System.out.println(RedisUtil.getString("name"));
			//System.out.println(redis.sadd("t1".getBytes(), SerializeUtil.serialize(user)));
			//RedisUtil.setString("one", SerializeUtil.serialize(user).toString());
			//System.out.println(RedisUtil.getString("one"));
			//System.out.println("随机取出一个值:"+redis.srandmember("t1".getBytes()));
			UsersInfo uu=(UsersInfo) SerializeUtil.unserialize(redis.srandmember("t1".getBytes()));
	        System.out.println(uu.getUsername());
			/*
			//redis.sadd("city", "北京","上海","重庆","武汉");  
	        //System.out.println("取出最上面的："+redis.spop("city"));  
	        
	        System.out.println("随机取出一个值2:"+SerializeUtil.unserialize(redis.srandmember("t1".getBytes())));
	        //redis.srandmember(key)
	        UsersInfo uu=(UsersInfo) SerializeUtil.unserialize(redis.srandmember("t1".getBytes()));
	        System.out.println(uu.getUsername());
	        
	        /*ByteArrayInputStream bis = new ByteArrayInputStream(redis.srandmember("t1").getBytes());
	        ObjectInputStream inputStream = new ObjectInputStream(bis);
	        inputStream.close();
	        bis.close();
	        redis.disconnect();
	        UsersInfo readObject = (UsersInfo) inputStream.readObject();
	        System.out.println("结果：\t" + readObject.toString());
	        
	        System.out.println("键： \t"+"-----"+ readObject.getId()+":"+readObject.getUsername());
			
			*/
			// 下面是对对象进行存储的测试代码
			
			// 获取所有key  
		    /*Set<byte[]> keySet = redis.keys("user*".getBytes());  
		    System.out.println(keySet.size());
			Iterator<String> it = redis.keys("userone").iterator();  
			while (it.hasNext()) {  
			    String key = it.next();  
			    System.out.println(key);
			    // 清空缓存中记录的数据  
			    //redis. 
			    byte[] bs = redis.get(key.getBytes());
		        ByteArrayInputStream bis = new ByteArrayInputStream(bs);
		        ObjectInputStream inputStream = new ObjectInputStream(bis);
		        inputStream.close();
		        bis.close();
		        redis.disconnect();
		        UsersInfo readObject = (UsersInfo) inputStream.readObject();
		        System.out.println("结果：\t" + readObject.toString());
		        
		        System.out.println("键： \t"+key+"-----"+ readObject.getId()+":"+readObject.getUsername());
			}  
			*/
			
	        /*ByteArrayOutputStream bos = new ByteArrayOutputStream();
	        ObjectOutputStream oos = new ObjectOutputStream(bos);
	        oos.writeObject(user);
	        byte[] byteArray = bos.toByteArray();
	        oos.close();
	        bos.close();
	        String setObjectRet = redis.set("userone2".getBytes(), byteArray);
	        System.out.println(" set object return \t" + setObjectRet);
	        byte[] bs = redis.get("userone2".getBytes());
	        ByteArrayInputStream bis = new ByteArrayInputStream(bs);
	        ObjectInputStream inputStream = new ObjectInputStream(bis);
	        inputStream.close();
	        bis.close();
	        redis.disconnect();
	        UsersInfo readObject = (UsersInfo) inputStream.readObject();
	        System.out.println(" read object \t" + readObject.toString());
	        
	        System.out.println(" read object \t" + readObject.getId()+":"+readObject.getUsername());*/
	        
	        
		} catch (Exception e) {
			System.out.println(e);
		}
		
		
		
	}
	
}

package com.java.letch.thread;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.java.letch.service.UsersInfoService;

/***
 * 线程发送短信信息
 * @author Administrator
 * 2017-10-10
 */
@Repository(value="SendMessageThread")
public class SendMessageThread implements Runnable{

	@Resource(name="UsersInfoService")
	private UsersInfoService usersinfoService;		//APP用户的数据业务操作类
	
	//开启线程
	@Override
	public void run() {
		//查询出用户信息
		List<String> list=this.usersinfoService.selectAllPhone();
		if(list.size()>0){
			//分段发送验证码
			List<String> nums=new ArrayList<>();
			StringBuffer str=new StringBuffer();
			int count=list.size()/90;
			if(list.size()<=90){		//不够一个组合
				for(int i=0;i<list.size();i++){
					str.append(list.get(i)).append(",");
				}
				nums.add(str.toString().substring(0, str.toString().length()-1));
			}else{
				for(int x=1;x<=count;x++){
					str=new StringBuffer();
					for(int y=x-1;y<x*90;y++){
						str.append(list.get(y)).append(",");
					}
					nums.add(str.toString().substring(0, str.toString().length()-1));
				}
				str=new StringBuffer();
				for(int z=90*count;z<list.size();z++){
					str.append(list.get(z)).append(",");
				}
				nums.add(str.toString().substring(0, str.toString().length()-1));
			}
			//循环发送验证码
			if(nums.size()>0){
				for(int v=0;v<nums.size();v++){
					System.out.println(nums.get(v));	
				}
			}
			
		}
		
	}

	/**传递参数**/
	public void doRunThread(String message){
		run();
	}
	
}

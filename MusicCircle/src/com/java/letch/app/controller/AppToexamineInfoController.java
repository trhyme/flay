package com.java.letch.app.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.java.letch.app.returnmodel.AppReturnCodeInfo;
import com.java.letch.model.ToexamineInfo;
import com.java.letch.model.UsersInfo;
import com.java.letch.service.ToexamineInfoService;
import com.java.letch.service.UsersInfoService;
import com.java.letch.tools.BannerImageOSSTools;
import com.java.letch.tools.DateHelper;

/***
 * 用户信息审核认证APP请求接口
 * @author Administrator
 * 2017-10-18
 */
@Controller
public class AppToexamineInfoController {

	@Resource(name="ToexamineInfoService")
	private ToexamineInfoService toexamineinfoService;		//审核信息类
	
	@Resource(name="UsersInfoService")
	private UsersInfoService userinfoService;				//APP数据操作类信息
	
	private Map<String, Object> map = new HashMap<String, Object>();
	private AppReturnCodeInfo app=new AppReturnCodeInfo();
	
	//JSON数据转换工具
	private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").serializeNulls().create();
		
	/**1.用户上传认证的真实头像照片**/
	@RequestMapping(value="certpicauthuplod",method=RequestMethod.POST,produces="text/json;charset=UTF-8")
	@ResponseBody
	public String certpicAuthLoad(@RequestParam(value="certpicName") MultipartFile certpicName,String appid) throws Exception{
		try {
			map.clear();
			BannerImageOSSTools ban=new BannerImageOSSTools();
		    String head = ban.updateHead(certpicName, 4);//此处是调用上传服务接口，4是需要更新的userId 测试数据。
		    map.put("datasrc", head);
		    System.out.println(head);
		    //查询用户数据
		    //查询出资料信息
			UsersInfo user=this.userinfoService.selectAppUserByAPPid(appid);
			if(user!=null){
				ToexamineInfo toexa=new ToexamineInfo();
				toexa.setUid(user.getId());
				toexa.setUuid(user.getNum());
				toexa.setUphone(user.getPhone());
				toexa.setUnames(user.getUsername());
				toexa.setUsex(user.getSex());
				toexa.setCertpic(head);
				toexa.setAddtime(DateHelper.getNewData());
				toexa.setStratus(0);
				toexa.setDelstra(1);
				//调用添加的方法
				if(this.toexamineinfoService.insertToexamOne(toexa)){
					app.setCode(200);
					app.setMessage("认证头像上传成功");
					map.put("certpic", head);
				}else{
					app.setCode(300);
					app.setMessage("认证头像上传失败");
				}
			}else{
				app.setCode(300);
				app.setMessage("认证头像上传失败");
			}
		} catch (Exception e) {
			app.setCode(300);
			app.setMessage("认证头像上传失败");
		}
		return gson.toJson(app);
	}
	/**1.用户上传认证的真实头像照片**/
	
	/**2.用户上传认证的真实照片【多照片上传】**/
	@RequestMapping(value="photoauthuplod",method=RequestMethod.POST,produces="text/json;charset=UTF-8")
	@ResponseBody
	public String userAuthorPhotoUpload(@RequestParam(value="photolist") MultipartFile[] photolist,String appid,String userid) throws Exception{
		try {
			System.out.println("OK");
			//遍历文件集合
			if(null != photolist && photolist.length > 0){  
				//保存图片信息
				List<String> list=new ArrayList<>();
	            //遍历并保存文件  
	            for(MultipartFile file : photolist){  
	            	BannerImageOSSTools ban=new BannerImageOSSTools();
	    		    String head = ban.updateHead(file, 4);//此处是调用上传服务接口，4是需要更新的userId 测试数据。
	    		    list.add(head);
	            }
	            String str=gson.toJson(list);
	            if(list.size()>0){
	            	str=str.replace("\"", "");
	            	str=str.replace("[", "");
	            	str=str.replace("]", "");
	            }
	            map.clear();
	            //修改数据
	            System.out.println("多图片数据:"+str);
	            if(this.toexamineinfoService.updatePiclistBy(userid, str)){
	            	app.setCode(200);
	            	app.setMessage("认证照片上传成功");
	            	map.put("piclist", gson.toJson(list));
	            	app.setMap(map);
	            }else{
	            	app.setCode(300);
	            	app.setMessage("认证照片上传失败");
	            	map.put("piclist", null);
	            }
	        } 
		} catch (Exception e) {
			app.setCode(300);
        	app.setMessage("认证照片上传失败");
		}
		return gson.toJson(app);
	}
	/**2.用户上传认证的照片【多照片上传】**/
	
	
	/***3.用户获取审核照片信息***/
	@RequestMapping(value="selectphotoauth",method=RequestMethod.POST,produces="text/json;charset=UTF-8")
	@ResponseBody
	public String selectAuthorPhotoByUser(String appid,int userid) throws Exception{
		map.clear();
		try {
			ToexamineInfo toex=this.toexamineinfoService.selectToexamByUseridAll(userid);
			app.setCode(200);
			if(toex==null){
				map.put("stratus", -1);
				map.put("info", "未提交审核数据");
				app.setMessage("未提交审核数据");
				map.put("certpic", null);	//头像
				map.put("piclist", null);	//展示照片集合
			}else{
				map.put("stratus", toex.getStratus());
				int stra=toex.getStratus();
				map.put("certpic", toex.getCertpic());	//头像
				map.put("piclist", toex.getPiclist());	//展示照片集合
				if(stra==0){
					map.put("info", "待审核");
					app.setMessage("待审核");
				}else if(stra==1){
					map.put("info", "审核通过");
					app.setMessage("审核通过");
				}else if(stra==2){
					app.setMessage("审核未通过");
					map.put("info", "审核未通过");
				}
			}
		} catch (Exception e) {
			app.setCode(300);
			map.put("stratus", -1);
			map.put("info", "未提交审核数据");
			map.put("certpic", null);	//头像
			map.put("piclist", null);	//展示照片集合
		}
		app.setMap(map);
		return gson.toJson(app);
	}
	/***3.用户获取审核照片信息***/
	
	
	
}

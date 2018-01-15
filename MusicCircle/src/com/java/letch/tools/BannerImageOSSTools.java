package com.java.letch.tools;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;import com.java.letch.entity.FinalUtlIMG;


/***
 * Banner图上传到阿里云OSS的请求工具类
 * @author Administrator
 * 2017-09-19
 */
public class BannerImageOSSTools {
	
	//图片文件上传到OSS
	public String updateHead(MultipartFile file, long userId) throws IOException{
		OSSClientUtil ossClient=new OSSClientUtil();
	    if (file == null || file.getSize() <= 0) {
	      System.out.println("图片不能为空");
	    }
	    
	    String name = ossClient.uploadImg2Oss(file);
	    String imgUrl = ossClient.getImgUrl(name);
	    System.out.println("图片名称："+name);
	    //绑定自定义域名
	    StringBuffer imgURL_two=new StringBuffer(FinalUtlIMG.CUSTOMURL);
	    imgURL_two.append(FinalUtlIMG.FILEDIR).append(name);
	    System.out.println("图片URL1："+imgUrl);
	    System.out.println("图片URL2："+imgURL_two.toString());
	    return imgURL_two.toString();
	}
	
	//声音文件上传到OSS的voices文件夹中
	public String updateHead(MultipartFile file, long userId,String wenjian,int x) throws IOException{
		OSSClientUtil ossClient=new OSSClientUtil();
	    if (file == null || file.getSize() <= 0) {
	      System.out.println("声音不能为空");
	    }
	    
	    String name = ossClient.uploadImg2OssVoice(file);
	    String imgUrl = ossClient.getVoicesUrl(name);
	    System.out.println("声音名称："+name);
	    //绑定自定义域名
	    StringBuffer imgURL_two=new StringBuffer(FinalUtlIMG.CUSTOMURL);
	    imgURL_two.append(FinalUtlIMG.VOICES).append(name);
	    System.out.println("声音URL1："+imgUrl);
	    System.out.println("声音URL2："+imgURL_two.toString());
	    return imgURL_two.toString();
	}
	
	
	//图片文件上传到OSS
	public String updateHead(MultipartFile file,long userId, String appid) throws IOException{
		OSSClientUtil ossClient=new OSSClientUtil();
	    if (file == null || file.getSize() <= 0) {
	      System.out.println("图片不能为空");
	    }
	    
	    String name = ossClient.uploadImg2Oss(file,appid);
	    String imgUrl = ossClient.getImgUrl(name);
	    System.out.println("图片名称："+name);
	    //绑定自定义域名
	    StringBuffer imgURL_two=new StringBuffer(FinalUtlIMG.CUSTOMURL);
	    imgURL_two.append(FinalUtlIMG.FILEDIR).append(name);
	    System.out.println("图片URL1："+imgUrl);
	    System.out.println("图片URL2："+imgURL_two.toString());
	    return imgURL_two.toString();
	}	
	
	/**文件上传到OSS**/
	public String uploadFile(InputStream instream, String fileName){
		OSSClientUtil ossClient=new OSSClientUtil();
		String retuen=ossClient.uploadFile2OSS(instream, fileName);
		System.out.println(retuen);
		//封装数据
		
		return retuen;
	}
	
}

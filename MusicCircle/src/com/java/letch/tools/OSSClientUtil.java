package com.java.letch.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.Random;

import org.springframework.web.multipart.MultipartFile;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.alibaba.druid.util.StringUtils;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;

/**OSS文件类**/
public class OSSClientUtil {
	Log log = LogFactory.getLog(OSSClientUtil.class);
	// endpoint以杭州为例，其它region请按实际情况填写
	private String endpoint = "http://oss-cn-shenzhen.aliyuncs.com/";
	// accessKey
	private String accessKeyId = "Mykcxmm4cH7qerEb"; //LTAIIxb8o3y4RGxR
	private String accessKeySecret = "L1d3qIIKk8qDHhe0rnsDfplhMvHTuk";  //LauibD8N8PSEw1kSqzBUXurAMdaVcm
	//空间
	private String bucketName = "chandoump4";
	//文件存储目录
	private String filedir = "images/";
	private String filedirvoice = "voice/";
	
	private OSSClient ossClient;
	
	public OSSClientUtil() {
	  ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
	}
	
	/**
	 * 初始化
	 */
	public void init() {
	  ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
	}
	
	/**
	 * 销毁
	 */
	public void destory() {
	  ossClient.shutdown();
	}
	
	/**
	 * 上传图片
	 *
	 * @param url
	 */
	public void uploadImg2Oss(String url) {
	  File fileOnServer = new File(url);
	  FileInputStream fin;
	  try {
	    fin = new FileInputStream(fileOnServer);
	    String[] split = url.split("/");
	    this.uploadFile2OSS(fin, split[split.length - 1]);
	   // this.uploadFile2OSS(fin, split[split.length - 1]);
	  } catch (FileNotFoundException e) {
	    System.out.println("图片上传失败");
	  }
	}
	
	
	public String uploadImg2Oss(MultipartFile file) {
	  if (file.getSize() > 1024 * 1024*100) {
	     System.out.println("上传图片大小不能超过100M！");
	  }
	  String originalFilename = file.getOriginalFilename();
	  String substring = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
	  Random random = new Random();
	  //String name = random.nextInt(10000) + System.currentTimeMillis() + substring;
	  String name = UuidGenerate.getUUID() + substring;
	  //name=originalFilename;
	  try {
	    InputStream inputStream = file.getInputStream();
	    this.uploadFile2OSS(inputStream, name);
	    return name;
	  } catch (Exception e) {
	    System.out.println("图片上传失败");
	    return "";
	  }
	}
	
	//上传声音文件
	public String uploadImg2OssVoice(MultipartFile file) {
		  if (file.getSize() > 1024 * 1024*100) {
		     System.out.println("上传图片大小不能超过100M！");
		  }
		  String originalFilename = file.getOriginalFilename();
		  String substring = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
		  Random random = new Random();
		  //String name = random.nextInt(10000) + System.currentTimeMillis() + substring;
		  String name = UuidGenerate.getUUID() + substring;
		  //name=originalFilename;
		  try {
		    InputStream inputStream = file.getInputStream();
		    
		    this.uploadFile2OSSVoices(inputStream, name);
		    System.out.println("声音上传成功");
		    return name;
		  } catch (Exception e) {
		    System.out.println("声音上传失败");
		    return "";
		  }
	}
	//上传声音文件
	
	/**头像上传方法**/
	public String uploadImg2Oss(MultipartFile file,String userid) {
		  if (file.getSize() > 1024 * 1024*8) {
		     System.out.println("上传图片大小不能超过8M！");
		  }
		  String originalFilename = file.getOriginalFilename();
		  String substring = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
		  Random random = new Random();
		  //String name = random.nextInt(10000) + System.currentTimeMillis() + substring;
		  String name = userid + substring;
		  //name=originalFilename;
		  try {
		    InputStream inputStream = file.getInputStream();
		    this.uploadFile2OSS(inputStream, name);
		    return name;
		  } catch (Exception e) {
		    System.out.println("图片上传失败");
		    return "";
		 }
	}
	
	/**
	 * 获得图片路径
	 *
	 * @param fileUrl
	 * @return
	 */
	public String getImgUrl(String fileUrl) {
	  if (!StringUtils.isEmpty(fileUrl)) {
	    String[] split = fileUrl.split("/");
	    return this.getUrl(this.filedir + split[split.length - 1]);
	  }
	  return null;
	}
	
	/**
	 * 获得声音文件路径
	 *
	 * @param fileUrl
	 * @return
	 */
	public String getVoicesUrl(String fileUrl) {
	  if (!StringUtils.isEmpty(fileUrl)) {
	    String[] split = fileUrl.split("/");
	    return this.getUrl(this.filedirvoice + split[split.length - 1]);
	  }
	  return null;
	}
	
	/**
	 * 上传到OSS服务器  如果同名文件会覆盖服务器上的
	 *
	 * @param instream 文件流
	 * @param fileName 文件名称 包括后缀名
	 * @return 出错返回"" ,唯一MD5数字签名
	 */
	public String uploadFile2OSS(InputStream instream, String fileName) {
	  String ret = "";
	  try {
	    //创建上传Object的Metadata 
	    ObjectMetadata objectMetadata = new ObjectMetadata();
	    objectMetadata.setContentLength(instream.available());
	    objectMetadata.setCacheControl("no-cache");
	    objectMetadata.setHeader("Pragma", "no-cache");
	    objectMetadata.setContentType(getcontentType(fileName.substring(fileName.lastIndexOf("."))));
	    objectMetadata.setContentDisposition("inline;filename=" + fileName);
	    //上传文件
	    PutObjectResult putResult = ossClient.putObject(bucketName, filedir + fileName, instream, objectMetadata);
	    ret = putResult.getETag();
	    
	  } catch (IOException e) {
	    log.error(e.getMessage(), e);
	  } finally {
	    try {
	      if (instream != null) {
	        instream.close();
	      }
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
	  }
	  return ret;
	}
	
	//上传声音文件
	public String uploadFile2OSSVoices(InputStream instream, String fileName) {
		  String ret = "";
		  try {
		    //创建上传Object的Metadata 
		    ObjectMetadata objectMetadata = new ObjectMetadata();
		    objectMetadata.setContentLength(instream.available());
		    objectMetadata.setCacheControl("no-cache");
		    objectMetadata.setHeader("Pragma", "no-cache");
		    System.out.println("名称："+fileName);
		    System.out.println("后缀名："+getcontentType(fileName.substring(fileName.lastIndexOf(".")+1)));
		    System.out.println("截取："+fileName.substring(fileName.lastIndexOf(".")+1));
		    objectMetadata.setContentType(getcontentType(fileName.substring(fileName.lastIndexOf(".")+1)));
		    objectMetadata.setContentDisposition("inline;filename=" + fileName);
		    //上传文件
		    PutObjectResult putResult = ossClient.putObject(bucketName, filedirvoice + fileName, instream, objectMetadata);
		    ret = putResult.getETag();
		    
		  } catch (IOException e) {
		    log.error(e.getMessage(), e);
		  } finally {
		    try {
		      if (instream != null) {
		        instream.close();
		      }
		    } catch (IOException e) {
		      e.printStackTrace();
		    }
		  }
		  return ret;
		}
	//上传声音文件
	
	/**
	 * Description: 判断OSS服务文件上传时文件的contentType
	 *
	 * @param FilenameExtension 文件后缀
	 * @return String
	 */
	public static String getcontentType(String FilenameExtension) {
	  if (FilenameExtension.equalsIgnoreCase("bmp")) {
	    return "image/bmp";
	  }
	  if (FilenameExtension.equalsIgnoreCase("gif")) {
	    return "image/gif";
	  }
	  if (FilenameExtension.equalsIgnoreCase("jpeg") ||
	      FilenameExtension.equalsIgnoreCase("jpg") ||
	      FilenameExtension.equalsIgnoreCase("png")) {
	    return "image/jpeg";
	  }
	  if (FilenameExtension.equalsIgnoreCase("html")) {
	    return "text/html";
	  }
	  if (FilenameExtension.equalsIgnoreCase("txt")) {
	    return "text/plain";
	  }
	  if (FilenameExtension.equalsIgnoreCase("vsd")) {
	    return "application/vnd.visio";
	  }
	  if (FilenameExtension.equalsIgnoreCase("pptx") ||
	      FilenameExtension.equalsIgnoreCase("ppt")) {
	    return "application/vnd.ms-powerpoint";
	  }
	  if (FilenameExtension.equalsIgnoreCase("docx") ||
	      FilenameExtension.equalsIgnoreCase("doc")) {
	    return "application/msword";
	  }
	  if (FilenameExtension.equalsIgnoreCase("xml")) {
	    return "text/xml";
	  }
	  if(FilenameExtension.equalsIgnoreCase("mp3")){
		  return "audio/mp3";
	  }
	  if(FilenameExtension.equalsIgnoreCase("mp4")){
		  return "video/mpeg4";
	  }
	  return "image/jpeg";
	}
	
	/**
	 * 获得url链接
	 *
	 * @param key
	 * @return
	 */
	public String getUrl(String key) {
	  // 设置URL过期时间为10年  3600l* 1000*24*365*10
	  Date expiration = new Date(new Date().getTime() + 3600l * 1000 * 24 * 365 * 10);
	  // 生成URL
	  URL url = ossClient.generatePresignedUrl(bucketName, key, expiration);
	  if (url != null) {
	    return url.toString();
	  }
	  return null;
	}
}

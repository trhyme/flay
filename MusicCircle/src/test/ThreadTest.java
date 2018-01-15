package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class ThreadTest {
	public static void main(String[] args) throws Exception{
		//http://app.leqtch.com/MusicCircle/appindexdata?appid=474f9d0bc87849969fe6645ec3927d01&userid=24146
		String urlStr="http://www.kg.com/baseinfo/getfriendlyAll";
		//127.0.0.1:8080/MusicCircle/admin_login
		//http://127.0.0.1:8080/MusicCircle/threaddemo
		
		for(int i=0;i<5000000;i++){
			URL url = new URL(urlStr);  
	        URLConnection urlConnection = url.openConnection(); // 打开连接  
	        
	        System.out.println(urlConnection.getURL().toString());  
	  
	        BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "utf-8")); // 获取输入流  
	        String line = null;  
	        StringBuilder sb = new StringBuilder();  
	        while ((line = br.readLine()) != null)  
	        {  
	            sb.append(line + "\n");  
	        }  
	        br.close();  
	        System.out.println(i+"："+sb.toString());  
		}
		
		
      
	}
	
	
	
	
   
}

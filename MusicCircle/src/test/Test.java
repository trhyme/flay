package test;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.java.letch.model.UsersInfo;
import com.java.letch.tools.UuidGenerate;

import antlr.StringUtils;

/**测试类**/
public class Test {
	
	public static void main(String[] args) {
		
		/*//JSON数据转换工具
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").serializeNulls().create();
		
		String json="{'id':24146,'num':'274834353','phone':'18723911401','password':'MTIzNDU2','username':'Sunrise','headpic':null,'sex':null,'birth':null,'province':null,'city':null,'area':null,'sign':null,'money':null,'omsg':0,'umsg':0,'auth':1,'date':'2017-09-19 10:33:05','recharge':null,'openid':null,'price':null,'contribution':0,'ischeck':0,'types_id':null,'android_ios':null,'apponlyid':'94a5823a7b3a4051b346d6e3246abbdd','constellation':null,'height':null,'workes':null}";
		
		UsersInfo user=gson.fromJson(json, UsersInfo.class);
		
		System.out.println(user.getId());
		
		System.out.println(UuidGenerate.getUUID());*/
		/*try {
			Date nowDate=new Date();//当前时间\r 
			long nowTime=nowDate.getTime(); 
			System.out.println(nowTime);
			Date date2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2017-10-16 14:30:01");
			
			long lastTime=date2.getTime();//以前的时间\r 
			System.out.println(lastTime);
			long time=nowTime-lastTime;//时间相减比较。 
			System.out.println(time);
			if(time>(long)60000*6){
				System.out.println(time);
			}//1分钟{} 
		} catch (Exception e) {
			System.out.println(e);
		}*/
		
		
		 String[] array = {"a","b","c","c","d","e","e","e","a"};  
	        Set<String> set = new HashSet<>();  
	        for(int i=0;i<array.length;i++){  
	            set.add(array[i]);  
	        }  
	        //set.remove("a");
	        String[] arrayResult = (String[]) set.toArray(new String[set.size()]);  
	        System.out.println(Arrays.toString(arrayResult));  
	        String strID=Arrays.toString(arrayResult);
			strID=strID.replace("[", "");
			strID=strID.replace("]", "");
			System.out.println(strID);
		
	}
	
	public static int getMinutesDiff(String startDate,String endDate){
        int ret=0;
        
            String startDateStr[]=startDate.split(":");
            String endDateStr[]=endDate.split(":");
            if(startDateStr[0].startsWith("0")){
                startDateStr[0]=startDateStr[0].substring(1); 
            }
            if(startDateStr[1].startsWith("0")){
                startDateStr[1]=startDateStr[1].substring(1); 
            }
            if(endDateStr[0].startsWith("0")){
                endDateStr[0]=endDateStr[0].substring(1); 
            }
            if(endDateStr[1].startsWith("0")){
                endDateStr[1]=endDateStr[1].substring(1); 
            }
            int s=Integer.parseInt(startDateStr[0])*60+Integer.parseInt(startDateStr[1]);
            int e=Integer.parseInt(endDateStr[0])*60+Integer.parseInt(endDateStr[1]);
            ret=e-s;
     
        return ret;
        
   }
	
}

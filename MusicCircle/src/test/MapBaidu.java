package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

/***
 * 通过IP地址获取经纬度[百度API]
 * @author Administrator
 * 2017-12-06
 */
public class MapBaidu {
	
	public static void main(String[] args) {
		//219.136.134.157
		String[] s=getIPXY("219.136.134.157");
		System.out.println("经度："+s[0]+",维度："+s[1]);
	}
	
	public static String[] getIPXY(String ip) {

		//百度API申请的KEY
		String ak = "adSON2fE9GRIacyodV4uiCmvCCoK9L4A";
		if (null == ip) {
			ip = "";
		}

		//通过IP地址获取经纬度[百度API]
		try {

			URL url = new URL("http://api.map.baidu.com/location/ip?ak=" + ak
			+ "&ip=" + ip + "&coor=bd09ll");
			InputStream inputStream = url.openStream();
			InputStreamReader inputReader = new InputStreamReader(inputStream);
			BufferedReader reader = new BufferedReader(inputReader);
			StringBuffer sb = new StringBuffer();
			String str;
			do {
				str = reader.readLine();
				sb.append(str);
			} while (null != str);
	
	
			str = sb.toString();
			if (null == str || str.isEmpty()) {
				return null;
			}
	
	
			// 获取坐标位子
			int index = str.indexOf("point");
			int end = str.indexOf("}}", index);
	
	
			if (index == -1 || end == -1) {
				return null;
			}
	
	
			str = str.substring(index - 1, end + 1);
			if (null == str || str.isEmpty()) {
				return null;
			}
	
	
			String[] ss = str.split(":");
			if (ss.length != 4) {
				return null;
			}
			String x = ss[2].split(",")[0];
			String y = ss[3];
			x = x.substring(x.indexOf("\"") + 1, x.indexOf("\"", 1));
			y = y.substring(y.indexOf("\"") + 1, y.indexOf("\"", 1));
			return new String[] { x, y };

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}

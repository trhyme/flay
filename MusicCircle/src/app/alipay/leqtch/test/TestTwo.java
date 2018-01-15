package app.alipay.leqtch.test;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.alipay.api.internal.util.AlipaySignature;

import app.alipay.leqtch.AlipayConfig;
import app.alipay.leqtch.AlipayCore;
import app.alipay.leqtch.UtilDate;

public class TestTwo {
	
	public static void main(String[] args) {
		try {
			JSONObject jo=new JSONObject();  
		//公共参数  
        Map<String, String> map = new HashMap<String, String>();  
        map.put("app_id", AlipayConfig.app_id);  
        map.put("method", "alipay.trade.app.pay");  
        map.put("format", "json");  
        map.put("charset", "UTF-8");  
        map.put("sign_type", AlipayConfig.sign_type);  
        map.put("timestamp",UtilDate.getDateFormatter());  
        System.out.println(UtilDate.getDateFormatter());
        map.put("version", "1.0");  
        map.put("notify_url", AlipayConfig.service);  
        Map<String, String> m = new HashMap<String, String>();  
        m.put("body", "购买会员过程");//对一笔交易的具体描述信息。如果是多种商品，请将商品描述字符串累加传给body。  
        m.put("subject", "购买会员");//商品的标题/交易标题/订单标题/订单关键字等。  
        m.put("out_trade_no","asa123456");//商户网站唯一订单号  
        m.put("timeout_express", "30m");//设置超时时间  
        m.put("total_amount", "22");//价格  
        m.put("seller_id", AlipayConfig.partner);//收款商户支付宝id  
        m.put("product_code", "QUICK_MSECURITY_PAY");  
        JSONObject bizcontentJson=new  JSONObject(m);  
  
        map.put("biz_content", bizcontentJson.toString());  
       //对未签名原始字符串进行签名         
       String rsaSign=null;  
       String json4=null;  
            rsaSign = AlipaySignature.rsaSign(map, AlipayConfig.private_key, "utf-8");  
        Map<String, String> map4 = new HashMap<String, String>();  
  
        map4.put("app_id", AlipayConfig.app_id);  
        map4.put("method", "alipay.trade.app.pay");  
        map4.put("format", "json");  
        map4.put("charset", "utf-8");  
        //map4.put("sign_type",AlipayConfig.sign_type );  
         
        map4.put("timestamp", URLEncoder.encode(UtilDate.getDateFormatter(),"UTF-8"));  
          
        map4.put("version", "1.0");  
        map4.put("notify_url",  URLEncoder.encode(AlipayConfig.service,"UTF-8"));  
        //最后对请求字符串的所有一级value（biz_content作为一个value）进行encode，编码格式按请求串中的charset为准，没传charset按UTF-8处理  
        map4.put("biz_content", URLEncoder.encode(bizcontentJson.toString(), "UTF-8"));  
        Map par = AlipayCore.paraFilter(map4); //除去数组中的空值和签名参数  
        json4= AlipayCore.createLinkString(par); //拼接后的字符串 
        System.out.println("JSON4:"+json4);
        System.out.println("结果："+rsaSign);
        json4=json4 +"&sign_type=RSA2&sign=" + URLEncoder.encode(rsaSign, "UTF-8"); 
        System.out.println(json4);
            jo.put("info", json4);  
            System.out.println(jo.toString());
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
	}
	
}

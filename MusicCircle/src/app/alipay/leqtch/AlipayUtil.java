package app.alipay.leqtch;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;

/***
 * 支付宝支付
 * @author Administrator
 * 2017-10-30
 */
public class AlipayUtil {
	
	//APPID:【沙箱：2016090800466212】
	//APPID:【2017102509507627】
	private static String APP_ID="2017102509507627";
	
	//PAY网关
	//https://openapi.alipay.com/gateway.do
	//https://openapi.alipaydev.com/gateway.do
	private static String PAY_URL="/https://openapi.alipay.com/gateway.do";
	
	private static String APP_PRIVATE_KEY="MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCnpKzgqVUYI5AJDmJVGX6qFjbtV+Nr7gZ62vawpyodY0tu6cC9C/kYEw8gToTFNABe18m5NrXiSwVV8r0E7EteUZKGXN4RF0It8mp1vAflg5xxk405LyIG/kaC5xQWJMM0GNQI5dkOR5Vz7K+hhqQESTcA26riZt6sXZk2plgEzPSacf88zVYIbWMlghlFABAtcE6t5tqv/teAYEgqtc/z+e/I/KOsZJtTnPN2Ex4/gwOFIcwAzGrsE+ipHGdmfsn6JGU3KFTC5lG7/gs4C3S6Qumrn3LldHBHL8ALAmVSjdFhV9pqKAj9kX7dWOe/EfJUBZ1RebL8mVGFeXchfblvAgMBAAECggEBAJVxcUMc8GSCX0YTcokWHxoUj4Zo0K1IFLK0OzEi86IbAAWxj0V3ROi3JMbR9/2r66CgkIzxPpap9em2Z9XZpx4/bXUlmDh4GWDINysZIZjyCCYfOMd/QsAlMEpFIFzVDMaGU45TB3l5Srt6hN5jHhdMBSJYsC7/MQVKsuhKxTAQ65lWeHXLFUnFr3Ce4a9YrAL25rnQ/Cmn9BT7luZg646tmrwchIAIf+CB9I0YH6yeMLyCvUqo01K7OE0VI+Us5h0LCtu+QrX1IoKy8I87GTPC3uuTwzHipqzn2eE5jCt0Hi5iObHICuyS/eeV80S1KodaqlHVvmXoctnv6qZzJPECgYEA0yCJz52apzoOH5fh0FgxyNoQ+1GLbv4Dqfyb5A9x/SIzfaYI91569SebyFBUmF6R+rA0J7RZ2LioKHGjlNevMB0qJMGn65PPsUePnfqqRakr3HjCuyeQazMBdDlf3kTjfm6LY0y8EQuWVBsa9b6VHc35295CEce5PH6Wol4J6RUCgYEAy0YsgDr32LhBuXgXjOVvEABSiCRiQ+wVtP/SC6vBPitT3IfTo3xLk5IWF4eHKtP9PI+/vN3CjMBv6vuZmEtp5mXdBl5yplHEez6NxPcB9Er1Db+z23/sIfdhcDpUeseWqxn9HOisZ+Q/ovyyglkSB2C3L6ujdXEJBfyKmCj5MXMCgYBDnAjlxoevA6VgMqgqqwOOWV7UzGFFcuzDc9SMLPII6PnqQ9BdPxFLDpsRg8ADZ02CJQkeqd+XylJ3WSTUVlmQo9ZKHQXkJ9p73/m60nnSgwE7wiRCAMJ7wcK2cGUl7ZOYelgCvaEAKGDEoEV66sQf3kUkRsGLphTIrZUZe9nanQKBgGKIqZme+/U5m+JjrEbIEIYZIeiMenWiQwNx3kd6Ajo4lnYYAScGYTwvXDYbpBHGoMJyi+bpOB0ySw4E1/dS1wqv5evobHrit/Nl5yLK+U+J5i+79Vs5LyVn3PuhysGZbheSiXcx8iCKK669/2nf8zVQVHvvH1UWbeFRAX2wntqxAoGAapFeTP47F7rSzV57Y9YP2jda2qXaci4I9Otgc3SEYoW34diQJPDu4mvErK/oGhIr6RVAoDJyum71al04YDX1/0nLX/Zs6Blgfog5NFoF6h7kPWnT/0MnzLi7fxGZBEKRt/p/Ez+kEcAR+in0L1RCCvOhG7ZuI/MkuVXzItCzqFU=";
	
	private static String CHARSET="UTF-8";
	
	private static String ALIPAY_PUBLIC_KEY="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAm+vV7BvmryHyE2x8VC8WSgt/nvmroDmODuuF99r8yMtV4B7qIAgrA+ur0wImUPqBRpQF3m/imP6TFlp91PGtG/y4SAyk8RxOjop0u2DfZrguxhw0BZM7ayaix2Lm2292reUE4wH7EwI1k6OXwhq6s2dhk0QKVEOQ8yQAZA7TQZbuuxaxpDgSO2HJ4H3bHXkl+Wbjnt3vLIybRQ/5TtAgUyyo4RY6lgIFp93wL9zj9o/cU6sXGGbpWiGcW0nL5Sid6OrBoqCu26OKoMkA960Jis24X+c0IdYWw8BgjzT89ad/6XttA55ogHxhM52m/Zh8adybtQkCHtK/7NKBBZ+pAwIDAQAB";
	
	public static String payMethod(){
		
		//实例化客户端
		AlipayClient alipayClient = new DefaultAlipayClient(PAY_URL, APP_ID, APP_PRIVATE_KEY, "json", CHARSET, ALIPAY_PUBLIC_KEY, "RSA2");
		//实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
		AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
		//SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
		AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
		model.setBody("我是测试数据");
		model.setSubject("App支付测试Java");
		model.setOutTradeNo("2088821324879541");
		model.setTimeoutExpress("30m");
		model.setTotalAmount("0.01");
		model.setProductCode("QUICK_MSECURITY_PAY");
		request.setBizModel(model);
		request.setNotifyUrl("app.leqtch.com/MusicCircle/adminlogin");
		String str="";
		try {
		        //这里和普通的接口调用不同，使用的是sdkExecute
		        AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
		        str=response.getBody();
		        System.out.println(response.getBody());//就是orderString 可以直接给客户端请求，无需再做处理。
		    } catch (AlipayApiException e) {
		        e.printStackTrace();
		}
		return str;
	}
	
	
	public static void main(String[] args) {
			
			AlipayUtil.payMethod();
			
		}
	
}

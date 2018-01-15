package app.zhifubao.pay;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;

/***
 * 为APP前端获取Sign签名的方法
 * @author Administrator
 * 2017-11-09
 */
public class AliPaySignMethod {
	
	/**1.开通会员支付的方法**/
	public static String getPayMember(AlipayEntity alientity){
		//1.实例化客户端
		AlipayClient alipayClient=new DefaultAlipayClient(AlipayUtil.ALI_URL, AlipayUtil.APP_ID, AlipayUtil.APP_PRIVATE_KEY, "json", AlipayUtil.CHARSET, AlipayUtil.ALIPAY_PUBLIC_KEY, AlipayUtil.SIGN_TYPE);
		//2.实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
		AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
		//3.SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
		AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
		model.setBody(alientity.getBody());
		model.setSubject(alientity.getSubject());
		model.setTotalAmount(alientity.getTotalAmount());
		model.setOutTradeNo(alientity.getOutTradeNo());
		model.setTimeoutExpress(AlipayUtil.TimeoutExpress);
		model.setProductCode(AlipayUtil.ProductCode);
		
		request.setBizModel(model);
		request.setNotifyUrl(alientity.getNotifyUrl());		//回调接口
		
		//4.执行并返回参数
		String sign="";
		try {
			//5.这里和普通的接口调用不同，使用的是sdkExecute
			AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
			//就是orderString 可以直接给客户端请求，无需再做处理
			sign=response.getBody();
		} catch (Exception e) {
			//错误
			System.out.println(e);
	        e.printStackTrace();
		}
		return sign;
	}
	
}

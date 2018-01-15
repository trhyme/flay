package app.zhifubao.pay;

import java.io.Serializable;

/***
 * 自定义支付宝的实体类
 * @author Administrator
 * 2017-11-09
 */
@SuppressWarnings("serial")
public class AlipayEntity implements Serializable{
	public AlipayEntity(){}
	//属性
	private String body;			//内容
	private String subject;			//订单信息
	private String outTradeNo;		//订单编号
	private String totalAmount;		//支付金额（数值）
	
	private String notifyUrl;		//支付回调接口
	
	
	public String getNotifyUrl() {
		return notifyUrl;
	}
	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getOutTradeNo() {
		return outTradeNo;
	}
	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}
	public String getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}
	
}

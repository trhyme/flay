package com.java.letch.app.controller;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jdom.JDOMException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.java.letch.app.returnmodel.AppReturnCodeInfo;
import com.java.letch.model.MemberGradeInfo;
import com.java.letch.model.MemberInfo;
import com.java.letch.model.RechargeInfo;
import com.java.letch.model.UsersInfo;
import com.java.letch.service.MemberGradeInfoService;
import com.java.letch.service.MemberInfoService;
import com.java.letch.service.RechargeInfoService;
import com.java.letch.service.UsersInfoService;
import com.java.letch.tools.DateHelper;

import app.wechartpay.leqtch.ConstantUtil;
import app.wechartpay.leqtch.Md5Util;
import app.wechartpay.leqtch.PrepayIdRequestHandler;
import app.wechartpay.leqtch.UUID;
import app.wechartpay.leqtch.WXUtil;
import app.wechartpay.leqtch.XMLUtil;

/***
 * 微信支付的请求
 * @author Administrator
 * 2017-11-06
 */
@Controller
public class AppPayWechartController {
	
	
	//1.用户的操作信息类
	@Resource(name="UsersInfoService")
	private UsersInfoService usersinfoService;
	
	//2.会员类型信息
	@Resource(name="MemberGradeInfoService")
	private MemberGradeInfoService membergradeinfoService;
	
	//3.会员信息的操作类
	@Resource(name="MemberInfoService")
	private MemberInfoService memberinfoService;
	
	//4.会员支付订单信息
	@Resource(name="RechargeInfoService")
	private RechargeInfoService rechargeinfoService;
		
	
	private String out_trade_no = "";
	
	//JSON数据转换工具
	private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").serializeNulls().create();
			
	private Map<String, Object> map=new HashMap<>();
	private AppReturnCodeInfo app=new AppReturnCodeInfo();
	
	private static HashMap<String, Lock> lockMap = new HashMap<String, Lock>();
	
	 /**
     * 【1.微信生成预支付订单，获取prepayId】
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/weixintenpay", method = RequestMethod.POST,produces="text/json;charset=UTF-8")
    @ResponseBody
    public String getOrder(HttpServletRequest request, HttpServletResponse response,
    		String appid,int userid,int memberid)
            throws Exception {
    	map.clear();
    	System.out.println("----------------支付宝支付-----------------");
    	System.out.println("用户ID："+userid);
		System.out.println("会员等级ID："+memberid);
    	try {
    		//1.用户信息
			UsersInfo user=this.usersinfoService.selectAppUserByID(userid);
			String orderID=DateHelper.getSimpDateMillis()+""+user.getId();  //订单编号
			out_trade_no = orderID;
			//同步锁
			Lock lock = lockMap.get(orderID);
			if(null==lock){
				synchronized(lockMap) {
	                lock = lockMap.get(orderID);
	                if (null == lock) {
	                    lock = new ReentrantLock();
	                    lockMap.put(orderID, lock);
	                }
				}
			}
			synchronized(lock) {
				//2.会员类型信息
				MemberGradeInfo membergred=this.membergradeinfoService.selectMemberGradeOneByID(memberid);
				//3.会员记录信息
				MemberInfo member=this.memberinfoService.selectMemberOneByID(userid);
				//4.订单信息
				RechargeInfo rech=new RechargeInfo();
				rech.setUid(userid);
				rech.setUuid(user.getNum());
				rech.setUphone(user.getPhone());
				rech.setUnames(user.getUsername());
				rech.setOrderid(orderID);		//订单编号
				rech.setAddtime(DateHelper.getNewData());
				rech.setAddmode("微信支付");
				rech.setMegid(membergred.getId()); 		//会员类型ID
				rech.setMegname(membergred.getMname()); //类型名称
				rech.setStratus(0);   	//还没生效
				rech.setDelstra(1);		//未删除
				//5.未支付订单入库
				if(this.rechargeinfoService.insertRechargeToDB(rech)){}else{
					this.rechargeinfoService.insertRechargeToDB(rech);
				}
				/**判断用户是否是否购买过会员**/
				if(member==null){
					
					member=new MemberInfo();
					member.setUid(userid);
					member.setUuid(user.getNum());
					member.setAddtime(DateHelper.getNewData());
					member.setMegid(membergred.getId()); 		//会员类型ID
					member.setMegname(membergred.getMname()); 	//类型名称
					member.setDaysnum(membergred.getDaynum()+"");	//初始化天数
					member.setBegintime(DateHelper.getNewData());	//会员开始时间
					//计算出结束时间
					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
			        Date date=new Date();  
			        Calendar calendar = Calendar.getInstance();  
			        calendar.setTime(date);  
			        calendar.add(Calendar.DAY_OF_MONTH, membergred.getDaynum());  
			        date = calendar.getTime();  
			        System.out.println("结束时间："+sdf.format(date)); 
					member.setEndestime(sdf.format(date));
					member.setStrutus(0);   //暂时设为到期
					member.setDisabls(0);   //暂时设为禁用
					member.setTotalcost(membergred.getPrices());  //支付金额
					if(this.memberinfoService.insertIntoMemberInfoDB(member)){}else{
						this.memberinfoService.insertIntoMemberInfoDB(member);
					}
				}else{
					
				}
				
				
				map = new HashMap<String, Object>();
	    		app.setMap(map);
	            //获取生成预支付订单的请求类
	            PrepayIdRequestHandler prepayReqHandler = new PrepayIdRequestHandler(request, response);
	            //支付金额
	            String totalFee=membergred.getPrices()+"";
	            int total_fee=(int) (Float.valueOf(totalFee)*100);
	            System.out.println("total:"+total_fee);
	            System.out.println("total_fee:" + total_fee);
	            prepayReqHandler.setParameter("appid", ConstantUtil.APP_ID);
	            prepayReqHandler.setParameter("body", ConstantUtil.BODY);
	            prepayReqHandler.setParameter("mch_id", ConstantUtil.MCH_ID);
	            String nonce_str = WXUtil.getNonceStr();
	            prepayReqHandler.setParameter("nonce_str", nonce_str);
	            prepayReqHandler.setParameter("notify_url", ConstantUtil.NOTIFY_URL);
	            
	            prepayReqHandler.setParameter("out_trade_no", out_trade_no);
	            prepayReqHandler.setParameter("spbill_create_ip", request.getRemoteAddr());
	            String timestamp = WXUtil.getTimeStamp();
	            prepayReqHandler.setParameter("time_start", timestamp);
	            System.out.println(String.valueOf(total_fee));
	            prepayReqHandler.setParameter("total_fee", String.valueOf(total_fee));
	            prepayReqHandler.setParameter("trade_type", "APP");
	            /**
	             * 注意签名（sign）的生成方式，具体见官方文档（传参都要参与生成签名，且参数名按照字典序排序，最后接上APP_KEY,转化成大写）
	             */
	            prepayReqHandler.setParameter("sign", prepayReqHandler.createMD5Sign());
	            prepayReqHandler.setGateUrl(ConstantUtil.GATEURL);
	            String prepayid = prepayReqHandler.sendPrepay();
	            System.out.println("微信返回："+prepayid);
	         // 若获取prepayid成功，将相关信息返回客户端
	            if (prepayid != null && !prepayid.equals("")) {
	                /*payRecord payRecord=new PayRecord();
	                AppCustomer appCustomer=(AppCustomer) request.getSession().getAttribute("appCustomer");
	                payRecord.setPhone(appCustomer.getPhone());
	                payRecord.setSerialId(Long.valueOf(out_trade_no));
	                payRecord.setType((byte)2);
	                payRecord.setGenerateTime(new Date());
	                payRecord.setTotalAmount(Float.valueOf(total_fee)/100);*/
	                //payRecordService.insert(payRecord);
	                String signs = "appid=" + ConstantUtil.APP_ID + "&noncestr=" + nonce_str + "&package=Sign=WXPay&partnerid="
	                        + ConstantUtil.PARTNER_ID + "&prepayid=" + prepayid + "&timestamp=" + timestamp + "&key="
	                        + ConstantUtil.APP_KEY;
	                map.put("code", 0);
	                map.put("info", "success");
	                map.put("prepayid", prepayid);
	                /**
	                 * 签名方式与上面类似
	                 */
	                map.put("sign", Md5Util.MD5Encode(signs, "utf8").toUpperCase());
	                map.put("appid", ConstantUtil.APP_ID);
	                map.put("timestamp", timestamp);  //等于请求prepayId时的time_start
	                map.put("noncestr", nonce_str);   //与请求prepayId时值一致
	                map.put("packageValue", "Sign=WXPay");  //固定常量
	                map.put("partnerid", ConstantUtil.PARTNER_ID);
	                app.setCode(200);
	                app.setMessage("成功");
	            } else {
	            	app.setCode(300);
	                app.setMessage("失败");
	                map.put("code", 1);
	                map.put("info", "获取prepayid失败");
	                
	            }
			}
			
    		/*map = new HashMap<String, Object>();
    		app.setMap(map);
            // 获取生成预支付订单的请求类
            PrepayIdRequestHandler prepayReqHandler = new PrepayIdRequestHandler(request, response);
            //获取金额
            //String totalFee = request.getParameter("total_fee");
            String totalFee="2";
            int total_fee=(int) (Float.valueOf(totalFee)*100);
            System.out.println("total:"+total_fee);
            System.out.println("total_fee:" + total_fee);
            prepayReqHandler.setParameter("appid", ConstantUtil.APP_ID);
            prepayReqHandler.setParameter("body", ConstantUtil.BODY);
            prepayReqHandler.setParameter("mch_id", ConstantUtil.MCH_ID);
            String nonce_str = WXUtil.getNonceStr();
            prepayReqHandler.setParameter("nonce_str", nonce_str);
            prepayReqHandler.setParameter("notify_url", ConstantUtil.NOTIFY_URL);
            out_trade_no = String.valueOf(UUID.next());
            prepayReqHandler.setParameter("out_trade_no", out_trade_no);
            prepayReqHandler.setParameter("spbill_create_ip", request.getRemoteAddr());
            String timestamp = WXUtil.getTimeStamp();
            prepayReqHandler.setParameter("time_start", timestamp);
            System.out.println(String.valueOf(total_fee));
            prepayReqHandler.setParameter("total_fee", String.valueOf(total_fee));
            prepayReqHandler.setParameter("trade_type", "APP");
            *//**
             * 注意签名（sign）的生成方式，具体见官方文档（传参都要参与生成签名，且参数名按照字典序排序，最后接上APP_KEY,转化成大写）
             *//*
            prepayReqHandler.setParameter("sign", prepayReqHandler.createMD5Sign());
            prepayReqHandler.setGateUrl(ConstantUtil.GATEURL);
            String prepayid = prepayReqHandler.sendPrepay();
            System.out.println("微信返回："+prepayid);
            // 若获取prepayid成功，将相关信息返回客户端
            if (prepayid != null && !prepayid.equals("")) {
                payRecord payRecord=new PayRecord();
                AppCustomer appCustomer=(AppCustomer) request.getSession().getAttribute("appCustomer");
                payRecord.setPhone(appCustomer.getPhone());
                payRecord.setSerialId(Long.valueOf(out_trade_no));
                payRecord.setType((byte)2);
                payRecord.setGenerateTime(new Date());
                payRecord.setTotalAmount(Float.valueOf(total_fee)/100);
                //payRecordService.insert(payRecord);
                String signs = "appid=" + ConstantUtil.APP_ID + "&noncestr=" + nonce_str + "&package=Sign=WXPay&partnerid="
                        + ConstantUtil.PARTNER_ID + "&prepayid=" + prepayid + "&timestamp=" + timestamp + "&key="
                        + ConstantUtil.APP_KEY;
                map.put("code", 0);
                map.put("info", "success");
                map.put("prepayid", prepayid);
                *//**
                 * 签名方式与上面类似
                 *//*
                map.put("sign", Md5Util.MD5Encode(signs, "utf8").toUpperCase());
                map.put("appid", ConstantUtil.APP_ID);
                map.put("timestamp", timestamp);  //等于请求prepayId时的time_start
                map.put("noncestr", nonce_str);   //与请求prepayId时值一致
                map.put("package", "Sign=WXPay");  //固定常量
                map.put("partnerid", ConstantUtil.PARTNER_ID);
                app.setCode(200);
                app.setMessage("成功");
            } else {
            	app.setCode(300);
                app.setMessage("失败");
                map.put("code", 1);
                map.put("info", "获取prepayid失败");
                
            }*/
            
		} catch (Exception e) {
			app.setCode(400);
            app.setMessage("错误");
			System.out.println(e);
		}
    	app.setMap(map);
    	return gson.toJson(app);
    }

    
	/****【2.微信支付回调函数】****/
    @RequestMapping(value = "/weixintenpay/goback", method = RequestMethod.POST,produces="text/json;charset=UTF-8")
    @ResponseBody
    public void backWeixinPay(HttpServletRequest request, HttpServletResponse response) throws Exception{
    	System.out.println("----------微信支付回调---------");
        PrintWriter writer = response.getWriter();
        InputStream inStream = request.getInputStream();
        ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outSteam.write(buffer, 0, len);
        }
        outSteam.close();
        inStream.close();
        String result = new String(outSteam.toByteArray(), "utf-8");
        System.out.println("微信支付通知结果：" + result);
        Map<String, String> map = null;
        try {
            /**
             * 解析微信通知返回的信息
             */
            map = XMLUtil.doXMLParse(result);
        } catch (JDOMException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("=========:"+result);
        // 若支付成功，则告知微信服务器收到通知
        if (map.get("return_code").equals("SUCCESS")) {
            if (map.get("result_code").equals("SUCCESS")) {
                System.out.println("充值成功！");
                System.out.println("订单号："+Long.valueOf(map.get("out_trade_no")));
                
                //业务处理
                String order_ID=Long.valueOf(map.get("out_trade_no"))+"";
                //更改订单状态  + 会员时间修改
          	  	//1.查询订单信息
          	  	RechargeInfo rech=this.rechargeinfoService.selectRechargeByOrderID(order_ID);
          	  	if(rech!=null){
        		    rech.setStratus(1);   	//设置订单生效
        		    //修改
        		    this.rechargeinfoService.updateRechargeByOrderID(rech.getOrderid());
        		    //2.会员类型信息
      			    MemberGradeInfo membergred=this.membergradeinfoService.selectMemberGradeOneByID(rech.getMegid());
      			    //3.会员记录信息
      			    MemberInfo member=this.memberinfoService.selectMemberOneByID(rech.getUid());
	      			if(member!=null){
	      				if(member.getStrutus()!=0){
	      					//计算会员天数
		      				//1.会员开始时间--->到当前时间的天数
		      				int num1=DateHelper.getDaysBetween(member.getBegintime(), DateHelper.getNewData());
		      				//2.会员开始时间--->会员到期时间
		      				int num2=DateHelper.getDaysBetween(member.getBegintime(), member.getEndestime());
		      				member.setMegid(membergred.getId());
		      				member.setMegname(membergred.getMname());
		      				member.setTotalcost(member.getTotalcost()+membergred.getPrices());
		      				//3.判断是否开通的同一类型的会员
		      				if(member.getMegid()==rech.getMegid()){
		      				//判断此刻的会员是否过期
		          				if(num1<num2){   		//1.会员还未到期
		          					//计算会员剩余天数
		          					int shengyu=DateHelper.getDaysBetween(DateHelper.getNewData(), member.getEndestime());
		          					//计算出结束时间
		          					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		          			        Date date=new Date();  
		          			        Calendar calendar = Calendar.getInstance();  
		          			        calendar.setTime(date);  
		          			        calendar.add(Calendar.DAY_OF_MONTH, membergred.getDaynum()+shengyu);  
		          			        date = calendar.getTime();  
		          			        System.out.println("结束时间1："+sdf.format(date)); 
		          					member.setEndestime(sdf.format(date));			//设置结束时间
		          					member.setStrutus(1);
		          					member.setDaysnum(membergred.getDaynum()+shengyu+"");
		          					member.setUpetime(DateHelper.getNewData());
		          				}else if(num1>num2){	//2.会员已到期
		          					member.setBegintime(DateHelper.getNewData());
		          					//计算出结束时间
		          					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		          			        Date date=new Date();  
		          			        Calendar calendar = Calendar.getInstance();  
		          			        calendar.setTime(date);  
		          			        calendar.add(Calendar.DAY_OF_MONTH, membergred.getDaynum());  
		          			        date = calendar.getTime();  
		          			        System.out.println("结束时间2："+sdf.format(date)); 
		          					member.setEndestime(sdf.format(date));			//设置结束时间
		          					member.setStrutus(1);
		          					member.setDaysnum(membergred.getDaynum()+"");
		          					member.setUpetime(DateHelper.getNewData());
		          				}else if(num1==num2){	//3.今天到期
		          					member.setBegintime(DateHelper.getNewData());
		          					//计算出结束时间
		          					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		          			        Date date=new Date();  
		          			        Calendar calendar = Calendar.getInstance();  
		          			        calendar.setTime(date);  
		          			        calendar.add(Calendar.DAY_OF_MONTH, membergred.getDaynum()+1);  
		          			        date = calendar.getTime();  
		          			        System.out.println("结束时间3："+sdf.format(date)); 
		          					member.setEndestime(sdf.format(date));			//设置结束时间
		          					member.setStrutus(1);
		          					member.setDaysnum(membergred.getDaynum()+"");
		          					member.setUpetime(DateHelper.getNewData());
		          				}
		      				}else{
		      					member.setBegintime(DateHelper.getNewData());
		      					//计算出结束时间
		      					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		      			        Date date=new Date();  
		      			        Calendar calendar = Calendar.getInstance();  
		      			        calendar.setTime(date);  
		      			        calendar.add(Calendar.DAY_OF_MONTH, membergred.getDaynum());  
		      			        date = calendar.getTime();  
		      			        System.out.println("结束时间4："+sdf.format(date)); 
		      					member.setEndestime(sdf.format(date));			//设置结束时间
		      					member.setStrutus(1);
		      					member.setDaysnum(membergred.getDaynum()+"");
		      					member.setUpetime(DateHelper.getNewData());
		      				}
	      				}else{
	      					//第一次开通
	      					//计算会员天数
		      				//1.会员开始时间--->到当前时间的天数
		      				int num1=DateHelper.getDaysBetween(member.getBegintime(), DateHelper.getNewData());
		      				//2.会员开始时间--->会员到期时间
		      				int num2=DateHelper.getDaysBetween(member.getBegintime(), member.getEndestime());
		      				member.setMegid(membergred.getId());
		      				member.setMegname(membergred.getMname());
		      				member.setTotalcost(member.getTotalcost()+membergred.getPrices());
		      				//3.判断是否开通的同一类型的会员
		      				if(member.getMegid()==rech.getMegid()){
		      				//判断此刻的会员是否过期
		          				if(num1<num2){   		//1.会员还未到期
		          					//计算会员剩余天数
		          					int shengyu=DateHelper.getDaysBetween(DateHelper.getNewData(), member.getEndestime());
		          					//计算出结束时间
		          					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		          			        Date date=new Date();  
		          			        Calendar calendar = Calendar.getInstance();  
		          			        calendar.setTime(date);  
		          			        calendar.add(Calendar.DAY_OF_MONTH, shengyu);  
		          			        date = calendar.getTime();  
		          			        System.out.println("结束时间1："+sdf.format(date)); 
		          					member.setEndestime(sdf.format(date));			//设置结束时间
		          					member.setStrutus(1);
		          					member.setDaysnum(shengyu+"");
		          					member.setUpetime(DateHelper.getNewData());
		          				}else if(num1>num2){	//2.会员已到期
		          					member.setBegintime(DateHelper.getNewData());
		          					//计算出结束时间
		          					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		          			        Date date=new Date();  
		          			        Calendar calendar = Calendar.getInstance();  
		          			        calendar.setTime(date);  
		          			        calendar.add(Calendar.DAY_OF_MONTH, membergred.getDaynum());  
		          			        date = calendar.getTime();  
		          			        System.out.println("结束时间2："+sdf.format(date)); 
		          					member.setEndestime(sdf.format(date));			//设置结束时间
		          					member.setStrutus(1);
		          					member.setDaysnum(membergred.getDaynum()+"");
		          					member.setUpetime(DateHelper.getNewData());
		          				}else if(num1==num2){	//3.今天到期
		          					member.setBegintime(DateHelper.getNewData());
		          					//计算出结束时间
		          					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		          			        Date date=new Date();  
		          			        Calendar calendar = Calendar.getInstance();  
		          			        calendar.setTime(date);  
		          			        calendar.add(Calendar.DAY_OF_MONTH, membergred.getDaynum()+1);  
		          			        date = calendar.getTime();  
		          			        System.out.println("结束时间3："+sdf.format(date)); 
		          					member.setEndestime(sdf.format(date));			//设置结束时间
		          					member.setStrutus(1);
		          					member.setDaysnum(membergred.getDaynum()+"");
		          					member.setUpetime(DateHelper.getNewData());
		          				}
		      				}else{
		      					member.setBegintime(DateHelper.getNewData());
		      					//计算出结束时间
		      					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		      			        Date date=new Date();  
		      			        Calendar calendar = Calendar.getInstance();  
		      			        calendar.setTime(date);  
		      			        calendar.add(Calendar.DAY_OF_MONTH, membergred.getDaynum());  
		      			        date = calendar.getTime();  
		      			        System.out.println("结束时间4："+sdf.format(date)); 
		      					member.setEndestime(sdf.format(date));			//设置结束时间
		      					member.setStrutus(1);
		      					member.setDaysnum(membergred.getDaynum()+"");
		      					member.setUpetime(DateHelper.getNewData());
		      				}
	      				}
	      				
	      			}
	      			member.setStrutus(1);
	    			//修改会员信息
	    			this.memberinfoService.insertIntoMemberInfoDB(member);
	    			  
	    			System.out.println("回调函数成功");
	          	 }
                
                //System.out.println("payRecord.getPayTime():"+payRecord.getPayTime()==null+","+payRecord.getPayTime());
                //判断通知是否已处理，若已处理，则不予处理
                //if(payRecord.getPayTime()==null){
                    System.out.println("通知微信后台");
                    //payRecord.setPayTime(new Date());
                    //String phone=payRecord.getPhone();
                   // AppCustomer appCustomer=appCustomerService.getByPhone(phone);
                    //float balance=appCustomer.getBalance();
                    //balance+=Float.valueOf(map.get("total_fee"))/100;
                    //appCustomer.setBalance(balance);
                    //appCustomerService.update(appCustomer);
                    //payRecordService.update(payRecord);
                 String notifyStr = XMLUtil.setXML("SUCCESS", "");
                 writer.write(notifyStr);
                 writer.flush();
            }
        }
    }
    
}

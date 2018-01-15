package com.java.letch.app.controller;

import java.io.PrintWriter;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alipay.api.internal.util.AlipaySignature;
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

import app.alipay.leqtch.AlipayConfig;
import app.alipay.leqtch.AlipayCore;
import app.alipay.leqtch.AlipayNotify;
import app.alipay.leqtch.AlipayUtil;
import app.alipay.leqtch.UtilDate;
import app.alipay.leqtch.test.XinPay;
import app.zhifubao.pay.AliPaySignMethod;
import app.zhifubao.pay.AlipayEntity;

/***
 * 支付宝支付
 * @author Administrator
 * 2017-11-06
 */
@Controller
public class AppPayZhiFuBaoController {
	
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
	
	
	//JSON数据转换工具
	private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").serializeNulls().create();
				
	private Map<String, Object> map=new HashMap<>();
	private AppReturnCodeInfo app=new AppReturnCodeInfo();
	
	private static HashMap<String, Lock> lockMap = new HashMap<String, Lock>();
	
	/***【1.支付宝支付的接口】***/
	@RequestMapping(value = "/zhifubaotenpay", method = RequestMethod.POST,produces="text/html;charset=UTF-8")
    @ResponseBody
	public String getOrder(HttpServletRequest request, HttpServletResponse response,String appid,int userid,int memberid) throws Exception{
		System.out.println("用户ID："+userid);
		System.out.println("会员等级ID："+memberid);
		
		try{
			//1.用户信息
			UsersInfo user=this.usersinfoService.selectAppUserByID(userid);
			String orderID=DateHelper.getSimpDateMillis()+""+user.getId();  //订单编号
			
			
			/********【2017-12-04】加锁********/
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
				rech.setAddmode("支付宝支付");
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
					/*//计算会员天数
					//1.会员开始时间--->到当前时间的天数
					int num1=DateHelper.getDaysBetween(member.getBegintime(), DateHelper.getNewData());
					//2.会员开始时间--->会员到期时间
					int num2=DateHelper.getDaysBetween(member.getBegintime(), member.getEndestime());
					
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
				        System.out.println("结束时间："+sdf.format(date)); 
						member.setEndestime(sdf.format(date));
					}else if(num1>num2){	//2.会员已到期
						
					}else if(num1==num2){	//3.今天到期
						
					}*/
					
				}
				
				//6.获取签名信息
				AlipayEntity alipay=new AlipayEntity();
				alipay.setBody("会员购买");
				alipay.setSubject(membergred.getMname()+"--支付");
				alipay.setOutTradeNo(orderID);
				alipay.setTotalAmount(membergred.getPrices()+"");
				alipay.setNotifyUrl("http://app.leqtch.com/MusicCircle/zhifubaotenpay/goback");
				
				app.setCode(200);
		        app.setMessage("获取签名成功");
		        Map<String, Object> mapNew=new HashMap<>();
		        
		        //直接
		        //mapNew.put("qianming", AlipayUtil.payMethod());
		        mapNew.put("qianming", AliPaySignMethod.getPayMember(alipay));
		        app.setMap(mapNew);
			}
	        
	        /********【2017-12-04】加锁********/
	        
        } catch (Exception e) {		  
            e.printStackTrace(); 
            app.setCode(300);
            app.setMessage("获取签名失败");
        }  
		return gson.toJson(app);
	}
	
	/**【2.支付宝支付回调方法】**/
	@RequestMapping(value = "/zhifubaotenpay/goback", method = RequestMethod.POST,produces="text/json;charset=UTF-8")
    @ResponseBody
    public void backWeixinPay(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String flag="";  
		
		System.out.println("**********************");
		System.out.println("支付回调函数");
		System.out.println("**********************");
		
		Map requestParams = request.getParameterMap();  
        PrintWriter out = response.getWriter();  
        Map<String,String> map=new HashMap<String, String>();  
        for(Iterator iter=requestParams.keySet().iterator();iter.hasNext();){  
            String name=(String)iter.next();  
            String[] values=(String[]) requestParams.get(name);  
            String valueStr="";  
            for(int i=0;i<values.length;i++){  
                valueStr=(i==values.length-1)?valueStr+values[i]:valueStr+values[i]+",";  
            }  
            map.put(name,valueStr);//将post过来的数据放到map中方便签名认证  
        }  
        String out_trade_no=map.get("out_trade_no");
        System.out.println("--订单ID："+out_trade_no+"--");
        String trade_status=map.get("trade_status");  
        System.out.println("--状态："+trade_status+"--");
      //商户订单号    
       //if(AlipayNotify.verify(map)){//验证签名  
    	   System.out.println("验证成功");
              if(trade_status.equals("TRADE_SUCCESS")) {//交易成功  
                  //更改订单状态  + 会员时间修改
            	  //1.查询订单信息
            	  RechargeInfo rech=this.rechargeinfoService.selectRechargeByOrderID(out_trade_no);
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
                  //boolean updateOrderInfo = WxAndAliPayService.updateOrderInfo(out_trade_no);  
                  //if(updateOrderInfo){  
                      //更新成功，应该将这个订单放到usercost  
                      //Map<String, String> orderinfo = WxAndAliPayService.getorderinfo(out_trade_no);  
                      //插入到usercost表中  
                     //boolean insertOrderInfo = WxAndAliPayService.insertOrderInfo(orderinfo);  
                      //if(insertOrderInfo){//订单完成  
                          //查找到user表中的user_id和会员天数  
                          //Map<String, String> useridAndDays = WxAndAliPayService.getUseridAndDays(orderinfo.get("userid").toString(),orderinfo.get("costname").toString());  
                          //将user表中的vip字段更新  
                         // BuyVIPServlet.execute(useridAndDays.get("userid").toString(),Integer.parseInt(useridAndDays.get("cardtime")));  
                     // }  
                  //}  
                  flag="success";  
              }else {  
                  //支付失败  
                  flag="fail";  
              }  
       //}  
       out.print(flag);  
	}
	
}

package app.wechartpay.leqtch;

import java.util.Date;

/***
 * 微信支付的工具类
 * @author Administrator
 * 2017-11-06
 */
public class UUID {
	 	private static Date date = new Date();
	    private static StringBuilder buf = new StringBuilder();
	    private static int seq = 0;
	    private static final int ROTATION = 99999;

	    public static synchronized long next() {
	        if (seq > ROTATION)
	            seq = 0;
	        buf.delete(0, buf.length());
	        date.setTime(System.currentTimeMillis());
	        String str = String.format("%1$tY%1$tm%1$td%1$tk%1$tM%1$tS%2$05d", date, seq++);
	        return Long.parseLong(str);
	    }
	    
	    private UUID(){
	        
	    }
}

package app.wechartpay.leqtch;

import java.util.Random;

/***
 * 微信支付的工具类
 * @author Administrator
 * 2017-10-06
 */
public class WXUtil {
	/**
     * 生成随机字符串
     * @return
     */
    public static String getNonceStr() {
        Random random = new Random();
        return Md5Util.MD5Encode(String.valueOf(random.nextInt(10000)), "utf8");
    }
    /**
     * 获取时间戳
     * @return
     */
    public static String getTimeStamp() {
        return String.valueOf(System.currentTimeMillis() / 1000);
    }
}

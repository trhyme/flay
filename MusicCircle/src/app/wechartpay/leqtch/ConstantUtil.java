package app.wechartpay.leqtch;

/***
 * 微信支付的根据类
 * @author Administrator
 * 2017-11-06
 */
public class ConstantUtil {
	/**
     * 微信开发平台应用ID
     */
    public static  String APP_ID="wx9b4feee5d656849d";
    /**
     * 应用对应的凭证
     */
    public static  String APP_SECRET="675b2677f820dcf17d2d6ac8ae09379d";
    /**
     * 应用对应的密钥   0ca44654ac0b56c6a5c4096323021f02
     */
    public static  String APP_KEY="vcTxhybCxD2eEloAxkPXd1FN9n7QPkLa";
    /**
     * 微信支付商户号 
     */
    public static  String MCH_ID="1493277542";
    /**
     * 商品描述
     */
    public static  String BODY="会员开通支付";
    /**
     * 商户号对应的密钥
     */
    public static  String PARTNER_key="vcTxhybCxD2eEloAxkPXd1FN9n7QPkLa";
    
    /**
     * 商户id
     */
    public static  String PARTNER_ID="14698sdfs402dsfdew402";
    /**
     * 常量固定值
     */
    public static  String GRANT_TYPE="client_credential";
    /**
     * 获取预支付id的接口url
     */
    public static String GATEURL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    /**
     * 微信服务器回调通知url
     */
    public static String NOTIFY_URL="http://app.leqtch.com/MusicCircle/weixintenpay/goback";
}

package app.alipay.leqtch;

public class AlipayConfig {
	// 合作身份者ID，以2088开头由16位纯数字组成的字符串  
    public static String partner = "2088821324879541";  
    // 商户的私钥,使用支付宝自带的openssl工具生成。  
    public static String private_key="MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCnpKzgqVUYI5AJDmJVGX6qFjbtV+Nr7gZ62vawpyodY0tu6cC9C/kYEw8gToTFNABe18m5NrXiSwVV8r0E7EteUZKGXN4RF0It8mp1vAflg5xxk405LyIG/kaC5xQWJMM0GNQI5dkOR5Vz7K+hhqQESTcA26riZt6sXZk2plgEzPSacf88zVYIbWMlghlFABAtcE6t5tqv/teAYEgqtc/z+e/I/KOsZJtTnPN2Ex4/gwOFIcwAzGrsE+ipHGdmfsn6JGU3KFTC5lG7/gs4C3S6Qumrn3LldHBHL8ALAmVSjdFhV9pqKAj9kX7dWOe/EfJUBZ1RebL8mVGFeXchfblvAgMBAAECggEBAJVxcUMc8GSCX0YTcokWHxoUj4Zo0K1IFLK0OzEi86IbAAWxj0V3ROi3JMbR9/2r66CgkIzxPpap9em2Z9XZpx4/bXUlmDh4GWDINysZIZjyCCYfOMd/QsAlMEpFIFzVDMaGU45TB3l5Srt6hN5jHhdMBSJYsC7/MQVKsuhKxTAQ65lWeHXLFUnFr3Ce4a9YrAL25rnQ/Cmn9BT7luZg646tmrwchIAIf+CB9I0YH6yeMLyCvUqo01K7OE0VI+Us5h0LCtu+QrX1IoKy8I87GTPC3uuTwzHipqzn2eE5jCt0Hi5iObHICuyS/eeV80S1KodaqlHVvmXoctnv6qZzJPECgYEA0yCJz52apzoOH5fh0FgxyNoQ+1GLbv4Dqfyb5A9x/SIzfaYI91569SebyFBUmF6R+rA0J7RZ2LioKHGjlNevMB0qJMGn65PPsUePnfqqRakr3HjCuyeQazMBdDlf3kTjfm6LY0y8EQuWVBsa9b6VHc35295CEce5PH6Wol4J6RUCgYEAy0YsgDr32LhBuXgXjOVvEABSiCRiQ+wVtP/SC6vBPitT3IfTo3xLk5IWF4eHKtP9PI+/vN3CjMBv6vuZmEtp5mXdBl5yplHEez6NxPcB9Er1Db+z23/sIfdhcDpUeseWqxn9HOisZ+Q/ovyyglkSB2C3L6ujdXEJBfyKmCj5MXMCgYBDnAjlxoevA6VgMqgqqwOOWV7UzGFFcuzDc9SMLPII6PnqQ9BdPxFLDpsRg8ADZ02CJQkeqd+XylJ3WSTUVlmQo9ZKHQXkJ9p73/m60nnSgwE7wiRCAMJ7wcK2cGUl7ZOYelgCvaEAKGDEoEV66sQf3kUkRsGLphTIrZUZe9nanQKBgGKIqZme+/U5m+JjrEbIEIYZIeiMenWiQwNx3kd6Ajo4lnYYAScGYTwvXDYbpBHGoMJyi+bpOB0ySw4E1/dS1wqv5evobHrit/Nl5yLK+U+J5i+79Vs5LyVn3PuhysGZbheSiXcx8iCKK669/2nf8zVQVHvvH1UWbeFRAX2wntqxAoGAapFeTP47F7rSzV57Y9YP2jda2qXaci4I9Otgc3SEYoW34diQJPDu4mvErK/oGhIr6RVAoDJyum71al04YDX1/0nLX/Zs6Blgfog5NFoF6h7kPWnT/0MnzLi7fxGZBEKRt/p/Ez+kEcAR+in0L1RCCvOhG7ZuI/MkuVXzItCzqFU=";  
      
    // 支付宝的公钥，无需修改该值  
    public static String ali_public_key  = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAp6Ss4KlVGCOQCQ5iVRl+qhY27Vfja+4Getr2sKcqHWNLbunAvQv5GBMPIE6ExTQAXtfJuTa14ksFVfK9BOxLXlGShlzeERdCLfJqdbwH5YOccZONOS8iBv5GgucUFiTDNBjUCOXZDkeVc+yvoYakBEk3ANuq4mberF2ZNqZYBMz0mnH/PM1WCG1jJYIZRQAQLXBOrebar/7XgGBIKrXP8/nvyPyjrGSbU5zzdhMeP4MDhSHMAMxq7BPoqRxnZn7J+iRlNyhUwuZRu/4LOAt0ukLpq59y5XRwRy/ACwJlUo3RYVfaaigI/ZF+3VjnvxHyVAWdUXmy/JlRhXl3IX25bwIDAQAB";  
    /*public static final String SIGN_ALGORITHMS = "SHA1WithRSA";*/  
    public static final String SIGN_ALGORITHMS = "SHA256withRsa";
    public static String seller = "3257509018@qq.com";  
  
    // 调试用，创建TXT日志文件夹路径  
    public static String log_path = "d:\\";  
  
    // 字符编码格式 目前支持 gbk 或 utf-8  
    public static String input_charset = "UTF-8";  
      
    // 签名方式 不需修改  
    public static String sign_type = "RSA2";  
      
    // 接收通知的接口名(这里的域名或者ip需要填写外网可以访问的地址)  
    public static String service = "http://192.168.0.175:8080/AppServer/CallbackServlet";  
    //APPID  
    public static String app_id="2017102509507627";  
}

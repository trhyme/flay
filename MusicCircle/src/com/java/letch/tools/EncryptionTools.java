package com.java.letch.tools;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

import com.thoughtworks.xstream.core.util.Base64Encoder;


/***加密的工具类***/
public class EncryptionTools {
	private static final String love = "rhyme!@#$%^&";
	public static EncryptionTools getEncryptionTools(){
		return new EncryptionTools();
	}
	
	/**密码加密的方法****/
	public static String passwordCoder(String data) throws Exception{
		try {
			StringBuffer str=new StringBuffer(data);
			str.append(love);
			String result=mdfiveCoder(basetoCoder(mdfiveCoder(str.toString())));
			for(int i=1; i<=2; i++){
				result=basetoCoder(result);
				result=mdfiveCoder(result);
			}
			return result;
		} catch (Exception e) {
			return null;
		}
		
		
	}
	
	/**
	 * BASE64加密方法
	 * @param data
	 * @return
	 */
    @SuppressWarnings("unused")
	private static String basetoCoder(String data) {
        Base64Encoder encoder = new Base64Encoder();
        return encoder.encode(simplecrypt(data).getBytes());
    }
    
    /***
     * BASE64加密方法
     * @param data
     * @return
     */
    private static String simplecrypt(String data){
    	char[] a = data.toCharArray();
		for (int i = 0; i < a.length; i++) {
			for(int j=0;j<love.length();j++){
				char c = love.charAt(j);
				a[i] = (char) (a[i] ^ c);
			}
		}
		String s = new String(a);
		return s;
    }
    
    /***
     * MB5加密
     * @param data
     * @return
     */
    private static String mdfiveCoder(String data) throws Exception{
    	MessageDigest mdfive = MessageDigest.getInstance("MD5");
		mdfive.update(data.getBytes());
		byte[] b=mdfive.digest();
		int i;
		StringBuffer buf = new StringBuffer(""); 
		for (int offset = 0; offset < b.length; offset++) { 
			i = b[offset]; 
			if(i<0) i+= 256; 
			if(i<16) 
			buf.append("0"); 
			buf.append(Integer.toHexString(i)); 
		} 
    	return new String(buf.toString());
    }
    
    /**1.0 BASE64加密**/  
    public static String setBase64(String str) {  
        byte[] b = null;  
        String s = null;  
        try {  
            b = str.getBytes("utf-8");  
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
        }  
        if (b != null) {  
            s = new Base64Encoder().encode(b);  
        }
        return s;  
    }  
    
    /** 2.0  BASE64解密**/   
    public static String getDecryptBase64(String str) {  
        byte[] b = null;  
        String result = null;  
        if (str != null) {  
        	Base64Encoder decoder = new Base64Encoder();  
            try {  
                b = decoder.decode(str);//.decodeBuffer(s);  
                result = new String(b, "utf-8");  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
        }  
        return result;  
    }  
    
    public static void main(String[] args) throws Exception{
    	EncryptionTools n=new EncryptionTools();
    	System.out.println("nn:"+n.setBase64("123456"));
    	System.out.println(UuidGenerate.getUUID());
    	System.out.println("jj:"+n.getDecryptBase64("950735badaf346cccaa9d9fbdf2364ce"));
    }
}

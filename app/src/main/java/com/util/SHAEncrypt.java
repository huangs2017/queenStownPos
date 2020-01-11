package com.util;

import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Random;
import android.text.format.Time;

public class SHAEncrypt {
	
	private final String token="queenstown2015";
	
	public  String getEncryption() {
		Time time = new Time();
		time.setToNow(); // 设定为当前时间
		String timestamp = time.format("%Y%m%d%H%M%S");//
//		String nonce = new Random().toString();//
		String nonce = new Random().nextInt(10000)+"";
		
		String[] tmpArr={token, timestamp, nonce};
        Arrays.sort(tmpArr);
        String signature = ArrayToString(tmpArr);
        signature = SHA1Encode(signature);//
        
        return "timestamp=" + timestamp + "&nonce=" + nonce + "&signature=" + signature;
	}
	
    public String SHA1Encode(String sourceString) {  
        String resultString = null;  
        try {  
           resultString = new String(sourceString);  
           MessageDigest md = MessageDigest.getInstance("SHA-1");  
           resultString = byte2hexString(md.digest(resultString.getBytes()));  
        } 
        catch (Exception ex) { }  
        return resultString;  
    } 
    
    //将byte[] 转换为String 
    private String byte2hexString(byte[] bytes) {
    	StringBuffer buf = new StringBuffer(bytes.length * 2);  
        for (int i = 0; i < bytes.length; i++) {  
            if ((bytes[i] & 0xff) < 0x10) {  
                buf.append("0");  
            }  
            buf.append(Long.toString(bytes[i] & 0xff, 16)); 
        }  
        return buf.toString().toLowerCase();    //小写
	}
    
    public String ArrayToString(String [] arr){  
        StringBuffer bf = new StringBuffer();  
        for(int i = 0; i < arr.length; i++){  
         bf.append(arr[i]);  
        }  
        return bf.toString();  
    }  

}

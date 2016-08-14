package com.eight.trundle.crypt;

import java.security.Key;  
import javax.crypto.Cipher;  
import javax.crypto.SecretKeyFactory;  
import javax.crypto.spec.DESedeKeySpec;  
import javax.crypto.spec.IvParameterSpec; 

public class DES {
	
	// 密钥  
    private static String secretKey = "weijinlong@lx100$#365#$" ;  
    // 向量  
    private static String iv = "01234567" ;  
    // 加解密统一使用的编码方式  
    private static String encoding = "utf-8" ;  
    
	public static String getSecretKey() {
		return secretKey;
	}

	public static void setSecretKey(String secretKey) {
		DES.secretKey = secretKey;
	}

	public static String getIv() {
		return iv;
	}

	public static void setIv(String iv) {
		DES.iv = iv;
	}

	public static String getEncoding() {
		return encoding;
	}

	public static void setEncoding(String encoding) {
		DES.encoding = encoding;
	}

	/** 
     * 3DES加密 
     *  
     * @param plainText 普通文本 
     * @return 
     * @throws Exception  
     */ 
    public static String encode(String plainText){  
        try {
			Key deskey = null ;  
			DESedeKeySpec spec = new DESedeKeySpec(secretKey.getBytes());  
			SecretKeyFactory keyfactory = SecretKeyFactory.getInstance( "desede" );  
			deskey = keyfactory.generateSecret(spec);  
      
			Cipher cipher = Cipher.getInstance( "desede/CBC/PKCS5Padding" );  
			IvParameterSpec ips = new IvParameterSpec(iv.getBytes());  
			cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);  
			byte [] encryptData = cipher.doFinal(plainText.getBytes(encoding));  
			return Base64.encode(encryptData);
		} catch (Exception e) {
			e.printStackTrace();
		} 
        return "";
    }  
       
    /** 
     * 3DES解密 
     *  
     * @param encryptText 加密文本 
     * @return 
     * @throws Exception 
     */ 
    public static String decode(String encryptText){  
        try {
			Key deskey = null ;  
			DESedeKeySpec spec = new DESedeKeySpec(secretKey.getBytes());  
			SecretKeyFactory keyfactory = SecretKeyFactory.getInstance( "desede" );  
			deskey = keyfactory.generateSecret(spec);  
			Cipher cipher = Cipher.getInstance( "desede/CBC/PKCS5Padding" );  
			IvParameterSpec ips = new IvParameterSpec(iv.getBytes());  
			cipher.init(Cipher.DECRYPT_MODE, deskey, ips);  
      
			byte [] decryptData = cipher.doFinal(Base64.decode(encryptText));  
      
			return new String(decryptData, encoding);
		} catch (Exception e) {
			e.printStackTrace();
		}
        return "";
    }  

}

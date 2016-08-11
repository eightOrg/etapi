package com.eight.trundle.socketserver.common;

import java.util.List;

/**
 * 工具类
 * @author weijl
 *
 */
public class Tools {
	private static String[] hexs = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
    		"a", "b", "c", "d", "e", "f"};
	private static String[] bins = new String[]{"0000", "0001", "0010", "0011", "0100", "0101",
    		"0110", "0111", "1000", "1001", "1010", "1011", "1100", "1101", "1110", "1111"};
	
	/**
     * 将字节码数组转换为16进制字符串
     * @param b 输入的字节码数组
     * @return 输出的16进制字符串
     */
    public static String byte2hex(byte[] b) {
        StringBuffer sb = new StringBuffer();
        for (int n = 0; n < b.length; n++) {
            if ((b[n] & 0xff) < 0x10) {
                sb.append("0");
            }
            sb.append(Integer.toHexString(b[n] & 0xFF));
        }
        return String.valueOf(sb);
    }


    /**
     * 将16进制字符串转换为字节码数组
     * @param  b 输出的字节码数组
     * @return 输入的16进制字符串
     */
    public static byte[] strToHex(String st) {
        byte[] bt = new byte[4];
        bt = st.getBytes();
        return bt;
    }

    /* 
     * 把16进制字符串转换成字节数组
     * @param hex
     * @return
     */
    public static byte[] hexStringToByte(String hex) {
        int len = (hex.length() / 2);
        byte[] result = new byte[len];
        char[] achar = hex.toCharArray();
        for (int i = 0; i < len; i++) {
            int pos = i * 2;
            result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
          }

        return result;
    }

    private static byte toByte(char c) {
        byte b = (byte) "0123456789ABCDEF".indexOf(c);
        return b;
    }


    /**
     * 通用方法
     * byte转int
     * @param b byte[]
     * @param length int
     * @return int
     */
    public static int bytes2int(byte[] b, int length) {
        int mask = 0xff;
        int temp = 0;
        int res = 0;
        for (int i = 0; i < length; i++) {
            res <<= 8;
            temp = b[i] & mask;
            res |= temp;
        }
        return res;
    }


    /**
     *
     * @param num long
     * @return byte[]
     */
    public static byte[] long2bytes(long num) {
        byte[] b = new byte[8];
        int mask = 0xff;
        for (int i = 0; i < 8; i++) {
            b[i] = (byte) (num >>> (56 - i * 8));
        }
        return b;
    }


    /**
     * 通用方法
     * byte转long
     * @param b byte[]
     * @param length int
     * @return int
     */
    public static long bytes2long(byte[] b, int length) {
        int mask = 0xff;
        long temp = 0;
        long res = 0;
        for (int i = 0; i < length; i++) {
            res <<= 8;
            temp = b[i] & mask;
            res |= temp;
        }
        return res;
    }


    /**
     * int to byte
     * @param num int
     * @return byte[]
     */
    public static byte[] int2bytes(int num) {
        byte[] b = new byte[4];
        int mask = 0xff;
        for (int i = 0; i < 4; i++) {
            b[i] = (byte) (num >>> (24 - i * 8));
        }
        return b;
    }
   
    
    /**
     * 16进度转换为10进度，只适合HX
     * @param str16
     * @return
     */
    public static String getHex10FromHex16(String str16){
    	if(str16 == null || str16.equals("")){
    		return "";
    	}
        if("7f".equals(str16) || "7F".equals(str16)){
    		return "00";
    	}
        String returnValue = Integer.valueOf(str16,16).toString();
        returnValue = returnValue.length() < 2 ? "0"+returnValue : returnValue;
        return returnValue;    	
    }
    /**
     * 16进度转换为2进度，只适合HX
     * @param str16
     * @return
     */
    public static String getHex2FromHex16(String str16){
    	
    	StringBuffer buff = new StringBuffer();
		int i;
		for(i=0; i<str16.length(); i++){
			buff.append(getBin(str16.substring(i,i+1)));
		}		
		return buff.toString();  	
    }
    private static String getBin(String hex){
		int i;
		for(i=0; i<hexs.length && !hex.toLowerCase().equals(hexs[i]); i++);
		return bins[i];
	}
    /**
     * 16进制转10进制
     * @param s
     */
    public static float getFloatFromHex16(String s){  
    	byte[] b = Tools.hexStringToByte(s.toUpperCase());
        int bits = b[3] & 0xff | (b[2] & 0xff) << 8 | (b[1] & 0xff) << 16 
        | (b[0] & 0xff) << 24; 

        int sign = ((bits & 0x80000000) == 0) ? 1 : -1; 
        int exponent = ((bits & 0x7f800000) >> 23); 
        int mantissa = (bits & 0x007fffff); 

        mantissa |= 0x00800000; 
        // Calculate the result: 
        float fx = (float) (sign * mantissa * Math.pow(2, exponent - 150));
        return fx;
   }  
   /**
    * CRC校验算法
    * @param str
    * @param len
    * @return
    */
   private static int _CRC16(String str){
	   int crc = 0xFFFF;
	   int i;
	   int temp;
	   int len = str.length()/2;
	   for(int m = 0; m < len; m++){
		   crc = crc ^ Integer.parseInt(str.substring(m*2,(m + 1)*2), 16);
		   for(i=0;i++<8;){
	          if((crc & 0x0001) != 0)
	        	  crc = (crc>>1)^0xa001;
	          else
	        	  crc>>=1;
	       }
	   }
	   temp = crc&0xff;
	   crc = ((crc>>8)&0xff)+(temp<<8);
	   return crc;
   }

   /**
    * byte转换16进制
    */
	public static String byteToHexString(byte b) {  
		return ("" + "0123456789ABCDEF".charAt(0xf & b >> 4) + "0123456789ABCDEF".charAt(b & 0xf)); 
	}
	/**
	 * 16进制字符串转Float
	 */
	public static float hexStrToFloat(String str) {        
		float result = 0;     
		try{         
			int temp = Integer.parseInt(str, 16);         
			result = Float.intBitsToFloat(temp);     
		}    
		catch (NumberFormatException e){ 			
			long ltemp = Integer.parseInt(str, 16);        
			result = Float.intBitsToFloat((int)ltemp);    
		}     
		return result; 
	} 
	/**
	 * 16进制字符串转double 
	 */
	public double hexStrToDouble(String str) {     
		long temp = Long.parseLong(str, 16);     
		double result = Double.longBitsToDouble(temp);           
		return result; 
	} 
	/**
	 * byte转2进制
	 */
	public static String getBinaryStrFromByte(byte b){  
		String result ="";  
		byte a = b; ;  
		for (int i = 0; i < 8; i++){  
			byte c=a;  
			a=(byte)(a>>1);//每移一位如同将10进制数除以2并去掉余数。   
			a=(byte)(a<<1);  
			if(a==c){  
				result="0"+result+" ";  
			}else{  
				result="1"+result+" ";  
			}  
			a=(byte)(a>>1);  
		}  
		return result;  
	} 
   
   public static String CRC16(String str){
	   int tempcrc = _CRC16(str);
	   String crc1 = Integer.toHexString(((tempcrc & 0xFF00) >> 8));
	   String crc2 = Integer.toHexString((tempcrc & 0x00FF));
	   return crc1.toUpperCase()+crc2.toUpperCase();
   }
    

}

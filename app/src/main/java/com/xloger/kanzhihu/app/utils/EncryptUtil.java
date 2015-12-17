package com.xloger.kanzhihu.app.utils;

/**
 * Created by xloger on 2015/11/4.
 * Author:xloger
 * Email:phoenix@xloger.com
 */

/**
 * 加密解密工具类
 */
public class EncryptUtil {
    private EncryptUtil(){

    }

    /**
     * 将数组的每个字节，转换成16进制字符串
     */
    public static String toHex(byte[] bytes){
        String ret = null;
        StringBuilder str;
        if (bytes != null) {
            str=new StringBuilder();
            for (byte b : bytes) {
                int high,low;
                char cHigh,cLow;
                high=b>>4&0x0f;
                low=b&0x0f;

                if(high>9){
                    cHigh=(char)('A'+high-10);
                }else {
                    cHigh= (char) ('0'+high);
                }
                if(low>9){
                    cLow=(char)('A'+low-10);
                }else {
                    cLow= (char) ('0'+low);
                }

                str.append(cHigh).append(cLow);
            }
            ret=str.toString();
        }


        return ret;
    }
}

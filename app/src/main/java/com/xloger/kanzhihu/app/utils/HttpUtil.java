package com.xloger.kanzhihu.app.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.zip.GZIPInputStream;

/**
 * Created by xloger on 2015/10/26.
 * Author:xloger
 * Email:phoenix@xloger.com
 */
public class HttpUtil {
    private HttpUtil(){

    }

    public static byte[] doGet(String url){
        byte[] bytes = null;
        InputStream inputStream = doInputStream(url, "GET");
        if (inputStream != null) {
            bytes=StreamUtil.readStream(inputStream);
            StreamUtil.close(inputStream);
        }

        return bytes;
    }

    public static byte[] doPost(String url){
        byte[] bytes = null;
        InputStream inputStream = doInputStream(url, "POST");
        if (inputStream != null) {
            bytes=StreamUtil.readStream(inputStream);
            StreamUtil.close(inputStream);
        }

        return bytes;
    }

    public static InputStream doInputStream(String url,String method){
        InputStream inputStream = null;
        if (url != null) {
            try {
                URL u=new URL(url);
                HttpURLConnection conn= (HttpURLConnection) u.openConnection();

                conn.setRequestMethod(method);

                conn.setConnectTimeout(5000);
                conn.setReadTimeout(60000);

                //代表客户端能够接受服务器传回的什么类型的格式
                conn.setRequestProperty("Accept","application/json,application/*,image/*,text/x,*/*");
                //设置客户端支持的压缩格式（内容编码 对应服务器返回的字段 Content-Encoding）
                conn.setRequestProperty("Accept-Encoding","gzip");

                int code=conn.getResponseCode();
                if (code==200){
                    inputStream = conn.getInputStream();

                    String encoding = conn.getContentEncoding();
                    if("gzip".equals(encoding)){
                        inputStream=new GZIPInputStream(inputStream);
                    }


//                    StreamUtil.close(conn);

                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return inputStream;
    }
}

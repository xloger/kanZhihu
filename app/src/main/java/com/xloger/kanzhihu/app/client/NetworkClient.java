package com.xloger.kanzhihu.app.client;

import com.xloger.kanzhihu.app.Constants;
import com.xloger.kanzhihu.app.utils.HttpUtil;
import com.xloger.kanzhihu.app.utils.MyLog;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 * Created by xloger on 2015/10/26.
 * Author:xloger
 * Email:phoenix@xloger.com
 */
public class NetworkClient {
    public static JSONObject getPosts(String timeStamp){
        JSONObject ret = null;
        String app="getposts";
        String url=Constants.API_URL+app;
        if (timeStamp != null) {
            url=url+"/"+timeStamp;
        }
        ret=getJsonObject(url);

        return ret;
    }

    public static JSONObject getAnswers(String date,String name){
        JSONObject ret = null;
        String app="getpostanswers";
        String url=Constants.API_URL+app;
        if (date != null&&name!=null) {
            url=url+"/"+date+"/"+name;
        }
        MyLog.d(url);
        ret=getJsonObject(url);

        return ret;
    }


    private static JSONObject getJsonObject(String url){
        JSONObject ret=null;
        byte[] bytes = HttpUtil.doGet(url);
        if (bytes != null) {
            try {
                String string=new String(bytes,"utf-8");
                ret=new JSONObject(string);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return ret;
    }
}

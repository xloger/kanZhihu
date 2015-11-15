package com.xloger.kanzhihu.app.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by xloger on 11月15日.
 * Author:xloger
 * Email:phoenix@xloger.com
 */
public class ConfigUtil {
    private static ConfigUtil configUtil;
    private SharedPreferences sp;

    private ConfigUtil(Context context){
        sp=context.getSharedPreferences("config",0);
    }

    public static void createInstance(Context context){
        configUtil=new ConfigUtil(context);
    }

    public static ConfigUtil newInstance(){
        if (configUtil == null) {
            throw new IllegalStateException("ConfigUtil尚未初始化");
        }
        return configUtil;
    }

    public boolean getIsOpenUrl(){
        boolean isOpenUrl = sp.getBoolean("isOpenUrl", true);
        return isOpenUrl;
    }

    public void setIsOpenUrl(boolean b){
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("isOpenUrl",b);
        editor.commit();
    }
}

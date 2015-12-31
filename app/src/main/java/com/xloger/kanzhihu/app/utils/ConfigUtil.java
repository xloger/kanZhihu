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

    private int isOpenUrlRam;

    private ConfigUtil(Context context){
        sp=context.getSharedPreferences("config",0);
        isOpenUrlRam=-1;
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
        if (isOpenUrlRam!=-1){
            if (isOpenUrlRam==1){
                return true;
            }else {
                return false;
            }
        }
        boolean isOpenUrl = sp.getBoolean("isOpenUrl", true);
        if (isOpenUrl){
            isOpenUrlRam=1;
        }else {
            isOpenUrlRam=0;
        }
        return isOpenUrl;
    }

    public void setIsOpenUrl(boolean b){
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("isOpenUrl",b);
        editor.apply();
        if (b){
            isOpenUrlRam=1;
        }else {
            isOpenUrlRam=0;
        }
    }

    public boolean isFirstRun(){
        boolean isFirstRun=sp.getBoolean("isFirstRun",true);
        if (isFirstRun){
            SharedPreferences.Editor editor = sp.edit();
            editor.putBoolean("isFirstRun",false);
            editor.apply();
        }

        return isFirstRun;
    }
}

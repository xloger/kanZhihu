package com.xloger.kanzhihu.app.utils;

/**
 * Created by xloger on 12月22日.
 * Author:xloger
 * Email:phoenix@xloger.com
 */

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import com.xloger.kanzhihu.app.Constants;

/**
 * 检查工具类，用于检测各种系统信息
 */
public class CheckUtil {
    private CheckUtil(){

    }

    public static boolean isInstallZhiHu(Context context){
        boolean ret = false;
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo;
        try {
            packageInfo = packageManager.getPackageInfo(Constants.ZHIHU_PACKAGE_NAME, 0);
        } catch (PackageManager.NameNotFoundException e) {
            packageInfo=null;
            e.printStackTrace();
        }

        if (packageInfo != null) {
            ret=true;
        }

        return ret;
    }
}

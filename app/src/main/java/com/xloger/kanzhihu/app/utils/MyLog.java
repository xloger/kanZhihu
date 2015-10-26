package com.xloger.kanzhihu.app.utils;

import android.util.Log;
import com.xloger.kanzhihu.app.BuildConfig;

/**
 * Created by xloger on 10月26日.
 * Author:xloger
 * Email:phoenix@xloger.com
 */
public class MyLog {
    private static final boolean LOG_ON= BuildConfig.DEBUG;
    private static final String deTag="kanlog";

    public static void d(String tag,String msg){
        if(LOG_ON){
            Log.d(tag,msg);
        }
    }
    public static void e(String tag,String msg){
        if(LOG_ON){
            Log.e(tag, msg);
        }
    }
    public static void i(String tag,String msg){
        if(LOG_ON){
            Log.i(tag, msg);
        }
    }
    public static void v(String tag,String msg){
        if(LOG_ON){
            Log.v(tag, msg);
        }
    }
    public static void w(String tag,String msg){
        if(LOG_ON){
            Log.w(tag, msg);
        }
    }

    public static void d(String msg){
        d(deTag,msg);
    }
    public static void e(String msg){
        e(deTag,msg);
    }
    public static void i(String msg){
        i(deTag,msg);
    }
    public static void v(String msg){
        v(deTag,msg);
    }
    public static void w(String msg){
        w(deTag,msg);
    }
}

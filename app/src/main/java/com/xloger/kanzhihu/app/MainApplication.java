package com.xloger.kanzhihu.app;

import android.app.Application;
import com.xloger.kanzhihu.app.cache.FileCache;
import com.xloger.kanzhihu.app.utils.ConfigUtil;

/**
 * Created by xloger on 2015/11/4.
 * Author:xloger
 * Email:phoenix@xloger.com
 */
public class MainApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FileCache.createInstance(getApplicationContext());
        ConfigUtil.createInstance(getApplicationContext());
    }
}

package com.xloger.kanzhihu.app;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xloger on 2015/10/26.
 * Author:xloger
 * Email:phoenix@xloger.com
 */
public class Constants {
    public static final String API_URL="http://api.kzhihu.com/";
    public static final String ZHIHU_URL="http://www.zhihu.com/";

    public static final int ACTION_SIMPLE_TASK=10010;

    public static final int ACTION_POSTS=10086;
    public static final int ACTION_ANSWER=10087;
    public static final int ACTION_POSTS_REFRESH=10088;
    public static final int ACTION_POSTS_MORE=10089;

    public static final int ACTION_ANSWER_RESULT=10090;

    public static final Map<String,String> nameMap;

    static {
        nameMap=new HashMap<String, String>();
        nameMap.put("yesterday", "昨日最新");
        nameMap.put("recent", "近日热门");
        nameMap.put("archive", "历史精华");
    }

    public static final String START_DATE="2014-06-25";
}

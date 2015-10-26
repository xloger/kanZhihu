package com.xloger.kanzhihu.app.entities;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by xloger on 10月26日.
 * Author:xloger
 * Email:phoenix@xloger.com
 */

/**
 *  posts(array)，文章信息列表，字段如下：
 *  date(string)，发表日期（yyyy-mm-dd）
 *  name(string)，文章名称（yesterday, recent, archive）
 *  pic(string)，抬头图url
 *  publishtime(number)，发表时间戳
 *  count(number)，文章包含答案数量
 *  excerpt(string)，摘要文字
 */
public class Post implements Parsable {
    private String date;
    private String name;
    private String pic;
    private long publishtime;
    private int count;
    private String excerpt;

    @Override
    public void parseJson(JSONObject json) throws JSONException {
        date=json.getString("date");
        name=json.getString("name");
        pic=json.getString("pic");
        publishtime=json.getLong("publishtime");
        count=json.getInt("count");
        excerpt=json.getString("excerpt");
    }
}

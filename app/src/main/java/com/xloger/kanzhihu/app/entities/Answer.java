package com.xloger.kanzhihu.app.entities;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by xloger on 10月26日.
 * Author:xloger
 * Email:phoenix@xloger.com
 */

/**
 *  answers(array)，答案列表，字段如下：
 *  title(string)，文章id
 *  time(datetime)，发表时间
 *  summary(string)，答案摘要
 *  questionid(string)，问题id，8位数字
 *  answered(string)，答案id，8位数字
 *  authorname(string)，答主名称
 *  authorhash(string)，答主hash
 *  avatar(string)，答主头像url
 *  vote(number)，赞同票数
 */
public class Answer implements Parsable {
    private String title;
    private String time;
    private String summary;
    private String questionid;
    private String answered;
    private String authorname;
    private String authorhash;
    private String avatar;
    private int vote;

    @Override
    public void parseJson(JSONObject json) throws JSONException {
        title=json.getString("title");
        time=json.getString("time");
        summary=json.getString("summary");
        questionid=json.getString("questionid");
        answered=json.getString("answered");
        authorname=json.getString("authorname");
        authorhash=json.getString("authorhash");
        avatar=json.getString("avatar");
        vote=json.getInt("vote");
    }
}

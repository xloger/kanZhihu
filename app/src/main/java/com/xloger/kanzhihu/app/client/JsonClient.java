package com.xloger.kanzhihu.app.client;

import com.xloger.kanzhihu.app.entities.Answer;
import com.xloger.kanzhihu.app.entities.Post;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by xloger on 10月26日.
 * Author:xloger
 * Email:phoenix@xloger.com
 */
public class JsonClient {

    public static List<Post> parsePosts(JSONObject json){
        List<Post> postList = null;
        try {
            if (json != null&&"".equals(json.getString("error"))) {
                postList=new LinkedList<Post>();
                JSONArray jsonArray = json.getJSONArray("posts");
                for (int i = 0; i < jsonArray.length(); i++) {
                    Post post=new Post();
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    post.parseJson(jsonObject);
                    postList.add(post);
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return postList;
    }

    public static List<Answer> parseAnswer(JSONObject json){
        List<Answer> answerList = null;
        try {
            if (json != null&&"".equals(json.getString("error"))) {
                answerList=new LinkedList<Answer>();
                JSONArray jsonArray = json.getJSONArray("answers");
                for (int i = 0; i < jsonArray.length(); i++) {
                    Answer answer=new Answer();
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    answer.parseJson(jsonObject);
                    answerList.add(answer);
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return answerList;
    }

}

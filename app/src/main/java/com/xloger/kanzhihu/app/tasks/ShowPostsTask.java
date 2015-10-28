package com.xloger.kanzhihu.app.tasks;

import com.xloger.kanzhihu.app.Constants;
import com.xloger.kanzhihu.app.client.NetworkClient;

import java.util.Map;

/**
 * Created by xloger on 2015/10/26.
 * Author:xloger
 * Email:phoenix@xloger.com
 */
public class ShowPostsTask extends BaseTask {
    public ShowPostsTask(TaskCallBack callBack) {
        super(callBack);
    }

    @Override
    protected TaskResult doInBackground(Map<String, String>... params) {
        TaskResult taskResult = null;

        taskResult=new TaskResult();
        taskResult.action= Constants.ACTION_POSTS;
        if (params.length!=0&&params[0]!=null) {
            String mode = params[0].get("mode");
            if("refresh".equals(mode)){
                taskResult.action= Constants.ACTION_POSTS_REFRESH;
            }else if("more".equals(mode)){
                taskResult.action= Constants.ACTION_POSTS_MORE;
            }
        }


        String timeStamp=null;
        if (params.length!=0&&params[0]!=null) {
            timeStamp=params[0].get("timeStamp");
        }
        taskResult.data= NetworkClient.getPosts(timeStamp);



        return taskResult;
    }
}

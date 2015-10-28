package com.xloger.kanzhihu.app.tasks;

import com.xloger.kanzhihu.app.Constants;
import com.xloger.kanzhihu.app.client.NetworkClient;

import java.util.Map;

/**
 * Created by xloger on 2015/10/28.
 * Author:xloger
 * Email:phoenix@xloger.com
 */
public class ShowAnswersTask extends BaseTask {
    public ShowAnswersTask(TaskCallBack callBack) {
        super(callBack);
    }

    @Override
    protected TaskResult doInBackground(Map<String, String>... params) {
        TaskResult taskResult = null;
        if(params.length!=0&&params[0]!=null){
            taskResult=new TaskResult();
            taskResult.action= Constants.ACTION_ANSWER;
            String date=params[0].get("date");
            String name=params[0].get("name");
            taskResult.data= NetworkClient.getAnswers(date,name);
        }

        return taskResult;
    }
}

package com.xloger.kanzhihu.app.tasks;

import android.os.AsyncTask;
import com.xloger.kanzhihu.app.Constants;
import com.xloger.kanzhihu.app.utils.HttpUtil;

/**
 * Created by xloger on 2015/11/4.
 * Author:xloger
 * Email:phoenix@xloger.com
 */
public class SimpleTask extends AsyncTask<String,Void,TaskResult> {
    private TaskCallBack callBack;
    public SimpleTask(TaskCallBack callBack) {
        this.callBack=callBack;
    }

    @Override
    protected TaskResult doInBackground(String... params) {
        TaskResult result = null;
        if(params.length==1){
            result=new TaskResult();
            result.action= Constants.ACTION_SIMPLE_TASK;
            String url=params[0];
            if (url != null) {
                result.data= HttpUtil.doGet(url);
            }
        }
        return result;
    }

    @Override
    protected void onPostExecute(TaskResult taskResult) {
        if (taskResult != null) {
            callBack.onTaskFinish(taskResult);
        }
    }
}

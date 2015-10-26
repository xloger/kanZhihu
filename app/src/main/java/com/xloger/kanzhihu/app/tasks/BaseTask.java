package com.xloger.kanzhihu.app.tasks;

import android.os.AsyncTask;

import java.util.Map;

/**
 * Created by xloger on 2015/10/26.
 * Author:xloger
 * Email:phoenix@xloger.com
 */
abstract class BaseTask extends AsyncTask<Map<String,String>,Void,TaskResult> {
    private TaskCallBack callBack;

    public BaseTask(TaskCallBack callBack) {
        this.callBack = callBack;
    }

    @Override
    protected void onPostExecute(TaskResult taskResult) {
        if (taskResult != null) {
            callBack.onTaskFinish(taskResult);
        }

    }
}

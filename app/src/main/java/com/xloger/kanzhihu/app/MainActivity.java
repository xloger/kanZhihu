package com.xloger.kanzhihu.app;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import com.xloger.kanzhihu.app.tasks.ShowPostsTask;
import com.xloger.kanzhihu.app.tasks.TaskCallBack;
import com.xloger.kanzhihu.app.tasks.TaskResult;


public class MainActivity extends ActionBarActivity implements TaskCallBack {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ShowPostsTask showPostsTask=new ShowPostsTask(this);
        showPostsTask.execute();
    }


    @Override
    public void onTaskFinish(TaskResult taskResult) {
        TextView textView= (TextView) findViewById(R.id.test);
        if (taskResult != null) {
            if (taskResult.data != null) {
                textView.setText(taskResult.data.toString());

            }
        }
    }
}

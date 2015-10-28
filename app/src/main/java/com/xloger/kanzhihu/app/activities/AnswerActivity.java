package com.xloger.kanzhihu.app.activities;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import com.xloger.kanzhihu.app.R;
import com.xloger.kanzhihu.app.tasks.ShowAnswersTask;
import com.xloger.kanzhihu.app.tasks.TaskCallBack;
import com.xloger.kanzhihu.app.tasks.TaskResult;

import java.util.HashMap;
import java.util.Map;

public class AnswerActivity extends ActionBarActivity implements TaskCallBack {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle=getIntent().getExtras();
        String date=bundle.getString("date");
        String name=bundle.getString("name");

        setContentView(R.layout.activity_answer);
        ShowAnswersTask task=new ShowAnswersTask(this);
        Map<String,String> map=new HashMap<String, String>();
        map.put("date",date);
        map.put("name",name);
        task.execute(map);

    }


    @Override
    public void onTaskFinish(TaskResult taskResult) {
        TextView textView= (TextView) findViewById(R.id.test);
        textView.setText(taskResult.data.toString());
    }
}

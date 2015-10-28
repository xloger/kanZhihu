package com.xloger.kanzhihu.app.activities;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import com.xloger.kanzhihu.app.R;
import com.xloger.kanzhihu.app.adapter.AnswerAdapter;
import com.xloger.kanzhihu.app.client.JsonClient;
import com.xloger.kanzhihu.app.entities.Answer;
import com.xloger.kanzhihu.app.tasks.ShowAnswersTask;
import com.xloger.kanzhihu.app.tasks.TaskCallBack;
import com.xloger.kanzhihu.app.tasks.TaskResult;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class AnswerActivity extends ActionBarActivity implements TaskCallBack {

    private RecyclerView recyclerView;
    private List<Answer> answerList;
    private AnswerAdapter answerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);

        recyclerView = (RecyclerView) findViewById(R.id.answer_recycler_view);

        //获取传过来的启动参数
        Bundle bundle=getIntent().getExtras();
        String date=bundle.getString("date");
        String name=bundle.getString("name");

        //启动异步任务加载内容
        ShowAnswersTask task=new ShowAnswersTask(this);
        Map<String,String> map=new HashMap<String, String>();
        map.put("date",date);
        map.put("name",name);
        task.execute(map);

        //配置RecyclerView
        answerList = new LinkedList<Answer>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        answerAdapter = new AnswerAdapter(answerList,this);
        recyclerView.setAdapter(answerAdapter);
    }


    @Override
    public void onTaskFinish(TaskResult taskResult) {
        JSONObject jsonObject= (JSONObject) taskResult.data;
        List<Answer> answers = JsonClient.parseAnswer(jsonObject);
        if (answers != null) {
            answerList.addAll(answers);
            answerAdapter.notifyDataSetChanged();

        }
    }
}

package com.xloger.kanzhihu.app.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import com.xloger.kanzhihu.app.Constants;
import com.xloger.kanzhihu.app.R;
import com.xloger.kanzhihu.app.adapter.AnswerAdapter;
import com.xloger.kanzhihu.app.client.JsonClient;
import com.xloger.kanzhihu.app.entities.Answer;
import com.xloger.kanzhihu.app.tasks.ShowAnswersTask;
import com.xloger.kanzhihu.app.tasks.TaskCallBack;
import com.xloger.kanzhihu.app.tasks.TaskResult;
import com.xloger.kanzhihu.app.utils.MyLog;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class AnswerActivity extends Activity implements TaskCallBack {

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
        answerAdapter = new AnswerAdapter(answerList,this,callBack);
        recyclerView.setAdapter(answerAdapter);

        Map<String, String> mapName = Constants.nameMap;

        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(false);
            String date1=date.charAt(4)+""+date.charAt(5)+"月"+date.charAt(6)+date.charAt(7)+"日";
            String title=date1+" "+mapName.get(name);
            actionBar.setTitle(title);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }

    /**
     * 完成异步任务时的回调方法
     */
    @Override
    public void onTaskFinish(TaskResult taskResult) {
        JSONObject jsonObject= (JSONObject) taskResult.data;
        List<Answer> answers = JsonClient.parseAnswer(jsonObject);
        if (answers != null) {
            answerList.addAll(answers);
            answerAdapter.notifyDataSetChanged();

        }
    }

    /**
     * 设置点击打开知乎App
     */
    private OpenLinkCallBack callBack=new OpenLinkCallBack() {
        @Override
        public void onClick(Answer answer) {
            String url= Constants.ZHIHU_URL+"question/"+answer.getQuestionid()+"/answer/"+answer.getAnswerid();
            MyLog.d(url);
            Uri uri=Uri.parse(url);
            Intent intent=new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setData(uri);
            startActivity(intent);
        }
    };

    /**
     * 点击事件的回调接口
     */
    public interface OpenLinkCallBack{
        void onClick(Answer answer);
    }
}

package com.xloger.kanzhihu.app.activities;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.Toast;
import com.xloger.kanzhihu.app.Constants;
import com.xloger.kanzhihu.app.R;
import com.xloger.kanzhihu.app.adapter.AnswerAdapter;
import com.xloger.kanzhihu.app.client.JsonClient;
import com.xloger.kanzhihu.app.entities.Answer;
import com.xloger.kanzhihu.app.sql.ReadDB;
import com.xloger.kanzhihu.app.tasks.ShowAnswersTask;
import com.xloger.kanzhihu.app.tasks.TaskCallBack;
import com.xloger.kanzhihu.app.tasks.TaskResult;
import com.xloger.kanzhihu.app.utils.ConfigUtil;
import com.xloger.kanzhihu.app.utils.MyLog;
import me.imid.swipebacklayout.lib.SwipeBackLayout;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class AnswerActivity extends BaseActivity implements TaskCallBack {

    private RecyclerView recyclerView;
    private List<Answer> answerList;
    private AnswerAdapter answerAdapter;
    private ShowAnswersTask task;
    private SwipeRefreshLayout swipeRefreshLayout;
    private String date;
    private String name;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);


//        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.answer_swipe_refresh_layout);
//
//        //设置SwipeRefreshLayout
//        swipeRefreshLayout.setColorSchemeResources(R.color.theme);
//        swipeRefreshLayout.setOnRefreshListener(this);

        recyclerView = (RecyclerView) findViewById(R.id.answer_recycler_view);

        //获取传过来的启动参数
        Bundle bundle=getIntent().getExtras();
        date = bundle.getString("date");
        name = bundle.getString("name");
        position=bundle.getInt("position");

        //启动异步任务加载内容
        task = new ShowAnswersTask(this);
        Map<String,String> map=new HashMap<String, String>();
        map.put("date", date);
        map.put("name", name);
        task.execute(map);

        //配置RecyclerView
        answerList = new LinkedList<Answer>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        answerAdapter = new AnswerAdapter(answerList,this,callBack);
        recyclerView.setAdapter(answerAdapter);

        Map<String, String> mapName = Constants.nameMap;

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(false);
            DateFormat format1=new SimpleDateFormat("yyyyMMdd",Locale.getDefault());
            Date titleDate=new Date();
            try {
                titleDate=format1.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            DateFormat format2=new SimpleDateFormat("MM月dd日",Locale.getDefault());
            String tempDate=format2.format(titleDate);
            String title=tempDate+" "+mapName.get(name);
            actionBar.setTitle(title);
        }

        //消除回退标记
        Intent intent = new Intent();
        intent.putExtra("result_id",position);
        setResult(Constants.ACTION_ANSWER_RESULT,intent);

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        task.cancel(true);
    }

    /**
     * 完成异步任务时的回调方法
     */
    @Override
    public void onTaskFinish(TaskResult taskResult) {
        JSONObject jsonObject= (JSONObject) taskResult.data;
        List<Answer> answers = JsonClient.parseAnswer(jsonObject);
        if (answers != null) {
            answerList.clear();
            answerList.addAll(answers);
            answerAdapter.notifyDataSetChanged();
        }else {
            Toast.makeText(this,"加载超时，请重试",Toast.LENGTH_SHORT).show();
        }
//        swipeRefreshLayout.setRefreshing(false);
        onRead();
    }

    /**
     * 设置点击打开知乎App
     */
    private OpenLinkCallBack callBack=new OpenLinkCallBack() {
        @Override
        public void onClick(Answer answer) {
            String url= Constants.ZHIHU_URL+"question/"+answer.getQuestionid()+"/answer/"+answer.getAnswerid();
            MyLog.d(url);
            boolean isOpenUrl = ConfigUtil.newInstance().getIsOpenUrl();
            if (isOpenUrl){ //通过Url打开App
                Uri uri=Uri.parse(url);
                Intent intent=new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(uri);
                startActivity(intent);
            }else { //通过内置浏览器打开
                Intent intent=new Intent(AnswerActivity.this,WebActivity.class);
                intent.putExtra("title",answer.getTitle());
                intent.putExtra("summary",answer.getSummary());
                intent.putExtra("url",url);
                startActivity(intent);
            }

        }
    };

//    @Override
//    public void onRefresh() {
//        task = new ShowAnswersTask(this);
//        Map<String,String> map=new HashMap<String, String>();
//        map.put("date",date);
//        map.put("name",name);
//        task.execute(map);
//    }

    private void onRead(){
        ReadDB readDB=new ReadDB(getApplicationContext());
        readDB.setRead(date,name);
    }



//    @Override
//    public void onBackPressed() {
//        Toast.makeText(this,"退出了",Toast.LENGTH_SHORT).show();
//        Intent intent = new Intent();
//        intent.putExtra("result_id",position);
//        setResult(Constants.ACTION_ANSWER_RESULT,intent);
//        super.onBackPressed();
//    }

    /**
     * 点击事件的回调接口
     */
    public interface OpenLinkCallBack{
        void onClick(Answer answer);
    }

}

package com.xloger.kanzhihu.app.activities;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.view.*;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.xloger.kanzhihu.app.Constants;
import com.xloger.kanzhihu.app.R;
import com.xloger.kanzhihu.app.adapter.PostAdapter;
import com.xloger.kanzhihu.app.client.JsonClient;
import com.xloger.kanzhihu.app.entities.Post;
import com.xloger.kanzhihu.app.tasks.ShowPostsTask;
import com.xloger.kanzhihu.app.tasks.TaskCallBack;
import com.xloger.kanzhihu.app.tasks.TaskResult;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class MainActivity extends Activity implements TaskCallBack, SwipeRefreshLayout.OnRefreshListener {

    private List<Post> postList;
    private PostAdapter adapter;
    private Context context;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(false);
        }


        //初始化控件
        listView = (ListView) findViewById(R.id.main_list_view);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.main_swipe_refresh_layout);

        //设置SwipeRefreshLayout
        swipeRefreshLayout.setColorSchemeResources(R.color.theme);
        swipeRefreshLayout.setOnRefreshListener(this);

        //配置适配器
        postList = new LinkedList<Post>();
        adapter = new PostAdapter(this,postList,clickCallBack);
        listView.setAdapter(adapter);
        //设置上拉加载的监听器
        listView.setOnScrollListener(scrollListener);

        //进行初始化的一次异步获取信息
        ShowPostsTask showPostsTask=new ShowPostsTask(this);
        showPostsTask.execute();


//        setOverflowShowingAlways();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.main_random:
                openRandom();
                break;
            case R.id.main_settings:
                openSettings();
                break;
            case R.id.main_about:
                openAbout();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    private void openRandom(){
        String date=null;
        Date startDate=new Date();
        long begin=System.currentTimeMillis();
        DateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        try {
            startDate=format.parse(Constants.START_DATE);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long end=startDate.getTime();
        long randomTime= (long) (begin+Math.random()*(end-begin));
        date=format.format(randomTime);

        String tempName[]=new String[]{"yesterday","recent","archive"};
        String name=tempName[((int) (Math.random() * 3))];
        openAnswer(-1,date,name);
    }

    private void openSettings(){
        Intent intent=new Intent(this,SettingActivity.class);
        startActivity(intent);
    }

    private void openAbout(){
        Intent intent=new Intent(this,AboutActivity.class);
        startActivity(intent);
    }

    /**
     * 异步任务结束后执行的回调方法
     */
    @Override
    public void onTaskFinish(TaskResult taskResult) {
        if (taskResult != null) {
            JSONObject json = (JSONObject) taskResult.data;
            switch (taskResult.action){
                case Constants.ACTION_POSTS:
                    upDate(json);
                    break;
                case Constants.ACTION_POSTS_REFRESH:
                    upDateRefresh(json);
                    break;
                case Constants.ACTION_POSTS_MORE:
                    upDateMore(json);
                    break;
            }
        }
    }

    /**
     * 首次进入的更新列表
     */
    private void upDate(JSONObject json){
        List<Post> posts = JsonClient.parsePosts(json);
        if (posts != null) {
            postList.addAll(posts);
            adapter.notifyDataSetChanged();
            Toast.makeText(this,"已自动更新至最新",Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this,"尚未有新内容",Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 下拉刷新的操作
     */
    private void upDateRefresh(JSONObject json){
        List<Post> posts = JsonClient.parsePosts(json);
        if (posts != null) {
            postList.clear();
            postList.addAll(posts);
            adapter.notifyDataSetChanged();
            Toast.makeText(this,"已更新至最新",Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this,"尚未有新内容",Toast.LENGTH_SHORT).show();
        }
        swipeRefreshLayout.setRefreshing(false);
    }

    /**
     * 上拉加载的操作
     */
    private void upDateMore(JSONObject json){
        List<Post> posts = JsonClient.parsePosts(json);
        if (posts != null) {
            postList.addAll(posts);
            adapter.notifyDataSetChanged();
        }else {
            Toast.makeText(this,"加载失败，请重试",Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * item点击事件的处理，用于启动答案页面
     */
    private MainClickCallBack clickCallBack=new MainClickCallBack() {
        @SuppressLint("NewApi")
        @Override
        public void onClick(int position,Post post) {
            String date=post.getDate();
            String name=post.getName();

            openAnswer(position,date,name);

        }
    };

    /**
     * 启动答案页，并传入需要的参数
     * @param position
     * @param date
     * @param name
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void openAnswer(int position,String date,String name){
        date=date.replaceAll("-","");
        Bundle bundle=new Bundle();
        bundle.putString("date",date);
        bundle.putString("name",name);
        bundle.putInt("position",position);
        Intent intent=new Intent(context,AnswerActivity.class);
        intent.putExtras(bundle);
//        this.startActivity(intent, bundle);
        this.startActivityForResult(intent,Constants.ACTION_ANSWER_RESULT,bundle);
    }

    /**
     * 答案页返回后产生的回调，用于局部刷新消除“未读”标记
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==Constants.ACTION_ANSWER_RESULT){
            int result_id = data.getIntExtra("result_id",-1);
            int firstVisiblePosition = listView.getFirstVisiblePosition();
            int lastVisiblePosition = listView.getLastVisiblePosition();
            if(result_id>=firstVisiblePosition && result_id<=lastVisiblePosition) {
                View childAt = listView.getChildAt(result_id - firstVisiblePosition);
                if (childAt != null) {
                    TextView readTag = (TextView) childAt.findViewById(R.id.item_post_read_tag);
                    if (readTag != null) {
                        readTag.setVisibility(View.INVISIBLE);
                    }
                }
            }



        }
    }

    /**
     * SwipeRefreshLayout附带的方法，执行一个刷新的异步操作
     */
    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        ShowPostsTask showPostsTask=new ShowPostsTask(this);
        Map<String,String> map=new HashMap<String, String>();
        map.put("mode","refresh");
        showPostsTask.execute(map);
    }

    /**
     * ListView的触摸监听对象。用于实现上拉加载
     */
    AbsListView.OnScrollListener scrollListener=new AbsListView.OnScrollListener() {
        private boolean isButtom;

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            if(isButtom&&scrollState==AbsListView.OnScrollListener.SCROLL_STATE_IDLE){
                ShowPostsTask showPostsTask=new ShowPostsTask(MainActivity.this);
                Map<String,String> map=new HashMap<String, String>();
                String timeStamp=postList.get(postList.size()-1).getPublishtime()+"";
                map.put("timeStamp",timeStamp);
                map.put("mode","more");
                showPostsTask.execute(map);
            }
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            isButtom=(firstVisibleItem+visibleItemCount)==totalItemCount;
        }
    };


    /**
     * 通过反射，强制显示Overflow（即右上角的菜单按钮）
     */
    private void setOverflowShowingAlways() {
        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            menuKeyField.setAccessible(true);
            menuKeyField.setBoolean(config, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    /**
     * item点击事件所需要的回调接口
     */
    public interface MainClickCallBack{
        void onClick(int position,Post post);
    }
}

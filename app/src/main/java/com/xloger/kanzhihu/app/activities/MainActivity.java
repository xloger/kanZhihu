package com.xloger.kanzhihu.app.activities;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
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
import com.xloger.kanzhihu.app.utils.MyLog;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class MainActivity extends Activity implements TaskCallBack, SwipeRefreshLayout.OnRefreshListener {

    private List<Post> postList;
    private PostAdapter adapter;
    private Context context;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        //初始化控件
        ListView listView= (ListView) findViewById(R.id.main_list_view);
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
        }
    }

    /**
     * item点击事件的处理，用于启动答案页面
     */
    private MainClickCallBack clickCallBack=new MainClickCallBack() {
        @SuppressLint("NewApi")
        @Override
        public void onClick(Post post) {
            String date=post.getDate();
            String name=post.getName();

            date=date.replaceAll("-","");


            Bundle bundle=new Bundle();
            bundle.putString("date",date);
            bundle.putString("name",name);
            Intent intent=new Intent(context,AnswerActivity.class);
            intent.putExtras(bundle);
            startActivity(intent, bundle);
        }
    };

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
     * item点击事件所需要的回调接口
     */
    public interface MainClickCallBack{
        void onClick(Post post);
    }
}

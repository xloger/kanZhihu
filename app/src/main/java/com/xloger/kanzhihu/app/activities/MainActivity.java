package com.xloger.kanzhihu.app.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import com.xloger.kanzhihu.app.R;
import com.xloger.kanzhihu.app.adapter.PostAdapter;
import com.xloger.kanzhihu.app.client.JsonClient;
import com.xloger.kanzhihu.app.entities.Post;
import com.xloger.kanzhihu.app.tasks.ShowPostsTask;
import com.xloger.kanzhihu.app.tasks.TaskCallBack;
import com.xloger.kanzhihu.app.tasks.TaskResult;
import com.xloger.kanzhihu.app.utils.MyLog;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;


public class MainActivity extends ActionBarActivity implements TaskCallBack {

    private List<Post> postList;
    private PostAdapter adapter;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        ListView listView= (ListView) findViewById(R.id.main_list_view);
        postList = new LinkedList<Post>();
        adapter = new PostAdapter(this,postList,clickCallBack);
        listView.setAdapter(adapter);

        ShowPostsTask showPostsTask=new ShowPostsTask(this);
        showPostsTask.execute();
    }


    @Override
    public void onTaskFinish(TaskResult taskResult) {
        if (taskResult != null) {
            if (taskResult.data != null) {
                List<Post> posts = JsonClient.parsePosts((JSONObject) taskResult.data);
                MyLog.d((taskResult.data).toString());
                if (posts != null) {
                    postList.addAll(posts);
                    adapter.notifyDataSetChanged();
                }

            }
        }
    }

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
            startActivity(intent,bundle);
        }
    };



    public interface MainClickCallBack{
        void onClick(Post post);
    }
}

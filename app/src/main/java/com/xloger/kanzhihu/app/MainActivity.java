package com.xloger.kanzhihu.app;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import com.xloger.kanzhihu.app.adapter.PostAdapter;
import com.xloger.kanzhihu.app.client.JsonClient;
import com.xloger.kanzhihu.app.entities.Post;
import com.xloger.kanzhihu.app.tasks.ShowPostsTask;
import com.xloger.kanzhihu.app.tasks.TaskCallBack;
import com.xloger.kanzhihu.app.tasks.TaskResult;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;


public class MainActivity extends ActionBarActivity implements TaskCallBack {

    private List<Post> postList;
    private PostAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView= (ListView) findViewById(R.id.main_list_view);
        postList = new LinkedList<Post>();
        adapter = new PostAdapter(this,postList);
        listView.setAdapter(adapter);

        ShowPostsTask showPostsTask=new ShowPostsTask(this);
        showPostsTask.execute();
    }


    @Override
    public void onTaskFinish(TaskResult taskResult) {
        if (taskResult != null) {
            if (taskResult.data != null) {
                List<Post> posts = JsonClient.parsePosts((JSONObject) taskResult.data);
                if (posts != null) {
                    postList.addAll(posts);
                    adapter.notifyDataSetChanged();
                }

            }
        }
    }
}

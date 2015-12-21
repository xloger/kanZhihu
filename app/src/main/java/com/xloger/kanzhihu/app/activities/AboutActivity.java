package com.xloger.kanzhihu.app.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.TextView;
import com.xloger.kanzhihu.app.BuildConfig;
import com.xloger.kanzhihu.app.R;

public class AboutActivity extends BaseActivity implements View.OnClickListener {

    private TextView a5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);


        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(false);
        }

        //自动匹配版本号
        TextView versions= (TextView) findViewById(R.id.versions);
        String versionName = BuildConfig.VERSION_NAME;
        versions.setText("版本号："+versionName);

        //彩蛋
        a5 = (TextView)findViewById(R.id.about_a5);
        a5.setOnClickListener(this);

    }

    private int a5ClickNum=0;

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.about_a5:
                a5ClickNum++;
                break;
        }
        if (a5ClickNum>=3){
            a5.setText(R.string.a5_hide);
        }
    }
}

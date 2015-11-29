package com.xloger.kanzhihu.app.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import com.xloger.kanzhihu.app.BuildConfig;
import com.xloger.kanzhihu.app.R;

public class AboutActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);


        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(false);
        }

        TextView versions= (TextView) findViewById(R.id.versions);
        String versionName = BuildConfig.VERSION_NAME;
        versions.setText("版本号："+versionName);

    }

}

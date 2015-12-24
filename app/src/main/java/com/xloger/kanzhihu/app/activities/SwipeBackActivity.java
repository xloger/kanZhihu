package com.xloger.kanzhihu.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import com.xloger.kanzhihu.app.R;
import com.xloger.kanzhihu.app.view.SwipeBackLayout;

/**
 * Created by xloger on 12月24日.
 * Author:xloger
 * Email:phoenix@xloger.com
 */
public class SwipeBackActivity extends BaseActivity {

    private SwipeBackLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layout = (SwipeBackLayout) LayoutInflater.from(this).inflate(
                R.layout.activity_swipe_back, null);
        layout.attachToActivity(this);
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
//        overridePendingTransition(R.anim.base_slide_right_in, R.anim.base_slide_remain);
    }




    // Press the back button in mobile phone
    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        overridePendingTransition(0, R.anim.base_slide_right_out);
    }
}

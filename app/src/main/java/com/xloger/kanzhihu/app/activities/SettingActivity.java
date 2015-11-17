package com.xloger.kanzhihu.app.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import com.xloger.kanzhihu.app.R;
import com.xloger.kanzhihu.app.utils.ConfigUtil;

public class SettingActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        final ConfigUtil configUtil = ConfigUtil.newInstance();

        final CheckBox openCheck= (CheckBox) findViewById(R.id.setting_open_check);

        openCheck.setChecked(!configUtil.getIsOpenUrl());

        openCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                configUtil.setIsOpenUrl(!openCheck.isChecked());
            }
        });
    }


}

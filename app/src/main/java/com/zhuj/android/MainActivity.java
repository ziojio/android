package com.zhuj.android;


import android.os.Bundle;
import android.view.View;

import androidx.appcompat.widget.Toolbar;

import com.orhanobut.logger.Logger;
import com.zhuj.android.ui.activity.BaseActivity;
import com.zhuj.android.ui.dialog.CenterDialog;


public class MainActivity extends BaseActivity {

    private MainActivity mainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = this;
        setContentView(R.layout.activity_main);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        final CenterDialog centerDialog = new CenterDialog();
        findViewById(R.id.button_a).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logger.d("aaaaaaaaaa");
                centerDialog.show(mainActivity);
            }
        });
    }

}

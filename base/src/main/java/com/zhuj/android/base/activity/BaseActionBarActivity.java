package com.zhuj.android.base.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;


public abstract class BaseActionBarActivity extends BaseActivity {
    protected ActionBar mActionBar;

    protected abstract int toolbarLayoutId();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Toolbar toolbar = findViewById(toolbarLayoutId());
        setSupportActionBar(toolbar);
        mActionBar = getSupportActionBar();
    }


}

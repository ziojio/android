package com.zhuj.android.base.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


public abstract class IActivity extends AppCompatActivity {
    protected final String TAG = getClass().getSimpleName();
    protected IActivity mActivity;
    protected ActionBar mActionBar;

    /**
     * @return 布局id
     */
    protected abstract int layoutId();

    /**
     * @return toolbar
     */
    protected int toolbarId() {
        return 0;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        if (layoutId() != 0) {
            setContentView(layoutId());
        }
        if (toolbarId() != 0) {
            Toolbar toolbar = findViewById(toolbarId());
            setSupportActionBar(toolbar);
            mActionBar = getSupportActionBar();
        }
    }

}

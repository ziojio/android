package com.zhuj.android.base.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


public abstract class IActivity extends AppCompatActivity {
    protected final String TAG = getClass().getSimpleName();
    protected IActivity mActivity;

    /**
     * @return 布局 id 自己创建 Dialog 时, return 0, 并且 Override onCreateDialog
     */
    protected abstract int layoutId();

    /**
     * @return toolbar id
     */
    protected int toolbarId() {
        return 0;
    }

    protected abstract void initView();

    protected abstract void initEvent();

    protected abstract void initData();

    /**
     * 修改初始化的顺序，在其中添加其他操作
     */
    protected void initBehavior() {
        initView();
        initData();
        initEvent();
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
        }
        initBehavior();
    }

    public IActivity getActivity() {
        return mActivity;
    }
}

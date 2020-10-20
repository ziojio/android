package com.zhuj.android.base.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


public abstract class IActivity extends AppCompatActivity {
    protected final String TAG = getClass().getSimpleName();
    protected IActivity mActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        onBeforeParentInitialized();
        setContentView();
        initView();
    }

    /**
     * @return 布局 id
     */
    protected abstract int layoutId();

    protected void onBeforeParentInitialized() {
    }

    protected abstract void initView();

    protected void setContentView() {
        if (layoutId() != 0) {
            setContentView(layoutId());
        }
    }

    protected void setActionBar(int toolbarBarId) {
        if (toolbarBarId > 0) {
            Toolbar toolbar = findViewById(toolbarBarId);
            setSupportActionBar(toolbar);
        }
    }

    public IActivity getActivity() {
        return mActivity;
    }
}

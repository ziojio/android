package com.zhuj.android.base.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public abstract class IActivity extends AppCompatActivity {
    protected final String TAG = this.getClass().getSimpleName();
    protected IActivity mActivity;

    /**
     * @return 布局id
     */
    protected abstract int layoutId();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        setContentView(layoutId());
    }

}

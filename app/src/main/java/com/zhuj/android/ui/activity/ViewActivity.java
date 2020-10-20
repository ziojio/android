package com.zhuj.android.ui.activity;

import android.os.Bundle;
import android.view.View;

import com.zhuj.android.R;
import com.zhuj.android.base.activity.BaseActivity;


public class ViewActivity extends BaseActivity {

    @Override
    protected int layoutId() {
        return R.layout.activity_view;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addClickListener(this::onClick, R.id.button_1);
    }

    public void onClick(View v) {
    }
}
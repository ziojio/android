package com.zhuj.android.base.fragment;


import android.view.View;

public abstract class BaseFragment extends IFragment implements View.OnClickListener {

    protected void addClick(View... views) {
        for (View view : views) {
            view.setOnClickListener(this);
        }
    }

    protected void addClick(int... viewIds) {
        for (int id : viewIds) {
            findViewById(id).setOnClickListener(this);
        }
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void initEvent() {
    }
}

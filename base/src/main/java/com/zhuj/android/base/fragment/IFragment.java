package com.zhuj.android.base.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public abstract class IFragment extends Fragment {
    protected final String TAG = getClass().getSimpleName();

    public IFragment() {
    }

    protected abstract int layoutId();

    protected abstract void initView();

    protected abstract void initData();

    protected abstract void initEvent();

    /**
     * 修改初始化的顺序，在其中添加其他操作
     */
    protected void initBehavior() {
        initView();
        initData();
        initEvent();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return layoutId() != 0 ? inflater.inflate(layoutId(), container, false) : super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initBehavior();
    }

    protected <T extends View> T findViewById(@IdRes int id) {
        return requireView().findViewById(id);
    }

}

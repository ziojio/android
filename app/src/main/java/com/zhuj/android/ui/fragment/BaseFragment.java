package com.zhuj.android.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public abstract class BaseFragment extends Fragment  implements View.OnClickListener{
    protected final String TAG = this.getClass().getSimpleName();

    public BaseFragment() {
    }

    protected abstract int inflateLayoutId();
    protected abstract void initView();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(inflateLayoutId(), container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated: initView");
        initView();
    }

    protected void addClick(View view){
        view.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }
}

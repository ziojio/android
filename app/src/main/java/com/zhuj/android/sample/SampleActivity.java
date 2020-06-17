package com.zhuj.android.sample;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.zhuj.android.base.activity.BaseActivity;
import com.zhuj.android.base.viewmodel.BaseViewModel;

public class SampleActivity extends BaseActivity {
    @Override
    protected int layoutId() {
        return 0 ;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BaseViewModel model = new ViewModelProvider(this).get(BaseViewModel.class);
    }

    @Override
    public void onClick(View v) {

    }
}

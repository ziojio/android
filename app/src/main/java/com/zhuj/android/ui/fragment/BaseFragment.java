package com.zhuj.android.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public abstract class BaseFragment extends Fragment implements View.OnClickListener {
    protected final String TAG = this.getClass().getSimpleName();

    public BaseFragment() {
    }

    protected abstract int layoutId();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(layoutId(), container, false);
    }


    protected <T extends View> T findViewById(@IdRes int id) {
        return requireView().findViewById(id);
    }

    protected void addClick(View view) {
        view.setOnClickListener(this);
    }

}

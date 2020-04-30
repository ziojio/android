package com.zhuj.android.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.zhuj.android.R;


public abstract class BaseDialog extends DialogFragment {
    protected final String TAG = this.getClass().getSimpleName();
    private View mRootView;

    public BaseDialog() {
        setStyle(STYLE_NO_TITLE, R.style.Theme_MaterialComponents_Dialog);
    }

    protected abstract int layoutId();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(layoutId(), container, false);
        return mRootView;
    }

    public void addOnClickListener(int[] viewIds, final View.OnClickListener clickListener) {
        for (int id : viewIds) {
            findViewById(id).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener.onClick(view);
                }
            });
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated: ");
    }

    protected <T extends View> T findViewById(@IdRes int id){
        return mRootView.findViewById(id);
    }

}

package com.zhuj.android.helper;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;

import androidx.fragment.app.Fragment;

public class ViewClickHelper {
    private ViewClickHelper() {
    }

    protected void addClickListener(View.OnClickListener listener, View view) {
        view.setOnClickListener(listener);
    }

    protected void addClickListener(View.OnClickListener listener, View... views) {
        for (View view : views) {
            view.setOnClickListener(listener);
        }
    }

    protected void addClickListener(View.OnClickListener listener, int... viewIds) {
        for (int id : viewIds) {
            if (listener instanceof Activity) {
                ((Activity) listener).findViewById(id).setOnClickListener(listener);
            } else if (listener instanceof Fragment) {
                ((Fragment) listener).requireView().findViewById(id).setOnClickListener(listener);
            } else if (listener instanceof Dialog) {
                ((Dialog) listener).findViewById(id).setOnClickListener(listener);
            }
        }
    }
}

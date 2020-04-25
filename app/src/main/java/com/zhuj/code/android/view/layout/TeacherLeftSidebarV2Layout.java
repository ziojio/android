package com.jbzh.android.layout;


import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jbzh.android.view.R;

public class TeacherLeftSidebarV2Layout extends LinearLayout {
    public interface OnItemClickListener {
        void onItemClick(View view);
    }
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener callback) {
        onItemClickListener = callback;
    }

    private LinearLayout parentView;
    public TeacherLeftSidebarV2Layout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout._teacher_left_sidebar_v2_container, this);
        parentView = findViewById(R.id.teacher_left_sidebar_v2_container);
        initClick();
    }

    private void initClick() {
        parentView.findViewById(R.id.camera_btn).setOnClickListener(v -> {
            if(onItemClickListener != null)
                onItemClickListener.onItemClick(v);
        });
        parentView.findViewById(R.id.mic_btn).setOnClickListener(v -> {
            if(onItemClickListener != null)
                onItemClickListener.onItemClick(v);
        });
        parentView.findViewById(R.id.sidebar_hidden_btn_left).setOnClickListener(v -> {
            if(onItemClickListener != null)
                onItemClickListener.onItemClick(v);
        });
    }

    public void setClassTime(String time) {
        ((TextView) parentView.findViewById(R.id.class_time)).setText(time);
    }
}

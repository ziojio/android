package com.jbzh.android.layout;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class ScreenRecorderLayout extends LinearLayout {
    public interface OnClickListener {
        void onStart();

        void onPause();

        void onResume();

        void onStop();
    }

    private OnClickListener clickListener;

    public void setClickListener(OnClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public ScreenRecorderLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LinearLayout parentView = (LinearLayout) getRootView();
        initViews(context, parentView);
    }

    Button start;
    Button stop;
    Button pause2resume;

    private void initViews(Context context, LinearLayout parentView) {
        // 开始， 结束， 暂停、恢复是一个按钮

        stop = new Button(context);
        stop.setText("完成");
        stop.setVisibility(View.GONE);
        stop.setOnClickListener(v -> {
            if (clickListener != null) {
                clickListener.onStop();
                stop.setVisibility(View.GONE);
                pause2resume.setVisibility(View.GONE);
                pause2resume.setText("暂停");
            }
        });
        parentView.addView(stop);

        pause2resume = new Button(context);
        pause2resume.setText("暂停");
        pause2resume.setVisibility(View.GONE);
        pause2resume.setOnClickListener(v -> {
            if (clickListener != null) {
                if (v.isSelected()) {
                    clickListener.onResume();
                    ((Button) v).setText("暂停");
                    v.setSelected(false);
                } else {
                    clickListener.onPause();
                    ((Button) v).setText("录制");
                    v.setSelected(true);
                }
            }
        });
        parentView.addView(pause2resume);

        start = new Button(context);
        start.setText("录屏");
        start.setOnClickListener(v -> {
            if (clickListener != null) {
                clickListener.onStart();
            } else {
                Toast.makeText(v.getContext(), "点击事件没有处理！", Toast.LENGTH_SHORT).show();
            }
        });
        parentView.addView(start);
    }

    public void setBtnVisibility(int visibility) {
        stop.setVisibility(visibility);
        pause2resume.setVisibility(visibility);
    }

}

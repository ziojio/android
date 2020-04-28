package com.zhuj.android.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;


public class CustomDialog extends Dialog {
    private View view;

    public interface InitView {
        void InitViews(View view);
    }

    private InitView initViewCallback;

    public void setInitViewCallback(InitView initViewCallback) {
        this.initViewCallback = initViewCallback;
    }

    public void addOnClickListener(int[] viewIds, View.OnClickListener clickListener) {
        for (int id : viewIds) {
            findViewById(id).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cancel();
                    clickListener.onClick(view);
                }
            });
        }
    }


    public CustomDialog(Context context, int layoutRes) {
        super(context);
        view = LayoutInflater.from(context).inflate(layoutRes, null);
        setContentView(view);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (initViewCallback != null) {
            initViewCallback.InitViews(view);
        }
    }

    public void show(int x, int y) {
        if (!isShowing()) {
            Window window = getWindow();
            if (window != null) {
                WindowManager.LayoutParams params = window.getAttributes();
                // window.setBackgroundDrawable(ResUtil.getDrawable(R.drawable.style_layout_rect_radius_8dp)); // 设置window的背景色
                window.getDecorView().setPadding(0, 0, 0, 0);
                params.gravity = Gravity.START | Gravity.TOP;
//                params.x = x;
//                params.y = y;
                window.setAttributes(params);
                window.setDimAmount(0);
            }
        } else {
            dismiss();
        }
    }

    public void show() {
        if (!isShowing()) {
            Window window = getWindow();
            if (window != null) {
                // window.setBackgroundDrawable(ResUtil.getDrawable(R.drawable.style_layout_rect_radius_8dp)); // 设置window的背景色
                window.getDecorView().setPadding(0, 0, 0, 0);
                window.setGravity(Gravity.CENTER);
                WindowManager.LayoutParams params = window.getAttributes();
//                params.alpha = 0.98f;
//                params.width = 800;
//                params.height = 400;
                window.setAttributes(params);
                window.setDimAmount(0);
            }
        } else {
            dismiss();
        }
    }


}

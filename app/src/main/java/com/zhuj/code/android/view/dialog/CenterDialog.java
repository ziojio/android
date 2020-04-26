package com.zhuj.code.android.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.LayoutRes;

import com.zhuj.code.android.R;


public class CenterDialog extends Dialog {

    public CenterDialog(Context context, @LayoutRes int resLayout) {
        super(context, R.style.Theme_MaterialComponents_Light_Dialog);
        setContentView(resLayout);
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

    @Override
    public void show() {
        super.show();
        Window window = getWindow();
        // window.setGravity(Gravity.CENTER);
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
    }
}

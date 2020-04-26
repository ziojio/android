package com.zhuj.code.android.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.telephony.VisualVoicemailService;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.LayoutRes;

import com.zhuj.code.android.R;


public class BottomDialog extends Dialog {

    public BottomDialog(Context context, @LayoutRes int resLayout) {
        super(context, R.style.Theme_MaterialComponents_BottomSheetDialog);
        setContentView(resLayout);
    }

    public void addOnClickListener(int[] viewIds, final View.OnClickListener clickListener) {
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
        // window.setGravity(Gravity.BOTTOM);
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
    }
}

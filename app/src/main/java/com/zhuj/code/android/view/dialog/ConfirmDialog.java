package com.jbzh.android.dialog;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.jbzh.android.util.ResUtil;
import com.jbzh.android.view.R;

public class ConfirmDialog extends Dialog {
    private Context mContext;
    private int defLayoutRes = R.layout._dialog_confirm;
    private String title;
    private int msgIcon;
    private int cancelIcon;
    private String message;
    private DialogConfirmListener confirmListener;
    private DialogCancelListener cancelListener;
    private String confirmText;
    private String cancelText;
    private boolean cancelBtnEnable;
    private boolean cancelIconEnable;
    private boolean msgIconEnable;
    private boolean titleEnable;

    public static class Builder {
        private Context mContext;
        private String title;
        private int msgIcon;
        private int cancelIcon;
        private String message;
        private DialogConfirmListener confirmListener;
        private DialogCancelListener cancelListener;
        private String confirmText;
        private String cancelText;
        private boolean cancelBtnEnable;
        private boolean cancelIconEnable;
        private boolean msgIconEnable;
        private boolean titleEnable;

        public Builder(Context context) {
            mContext = context;
        }

        public Builder setTitle(String title) {
            this.titleEnable = true;
            this.title = title;
            return this;
        }

        public Builder setCancelIcon(int cancelIcon) {
            this.cancelIconEnable = true;
            this.cancelIcon = cancelIcon;
            return this;
        }

        public Builder setMsgIcon(int msgIcon) {
            this.msgIconEnable = true;
            this.msgIcon = msgIcon;
            return this;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder setConfirmString(String confirmText) {
            this.confirmText = confirmText;
            return this;
        }

        public Builder setConfirmListener(DialogConfirmListener confirmListener) {
            this.confirmListener = confirmListener;
            return this;
        }

        public Builder setCancelListener(DialogCancelListener cancelListener) {
            this.cancelBtnEnable = true;
            this.cancelListener = cancelListener;
            return this;
        }

        public Builder setCancelString(String cancelText) {
            this.cancelBtnEnable = true;
            this.cancelText = cancelText;
            return this;
        }

        public ConfirmDialog build() {
            return new ConfirmDialog(this);
        }
    }

    private ConfirmDialog(Builder builder) {
        super(builder.mContext);
        mContext = builder.mContext;
        setContentView(defLayoutRes);
        title = builder.title;
        msgIcon = builder.msgIcon;
        cancelIcon = builder.cancelIcon;
        message = builder.message;
        confirmListener = builder.confirmListener;
        cancelListener = builder.cancelListener;
        confirmText = builder.confirmText;
        cancelText = builder.cancelText;
        cancelBtnEnable = builder.cancelBtnEnable;
        cancelIconEnable = builder.cancelIconEnable;
        msgIconEnable = builder.msgIconEnable;
        titleEnable = builder.titleEnable;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (titleEnable) {
            TextView title = findViewById(R.id.dialog_title);
            title.setText(this.title);
        } else {
            findViewById(R.id.dialog_title).setVisibility(View.GONE);
        }

        if (cancelIconEnable) {
            ImageView cancelIcon = findViewById(R.id.dialog_cancel_icon);
            cancelIcon.setImageResource(this.cancelIcon);
        } else {
            findViewById(R.id.dialog_cancel_icon).setVisibility(View.GONE);
        }

        if (msgIconEnable) {
            ImageView msgIcon = findViewById(R.id.dialog_msg_icon);
            msgIcon.setImageResource(this.msgIcon);
        } else {
            findViewById(R.id.dialog_msg_icon).setVisibility(View.GONE);
        }

        TextView msg = findViewById(R.id.dialog_message);
        msg.setText(this.message);

        if (cancelBtnEnable) {
            Button cancelBtn = findViewById(R.id.dialog_cancel);
            if(this.cancelText != null)
                cancelBtn.setText(this.cancelText);
            cancelBtn.setOnClickListener(v -> {
                if (cancelListener != null) {
                    cancelListener.onCancel();
                    cancel();
                }
            });
        } else {
            findViewById(R.id.dialog_cancel).setVisibility(View.GONE);
        }

        Button confirmBtn = findViewById(R.id.dialog_confirm);
        if(this.confirmText != null)
            confirmBtn.setText(this.confirmText);
        confirmBtn.setOnClickListener(v -> {
            if (confirmListener != null) {
                confirmListener.onConfirm();
                cancel();
            }
        });
    }

    public void show(int x, int y) {
        if (!isShowing()) {
            Window window = getWindow();
            if (window != null) {
                WindowManager.LayoutParams params = window.getAttributes();
                window.setBackgroundDrawable(ResUtil.getDrawable(R.drawable.style_layout_rect_radius_8dp)); // 设置window的背景色
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
                window.setBackgroundDrawable(ResUtil.getDrawable(R.drawable.style_layout_rect_radius_8dp)); // 设置window的背景色
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

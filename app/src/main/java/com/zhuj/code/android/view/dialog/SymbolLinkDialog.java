package com.jbzh.android.dialog;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jbzh.android.logger.Logger;
import com.jbzh.android.util.DisplayUtil;
import com.jbzh.android.util.ResUtil;
import com.jbzh.android.view.R;

public class SymbolLinkDialog extends Dialog {
    private Context mContext;
    private int[] iconArray;
    private String[] textArray;

    public interface OnItemClickListener {
        void onItemClick(int x, int y, int position);
    }

    private OnItemClickListener itemClickListener;

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public SymbolLinkDialog(@NonNull Context context) {
        super(context, R.style.dialog_no_title_no_mask);
        mContext = context;
    }

    public void setListData(int[] icon, String[] text) {
        iconArray = icon;
        textArray = text;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (textArray.length < 1 || iconArray.length < 1) {
            return;
        }
        initView();
    }

    private int x;
    private int y;

    public void show(int x, int y) {
        Window window = getWindow();
        WindowManager.LayoutParams param = window.getAttributes();
        param.gravity = Gravity.START | Gravity.TOP;
        this.x = x - 100;
        this.y = y - 100;
        param.x = x - 125;
        param.y = y - 120;
        Logger.i("x=%s, y=%s", x, y);
        window.setAttributes(param);
        super.show();
    }

    private void initView() {
        LinearLayout rootView = new LinearLayout(mContext);
        rootView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        rootView.setPadding(0, 10, 0, 10);
        rootView.setOrientation(LinearLayout.VERTICAL);
        for (int i = 0; i < textArray.length; i++) {
            LinearLayout itemView = new LinearLayout(mContext);
            itemView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            itemView.setPadding(20, 10, 20, 10);
            itemView.setOrientation(LinearLayout.HORIZONTAL);
            itemView.setTag(i);
            itemView.setOnClickListener(v -> {
                if (itemClickListener != null) {
                    itemClickListener.onItemClick( x,  y, (Integer) v.getTag());
                }
                cancel();
            });

            ImageView icon = new ImageView(mContext);
            icon.setLayoutParams(new LinearLayout.LayoutParams(42, 42));
            icon.setImageResource(iconArray[i]);
            itemView.addView(icon);

            TextView text = new TextView(mContext);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMarginStart(25);
            text.setLayoutParams(params);
            text.setText(textArray[i]);
            text.setTextSize(DisplayUtil.spToPx(14));
            itemView.addView(text);

            rootView.addView(itemView);
            if (i != textArray.length - 1) {
                View view = new View(mContext);
                LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1);
                params1.setMarginStart(85);
                params1.setMarginEnd(12);
                view.setLayoutParams(params1);
                view.setBackgroundColor(ResUtil.getColor(R.color.scE4));
                rootView.addView(view);
            }
        }
        setContentView(rootView);
    }

}

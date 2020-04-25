package com.jbzh.android.layout;


import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jbzh.android.util.ResUtil;
import com.jbzh.android.view.R;

import java.util.ArrayList;
import java.util.List;

public class PaintToolbarLayout extends LinearLayout {
    private int orientation = HORIZONTAL;  // 根据标签设置布局方向

    public enum BtnName {select, pen, eraser, color, shape, library, clean, undo, redo}

    private enum Tag {THRV2, STUV1, THRV1}

    public class ToolbarItem {
        public String name;
        public int resId;

        public ToolbarItem(String name, int resId) {
            this.name = name;
            this.resId = resId;
        }
    }

    private List<ToolbarItem> toolbarItems = new ArrayList<>();

    public interface OnItemClickListener {
        void onItemClick(View view, String name);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener callback) {
        onItemClickListener = callback;
    }
    private View vSelect;

    public PaintToolbarLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LinearLayout rootView = (LinearLayout) getRootView();
        String tag = attrs != null ? attrs.getAttributeValue(
                "http://schemas.android.com/apk/res/android", "tag") : null;
        if (null == tag) {
            initDefToolbar();
        } else {
            initToolbar(tag);
        }
        if (orientation == 0) {
            setOrientation(HORIZONTAL);
        } else {
            setOrientation(VERTICAL);
        }
        setBackgroundColor(ResUtil.getColor(R.color.white));
        for (int i = 0; i < toolbarItems.size(); i++) {
            ToolbarItem item = toolbarItems.get(i);
            ImageView btn = new ImageView(context);

            LayoutParams params1 = new LayoutParams(60, 60);
            if (orientation == 0) {  // 水平方向
                params1.leftMargin = 9;
                params1.rightMargin = 9;
            } else {   // 垂直方向
                params1.topMargin = 9;
                params1.bottomMargin = 9;
            }
            btn.setLayoutParams(params1);

            btn.setTag(item);
            if(item.name.equalsIgnoreCase(BtnName.pen.toString())) {
                changeToLight(btn, R.drawable.pen_selected);
            } else if(item.name.equalsIgnoreCase(BtnName.select.toString())){
                vSelect = btn;
            }
            if(item.name.equalsIgnoreCase(BtnName.color.toString())) {
                btn.setBackgroundColor(item.resId);
            } else {
                btn.setBackgroundResource(item.resId);
            }
            btn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        ToolbarItem item1 = (ToolbarItem) v.getTag();
                        String name = item1.name;
                        onItemClickListener.onItemClick(v, name);
                        switch (BtnName.valueOf(name)) {
                            case select:
                                changePreToDark();
                                changeToLight(v,R.drawable.select_selected);
                                break;
                            case eraser:
                                changePreToDark();
                                changeToLight(v, R.drawable.eraser_selected);
                                break;
                            case library:
                                v.setBackgroundResource(R.drawable.library_selected);
                                new Handler().postDelayed(() -> v.setBackgroundResource(R.drawable.library), 250);
                                vSelect.performClick();
                                break;
                            case clean:
                                v.setBackgroundResource(R.drawable.clean_selected);
                                new Handler().postDelayed(() -> v.setBackgroundResource(R.drawable.clean), 250);
                                break;
                            case redo:
                                v.setBackgroundResource(R.drawable.redo_selected);
                                new Handler().postDelayed(() -> v.setBackgroundResource(R.drawable.redo), 250);
                                break;
                            case undo:
                                v.setBackgroundResource(R.drawable.undo_selected);
                                new Handler().postDelayed(() -> v.setBackgroundResource(R.drawable.undo), 250);
                                break;
                        }
                    }
                }
            });
            rootView.addView(btn);
        }
    }

    private void initDefToolbar() {
        toolbarItems.add(new ToolbarItem(BtnName.select.toString(), R.drawable.select));
        toolbarItems.add(new ToolbarItem(BtnName.pen.toString(), R.drawable.pen));
        toolbarItems.add(new ToolbarItem(BtnName.eraser.toString(), R.drawable.eraser));
        toolbarItems.add(new ToolbarItem(BtnName.color.toString(),  Color.BLACK));
        toolbarItems.add(new ToolbarItem(BtnName.shape.toString(), R.drawable.shape));
        toolbarItems.add(new ToolbarItem(BtnName.library.toString(), R.drawable.library));
        toolbarItems.add(new ToolbarItem(BtnName.clean.toString(), R.drawable.clean));
        toolbarItems.add(new ToolbarItem(BtnName.undo.toString(), R.drawable.undo));
        toolbarItems.add(new ToolbarItem(BtnName.redo.toString(), R.drawable.redo));
    }

    private void initToolbar(String tag) {
        if (Tag.THRV1.toString().equalsIgnoreCase(tag)) {
            initTHRV1();
        } else if (Tag.THRV2.toString().equalsIgnoreCase(tag)) {
            initTHRV2();
        } else if (Tag.STUV1.toString().equalsIgnoreCase(tag)) {
            initSTUV1();
        }
    }

    private void initSTUV1() {
        orientation = HORIZONTAL;
        toolbarItems.add(new ToolbarItem(BtnName.undo.toString(), R.drawable.undo));
        toolbarItems.add(new ToolbarItem(BtnName.redo.toString(), R.drawable.redo));
        toolbarItems.add(new ToolbarItem(BtnName.color.toString(),Color.BLACK));
        toolbarItems.add(new ToolbarItem(BtnName.select.toString(), R.drawable.select));
        toolbarItems.add(new ToolbarItem(BtnName.pen.toString(), R.drawable.pen));
        toolbarItems.add(new ToolbarItem(BtnName.shape.toString(), R.drawable.shape));
        toolbarItems.add(new ToolbarItem(BtnName.eraser.toString(), R.drawable.eraser));
        toolbarItems.add(new ToolbarItem(BtnName.library.toString(), R.drawable.library));
        toolbarItems.add(new ToolbarItem(BtnName.clean.toString(), R.drawable.clean));
    }

    private void initTHRV1() {
        orientation = HORIZONTAL;
        toolbarItems.add(new ToolbarItem(BtnName.undo.toString(), R.drawable.undo));
        toolbarItems.add(new ToolbarItem(BtnName.redo.toString(), R.drawable.redo));
        toolbarItems.add(new ToolbarItem(BtnName.color.toString(),Color.BLACK));
        toolbarItems.add(new ToolbarItem(BtnName.select.toString(), R.drawable.select));
        toolbarItems.add(new ToolbarItem(BtnName.pen.toString(), R.drawable.pen));
        toolbarItems.add(new ToolbarItem(BtnName.shape.toString(), R.drawable.shape));
        toolbarItems.add(new ToolbarItem(BtnName.eraser.toString(), R.drawable.eraser));
        toolbarItems.add(new ToolbarItem(BtnName.library.toString(), R.drawable.library));
        toolbarItems.add(new ToolbarItem(BtnName.clean.toString(), R.drawable.clean));
    }

    private void initTHRV2() {
        orientation = VERTICAL;
        toolbarItems.add(new ToolbarItem(BtnName.select.toString(), R.drawable.select));
        toolbarItems.add(new ToolbarItem(BtnName.pen.toString(), R.drawable.pen));
        toolbarItems.add(new ToolbarItem(BtnName.eraser.toString(), R.drawable.eraser));
        toolbarItems.add(new ToolbarItem(BtnName.color.toString(),Color.BLACK));
        toolbarItems.add(new ToolbarItem(BtnName.shape.toString(), R.drawable.shape));
        toolbarItems.add(new ToolbarItem(BtnName.library.toString(), R.drawable.library));
        toolbarItems.add(new ToolbarItem(BtnName.clean.toString(), R.drawable.clean));
        toolbarItems.add(new ToolbarItem(BtnName.undo.toString(), R.drawable.undo));
        toolbarItems.add(new ToolbarItem(BtnName.redo.toString(), R.drawable.redo));
    }

    private View preView;

    public void changePreToDark() {
        if (preView != null) {
            ToolbarItem item = (ToolbarItem) preView.getTag();
            preView.setBackgroundResource(item.resId);
        }
    }

    public void changeToDark(View curView) {
        preView = curView;
        if (curView != null) {
            ToolbarItem item = (ToolbarItem) curView.getTag();
            curView.setBackgroundResource(item.resId);
        }
    }

    public void changeToLight(View curView, int resId) {
        preView = curView;
        if (curView != null) {
            curView.setBackgroundResource(resId);
        }
    }

}

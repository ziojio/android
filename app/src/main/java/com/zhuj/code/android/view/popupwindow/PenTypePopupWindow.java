package com.jbzh.android.popupwindow;


import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.jbzh.android.util.DisplayUtil;
import com.jbzh.android.view.R;

public class PenTypePopupWindow extends PopupWindow implements View.OnClickListener {

    public interface PenTypeCallback {
        void setPenType(int penType);
    }

    public void setPenTypeCallback(PenTypeCallback callBack) {
        this.penTypeCallBack = callBack;
    }

    private PenTypeCallback penTypeCallBack;

    public PenTypePopupWindow(Context context) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout._popupwindow_select_pen_layout, null);
        setContentView(view);
        ImageView pen_handle = view.findViewById(R.id.pen_handle);
        ImageView pen_alphapen = view.findViewById(R.id.pen_alphapen);
        ImageView pen_shadow = view.findViewById(R.id.pen_shadow);

        ImageView pen_corner = view.findViewById(R.id.pen_type_corner);
        ImageView pen_dash = view.findViewById(R.id.pen_type_dash);
        ImageView pen_dash2 = view.findViewById(R.id.pen_type_dash2);
        ImageView pen_discrete = view.findViewById(R.id.pen_type_discrete);

        pen_handle.setOnClickListener(this);
        pen_alphapen.setOnClickListener(this);
        pen_shadow.setOnClickListener(this);

        pen_corner.setOnClickListener(this);
        pen_dash.setOnClickListener(this);
        pen_dash2.setOnClickListener(this);
        pen_discrete.setOnClickListener(this);

        setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setBackgroundDrawable(new ColorDrawable(0x000000));
        setOutsideTouchable(true);
    }


    public void show(View parent) {
        if (!isShowing()) {
            int[] location = new int[2];
            parent.getLocationOnScreen(location);
            int i = DisplayUtil.dpToPx(28);
            showAtLocation(parent, Gravity.NO_GRAVITY, location[0] + parent.getWidth() + i, location[1]);
        } else {
            dismiss();
        }
    }

    public void show(View parent, int x, int y) {
        if (isShowing()) {
            dismiss();
        } else {
            showAtLocation(parent, Gravity.NO_GRAVITY, x, y);
        }
    }

    @Override
    public void onClick(View v) {
//        int id = v.getId();
//        if (id == R.id.pen_handle) {
//            penTypeCallBack.setPenType(jbconst.PEN_LINE_HANDLE);
//        } else if (id == R.id.pen_alphapen) {
//            penTypeCallBack.setPenType(jbconst.PEN_LINE_alphapen);
//        } else if (id == R.id.pen_shadow) {
//            penTypeCallBack.setPenType(jbconst.PEN_LINE_Shadow);
//        } else if (id == R.id.pen_type_corner) {
//            penTypeCallBack.setPenType(jbconst.PEN_LINE_CornerPathEffect);
//        } else if (id == R.id.pen_type_dash) {
//            penTypeCallBack.setPenType(jbconst.PEN_LINE_DashPathEffect);
//        } else if (id == R.id.pen_type_dash2) {
//            penTypeCallBack.setPenType(jbconst.PEN_LINE_DashPathEffect2);
//        } else if (id == R.id.pen_type_discrete) {
//            penTypeCallBack.setPenType(jbconst.PEN_LINE_DiscretePathEffect);
//        }
    }
}

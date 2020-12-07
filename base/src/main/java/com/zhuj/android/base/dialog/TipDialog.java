package com.zhuj.android.base.dialog;

import android.app.Dialog;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.zhuj.android.base.R;
import com.zhuj.android.zui.widget.round.RoundRectDrawable;


public class TipDialog extends IDialogFragment {

    @Override
    protected int layoutId() {
        return 0;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Drawable drawable = getActivity().getDrawable(R.drawable.ic_mp_menu);
        drawable.setBounds(0, 0, 10, 10);

        Dialog dialog = new MaterialAlertDialogBuilder(requireContext(), R.style.Theme_MaterialComponents_Light_Dialog)
                .setMessage("一些消息一些消息一些消息一些消息")
                .setBackground(new RoundRectDrawable(ColorStateList.valueOf(Color.WHITE), 10))
                .setIcon(R.drawable.ic_mp_close)
                .setTitle("这是标题")
                .setPositiveButton("确定", null)
                .setPositiveButtonIcon(drawable)
                .create();
        return dialog;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initWindow() {

    }
}

package com.zhuj.android.base.dialog;

import android.app.Dialog;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.zhuj.android.widget.RoundRectDrawable;

public class TipDialog extends IDialogFragment {

    @Override
    protected int layoutId() {
        return 0;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = new MaterialAlertDialogBuilder(requireContext())
                .setMessage("sssssssssssss")
                .setBackground(new RoundRectDrawable(ColorStateList.valueOf(Color.WHITE), 15))
                .setPositiveButton("conf", null)
                .create();
        return dialog;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void windowBehavior() {

    }
}

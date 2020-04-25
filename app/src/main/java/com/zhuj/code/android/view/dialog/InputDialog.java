package com.jbzh.android.dialog;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;

import com.jbzh.android.view.R;

public class InputDialog extends Dialog {
    public InputDialog(@NonNull Context context) {
        super(context);
    }

    public interface ICallback {
        void onConfirm(String url);
    }

    private ICallback callback;

    public InputDialog setCallback(ICallback callback) {
        this.callback = callback;
        return this;
    }

    private EditText inputUrl;
    private Button confirm;
    private Button cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_input_url);
        initViews();
        initEvents();
    }

    private void initEvents() {
        confirm.setOnClickListener(v -> {
            callback.onConfirm(inputUrl.getText().toString().trim());
            cancel();
        });
        cancel.setOnClickListener(v -> cancel());
    }

    private void initViews() {
        inputUrl = findViewById(R.id.input_url);
        inputUrl.setImeOptions(EditorInfo.IME_ACTION_DONE);
        inputUrl.setFilters(new InputFilter[]{new InputFilter.LengthFilter(1024), new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if (dend == 0 && source.toString().trim().equals(""))
                    return null;
                else
                    return source;
            }
        }});
        confirm = findViewById(R.id.input_confirm);
        cancel = findViewById(R.id.input_cancel);
    }
}

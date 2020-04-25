package com.jbzh.android.layout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jbzh.android.util.DisplayUtil;
import com.jbzh.android.util.ResUtil;
import com.jbzh.android.view.R;

public class MessageInputLayout extends LinearLayout {
    private LinearLayout rootView;
    private EditText etMessage;

    public interface ISendMessageListener {
        void onSendText(String msg);
    }

    private ISendMessageListener sendMessageListener;

    public void setSendMessageListener(ISendMessageListener sendMessageListener) {
        this.sendMessageListener = sendMessageListener;
    }

    public interface OnMessageIconClickListener {
        void onIconClick();
    }

    private OnMessageIconClickListener messageIconClickListener;

    public void setOnMessageIconClickListener(OnMessageIconClickListener messageIconClickListener) {
        this.messageIconClickListener = messageIconClickListener;
    }

    public MessageInputLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        DisplayUtil.init(context);
        ResUtil.init(context);
        rootView = (LinearLayout) getRootView();
        initLayout(context);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initLayout(Context context) {
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER);

        ImageView messageIcon = new ImageView(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DisplayUtil.dpToPx(32), DisplayUtil.dpToPx(32));
        messageIcon.setLayoutParams(params);
        messageIcon.setImageResource(R.drawable.icon_voice);
        messageIcon.setOnClickListener(v -> {
            if (messageIcon.isSelected()) {
                messageIcon.setSelected(false);
                messageIconClickListener.onIconClick();
                etMessage.setCursorVisible(true);
                etMessage.setFocusable(true);
                etMessage.setTextIsSelectable(true);
            } else {
                messageIcon.setSelected(true);
                etMessage.setCursorVisible(false);
                if (etMessage.hasFocus()) {
                    etMessage.clearFocus();
                }
                etMessage.setFocusable(false);
                etMessage.setTextIsSelectable(false);
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
                messageIconClickListener.onIconClick();
            }
        });
        rootView.addView(messageIcon);

        etMessage = new EditText(context);
        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(DisplayUtil.dpToPx(188), DisplayUtil.dpToPx(36));
        params1.setMarginStart(DisplayUtil.dpToPx(7));
        params1.setMarginEnd(DisplayUtil.dpToPx(7));
        etMessage.setLayoutParams(params1);
        etMessage.setBackgroundResource(R.drawable.bg_chat_edit_text);
        etMessage.setPadding(8, 0, 8, 0);
        etMessage.setFilters(new InputFilter[]{new InputFilter.LengthFilter(127)});
        etMessage.setInputType(EditorInfo.TYPE_CLASS_TEXT);
        etMessage.setImeOptions(EditorInfo.IME_ACTION_DONE);
        rootView.addView(etMessage);

        Button sendBtn = new Button(context);
        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(DisplayUtil.dpToPx(47), DisplayUtil.dpToPx(32));
        sendBtn.setLayoutParams(params2);
        sendBtn.setBackgroundResource(R.drawable.bg_chat_send_btn);
        sendBtn.setPadding(0, 0, 0, 0);
        sendBtn.setGravity(Gravity.CENTER);
        sendBtn.setText("发送");
        sendBtn.setTextSize(16);
        sendBtn.setTextColor(Color.WHITE);
        sendBtn.setOnClickListener(v -> {
            sendMessageListener.onSendText(etMessage.getText().toString().trim());
            etMessage.setText("");
        });
        rootView.addView(sendBtn);
    }
}

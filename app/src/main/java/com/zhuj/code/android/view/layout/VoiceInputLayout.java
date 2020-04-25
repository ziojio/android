package com.jbzh.android.layout;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jbzh.android.util.DateTimeUtil;
import com.jbzh.android.util.ViewUtils;
import com.jbzh.android.view.R;

public class VoiceInputLayout extends LinearLayout {
    private int[] voiceImageLeft = new int[]{
            R.drawable.volume0l,
            R.drawable.volume1l, R.drawable.volume2l, R.drawable.volume3l, R.drawable.volume4l, R.drawable.volume5l,
            R.drawable.volume6l, R.drawable.volume7l, R.drawable.volume8l, R.drawable.volume9l, R.drawable.volume10l
    };
    private int[] voiceImageRight = new int[]{
            R.drawable.volume0r,
            R.drawable.volume1r, R.drawable.volume2r, R.drawable.volume3r, R.drawable.volume4r, R.drawable.volume5r,
            R.drawable.volume6r, R.drawable.volume7r, R.drawable.volume8r, R.drawable.volume9r, R.drawable.volume10r
    };

    public interface OnVoiceListener {
        void onStartRecord();

        void onSend();

        void toText();

        void onCancel();
    }

    private OnVoiceListener voiceListener;

    private TextView tipsText;
    private ImageView iconLeftVolume;
    private ImageView iconRightVolume;
    private ImageView iconToText;
    private ImageView iconCancelBtn;
    private ImageView iconVoiceBtn;
    private View root;
    // 这个必须是100，在下面定义的，为了更快的更新时间
    private MeCountDownTimer timer = new MeCountDownTimer(3600000 * 8, 100);

    public void setVoiceListener(OnVoiceListener voiceListener) {
        this.voiceListener = voiceListener;
    }

    public VoiceInputLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        root = LayoutInflater.from(context).inflate(R.layout._voice_input_container, this);
        initView();
        initClickEvent();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initClickEvent() {
        iconVoiceBtn.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                int x = (int) event.getRawX();
                int y = (int) event.getRawY();
                if (action == MotionEvent.ACTION_DOWN) {
                    iconVoiceBtn.setBackgroundResource(R.drawable.voice_btn_active);
                    timer.start();
                    voiceListener.onStartRecord();
                } else if (action == MotionEvent.ACTION_UP) {
                    timer.onFinish();
                    // 在录音按钮之上
                    if (ViewUtils.isTouchPointInView(iconVoiceBtn, x, y)) voiceListener.onSend();
                        // 在取消发送按钮之上
                    else if (ViewUtils.isTouchPointInView(iconCancelBtn, x, y))
                        voiceListener.onCancel();
                        // 在转换文本按钮之上
                    else if (ViewUtils.isTouchPointInView(iconToText, x, y)) voiceListener.toText();
                    else voiceListener.onCancel();
                    new Handler().postDelayed(() -> initStart(), 200);
                } else if (action == MotionEvent.ACTION_MOVE) {
                    // 在取消发送按钮之上
                    if (ViewUtils.isTouchPointInView(iconCancelBtn, x, y)) {
                        iconCancelBtn.setBackgroundResource(R.drawable.voice_cancel_active);
                        timer.setFlag(1);
                        tipsText.setText("松手取消发送");
                    } else {
                        iconCancelBtn.setBackgroundResource(R.drawable.voice_cancel);
                        timer.setFlag(0);
                    }
                    // 在转换文本按钮之上
                    if (ViewUtils.isTouchPointInView(iconToText, x, y)) {
                        iconToText.setBackgroundResource(R.drawable.voice_to_text_active);
                        timer.setFlag(1);
                        tipsText.setText("转文本");
                    } else {
                        iconToText.setBackgroundResource(R.drawable.voice_to_text);
                        timer.setFlag(0);
                    }
                }
                return true;
            }
        });
    }

    private void initView() {
        tipsText = root.findViewById(R.id.tips_text);
        iconLeftVolume = root.findViewById(R.id.icon_left_volume);
        iconRightVolume = root.findViewById(R.id.icon_right_volume);
        iconToText = root.findViewById(R.id.icon_to_text);
        iconCancelBtn = root.findViewById(R.id.icon_cancel_btn);
        iconVoiceBtn = root.findViewById(R.id.icon_voice_btn);
    }

    public void initStart() {
        tipsText.setText("按住说话");
        iconLeftVolume.setBackgroundResource(voiceImageLeft[0]);
        iconRightVolume.setBackgroundResource(voiceImageRight[0]);
        iconToText.setBackgroundResource(R.drawable.voice_to_text);
        iconCancelBtn.setBackgroundResource(R.drawable.voice_cancel);
        iconVoiceBtn.setBackgroundResource(R.drawable.voice_btn);
    }

    private void setTipsText(String tips) {
        tipsText.setText(tips);
    }

    public void setVoiceVolume(int voiceVolume) {
        int imgId = selectVoiceImage(voiceVolume);
        iconLeftVolume.setBackgroundResource(voiceImageLeft[imgId]);
        iconRightVolume.setBackgroundResource(voiceImageRight[imgId]);
    }

    private int selectVoiceImage(int voiceVolume) {
        return voiceVolume / 10 > 10 ? 10 : voiceVolume / 10;
    }


    private class MeCountDownTimer extends CountDownTimer {
        private int timeMs;
        private int flag;

        MeCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        void setFlag(int flag) {
            this.flag = flag;
        }

        @Override
        public void onTick(long millisUntilFinished) {
            timeMs += 100;
            if (flag == 0) {
                setTipsText(DateTimeUtil.formatSecondsToMMss(timeMs / 1000) + "");
            }
        }

        @Override
        public void onFinish() {
            timeMs = 0;
            cancel();
        }
    }
}

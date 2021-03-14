package zhuj.android.base.recordvoice;

import android.app.Dialog;
import android.content.Context;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import zhuj.android.base.R;


public class RecordVoiceUtils {
    private Context context;
    private Dialog recordIndicator;
    private ImageView mVolumeIv, mIvPauseContinue, mIvComplete;
    private VoiceLineView voicLine;
    private TextView mRecordHintTv;
    private Context mContext;
    private EnRecordVoiceListener enRecordVoiceListener;
    private VoiceManager voiceManager;

    public RecordVoiceUtils(Context context) {
        this.context = context;
        init();
    }

    private void init() {
        voiceManager = VoiceManager.getInstance(mContext);
    }

    /**
     * 设置监听
     *
     * @param enRecordVoiceListener
     */
    public void setEnrecordVoiceListener(EnRecordVoiceListener enRecordVoiceListener) {
        this.enRecordVoiceListener = enRecordVoiceListener;
    }

    /**
     * 启动录音dialog
     */
    private void startRecordDialog() {
        recordIndicator = new Dialog(context, R.style.record_voice_dialog);
        recordIndicator.setContentView(R.layout.dialog_record_voice);
        recordIndicator.setCanceledOnTouchOutside(false);
        recordIndicator.setCancelable(false);
        mVolumeIv = (ImageView) recordIndicator.findViewById(R.id.iv_voice);
        voicLine = (VoiceLineView) recordIndicator.findViewById(R.id.voicLine);
        mRecordHintTv = (TextView) recordIndicator.findViewById(R.id.tv_length);
        mRecordHintTv.setText("00:00:00");
        mIvPauseContinue = (ImageView) recordIndicator.findViewById(R.id.iv_continue_or_pause);
        mIvComplete = (ImageView) recordIndicator.findViewById(R.id.iv_complete);
        recordIndicator.show();
        //暂停或继续
        mIvPauseContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (voiceManager != null) {
                    voiceManager.pauseOrStartVoiceRecord();
                }
            }
        });
        //完成
        mIvComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (voiceManager != null) {
                    voiceManager.stopVoiceRecord();
                }
                recordIndicator.dismiss();
            }
        });
    }


    public void showstartRecord(final VoiceManager.VoiceRecordCallBack callback) {
        startRecordDialog();
        voiceManager.setVoiceRecordListener(new VoiceManager.VoiceRecordCallBack() {
            @Override
            public void recDoing(long time, String strTime) {
                mRecordHintTv.setText(strTime);
                if (callback != null)
                    callback.recDoing(time, strTime);
            }

            @Override
            public void recVoiceGrade(int grade) {
                voicLine.setVolume(grade);
                if (callback != null)
                    callback.recVoiceGrade(grade);
            }

            @Override
            public void recStart(boolean init) {
                mIvPauseContinue.setImageResource(R.drawable.icon_pause);
                voicLine.setContinue();
                if (callback != null)
                    callback.recStart(init);
            }

            @Override
            public void recPause(String str) {
                mIvPauseContinue.setImageResource(R.drawable.icon_continue);
                voicLine.setPause();
                if (callback != null)
                    callback.recPause(str);
            }


            @Override
            public void recFinish(long length, String strLength, String path) {
                if (enRecordVoiceListener != null) {
                    enRecordVoiceListener.onFinishRecord(length, strLength, path);
                }
                if (callback != null)
                    callback.recFinish(length, strLength, path);
            }
        });
        voiceManager.startVoiceRecord(Environment.getExternalStorageDirectory().getPath() + "/jbzh/SoundRecorder", null);
    }


    public void startRecord(final VoiceManager.VoiceRecordCallBack callback) {
        voiceManager.setVoiceRecordListener(callback);
        voiceManager.startVoiceRecord(Environment.getExternalStorageDirectory().getPath() + "/jbzh/SoundRecorder", null);
    }

    public void stopRecord() {
        voiceManager.stopVoiceRecord();
    }

    /**
     * 结束回调监听
     */
    public interface EnRecordVoiceListener {
        void onFinishRecord(long length, String strLength, String filePath);
    }

}

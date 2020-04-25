package com.jbzh.android.popupwindow;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jbzh.android.util.DisplayUtil;
import com.jbzh.android.view.R;
import com.starrtc.starrtcsdk.core.player.StarPlayer;

public class ShowVideoPlayerPopupWindow extends PopupWindow {
    public interface IEnableCameraCallBack {
        void onCameraEnable(Boolean cameraEnable);
    }

    private IEnableCameraCallBack enableCameraCallBack;

    private ImageView btnSwitchCamera;
    private TextView videoTitle;
    private LinearLayout video_player_tips1;
    private LinearLayout video_player_tips2;
    private TextView video_player_tips1_text;
    private TextView video_player_tips2_text;
    private View contentView;
    private RelativeLayout videoPlayerView;
    private StarPlayer videoPlayer;

    public ShowVideoPlayerPopupWindow(Context context) {
        super(context);
        contentView = LayoutInflater.from(context).inflate(R.layout._popwindow_show_video_player, null);
        setContentView(contentView);
        setOutsideTouchable(false);
        setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
        setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        initView();
    }

    private void initView() {
        videoTitle = contentView.findViewById(R.id.video_title);
        video_player_tips1 = contentView.findViewById(R.id.video_player_tips1);
        video_player_tips2 = contentView.findViewById(R.id.video_player_tips2);
        video_player_tips1_text = contentView.findViewById(R.id.video_player_tips1_text);
        video_player_tips2_text = contentView.findViewById(R.id.video_player_tips2_text);
        btnSwitchCamera = contentView.findViewById(R.id.btn_switch_camera);
        videoPlayerView = contentView.findViewById(R.id.video_player);
        int playerWidth = DisplayUtil.dpToPx(273);
        int playerHeight = DisplayUtil.dpToPx(170);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(playerWidth, playerHeight);
        videoPlayerView.setLayoutParams(lp);
    }

    public void setStarPlayer(String name, StarPlayer starPlayer) {
        videoTitle.setText(name);
        videoPlayer = starPlayer;
        videoPlayerView.addView(videoPlayer);
    }

    public void initWatchStudentVideoPlayer() {
        btnSwitchCamera.setVisibility(View.GONE);
        video_player_tips1.setVisibility(View.GONE);
    }

    public void initTeacherSelfVideoPlayer(IEnableCameraCallBack iEnableCameraCallBack) {
        video_player_tips2.setVisibility(View.GONE);
        btnSwitchCamera = contentView.findViewById(R.id.btn_switch_camera);
        btnSwitchCamera.setSelected(true);
        btnSwitchCamera.setOnClickListener( v -> {
            if (btnSwitchCamera.isSelected()) {
                btnSwitchCamera.setSelected(false);
                btnSwitchCamera.setImageResource(R.drawable.icon_switch_off);
                enableCameraCallBack.onCameraEnable(false);
            } else {
                btnSwitchCamera.setSelected(true);
                btnSwitchCamera.setImageResource(R.drawable.icon_switch_on);
                enableCameraCallBack.onCameraEnable(true);
            }
        });
        this.enableCameraCallBack = iEnableCameraCallBack;
    }

    public void show(View parent) {
        if (!isShowing()) {
            int[] location = new int[2];
            parent.getLocationOnScreen(location);
            showAtLocation(parent, Gravity.NO_GRAVITY, location[0] - 90, location[1] + parent.getHeight() + 20);
        } else {
            dismiss();
        }
    }

    public void show(View parent, int x, int y) {
        if (!isShowing()) {
            showAtLocation(parent, Gravity.NO_GRAVITY, x, y);
        } else {
            dismiss();
        }
    }

    @Override
    public void dismiss(){
        super.dismiss();
        videoPlayer = null;
    }

}

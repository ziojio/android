package com.jbzh.android.util;

import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.util.Log;

import com.jbzh.android.logger.Logger;

import java.io.IOException;

public class MediaPlayerUtil {
    private MediaPlayer mediaPlayer;

    public MediaPlayerUtil() {
        mediaPlayer = new MediaPlayer();
        initListener();
    }

    public void initMediaPlayer() {
        mediaPlayer = new MediaPlayer();
        initListener();
    }

    private void initListener() {
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Logger.e("onCompletion: ");
                release();
            }
        });
        mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int i, int i1) {
                Logger.e("onError: i=" + i + ", i1=" + i1);
                release();
                return false;
            }
        });
    }

    public void setDataSource(String mp3Path) {
        try {
            mediaPlayer.setDataSource(mp3Path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void play() {
        try {
            mediaPlayer.setAudioAttributes(new AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_SPEECH).build());
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void playAsync() {
        try {
            mediaPlayer.setAudioAttributes(new AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_SPEECH).build());
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mediaPlayer.start();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 在播放的时候，可以直接调用播放另一个文件
     *
     * @param mp3Path
     */
    public void play(String mp3Path) {
        if (mediaPlayer != null) { // 正在播放
            if (!mediaPlayer.isPlaying()) { // 在其他状态什么都不做, 说明在完成和播放之外
                return;
            }
            mediaPlayer.stop();
            mediaPlayer.reset();
            setDataSource(mp3Path);
            play();
            Logger.w("play: 停止并播放MP3=" + mp3Path);
        } else {
            initMediaPlayer();
            setDataSource(mp3Path);
            if (mp3Path.startsWith("http")) {
                playAsync();
            } else {
                play();
            }
            Logger.w("play: 播放MP3=" + mp3Path);
        }
    }

    public void release() {
        mediaPlayer.release();
        mediaPlayer = null;
    }

    public void stop() {
        mediaPlayer.stop();
    }

    /**
     * 重置到初始化状态，必须重新设置数据源setDataSource()
     */
    public void reset() {
        mediaPlayer.reset();
    }

    /**
     * 在暂停之后，使用start()开始播放
     */
    public void pause() {
        mediaPlayer.pause();
    }

    public boolean isPlaying() {
        return mediaPlayer.isPlaying();
    }
}

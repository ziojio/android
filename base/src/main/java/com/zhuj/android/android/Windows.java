package com.zhuj.android.android;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.view.View;
import android.view.Window;

import androidx.fragment.app.FragmentActivity;

public class Windows {
    private Windows(){
    }

    /**
     * 设置让应用内容占据状态栏和导航栏
     * 内容延申到状态栏，原状态栏的颜色设为透明
     */
    private void setFullScreenExtendsStatusBar(Activity activity) {
        View decorView = activity.getWindow().getDecorView();
        int uiOptions =
                // 与 SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN 或 SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION 联合使用
                // 内容显示在状态栏或导航栏下方
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        // 隐藏导航栏，内容显示在导航栏下方
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        // 隐藏导航栏
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        // 内容延申到状态栏
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // 全屏显示，隐藏状态栏
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        // 在状态栏或导航栏，向上向下滑动，重新显示，无操作后自动隐藏
                        // SYSTEM_UI_FLAG_IMMERSIVE 滑出后不自动隐藏
                        // app接收所有的触碰事件，直到状态栏或导航栏显示
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
//            IMMERSIVE： 并且用户滑动去显示system bars，
//            清除 flag SYSTEM_UI_FLAG_HIDE_NAVIGATION 和 SYSTEM_UI_FLAG_FULLSCREEN。
//            一旦这些flags 被清除，system bars 会重新出现并保持可见状态。
//            IMMERSIVE_STICKY：滑动后显示了system bars。
//            半透明的system bars会短暂的出现然后再次消失。这种行为不会清除任何的flags
        decorView.setSystemUiVisibility(uiOptions);
    }

    /**
     * 设置让应用内容占据状态栏和导航栏
     * 内容延申到状态栏，原状态栏的颜色设为透明
     */
    private void setLayoutExtendsStatusBar(FragmentActivity activity){
        View decorView = activity.getWindow().getDecorView();
        int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        decorView.setSystemUiVisibility(option);
        // 设置状态栏和导航栏颜色为透明
        activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
        activity.getWindow().setNavigationBarColor(Color.TRANSPARENT);
    }

    public static void fullScreen(Window window) {
        View decorView = window.getDecorView();
        int uiOptions =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        // 隐藏导航栏，内容显示在导航栏下方
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        // 隐藏导航栏
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        // 内容延申到状态栏
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // 全屏显示，隐藏状态栏
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);
    }

    public static int getSystemStatusBarHeight(Context context) {
        int id = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        return id > 0 ? context.getResources().getDimensionPixelSize(id) : id;
    }


    public static int getStatusBarHeight(Activity activity) {
        Rect rectangle = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rectangle);
        return rectangle.top;
    }

    public static int getTitleBarHeight(Activity activity) {
        Rect rectangle = new Rect();
        Window window = activity.getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(rectangle);
        int contentViewTop = window.findViewById(Window.ID_ANDROID_CONTENT).getTop();
        return contentViewTop - rectangle.top;
    }
}

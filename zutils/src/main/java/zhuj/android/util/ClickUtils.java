package zhuj.android.util;

import android.os.SystemClock;
import android.view.View;


import java.util.Timer;
import java.util.TimerTask;

import zhuj.android.R;
import zhuj.android.Toasts;

public final class ClickUtils {

    /**
     * 默认点击时间间期（毫秒）
     */
    private final static int DEFAULT_INTERVAL_MILLIS = 1000;
    /**
     * 最近一次点击的时间
     */
    private static long sLastClickTime;
    /**
     * 最近一次点击的控件ID
     */
    private static int sLastClickViewId;


    private final static int LAST_TIME_VIEW_TAG = R.id.view_tag_click_1;

    private ClickUtils() {
    }

    /**
     * 是否是快速点击
     *
     * @param view     点击的控件
     * @param duration 时间间期（毫秒）
     */
    public static boolean isDoubleClick(View view, long duration) {
        long cur = System.currentTimeMillis();
        Object time = view.getTag(LAST_TIME_VIEW_TAG);
        long lastTime = time == null ? 0 : (long) time;
        long timeInterval = cur - lastTime;
        if (0 < lastTime && timeInterval < duration) {
            view.setTag(LAST_TIME_VIEW_TAG, 0); // 清除点击状态
            return true;
        } else {
            view.setTag(LAST_TIME_VIEW_TAG, cur);
            return false;
        }
    }

    public static boolean isSingleClick(View view) {
        return isSingleClick(view, DEFAULT_INTERVAL_MILLIS);
    }

    /**
     * 在持续时间内只有一次点击事件有效
     *
     * @param view
     * @param duration
     */
    public static boolean isSingleClick(View view, int duration) {
        Object time = view.getTag(LAST_TIME_VIEW_TAG);
        long lastTime = time == null ? 0 : (long) time;
        long cur = System.currentTimeMillis();
        if (lastTime == 0 || cur - lastTime > duration) {
            view.setTag(LAST_TIME_VIEW_TAG, cur);
            return true;
        } else {
            view.setTag(LAST_TIME_VIEW_TAG, cur);
            return false;
        }
    }


    /**
     * 是否是快速点击
     *
     * @param v 点击的控件
     * @return true:是，false:不是
     */
    public static boolean isFastDoubleClick(View v) {
        return isFastDoubleClick(v, DEFAULT_INTERVAL_MILLIS);
    }

    /**
     * 是否是快速点击
     *
     * @param v              点击的控件
     * @param intervalMillis 时间间期（毫秒）
     */
    public static boolean isFastDoubleClick(View v, long intervalMillis) {
        long time = System.currentTimeMillis();
        int viewId = v.getId();
        long timeD = time - sLastClickTime;
        if (0 < timeD && timeD < intervalMillis && viewId == sLastClickViewId) {
            return true;
        } else {
            sLastClickTime = time;
            sLastClickViewId = viewId;
            return false;
        }
    }

    //====================多次点击==========================//

    private final static int COUNTS = 5;// 点击次数
    private final static long DURATION = 1000;// 规定有效时间
    private static long[] mHits = new long[COUNTS];

    /**
     * 连续点击
     *
     * @param listener
     */
    public static void doClick(OnContinuousClickListener listener) {
        //每次点击时，数组向前移动一位
        System.arraycopy(mHits, 1, mHits, 0, mHits.length - 1);
        //为数组最后一位赋值
        mHits[mHits.length - 1] = SystemClock.uptimeMillis();
        if (mHits[0] >= (SystemClock.uptimeMillis() - DURATION)) {
            mHits = new long[COUNTS];//重新初始化数组
            if (listener != null) {
                listener.onContinuousClick();
            }
        }
    }

    /**
     * 多次点击的监听
     */
    public interface OnContinuousClickListener {
        /**
         * 多次点击
         */
        void onContinuousClick();
    }

    /**
     * 双击退出函数
     */
    private static boolean sIsExit = false;

    /**
     * 双击返回退出程序
     */
    public static void exitBy2Click() {
        exitBy2Click(2000, null);
    }

    /**
     * 双击返回退出程序
     *
     * @param intervalMillis 按键间隔
     * @param listener       退出监听
     */
    public static void exitBy2Click(long intervalMillis, OnClick2ExitListener listener) {
        if (!sIsExit) {
            sIsExit = true;
            // 准备退出
            if (listener != null) {
                listener.onRetry();
            } else {
                Toasts.show("再按一次退出程序");
            }
            Timer tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    // 取消退出
                    sIsExit = false;
                }
                // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务
            }, intervalMillis);
        } else {
            if (listener != null) {
                listener.onExit();
            } else {
                System.exit(0);
            }
        }
    }

    /**
     * 点击返回退出监听
     */
    public interface OnClick2ExitListener {
        /**
         * 再点击一次
         */
        void onRetry();

        /**
         * 退出
         */
        void onExit();
    }
}

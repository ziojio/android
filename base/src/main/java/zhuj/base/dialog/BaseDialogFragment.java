package zhuj.base.dialog;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;

public abstract class BaseDialogFragment extends IDialogFragment {

    public static final String WINDOW_WIDTH = "w";
    public static final String WINDOW_HEIGHT = "h";
    public static final String WINDOW_GRAVITY = "g";
    public static final String WINDOW_GRAVITY_X = "gx";
    public static final String WINDOW_GRAVITY_Y = "gy";

    protected int mWidth;
    protected int mHeight;
    protected int mGravity = Gravity.CENTER;
    protected int mGravityX;
    protected int mGravityY;

    /**
     * 不设置就是 0
     *
     * @param gravity
     * @param gravity_x
     * @param gravity_y
     */
    public void setGravity(int gravity, int gravity_x, int gravity_y) {
        Bundle bundle = getArguments();
        if (bundle == null) {
            bundle = new Bundle();
        }
        bundle.putInt(WINDOW_GRAVITY, gravity);
        bundle.putInt(WINDOW_GRAVITY_X, gravity_x);
        bundle.putInt(WINDOW_GRAVITY_Y, gravity_y);

        mGravity = gravity;
        mGravityX = gravity_x;
        mGravityY = gravity_y;
    }

    public void setLayoutParams(int gravity) {
        setGravity(gravity, mGravityX, mGravityY);
    }

    public void setLayoutParams(int width, int height) {
        Bundle bundle = getArguments();
        if (bundle == null) {
            bundle = new Bundle();
        }
        bundle.putInt(WINDOW_WIDTH, width);
        bundle.putInt(WINDOW_HEIGHT, height);

        mWidth = width;
        mHeight = height;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mWidth = bundle.getInt(WINDOW_WIDTH);
            mHeight = bundle.getInt(WINDOW_HEIGHT);
            mGravity = bundle.getInt(WINDOW_GRAVITY);
            mGravityX = bundle.getInt(WINDOW_GRAVITY_X);
            mGravityY = bundle.getInt(WINDOW_GRAVITY_Y);
        }
    }

    @Override
    protected void initWindow() {
        Window window = requireDialog().getWindow();
        if (window != null) {
            WindowManager.LayoutParams params = window.getAttributes();
            if (mWidth != 0 && mHeight != 0) {
                params.width = mWidth;
                params.height = mHeight;
            }

            params.gravity = mGravity;
            params.x = mGravityX;
            params.y = mGravityY;
            window.setAttributes(params);

        }
    }

    /**
     * 给Activity 监控 Dialog 的 View Click事件
     */
    protected View.OnClickListener callbackListener;

    public void setCallbackListener(View.OnClickListener callbackListener) {
        this.callbackListener = callbackListener;
    }

    protected void addClickCallback(View... views) {
        for (View view : views) {
            view.setOnClickListener(callbackListener);
        }
    }

    protected void addClickCallback(int... viewIds) {
        for (int id : viewIds) {
            findViewById(id).setOnClickListener(callbackListener);
        }
    }

    protected void addClickListener(View.OnClickListener listener, View... views) {
        for (View view : views) {
            view.setOnClickListener(listener);
        }
    }

    protected void addClickListener(View.OnClickListener listener, int... viewIds) {
        for (int id : viewIds) {
            findViewById(id).setOnClickListener(listener);
        }
    }
}

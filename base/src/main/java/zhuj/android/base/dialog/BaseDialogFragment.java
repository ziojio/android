package zhuj.android.base.dialog;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;

public abstract class BaseDialogFragment extends  IDialogFragment {
    private static final String WINDOW_WIDTH = "win_width";
    private static final String WINDOW_HEIGHT = "win_height";
    private static final String WINDOW_GRAVITY = "win_gravity";
    private static final String WINDOW_GRAVITY_X = "win_gravity_x";
    private static final String WINDOW_GRAVITY_Y = "win_gravity_y";
    private static final String WINDOW_DIM_AMOUNT = "win_dim_amount";
    private static final String WINDOW_CANCELABLE = "win_cancelable";

    protected int mWidth = 0;
    protected int mHeight = 0;
    protected int mGravity = 0;
    protected int mGravityX = 0;
    protected int mGravityY = 0;
    protected float mDimAmount = -1;
    protected boolean dialogCancelable = true;


    public void setGravity(int gravity) {
        Bundle bundle = getArgumentsNotNull();
        bundle.putInt(WINDOW_GRAVITY, gravity);
        setArguments(bundle);
    }

    public void setGravity(int gravity, int x, int y) {
        Bundle bundle = getArgumentsNotNull();
        bundle.putInt(WINDOW_GRAVITY, gravity);
        bundle.putInt(WINDOW_GRAVITY_X, x);
        bundle.putInt(WINDOW_GRAVITY, y);
        setArguments(bundle);
    }

    public void setLayoutParams(int width, int height) {
        Bundle bundle = getArgumentsNotNull();
        bundle.putInt(WINDOW_WIDTH, width);
        bundle.putInt(WINDOW_HEIGHT, height);
        setArguments(bundle);
    }

    public void setDimAmount(float dimAmount) {
        Bundle bundle = getArgumentsNotNull();
        bundle.putFloat(WINDOW_DIM_AMOUNT, dimAmount);
        setArguments(bundle);
    }

    public void setDialogCancelable(boolean dialogCancelable) {
        Bundle bundle = getArgumentsNotNull();
        bundle.putBoolean(WINDOW_CANCELABLE, dialogCancelable);
        setArguments(bundle);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mWidth = bundle.getInt(WINDOW_WIDTH, mWidth);
            mHeight = bundle.getInt(WINDOW_HEIGHT, mHeight);
            mGravity = bundle.getInt(WINDOW_GRAVITY, mGravity);
            mGravityX = bundle.getInt(WINDOW_GRAVITY_X, mGravityX);
            mGravityY = bundle.getInt(WINDOW_GRAVITY_Y, mGravityY);
            mDimAmount = bundle.getFloat(WINDOW_DIM_AMOUNT, mDimAmount);
            dialogCancelable = bundle.getBoolean(WINDOW_CANCELABLE, dialogCancelable);
        }
    }

    @Override
    public void initWindow() {
        setCancelable(dialogCancelable);
        Window window = requireDialog().getWindow();
        if (window != null) {
            WindowManager.LayoutParams params = window.getAttributes();
            boolean isModified = false;
            if (mWidth != 0 && mHeight != 0) {
                params.width = mWidth;
                params.height = mHeight;
                isModified = true;
            }
            if (mGravity != 0) {
                params.gravity = mGravity;
                params.x = mGravityX;
                params.y = mGravityY;
                isModified = true;
            }
            if (mDimAmount != -1) {
                params.dimAmount = mDimAmount;
                isModified = true;
            }
            if (isModified) {
                window.setAttributes(params);
            }
        }
    }

}

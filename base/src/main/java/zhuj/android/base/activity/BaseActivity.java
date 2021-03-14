package zhuj.android.base.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import zhuj.android.base.R;
import zhuj.android.base.load.LoadStateManager;
import zhuj.android.utils.ScreenUtils;


public abstract class BaseActivity extends IActivity {
    private static final boolean DEBUG = true;
    private static final String BASE_TAG = "BaseActivity";
    protected final String TAG = getClass().getSimpleName();

    private LoadStateManager loadStateManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (DEBUG) {
                bundle.get("");
                Log.d(BASE_TAG, "[Bundle]: " + bundle.toString());
            }
        } else {
            if (DEBUG) {
                Log.d(BASE_TAG, "[Bundle]: NULL");
            }
        }

        ScreenUtils.setStatusBarColor(this, getColor(R.color.statusbar_background_color));
        if (getLayoutRes() > 0) {
            initView();
        }
    }

    /**
     * 不覆盖 layoutId(), 不需要覆写这个方法
     */
    protected void initView() {
    }

    public IActivity getActivity() {
        return mActivity;
    }

    public LoadStateManager getLoadStateManager() {
        if (loadStateManager == null) {
            ViewGroup viewGroup = getViewHelper().findViewById(R.id.layout_load_state);
            if (viewGroup != null) {
                loadStateManager = new LoadStateManager(viewGroup);
            } else {
                loadStateManager = new LoadStateManager();
            }
        }
        return loadStateManager;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            Bundle bundle = data.getExtras();
            if (bundle != null) {
                if (DEBUG) {
                    bundle.get("");
                    Log.d(BASE_TAG, "requestCode=" + requestCode + ", resultCode=" + resultCode + ", data=" + bundle.toString());
                }
            }
        } else {
            if (DEBUG) {
                Log.d(BASE_TAG, "requestCode=" + requestCode + ", resultCode=" + resultCode);
            }
        }
    }

}

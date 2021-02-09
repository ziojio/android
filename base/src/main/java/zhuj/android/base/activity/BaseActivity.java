package zhuj.android.base.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;

import zhuj.android.ScreenUtils;
import zhuj.android.base.R;
import zhuj.android.base.helper.LoadingHelper;

public abstract class BaseActivity extends  IActivity {
    private LoadingHelper loadingHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScreenUtils.setStatusBarColor(this, getColor(R.color.statusbar_background_color));
        if(getLayoutRes() > 0){
            initView();
        }
    }

    /**
     * 不覆盖 layoutId(), 不需要覆写这个方法
     */
    protected void initView() {
    }


    public LoadingHelper getLoadingHelper() {
        if (loadingHelper == null) {
            loadingHelper = new LoadingHelper(getViewHelper().getRootView());
        }
        return loadingHelper;
    }

}

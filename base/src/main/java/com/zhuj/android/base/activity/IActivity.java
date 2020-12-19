package com.zhuj.android.base.activity;

import android.app.ActionBar;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


public abstract class IActivity extends AppCompatActivity {
    protected final String TAG = getClass().getSimpleName();
    protected IActivity mActivity;
    protected ActionBar mActionBar;
    protected Toolbar mToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        onBeforeInitialized();
        initContent();
    }

    /**
     * @return 布局 id
     */
    protected  int layoutId() {
        return 0;
    }

    /**
     * 不覆盖 layoutId(), 需要在设置content后, 手动调用 initView()
     */
    protected abstract void initView();

    protected void onBeforeInitialized() {
    }

    protected void initContent() {
        if (layoutId() > 0) {
            setContentView(layoutId());
            initView();
        }
    }

    protected void initToolbar(int toolbarId) {
        if (toolbarId > 0) {
            mToolbar = findViewById(toolbarId);
            setSupportActionBar(mToolbar);
        }
    }

    public IActivity getActivity() {
        return this;
    }
}

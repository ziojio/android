package zhuj.android.base.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import zhuj.android.utils.helper.ViewHelper;


public class IActivity extends AppCompatActivity {
    private final String TAG = "IActivity";

    protected ViewHelper mViewHelper;
    protected IActivity mActivity = this;
    private int layoutRes = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parentInit();
    }

    public void parentInit() {
        if (getLayoutRes() > 0) {
            setContentView(getLayoutRes());
        }
    }

    public int getLayoutRes() {
        return layoutRes;
    }

    public void setLayoutRes(int layoutRes) {
        this.layoutRes = layoutRes;
    }

    public void setToolbar(int toolbarId) {
        if (toolbarId > 0) {
            Toolbar mToolbar = findViewById(toolbarId);
            if (mToolbar != null) {
                setSupportActionBar(mToolbar);
            }
        }
    }

    public ViewHelper getViewHelper() {
        if (mViewHelper == null) {
            initViewHelper();
        }
        return mViewHelper;
    }

    public void initViewHelper() {
        ViewGroup vg = findViewById(android.R.id.content);
        if (vg == null || vg.getChildAt(0) == null) {
            throw new IllegalStateException("need pre initialize content view");
        }
        mViewHelper = new ViewHelper(vg.getChildAt(0));
    }

}

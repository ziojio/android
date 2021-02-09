package zhuj.android.base.activity;

import android.os.Bundle;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import zhuj.android.helper.ViewHelper;


public abstract class IActivity extends AppCompatActivity {
    protected final String TAG = getClass().getSimpleName();
    private ViewHelper mViewHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getLayoutRes() > 0) {
            setContentView(getLayoutRes());
        }
    }

    /**
     * @return 布局 id
     */
    protected int getLayoutRes() {
        return 0;
    }

    protected void initToolbar(int toolbarId) {
        if (toolbarId > 0) {
            Toolbar mToolbar = findViewById(toolbarId);
            setSupportActionBar(mToolbar);
        }
    }

    public IActivity getActivity() {
        return this;
    }

    public ViewHelper getViewHelper() {
        if (mViewHelper == null) {
            initViewHelper();
        }
        return mViewHelper;
    }

    protected void initViewHelper() {
        ViewGroup vg = findViewById(android.R.id.content);
        if (vg == null || vg.getChildAt(0) == null) {
            throw new IllegalStateException("need pre initialize content view");
        }
        mViewHelper = new ViewHelper(vg.getChildAt(0));
    }

}
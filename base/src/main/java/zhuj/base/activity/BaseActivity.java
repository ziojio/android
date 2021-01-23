package zhuj.base.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.library.baseAdapters.BR;

import zhuj.base.R;
import zhuj.base.helper.LoadingHelper;
import zhuj.utils.ScreenUtils;


public abstract class BaseActivity extends IActivity {
    private LoadingHelper loadingHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public LoadingHelper getLoadingHelper() {
        if (loadingHelper == null) {
            loadingHelper = new LoadingHelper(getViewHelper().getRootView());
        }
        return loadingHelper;
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

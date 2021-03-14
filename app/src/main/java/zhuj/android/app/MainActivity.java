package zhuj.android.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import zhuj.android.database.sqlitehelper.AppDatabase;
import zhuj.android.base.activity.BaseActivity;
import zhuj.android.base.service.BaseService;
import zhuj.android.app.ui.activity.TestActivity;
import zhuj.android.app.ui.activity.ViewActivity;
import zhuj.android.web.activity.WebViewActivity;
import zhuj.android.utils.helper.ClickHelper;
import zhuj.java.thread.WorkExecutor;

public class MainActivity extends BaseActivity {

    private WorkExecutor workExecutor;
    private AppDatabase database;

    private TextView showText;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ClickHelper.addClickListener(
                this.getViewHelper().getRootView(),
                this::onClick,
                R.id.button_sql, R.id.button_test, R.id.button_webview,
                R.id.button_view, R.id.button_button, R.id.button_do);
    }

    @Override
    public void parentInit() {
        super.parentInit();
        setToolbar(R.id.toolbar);
    }

    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.button_sql) {
            startActivity(new Intent(getActivity(), MainActivity.class));
        } else if (id == R.id.button_test) {
            startActivity(new Intent(getActivity(), TestActivity.class));
        } else if (id == R.id.button_webview) {
            startActivity(new Intent(getActivity(), WebViewActivity.class));
        } else if (id == R.id.button_view) {
            startActivity(new Intent(getActivity(), ViewActivity.class));
        } else if (id == R.id.button_button) {// bindService();
            Intent intent = new Intent(this, BaseService.class);
            startService(intent);
            // startActivity(new Intent(mActivity, TestFragmentActivity.class));
        } else if (id == R.id.button_do) {

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

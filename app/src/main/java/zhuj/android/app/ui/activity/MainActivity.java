package zhuj.android.app.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import zhuj.android.app.R;
import zhuj.android.base.activity.BaseActivity;
import zhuj.android.database.sqlitehelper.AppDatabase;
import zhuj.android.test.TestViewModelActivity;
import zhuj.android.web.activity.WebViewActivity;
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
    public void parentInit() {
        super.parentInit();
        setToolbar(R.id.toolbar);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getViewHelper().setOnClickListener(R.id.button_sql, this::onClick);
        getViewHelper().setOnClickListener(R.id.button_test, this::onClick);
        getViewHelper().setOnClickListener(R.id.button_webview, this::onClick);
        getViewHelper().setOnClickListener(R.id.button_view, this::onClick);
        getViewHelper().setOnClickListener(R.id.button_do, this::onClick);
    }


    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.button_sql) {
            startActivity(new Intent(getActivity(), MainActivity.class));
        } else if (id == R.id.button_test) {
            startActivity(new Intent(getActivity(), TestViewModelActivity.class));
        } else if (id == R.id.button_webview) {
            startActivity(new Intent(getActivity(), WebViewActivity.class));
        } else if (id == R.id.button_view) {
            startActivity(new Intent(getActivity(), ViewActivity.class));
        } else if (id == R.id.button_do) {

        }
    }

}

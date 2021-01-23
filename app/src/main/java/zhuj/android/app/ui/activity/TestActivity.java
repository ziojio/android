package zhuj.android.app.ui.activity;

import android.os.Bundle;

import com.qmuiteam.qmui.widget.QMUILoadingView;

import zhuj.android.app.R;
import zhuj.base.activity.BaseActivity;

public class TestActivity extends BaseActivity {

    @Override
    protected int layoutId() {
        return R.layout.activity_test;
    }

    @Override
    protected void initView() {
        QMUILoadingView loading = findViewById(R.id.loading);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

}

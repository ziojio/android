package zhuj.android.app.ui.activity;

import android.os.Bundle;
import android.view.View;

import zhuj.android.app.R;
import zhuj.android.base.activity.BaseActivity;


public class ViewActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
    }

    public void onClick(View v) {
    }
}
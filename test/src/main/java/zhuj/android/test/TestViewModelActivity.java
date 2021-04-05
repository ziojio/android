package zhuj.android.test;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.google.android.material.button.MaterialButton;

import zhuj.android.test.base.viewmodel.ViewModelActivity;


public class TestViewModelActivity extends ViewModelActivity {

    TestViewModel testViewModel;
    MaterialButton materialButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_test_view_model);

        setSupportActionBar(findViewById(R.id.toolbar));
        materialButton = findViewById(R.id.button);

        testViewModel = getActivityViewModel(TestViewModel.class);
        testViewModel.getStringLiveData().observe(this, s ->
                materialButton.setText("hello world!  hello world!")
        );
        doing();
    }

    private void doing() {
        materialButton.setText("hello ");
    }
}

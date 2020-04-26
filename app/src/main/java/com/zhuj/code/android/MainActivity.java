package com.zhuj.code.android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.zhuj.code.android.view.dialog.BottomDialog;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new BottomDialog(this,R.layout.activity_main).show();
    }
}

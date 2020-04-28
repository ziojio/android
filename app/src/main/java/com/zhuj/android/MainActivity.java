package com.zhuj.android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        new BottomDialog(this,R.layout.activity_main).show();

//        startActivity(new Intent(this, Main2Activity.class));
    }

}

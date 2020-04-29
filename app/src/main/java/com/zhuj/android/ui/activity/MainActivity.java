package com.zhuj.android.ui.activity;


import android.os.Bundle;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.zhuj.android.R;
import com.zhuj.android.ui.fragment.AppFragment;


public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar(findViewById(R.id.toolbar));


        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction transaction =  fragmentManager.beginTransaction();
        transaction.add(R.id.fragment_container, new AppFragment());
        transaction.commit();

//        new BottomDialog(this,R.layout.activity_main).show();
//
//        startActivity(new Intent(this, Main2Activity.class));
    }

}

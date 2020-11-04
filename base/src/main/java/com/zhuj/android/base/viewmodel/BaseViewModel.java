package com.zhuj.android.base.viewmodel;

import androidx.databinding.BaseObservable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BaseViewModel extends ViewModel {
    private LiveData<String> stringLiveData;
    private MutableLiveData<Integer> integerMutableLiveData;

}

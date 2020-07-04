package com.zhuj.android.observer;


public interface Observer<T> extends IObservable {
    void update(T t);
}

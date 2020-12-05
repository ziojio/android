package com.zhuj.android.util.observer;


public interface Observer<T> extends IObservable {
    void update(T t);
}

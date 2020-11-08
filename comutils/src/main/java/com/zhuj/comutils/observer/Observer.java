package com.zhuj.comutils.observer;


public interface Observer<T> extends IObservable {
    void update(T t);
}

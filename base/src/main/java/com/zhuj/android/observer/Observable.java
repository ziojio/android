package com.zhuj.android.observer;

/**
 * 同时实现观察者接口，通知观察者
 *
 * @param <T> 更新观察的内容
 */
public class Observable<T> extends AbsObservable<Observer<T>> implements Observer<T> {

    @Override
    public void update(T t) {
        synchronized (mObservers) {
            for (Observer<T> observer : mObservers) {
                observer.update(t);
            }
        }
    }
}

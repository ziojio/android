package com.zhuj.android.util.helper;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

import org.greenrobot.eventbus.EventBus;


public class EventBusHelper {
    private EventBusHelper() {
    }

    public static void register(LifecycleOwner lifecycleOwner) {
        lifecycleOwner.getLifecycle().addObserver(new LifecycleObserver() {
            @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
            void ON_CREATE() {
                EventBus.getDefault().register(lifecycleOwner);
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
            void ON_DESTROY() {
                EventBus.getDefault().unregister(lifecycleOwner);
            }
        });
    }

    public static void registerStartStop(LifecycleOwner lifecycleOwner) {
        lifecycleOwner.getLifecycle().addObserver(new LifecycleObserver() {
            @OnLifecycleEvent(Lifecycle.Event.ON_START)
            void ON_START() {
                EventBus.getDefault().register(lifecycleOwner);
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
            void ON_STOP() {
                EventBus.getDefault().unregister(lifecycleOwner);
            }
        });
    }
}

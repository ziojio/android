package com.zhuj.code.lang;

@FunctionalInterface
public interface Function2<T1, T2> {
    void apply(T1 t1, T2 t2);
}

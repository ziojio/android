package com.zhuj.code.lang;

@FunctionalInterface
public interface Function<T,R> {
    R apply(T t);
}

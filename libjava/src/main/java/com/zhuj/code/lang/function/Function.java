package com.zhuj.code.lang.function;

@FunctionalInterface
public interface Function<I, O> {
    O apply(I i);
}

package com.zhuj.code.lang.function;

@FunctionalInterface
public interface FunctionIO<I, O> {
    O apply(I i);
}

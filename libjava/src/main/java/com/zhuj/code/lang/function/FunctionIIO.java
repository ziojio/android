package com.zhuj.code.lang.function;

@FunctionalInterface
public interface FunctionIIO<I1, I2, O> {
    O apply(I1 i, I2 i2);
}

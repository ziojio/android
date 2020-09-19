package com.zhuj.android.http.callback;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class ApiResultTypeImpl implements ParameterizedType {
    protected final Class<?> raw = ApiResult.class;
    private Type[] typeArguments;

    public ApiResultTypeImpl(Class<?> curClass) {
        Type type = TypeUtils.getSuperclassTypeParameter(curClass);
        this.typeArguments = new Type[]{type};
    }

    @Override
    public Type[] getActualTypeArguments() {
        return typeArguments;
    }

    @Override
    public Type getRawType() {
        return raw;
    }

    @Override
    public Type getOwnerType() {
        return null;
    }
}
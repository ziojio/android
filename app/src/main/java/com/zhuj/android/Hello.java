package com.zhuj.android;

import com.zhuj.code.libannotations.ImplClass;

@ImplClass
public class Hello {
    private String helloStr = "123456";

    public void sayHello() {
        System.out.println(helloStr);
    }
}

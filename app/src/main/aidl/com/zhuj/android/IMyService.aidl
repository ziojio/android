// IJbzhCoreServe.aidl
package com.jbzh.jbzhcoresdk;

// Declare any non-default types here with import statements
import com.jbzh.jbzhcoresdk.IJbzhCoreCallBack;
interface IJbzhCoreServe {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    String syncfunc(String funcname,String data);
    void asyncfunc(String funcname,String data,IJbzhCoreCallBack callback);
}

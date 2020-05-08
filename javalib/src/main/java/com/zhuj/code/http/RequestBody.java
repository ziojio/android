package com.zhuj.code.http;

import java.io.IOException;
import java.io.OutputStream;

public interface RequestBody {

    long contentLength();

    String contentType();

    void onWrite(OutputStream writer) throws IOException;
}

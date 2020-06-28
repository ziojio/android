package com.zhuj.android.http.request;

import java.io.IOException;
import java.io.OutputStream;

public interface RequestBody {

    /**
     * Returns the length of the content.
     */
    long contentLength();

    /**
     * Get the type of the content.
     */
    String contentType();

    /**
     * Content data.
     */
    void writeTo(OutputStream writer) throws IOException;
}
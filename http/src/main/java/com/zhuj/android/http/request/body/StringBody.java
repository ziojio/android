package com.zhuj.android.http.request.body;


import com.zhuj.android.http.util.StringUtils;
import com.zhuj.code.file.FileIO;
import com.zhuj.code.file.IOUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import static com.zhuj.android.http.HttpHeaders.VALUE_APPLICATION_STREAM;


public class StringBody implements RequestBody {

    private final String mBody;
    private final Charset mCharset;
    private final String mContentType;

    public StringBody(String body) {
        this(body, StandardCharsets.UTF_8);
    }

    public StringBody(String body, Charset charset) {
        this(body, charset, VALUE_APPLICATION_STREAM);
    }

    public StringBody(String body, String contentType) {
        this(body, StandardCharsets.UTF_8, contentType);
    }

    public StringBody(String body, Charset charset, String contentType) {
        this.mBody = body;
        this.mCharset = charset;
        this.mContentType = contentType;
    }

    @Override
    public long contentLength() {
        if (StringUtils.isEmpty(mBody)) return 0;
        byte[] bytes = IOUtils.toByteArray(mBody, mCharset);
        return bytes.length;
    }

    @Override
    public String contentType() {
        return mContentType + "; charset=" + mCharset.name();
    }

    @Override
    public void writeTo(OutputStream writer) throws IOException {
        IOUtils.write(writer, mBody, mCharset);
    }

    @Override
    public String toString() {
        return mBody;
    }
}
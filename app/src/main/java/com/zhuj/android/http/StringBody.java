/*
 * Copyright © 2018 Zhenjie Yan.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.zhuj.android.http;


import android.text.TextUtils;

import com.zhuj.android.common.IOUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import static com.zhuj.android.http.Headers.VALUE_APPLICATION_STREAM;

/**
 * Created by Zhenjie Yan on 2018/2/11.
 */
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
        if (TextUtils.isEmpty(mBody)) return 0;
        return IOUtils.toByteArray(mBody, mCharset).length;
    }

    @Override
    public String contentType() {
        return mContentType + "; charset=" + mCharset.name();
    }

    @Override
    public void onWrite(OutputStream writer) throws IOException {
        IOUtils.write(writer, mBody, mCharset);
    }

    @Override
    public String toString() {
        return mBody;
    }
}
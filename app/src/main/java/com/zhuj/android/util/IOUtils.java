/*
 * Copyright 2019 Zhenjie Yan
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
package com.zhuj.android.util;

import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created Zhenjie Yan on 2019-10-10.
 */
public class IOUtils {

    public static void write(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[2048];
        for (int len = in.read(buffer); len != -1; len = in.read(buffer)) {
            out.write(buffer, 0, len);
            out.flush();
        }
    }

    public static void close(Closeable... closeables) {
        try {
            for (Closeable close : closeables) {
                close.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void flush(Flushable... flushables) {
        try {
            for (Flushable flush : flushables) {
                flush.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

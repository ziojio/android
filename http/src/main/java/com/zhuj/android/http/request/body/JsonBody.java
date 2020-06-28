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
package com.zhuj.android.http.request.body;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import static com.zhuj.code.http.Headers.VALUE_APPLICATION_JSON;

/**
 * Created by Zhenjie Yan on 2018/2/11.
 */
public class JsonBody extends StringBody {
    
    public JsonBody(String body) {
        this(body, StandardCharsets.UTF_8);
    }

    public JsonBody(String body, Charset charset) {
        super(body, charset, VALUE_APPLICATION_JSON);
    }
}
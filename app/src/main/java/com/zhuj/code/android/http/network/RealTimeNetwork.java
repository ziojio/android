/*
 * Copyright © 2019 Zhenjie Yan.
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
package com.zhuj.code.android.http.network;

import android.content.Context;

/**
 * Created by Zhenjie Yan on 2019-05-19.
 */
public class RealTimeNetwork implements Network {

    private final Context mContext;
    private final NetworkChecker mChecker;

    public RealTimeNetwork(Context context) {
        this.mContext = context.getApplicationContext();
        this.mChecker = new NetworkChecker(mContext);
    }

    @Override
    public boolean isAvailable() {
        return mChecker.isAvailable();
    }
}
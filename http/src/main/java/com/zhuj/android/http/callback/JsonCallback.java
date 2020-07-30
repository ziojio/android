package com.zhuj.android.http.callback;


import android.service.media.MediaBrowserService;

import okhttp3.Call;

/**
 * @deprecated 所有接口返回格式都是data, resultCode, resultMsg, 使用BaseCallback等封装转换成bean类，
 */
@Deprecated
public abstract class JsonCallback extends AbstractCallback<MediaBrowserService.Result<String>> {
    @Override
    String parseResponse(Call call, String body) {
        return body;
    }
}

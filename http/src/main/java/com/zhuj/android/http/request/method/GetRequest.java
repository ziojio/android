package com.zhuj.android.http.request.method;

import com.zhuj.android.http.request.BaseRequest;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.BufferedSource;

/**
 * <p>描述：get请求</p>
 *
 * @author xuexiang
 * @since 2018/6/25 上午12:46
 */
public class GetRequest extends BaseRequest<GetRequest> {

    public GetRequest(String url) {
        super(url);
    }

    @Override
    protected  ResponseBody  generateRequest() {
        return new ResponseBody() {
            @Override
            public MediaType contentType() {
                return null;
            }

            @Override
            public long contentLength() {
                return 0;
            }

            @Override
            public BufferedSource source() {
                return null;
            }
        };
    }
}

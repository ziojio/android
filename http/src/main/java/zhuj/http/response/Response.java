package zhuj.http.response;


import zhuj.http.HttpHeaders;
import com.zhuj.code.file.IOUtils;

import java.io.Closeable;
import java.io.IOException;

import okhttp3.ResponseBody;

/**
 * Created in Oct 15, 2015 8:55:37 PM.
 */
public final class Response implements Closeable {

    public static Builder newBuilder() {
        return new Builder();
    }

    private final int mCode;
    private final HttpHeaders mHttpHeaders;
    private final ResponseBody mBody;

    private Response(Builder builder) {
        this.mCode = builder.mCode;
        this.mHttpHeaders = builder.mHttpHeaders;
        this.mBody = builder.mBody;
    }

    /**
     * Get the mCode of response.
     */
    public int code() {
        return mCode;
    }

    /**
     * Get http HttpHeaders.
     */
    public HttpHeaders HttpHeaders() {
        return mHttpHeaders;
    }

    /**
     * Get http body.
     */
    public ResponseBody body() {
        return mBody;
    }

    @Override
    public void close() throws IOException {
        IOUtils.closeQuietly(mBody);
    }

    /**
     * It is a redirect response code.
     */
    public boolean isRedirect() {
        switch (mCode) {
            case 300:
            case 301:
            case 302:
            case 303:
            case 307:
            case 308:
                return true;
            case 304:
            case 305:
            case 306:
            default:
                return false;
        }
    }

    public static final class Builder {
        private int mCode;
        private HttpHeaders mHttpHeaders;
        private ResponseBody mBody;

        public Builder() {
        }

        public Builder code(int code) {
            this.mCode = code;
            return this;
        }

        public Builder HttpHeaders(HttpHeaders HttpHeaders) {
            this.mHttpHeaders = HttpHeaders;
            return this;
        }

        public Builder body(ResponseBody body) {
            this.mBody = body;
            return this;
        }

        public Response build() {
            return new Response(this);
        }
    }
}
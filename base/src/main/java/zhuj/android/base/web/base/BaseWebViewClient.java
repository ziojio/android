package zhuj.android.base.web.base;

import android.webkit.WebViewClient;


public class BaseWebViewClient extends WebViewClientDelegate {
    private final String TAG = getClass().getSimpleName();

    private BaseWebViewClient baseWebViewClient;

    protected BaseWebViewClient(WebViewClient client) {
        super(client);
    }

    protected BaseWebViewClient() {
        super(null);
    }


    final BaseWebViewClient enq(BaseWebViewClient baseWebViewClient) {
        setDelegate(baseWebViewClient);
        this.baseWebViewClient = baseWebViewClient;
        return baseWebViewClient;
    }

    final BaseWebViewClient next() {
        return this.baseWebViewClient;
    }

}

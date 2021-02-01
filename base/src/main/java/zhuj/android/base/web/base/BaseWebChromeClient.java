package zhuj.android.base.web.base;

import android.webkit.WebChromeClient;


public class BaseWebChromeClient extends WebChromeClientDelegate {
    private final String TAG = getClass().getSimpleName();

    private BaseWebChromeClient webChromeClient;

    protected BaseWebChromeClient(WebChromeClient webChromeClient) {
        super(webChromeClient);
    }

    protected BaseWebChromeClient() {
        super(null);
    }


    final BaseWebChromeClient enq(BaseWebChromeClient baseWebChromeClient) {
        setDelegate(baseWebChromeClient);
        this.webChromeClient = baseWebChromeClient;
        return this.webChromeClient;
    }

    final BaseWebChromeClient next() {
        return this.webChromeClient;
    }

}

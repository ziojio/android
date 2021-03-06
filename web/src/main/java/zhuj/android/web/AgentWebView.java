package zhuj.android.web;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityManager;
import android.webkit.JsPromptResult;
import android.webkit.WebBackForwardList;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;


import org.json.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import zhuj.android.web.base.BaseWebChromeClient;
import zhuj.android.web.base.BaseWebViewClient;
import zhuj.android.web.base.LollipopFixedWebView;


public class AgentWebView extends LollipopFixedWebView {
    private static final String TAG = AgentWebView.class.getSimpleName();

    private Map<String, JsCallJava> mJsCallJavas;
    private Map<String, String> mInjectJavaScripts;
    private FixedOnReceivedTitle mFixedOnReceivedTitle;
    private boolean mIsInited;
    private Boolean mIsAccessibilityEnabledOriginal;

    public AgentWebView(Context context) {
        this(context, null);
    }

    public AgentWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mIsInited = true;
        mFixedOnReceivedTitle = new FixedOnReceivedTitle();
    }


    protected void addJavascriptInterfaceSupport(Object interfaceObj, String interfaceName) {
    }

    @Override
    public final void setWebChromeClient(WebChromeClient client) {
        AgentWebChrome mAgentWebChrome = new AgentWebChrome(this);
        mAgentWebChrome.setDelegate(client);
        mFixedOnReceivedTitle.setWebChromeClient(client);
        super.setWebChromeClient(mAgentWebChrome);
        setWebChromeClientSupport(mAgentWebChrome);
    }

    protected final void setWebChromeClientSupport(WebChromeClient client) {
    }

    @Override
    public final void setWebViewClient(WebViewClient client) {
        AgentWebClient mAgentWebClient = new AgentWebClient(this);
        mAgentWebClient.setDelegate(client);
        super.setWebViewClient(mAgentWebClient);
        setWebViewClientSupport(mAgentWebClient);
    }

    public final void setWebViewClientSupport(WebViewClient client) {
    }

    @Override
    public void destroy() {
        setVisibility(View.GONE);
        if (mJsCallJavas != null) {
            mJsCallJavas.clear();
        }
        if (mInjectJavaScripts != null) {
            mInjectJavaScripts.clear();
        }
        removeAllViewsInLayout();
        fixedStillAttached();
        releaseConfigCallback();
        if (mIsInited) {
            resetAccessibilityEnabled();
            Log.i(TAG, "destroy web");
            super.destroy();
        }
    }

    @Override
    public void clearHistory() {
        if (mIsInited) {
            super.clearHistory();
        }
    }

    public static Pair<Boolean, String> isWebViewPackageException(Throwable e) {
        String messageCause = e.getCause() == null ? e.toString() : e.getCause().toString();
        String trace = Log.getStackTraceString(e);
        if (trace.contains("android.content.pm.PackageManager$NameNotFoundException")
                || trace.contains("java.lang.RuntimeException: Cannot load WebView")
                || trace.contains("android.webkit.WebViewFactory$MissingWebViewPackageException: Failed to load WebView provider: No WebView installed")) {

            Log.e(TAG, "isWebViewPackageException", e);
            return new Pair<Boolean, String>(true, "WebView load failed, " + messageCause);
        }
        return new Pair<Boolean, String>(false, messageCause);
    }

    @Override
    public void setOverScrollMode(int mode) {
        try {
            super.setOverScrollMode(mode);
        } catch (Throwable e) {
            Pair<Boolean, String> pair = isWebViewPackageException(e);
            if (pair.first) {
                Toast.makeText(getContext(), pair.second, Toast.LENGTH_SHORT).show();
                destroy();
            } else {
                throw e;
            }
        }
    }

    @Override
    public boolean isPrivateBrowsingEnabled() {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1
                && getSettings() == null) {

            return false; // getSettings().isPrivateBrowsingEnabled()
        } else {
            return super.isPrivateBrowsingEnabled();
        }
    }

    /**
     * ???????????????JavaScript???????????????addJavascriptInterface???????????????????????????????????????100%?????????????????????
     * ????????????????????????100%???????????????????????????js????????????????????????????????????????????????if (typeof(window.Android) = 'undefined')??????
     *
     * @param javaScript
     */
    public void addInjectJavaScript(String javaScript) {
        if (mInjectJavaScripts == null) {
            mInjectJavaScripts = new HashMap<String, String>();
        }
        mInjectJavaScripts.put(String.valueOf(javaScript.hashCode()), javaScript);
        injectExtraJavaScript();
    }

    private void injectJavaScript() {
        for (Map.Entry<String, JsCallJava> entry : mJsCallJavas.entrySet()) {
            this.loadUrl(buildNotRepeatInjectJS(entry.getKey(), entry.getValue().getPreloadInterfaceJs()));
        }
    }

    private void injectExtraJavaScript() {
        for (Map.Entry<String, String> entry : mInjectJavaScripts.entrySet()) {
            this.loadUrl(buildNotRepeatInjectJS(entry.getKey(), entry.getValue()));
        }
    }

    /**
     * ???????????????????????????????????????js?????????
     *
     * @param key
     * @param js
     */
    public String buildNotRepeatInjectJS(String key, String js) {
        String obj = String.format("__injectFlag_%1$s__", key);
        return "javascript:" +
                "try{ (function(){ " +
                "       if(window." + obj + ") { " +
                "           console.log('" + obj + " has been injected');" +
                "           return;" +
                "         }" +
                " window." + obj + "=true;" +
                js + "}())" +
                "}catch(e){console.warn(e)}";
    }

    /**
     * ??????????????????try catch??????js?????????
     *
     * @param js
     */
    public String buildTryCatchInjectJS(String js) {
        return "javascript:try{" + js + "}catch(e){console.warn(e)}";
    }


    public static class AgentWebClient extends BaseWebViewClient {

        private AgentWebView mAgentWebView;

        private AgentWebClient(AgentWebView agentWebView) {
            this.mAgentWebView = agentWebView;
        }

        @Override
        protected void setDelegate(WebViewClient delegate) {
            super.setDelegate(delegate);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            if (mAgentWebView.mJsCallJavas != null) {
                mAgentWebView.injectJavaScript();
                Log.d(TAG, "injectJavaScript, onPageStarted.url = " + view.getUrl());
            }
            if (mAgentWebView.mInjectJavaScripts != null) {
                mAgentWebView.injectExtraJavaScript();
            }
            mAgentWebView.mFixedOnReceivedTitle.onPageStarted();
            mAgentWebView.fixedAccessibilityInjectorExceptionForOnPageFinished(url);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            mAgentWebView.mFixedOnReceivedTitle.onPageFinished(view);
            Log.d(TAG, "onPageFinished.url = " + view.getUrl());
        }

    }

    public static class AgentWebChrome extends BaseWebChromeClient {

        private AgentWebView mAgentWebView;

        private AgentWebChrome(AgentWebView agentWebView) {
            this.mAgentWebView = agentWebView;
        }

        @Override
        protected void setDelegate(WebChromeClient delegate) {
            super.setDelegate(delegate);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            this.mAgentWebView.mFixedOnReceivedTitle.onReceivedTitle();
            super.onReceivedTitle(view, title);
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (this.mAgentWebView.mJsCallJavas != null) {
                this.mAgentWebView.injectJavaScript();
                Log.d(TAG, "injectJavaScript, onProgressChanged.newProgress = " + newProgress + ", url = " + view.getUrl());
            }
            if (this.mAgentWebView.mInjectJavaScripts != null) {
                this.mAgentWebView.injectExtraJavaScript();
            }
            super.onProgressChanged(view, newProgress);

        }

        @Override
        public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
            Log.i(TAG, "onJsPrompt:" + url + "  message:" + message + "  d:" + defaultValue + "  ");
            if (this.mAgentWebView.mJsCallJavas != null && JsCallJava.isSafeWebViewCallMsg(message)) {
                JSONObject jsonObject = JsCallJava.getMsgJSONObject(message);
                String interfacedName = JsCallJava.getInterfacedName(jsonObject);
                if (interfacedName != null) {
                    JsCallJava mJsCallJava = this.mAgentWebView.mJsCallJavas.get(interfacedName);
                    if (mJsCallJava != null) {
                        result.confirm(mJsCallJava.call(view, jsonObject));
                    }
                }
                return true;
            } else {
                return super.onJsPrompt(view, url, message, defaultValue, result);
            }
        }
    }

    /**
     * ??????????????????webView??????????????????onReceivedTitle????????????????????????SM-G9008V 4.4.2??????
     */
    private static class FixedOnReceivedTitle {
        private WebChromeClient mWebChromeClient;
        private boolean mIsOnReceivedTitle;

        public void setWebChromeClient(WebChromeClient webChromeClient) {
            mWebChromeClient = webChromeClient;
        }

        public void onPageStarted() {
            mIsOnReceivedTitle = false;
        }

        public void onPageFinished(WebView view) {
            if (!mIsOnReceivedTitle && mWebChromeClient != null) {
                WebBackForwardList list = null;
                try {
                    list = view.copyBackForwardList();
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
                if (list != null
                        && list.getSize() > 0
                        && list.getCurrentIndex() >= 0
                        && list.getItemAtIndex(list.getCurrentIndex()) != null) {
                    String previousTitle = list.getItemAtIndex(list.getCurrentIndex()).getTitle();
                    mWebChromeClient.onReceivedTitle(view, previousTitle);
                }
            }
        }

        public void onReceivedTitle() {
            mIsOnReceivedTitle = true;
        }
    }

    // Activity???onDestory?????????webView???destroy???????????????????????????????????????
    private void fixedStillAttached() {
        // java.lang.Throwable: Error: WebView.destroy() called while still attached!
        // at android.webkit.WebViewClassic.destroy(WebViewClassic.java:4142)
        // at android.webkit.WebView.destroy(WebView.java:707)
        ViewParent parent = getParent();
        if (parent instanceof ViewGroup) { // ???????????????webView?????????????????????Activity???context?????????????????????????????????????????????webView??????????????????webView???
            ViewGroup mWebViewContainer = (ViewGroup) getParent();
            mWebViewContainer.removeAllViewsInLayout();
        }
    }

    // ??????WebView?????????????????????
    private void releaseConfigCallback() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) { // JELLY_BEAN
            try {
                Field field = WebView.class.getDeclaredField("mWebViewCore");
                field = field.getType().getDeclaredField("mBrowserFrame");
                field = field.getType().getDeclaredField("sConfigCallback");
                field.setAccessible(true);
                field.set(null, null);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) { // KITKAT
            try {
                Field sConfigCallback = Class.forName("android.webkit.BrowserFrame").getDeclaredField("sConfigCallback");
                if (sConfigCallback != null) {
                    sConfigCallback.setAccessible(true);
                    sConfigCallback.set(null, null);
                }
            } catch (NoSuchFieldException | ClassNotFoundException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    protected void trySetWebDebuggEnabled() {
        try {
            Class<?> clazz = WebView.class;
            Method method = clazz.getMethod("setWebContentsDebuggingEnabled", boolean.class);
            method.invoke(null, true);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    protected void fixedAccessibilityInjectorException() {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.JELLY_BEAN_MR1
                && mIsAccessibilityEnabledOriginal == null
                && isAccessibilityEnabled()) {
            mIsAccessibilityEnabledOriginal = true;
            setAccessibilityEnabled(false);
        }
    }

    protected void fixedAccessibilityInjectorExceptionForOnPageFinished(String url) {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.JELLY_BEAN
                && getSettings().getJavaScriptEnabled()
                && mIsAccessibilityEnabledOriginal == null
                && isAccessibilityEnabled()) {
            try {
                try {
                    URLEncoder.encode(String.valueOf(new URI(url)), "utf-8");
//                    URLEncodedUtils.parse(new URI(url), null); // AccessibilityInjector.getAxsUrlParameterValue
                } catch (IllegalArgumentException e) {
                    if ("bad parameter".equals(e.getMessage())) {
                        mIsAccessibilityEnabledOriginal = true;
                        setAccessibilityEnabled(false);
                        Log.i(TAG, "fixedAccessibilityInjectorExceptionForOnPageFinished.url = " + url, e);
                    }
                }
            } catch (Throwable e) {
                Log.e(TAG, "fixedAccessibilityInjectorExceptionForOnPageFinished", e);
            }
        }
    }

    private boolean isAccessibilityEnabled() {
        AccessibilityManager am = (AccessibilityManager) getContext().getSystemService(Context.ACCESSIBILITY_SERVICE);
        return am.isEnabled();
    }

    private void setAccessibilityEnabled(boolean enabled) {
        AccessibilityManager am = (AccessibilityManager) getContext().getSystemService(Context.ACCESSIBILITY_SERVICE);
        try {
            Method setAccessibilityState = am.getClass().getDeclaredMethod("setAccessibilityState", boolean.class);
            setAccessibilityState.setAccessible(true);
            setAccessibilityState.invoke(am, enabled);
            setAccessibilityState.setAccessible(false);
        } catch (Throwable e) {
            Log.e(TAG, "setAccessibilityEnabled", e);
        }
    }

    private void resetAccessibilityEnabled() {
        if (mIsAccessibilityEnabledOriginal != null) {
            setAccessibilityEnabled(mIsAccessibilityEnabledOriginal);
        }
    }
}
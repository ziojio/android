package com.zhuj.android.ui.activity;

import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.just.agentweb.AgentWeb;
import com.just.agentweb.AgentWebConfig;
import com.just.agentweb.WebViewClient;
import com.zhuj.android.R;
import com.zhuj.android.jsbridge.webview.BridgeWebView;
import com.zhuj.android.jsbridge.webview.BridgeWebViewClient;

public class WebViewActivity extends BaseActivity {
    BridgeWebView mWebView;
    AgentWeb mAgentWeb;
    BridgeWebViewClient mBridgeWebViewClient;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webvew);
        String url = "http://www.baidu.com";
        mWebView = new BridgeWebView(this);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebContentsDebuggingEnabled(true);
        BridgeWebViewClient mBridgeWebViewClient = new BridgeWebViewClient(new BridgeWebViewClient.OnLoadJSListener() {
            @Override
            public void onLoadStart() {

            }

            @Override
            public void onLoadFinished() {

            }
        });
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(findViewById(R.id.mLinearLayout), new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .setWebView(mWebView)
                // .setWebChromeClient(mWebChromeClient)
                .setWebViewClient(new WebViewClient())
                // .setSecurityType(AgentWeb.SecurityType.strict)
                .createAgentWeb()
                .ready()
                // .go("file://"+this.getFilesDir().getPath()+"/d2/dist/index.html");
                .go(url);
        AgentWebConfig.debug();
    }
}

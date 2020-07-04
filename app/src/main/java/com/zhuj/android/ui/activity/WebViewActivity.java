package com.zhuj.android.ui.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.widget.LinearLayout;
import androidx.annotation.Nullable;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.AgentWebConfig;
import com.just.agentweb.AgentWebView;
import com.just.agentweb.WebChromeClient;
import com.just.agentweb.WebViewClient;
import com.zhuj.android.R;
import com.zhuj.android.base.activity.BaseActivity;

public class WebViewActivity extends BaseActivity {
    AgentWeb mAgentWeb;
    AgentWebView.AgentWebClient mBridgeWebViewClient;

    @Override
    protected int layoutId() {
        return R.layout.activity_webvew;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String url = "http://www.baidu.com";

        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(findViewById(R.id.mLinearLayout), new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .setWebChromeClient(mWebChromeClient)
                .setWebViewClient(mWebViewClient)
                // .setSecurityType(AgentWeb.SecurityType.strict)
                .createAgentWeb()
                .ready()
                // .go("file://"+this.getFilesDir().getPath()+"/d2/dist/index.html");
                .go(url);
        AgentWebConfig.debug();
    }

    private com.just.agentweb.WebViewClient mWebViewClient = new WebViewClient() {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return super.shouldOverrideUrlLoading(view, request);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            //do you  work
            Log.i("Info", "BaseWebActivity onPageStarted");
        }
    };
    private com.just.agentweb.WebChromeClient mWebChromeClient = new WebChromeClient() {
        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);

        }
    };
    @Override
    public void onClick(View v) {

    }
}

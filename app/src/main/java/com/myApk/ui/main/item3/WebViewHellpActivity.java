package com.myApk.ui.main.item3;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

import com.myApk.R;

/**
 * Created by admin on 2015/9/5.
 */
public class WebViewHellpActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview_hellp);
        WebView webView= (WebView) findViewById(R.id.webview);
        webView.loadUrl("file:///android_asset/html/pritection_help.html");
    }
}

package com.myApk.ui.main.item3;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.myApk.R;

/**
 * Created by admin on 2015/9/5.
 */
public class WebViewActivity extends Activity
{
    private WebView webView;
    
    private ProgressBar progressBar;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        webView = (WebView) findViewById(R.id.wv_webview);
        // 支持js
        webView.getSettings().setJavaScriptEnabled(true);
        // 创建webWiew 的辅助类
        webView.setWebViewClient(new WebViewClient()
        {
            // 回调函数：条件：webView开始载入网页代码时
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon)
            {
                super.onPageStarted(view, url, favicon);
                Toast.makeText(getBaseContext(), "拼命加载...", Toast.LENGTH_LONG)
                        .show();
            }
            
            // 回调函数：条件 webView结束载入网页代码时
            @Override
            public void onPageFinished(WebView view, String url)
            {
                super.onPageFinished(view, url);
                Toast.makeText(getBaseContext(), "加载完成...", Toast.LENGTH_LONG)
                        .show();
            }
        });
        
        // 创建webWiew 的辅助类
        webView.setWebChromeClient(new WebChromeClient()
        {
            @Override
            public void onProgressChanged(WebView view, int newProgress)
            {
                super.onProgressChanged(view, newProgress);
                if (newProgress < 100)
                {
                    System.out.println(newProgress);
                    progressBar.setProgress(newProgress);
                    progressBar.setVisibility(View.VISIBLE);
                }
                else
                {
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
        
        // 网页地址
        webView.loadUrl("http://baidu.com");
    }
}

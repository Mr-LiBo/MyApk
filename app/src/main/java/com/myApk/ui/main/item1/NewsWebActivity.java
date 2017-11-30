package com.myApk.ui.main.item1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.myApk.R;
import com.myApk.model.News;

/**
 * Created by admin on 2016/5/11.
 */
public class NewsWebActivity extends Activity
{
    private Context mContext;
    private WebView webView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_web);
        mContext = this;
        Intent intent = this.getIntent();
        News news = (News) intent.getSerializableExtra("news");
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        webView= (WebView) findViewById(R.id.wv_webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                Toast.makeText(mContext, "拼命加载....", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                Toast.makeText(mContext, "加载完成....", Toast.LENGTH_LONG).show();
            }
        });

        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress <100)
                {
                    progressBar.setProgress(newProgress);
                    progressBar.setVisibility(View.VISIBLE);
                }else
                {
                    progressBar.setVisibility(View.VISIBLE);
                }
            }
        });
        webView.loadUrl(news.url);
    }
}

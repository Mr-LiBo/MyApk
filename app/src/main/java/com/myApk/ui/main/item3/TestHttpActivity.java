package com.myApk.ui.main.item3;

import android.os.Bundle;
import android.os.Message;

import com.myApk.R;
import com.myApk.common.GlobalMessageType;
import com.myApk.framework.app.BaseActivity;
import com.myApk.logic.news.impl.NewsLogic;
import com.myApk.model.News;

import java.util.List;

/**
 * Created by admin on 2016/1/26.
 */
public class TestHttpActivity extends BaseActivity
{

    public void handleMsg(Message msg) {
        switch (msg.what)
        {
            case GlobalMessageType.TrdPtyErrPolicyMessage.TRDPTYERRPOLICY_SUCCESS_SERVER:


                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        new Thread(new Runnable() {
            @Override
            public void run() {
                List<News> lists =  NewsLogic.getInstance(TestHttpActivity.this).qryNews(true);

            }
        }).start();

    }

    @Override
    protected void initLogics() {

    }


}

package com.myApk.ui.main.item3;

import android.app.Activity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;

import com.myApk.uitl.ShareUtil;
import com.myApk.R;
/**
 * 分享
 */
public class ShareActivity extends Activity implements View.OnClickListener
{
    private  Button btn_share;
    @Override
    public void onCreate(Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        initView();
    }

    private void initView() {
        btn_share= (Button) findViewById(R.id.btn_share);
        btn_share.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_share:

              String  path ="http://att.x2.hiapk.com/forum/month_1104/11042023462f469607abc59c53.gif";
                ShareUtil.share(ShareActivity.this, "www.baidu.com", "我是好人", "他是坏人", path);
                break;
        }
    }
}

package com.myApk.ui;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.myApk.R;
import com.myApk.framework.app.BaseActivity;
import com.myApk.logic.news.impl.TextLogic;

/**
 * Created by admin on 2015/11/5.
 */
public class Test2Activity extends BaseActivity {
    TextView tv = null;
    Button tv_btn2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_text2);
        tv = (TextView) findViewById(R.id.tv_test);
        tv_btn2 = (Button) findViewById(R.id.tv_btn2);
        tv_btn2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                TextLogic.getInstance().getText();
            }
        });
    }

    @Override
    protected void initLogics() {

    }


    public void handleMsg(Message msg) {
        switch (msg.what){
            case 2:
                tv.setText("我成功了");
                break;

        }
    }
}

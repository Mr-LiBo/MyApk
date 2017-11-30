package com.myApk.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.myApk.R;
import com.myApk.framework.app.BaseActivity;
import com.myApk.logic.news.impl.TextLogic;

/**
 * Created by admin on 2015/11/4.
 */
public class TextActivity extends BaseActivity implements   OnClickListener {
    Button text_btn ;
    Button text_btn2,text_btn3;
    TextView text_tv;
    TextLogic logic = null;


    public void handleMsg(Message msg) {
        Log.i("------------->","TextActivity");
        switch (msg.what){
            case 1:
                String str = (String) msg.obj;
                Toast.makeText(TextActivity.this ,str,Toast.LENGTH_LONG).show();
                break;
            case 2:
                text_tv.setText("我成功了");
                break;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);
        logic= TextLogic.getInstance();

        text_btn = (Button) findViewById(R.id.text_btn);
        text_btn.setOnClickListener(this);
        text_btn2 = (Button) findViewById(R.id.text_btn2);
        text_btn2.setOnClickListener(this);
        text_tv = (TextView) findViewById(R.id.text_tv);
        text_btn3 =(Button) findViewById(R.id.text_btn3);
        text_btn3.setOnClickListener(this);

    }

    @Override
    protected void initLogics() {

    }


    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.text_btn:

                Log.i("------>","点击");
                logic.getText();

                break;
            case R.id.text_btn2:

                 Intent intent = new Intent(TextActivity.this,Test2Activity.class);
                startActivity(intent);

                break;
            case R.id.text_btn3:

                Intent intent1 = new Intent(TextActivity.this,Text3Activity.class);
                startActivity(intent1);

                break;
        }
    }


}

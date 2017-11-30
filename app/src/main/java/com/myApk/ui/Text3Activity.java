package com.myApk.ui;

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
public class Text3Activity extends BaseActivity implements   OnClickListener {
    Button text_btn3;
    TextView text_tv;
    TextLogic logic = null;


    public void handleMsg(Message msg) {
        Log.i("------------->","TextActivity");
        switch (msg.what){
            case 1:
                String str = (String) msg.obj;
                Toast.makeText(Text3Activity.this ,str,Toast.LENGTH_LONG).show();
                break;
            case 2:
                text_tv.setText("我成功了");
                break;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text3);
        logic= TextLogic.getInstance();


        text_btn3 = (Button) findViewById(R.id.text_btn33);
        text_btn3.setOnClickListener(this);
        text_tv = (TextView) findViewById(R.id.text_tv);
    }

    @Override
    protected void initLogics() {

    }


    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.text_btn33:

                Log.i("------>","点击");
                logic.getText();

                break;

        }
    }


}

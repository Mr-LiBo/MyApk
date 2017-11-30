package com.myApk.logic.news.impl;

import android.os.Handler;
import android.os.Message;

import com.myApk.framework.logic.BaseLogic;


/**
 * Created by admin on 2015/11/4.
 */
public class TextLogic extends BaseLogic {

    private static TextLogic logic;

    private Handler mHandler;

    public TextLogic() {

    }

//    private  TextLogic(Handler handler)
//    {
//        mHandler = handler;
//    }

    public static TextLogic  getInstance(){
        if (logic == null){
              logic= new TextLogic();
        }
        return  logic;
    }




    public void getText() {

        Message  msg1 = new Message();
        msg1.what=2;
        msg1.obj ="2";
        sendMessage(msg1);

    }
}

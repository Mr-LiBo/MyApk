package com.myApk.ui.main.item3;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import com.myApk.R;

/**
 * 加载蒙层
 * Created by admin on 2015/7/29.
 */
public class ProgressDialog  extends android.app.ProgressDialog

{
    public ProgressDialog(Context context) {
        super(context);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_dialog);
    }


    /**
     * 让手机按键返回无效
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        return false;
    }
}
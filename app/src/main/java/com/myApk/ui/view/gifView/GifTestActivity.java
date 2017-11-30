package com.myApk.ui.view.gifView;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.myApk.R;

/**
 * Created by admin on 2016/1/8.
 */
public class GifTestActivity  extends Activity{
    private static final int CLOSE_DIALOG = 1;
    private Handler mHandler = new Handler(){
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case CLOSE_DIALOG:
                    try {
                        Thread.sleep(5000);
                        Log.i("---》L", "lastTime:接");
                        finish();
                        overridePendingTransition(R.anim.zoomout, R.anim.zoomin);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
            }
        }};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.happy_new_year);
        GifView gif= (GifView) findViewById(R.id.gif);
        gif.setGifImage(R.raw.test);
        gif.setGifImageType(GifView.GifImageType.SYNC_DECODER);
        gif.setVisibility(View.VISIBLE);
        Log.i("---》L", "lastTime:发" );
//        mHandler.sendEmptyMessageDelayed(CLOSE_DIALOG, 6000);
    }


    /**
     * 让手机按键返回无效
     * @param keyCode
     * @param event
     * @return
     */
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if(keyCode == KeyEvent.KEYCODE_BACK){
//            return false;
//        }
//        return super.onKeyDown(keyCode, event);
//    }
}

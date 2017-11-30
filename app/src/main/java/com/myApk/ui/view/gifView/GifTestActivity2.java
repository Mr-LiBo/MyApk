package com.myApk.ui.view.gifView;


import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.myApk.R;


public class GifTestActivity2 extends Activity implements GifView.IGifViewListener
{
    GifView gif = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.happy_new_year);
        gif = (GifView) findViewById(R.id.gif);
        gif.setListener(this);
        gif.setGifImage(R.raw.new_year);
        gif.setGifImageType(GifView.GifImageType.SYNC_DECODER);
        gif.setVisibility(View.VISIBLE);
        setLayOutParams(gif);
    }

    private void setLayOutParams(View view)
    {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int width = (int) (1 * displayMetrics.widthPixels);
        int height = (int) (1 * displayMetrics.heightPixels);
        if (view == gif)
        {
            gif.setShowDimension(width, height);
        }
        RelativeLayout.LayoutParams params = (android.widget.RelativeLayout.LayoutParams) view.getLayoutParams();
        params.height = height;
        params.width = width;
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        view.setLayoutParams(params);
    }

    /**
     * 让手机按键返回无效
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy()
    {
        if (gif != null)
        {
            gif.destroy();
            gif = null;
        }
        super.onDestroy();
    }

    @Override
    public boolean willRunNextLoop()
    {
        runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {

                stopAnimation();
            }
        });
        return false;
    }

    private void stopAnimation()
    {
        gif.setVisibility(View.GONE);
        finish();
    }
}
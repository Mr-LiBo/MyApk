package com.myApk.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * 获得焦点
 */
public class FocusTextView extends TextView{
    public FocusTextView(Context context) {
        super(context);
    }

    public FocusTextView(Context context,AttributeSet attrs,int defStyle) {
        super(context,attrs,defStyle);
    }

    public FocusTextView(Context context,AttributeSet attrs){
        super(context,attrs);
    }

    @Override
    public boolean isFocused() {
        return  true;
    }
}

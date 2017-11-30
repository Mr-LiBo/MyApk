package com.myApk.ui.main.item3;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


import com.myApk.R;
import com.myApk.ui.view.toMenu.ExpandTabView;
import com.myApk.ui.view.toMenu.ViewLeft;
import com.myApk.ui.view.toMenu.ViewMiddle;
import com.myApk.ui.view.toMenu.ViewRight;

import java.util.ArrayList;

/**
 * Created by admin on 2015/8/19.
 */
public class TwoMenuActivit extends Activity
{
    private ExpandTabView expandTabView;
    private ArrayList<View> mViewArray = new ArrayList<>();
    private ViewLeft viewLeft;
    private ViewMiddle viewMiddle;
    private ViewRight viewRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_towmenu);
        initView();
        initValue();
        initListener();
    }

    private void initView() {
        expandTabView = (ExpandTabView) findViewById(R.id.expandtab_view);
        viewLeft = new ViewLeft(this);
        viewMiddle = new ViewMiddle(this);
        viewRight = new ViewRight(this);
    }

    private void initValue() {
        mViewArray.add(viewMiddle);
        ArrayList<String> mTextArray = new ArrayList<>();
        mTextArray.add("区域");
        expandTabView.setValue(mTextArray,mViewArray);
    }


    private void initListener() {

        viewLeft.setOnSelectListener(new ViewLeft.OnSelectListener() {

            @Override
            public void getValue(String distance, String showText) {
                onRefresh(viewLeft, showText);
            }
        });

        viewMiddle.setOnSelectListener(new ViewMiddle.OnSelectListener() {
            @Override
            public void getValue(String showText) {
                onRefresh(viewMiddle,showText);

            }
        });

        viewRight.setOnSelectListener(new ViewRight.OnSelectListener() {

            @Override
            public void getValue(String distance, String showText) {
                onRefresh(viewRight, showText);
            }
        });

    }
    private void onRefresh(View view , String showText) {
        expandTabView.onPressBack();
        int position = getPositon(view);
        if (position >= 0 && !expandTabView.getTitle(position).equals(showText))
        {
            expandTabView.setTitle(showText,position);
        }
        Toast.makeText(TwoMenuActivit.this,showText,Toast.LENGTH_LONG).show();
    }

    private int getPositon(View tView)
    {
        for (int i=0; i< mViewArray.size();i++)
        {
            if (mViewArray.get(i) == tView)
            { return i;}
        }
        return -1;
    }

    @Override
    public void onBackPressed() {
        if (!expandTabView.onPressBack()){
            finish();
        }
    }
}

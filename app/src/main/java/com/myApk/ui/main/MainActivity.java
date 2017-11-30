package com.myApk.ui.main;

import java.util.ArrayList;
import java.util.HashMap;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.myApk.framework.app.BaseV4Fragment;
import com.myApk.ui.adapter.MyBaseFragmentPagerAdapter;
import com.myApk.R;

public class MainActivity extends FragmentActivity
{
    private ViewPager mPager;
    
    private ArrayList<Fragment> fragmentList;
    
    private TextView barText;
    
    private TextView view1, view2, view3;
    
    private int currIndex;// 当前页卡编号

    private String[] tags = {"home", "online", "mall", "my"};
    public static HashMap<String, BaseV4Fragment> mFragments = new HashMap<>();
    private RadioGroup radioGroup;
    private int currentPosition = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initFragragmentData();

        radioGroup = (RadioGroup) findViewById(R.id.menu_rg_navigate_bar);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                BaseV4Fragment from = mFragments.get(tags[currentPosition]);
                currentPosition = getPositionByID(checkedId);
                BaseV4Fragment targetFragment = mFragments.get(tags[currentPosition]);
                switchContent(from, targetFragment, tags[currentPosition]);
                if(R.id.munu_rb_internet==checkedId){

                }
            }
        });

        getSupportFragmentManager().beginTransaction()
                .add(R.id.munu_fl_container, mFragments.get(tags[0]), tags[0])
                .commit();




    }



    private int getPositionByID(@IdRes int checkedId) {
        int position = 0;
        switch (checkedId) {
            case R.id.munu_rb_home:
                position = 0;
                break;
            case R.id.munu_rb_internet:
                position = 1;
                break;
            case R.id.munu_rb_mall:
                position = 2;
                break;
            case R.id.munu_rb_my:
                position = 3;
                break;
            default:
                position = 0;
                break;

        }
        return position;
    }

    private void initFragragmentData() {
        HomeFragment homeFragment = new HomeFragment();
        IntroductionFragment introductionFragment = new IntroductionFragment();
        RecommendedFragment recommendedFragment = new RecommendedFragment();

        mFragments.put(tags[0], homeFragment);
        mFragments.put(tags[1], introductionFragment);
        mFragments.put(tags[2], recommendedFragment);


    }

    private synchronized void switchContent(BaseV4Fragment from, BaseV4Fragment targetFragment, String tag) {

        if (from == targetFragment) {
            return;
        }
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (from == null && targetFragment == null) {
            return;
        }
        if (from != null) {
            fragmentTransaction.hide(from);
        }
        if (targetFragment != null) {
            if (!targetFragment.isAdded()) {
                fragmentTransaction.add(R.id.munu_fl_container, targetFragment, tag);
            } else {
                fragmentTransaction.show(targetFragment);
            }
        }
        Fragment oldFragment = getSupportFragmentManager().findFragmentByTag(tag);
        if (oldFragment != null && oldFragment != targetFragment && !oldFragment.isRemoving()) {
            fragmentTransaction.remove(oldFragment);
        }
        fragmentTransaction.commitAllowingStateLoss();
    }
    
    public class txListener implements View.OnClickListener
    {
        private int index = 0;
        
        public txListener(int i)
        {
            index = i;
        }
        
        @Override
        public void onClick(View v)
        {
            mPager.setCurrentItem(index);
        }
    }
//



    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            // TODO Auto-generated method stub
            // 取得该控件的实例
            LinearLayout.LayoutParams ll = (android.widget.LinearLayout.LayoutParams) barText
                    .getLayoutParams();

            if(currIndex == arg0){
                ll.leftMargin = (int) (currIndex * barText.getWidth() + arg1
                        * barText.getWidth());
            }else if(currIndex > arg0){
//                	 ll.leftMargin = (int) (currIndex * barText.getWidth() - (1 - arg1)* barText.getWidth());
            }
            barText.setLayoutParams(ll);
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onPageSelected(int arg0) {
            // TODO Auto-generated method stub
            currIndex = arg0;
            int i = currIndex + 1;
            Toast.makeText(MainActivity.this, "您选择了第" + i + "个页卡", Toast.LENGTH_SHORT).show();
        }
    }

}

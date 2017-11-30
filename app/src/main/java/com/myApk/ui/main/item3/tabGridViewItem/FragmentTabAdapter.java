package com.myApk.ui.main.item3.tabGridViewItem;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentActivity;
import android.widget.RadioGroup;

import com.myApk.R;
import java.util.List;

/**
 * Created by admin on 2015/9/24.
 */
public class FragmentTabAdapter implements RadioGroup.OnCheckedChangeListener {
    //一个tab对应一个Fragment
    private List<Fragment> fragments;
    //用于切换的tab
    private RadioGroup radioGroup;

    private FragmentActivity fragmentActivity;
    // Activity中所要被替换的区域的id
    private int fragmentContentId;
    //当前Tab页面索引
    private int currentTab;
    // 用于让调用者在切换tab时候增加新的功能
    private OnRgsCheckedChangedListener onRgsCheckedChangedListener;


    public FragmentTabAdapter(FragmentActivity fragmentActivity,List<Fragment> fragments,int fragmentContentId,RadioGroup rgs)
    {
        this.fragmentActivity = fragmentActivity;
        this.fragments = fragments;
        this.radioGroup = rgs;
        this.fragmentContentId = fragmentContentId;

        //默认显示第一页
        FragmentTransaction ft = fragmentActivity.getSupportFragmentManager().beginTransaction();
        ft.add(fragmentContentId,fragments.get(0));
        ft.commit();
        radioGroup.setOnCheckedChangeListener(this);

    }


    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
           for (int i =0; i<radioGroup.getChildCount();i++){
                if (radioGroup.getChildAt(i).getId() == checkedId)
                {
                    Fragment fragment = fragments.get(i);
                    FragmentTransaction ft = getFMTransaction(i);

                    getCurrentFragment().onPause();//暂停当前tab

                    if (fragment.isAdded()){
                        fragment.onResume();//启动目标tab的onResume()
                    }else{
                        ft.add(fragmentContentId,fragment);
                    }
                    showTab(i);//显示目标
                    ft.commit();
                    //如果设置了切换tab额外功能接口
                    if (null != onRgsCheckedChangedListener){
                        onRgsCheckedChangedListener.OnRgsCheckedChanged(radioGroup,checkedId,i);
                    }
                }
           }
    }

    private void showTab(int idx) {
        for (int i =0 ;i< fragments.size();i++){
            Fragment fragment = fragments.get(i);
            FragmentTransaction ft = getFMTransaction(idx);
            if (idx == i){
                ft.show(fragment);
            }else{
                ft.hide(fragment);
            }
            ft.commit();
        }
        currentTab = idx;//更新目标tab当前的tab
    }

    public FragmentTransaction getFMTransaction(int index){
        FragmentTransaction ft = fragmentActivity.getSupportFragmentManager().beginTransaction();
        //设置切换动画
        if (index >currentTab){
            ft.setCustomAnimations(R.anim.slide_left_in,R.anim.slide_left_out);
        }else {
            ft.setCustomAnimations(R.anim.slide_right_in,R.anim.slide_right_out);
        }
        return  ft;
    }

    public int getCurrentTab(){
        return  currentTab;
    }

    public Fragment getCurrentFragment(){
        return fragments.get(currentTab);
    }


    public OnRgsCheckedChangedListener getOnRgsCheckedChangedListener() {
        return onRgsCheckedChangedListener;
    }

    public void setOnRgsCheckedChangedListener(OnRgsCheckedChangedListener onRgsCheckedChangedListener) {
        this.onRgsCheckedChangedListener = onRgsCheckedChangedListener;
    }

    public static class OnRgsCheckedChangedListener{
        public void OnRgsCheckedChanged(RadioGroup rg,int checkedId,int index){

        }
    }
}

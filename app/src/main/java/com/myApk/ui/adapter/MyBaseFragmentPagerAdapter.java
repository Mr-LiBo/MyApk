package com.myApk.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;




import java.util.ArrayList;

/**
 * Created by admin on 2015/8/4.
 */
public class MyBaseFragmentPagerAdapter extends FragmentPagerAdapter
{
    ArrayList<Fragment> list;

    public MyBaseFragmentPagerAdapter(FragmentManager fm,ArrayList<Fragment> fragmentList)
    {
        super(fm);
        this.list=fragmentList;
    }

    @Override
    public Fragment getItem(int arg0)
    {
        // TODO Auto-generated method stub
        return list.get(arg0);
    }

    @Override
    public int getCount()
    {
        return list.size() != 0 ?  list.size() : 0;
    }



}

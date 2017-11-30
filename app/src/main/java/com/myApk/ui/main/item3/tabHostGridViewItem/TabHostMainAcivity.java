package com.myApk.ui.main.item3.tabHostGridViewItem;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.myApk.R;

/**
 * Created by admin on 2015/10/20.
 */
public class TabHostMainAcivity extends TabActivity implements OnClickListener
{
    private Context context;
    
    private static TabHost tabHost;
    
    public static final String TAB_JIUYI = "TAB_JIUYI";
    
    public static final String TAB_ZIXUN = "TAB_ZIXUN";
    
    public static final String TAB_FAXIAN = "TAB_FAXIAN";
    
    public static final String TAB_MYINFO = "TAB_MYINFO";
    
    private Animation left_in, left_out, right_in, right_out;
    
    private TextView tv_jy, tv_zx, tv_fx, tv_my;
    
    private ImageView iv_jy, iv_zx, iv_fx, iv_my;
    
    private Intent jyIntent, zxIntent, fxIntent, myIntent;
    
    int curTabId = R.id.ll_tabhost_jy;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabhost_main);
        this.context = this;
        
        initAnim();
        initView();
        
        // tabHost.setCurrentTabByTag(TAB_FAXIAN); //设置首次显示页
        tabHost.setCurrentTab(2); // 设置首次显示页
        
    }
    
    private void initAnim()
    {
        left_in = AnimationUtils.loadAnimation(context, R.anim.slide_left_in);
        left_out = AnimationUtils.loadAnimation(context, R.anim.slide_left_out);
        right_in = AnimationUtils.loadAnimation(context, R.anim.slide_right_in);
        right_out = AnimationUtils.loadAnimation(context,
                R.anim.slide_right_out);
    }
    
    private void initView()
    {
        iv_jy = (ImageView) findViewById(R.id.iv_jy);
        iv_zx = (ImageView) findViewById(R.id.iv_zx);
        iv_fx = (ImageView) findViewById(R.id.iv_fx);
        iv_my = (ImageView) findViewById(R.id.iv_my);
        
        findViewById(R.id.ll_tabhost_jy).setOnClickListener(this);
        findViewById(R.id.ll_tabhost_zx).setOnClickListener(this);
        findViewById(R.id.ll_tabhost_fx).setOnClickListener(this);
        findViewById(R.id.ll_tabhost_my).setOnClickListener(this);
        
        tv_jy = (TextView) findViewById(R.id.tv_jy);
        tv_zx = (TextView) findViewById(R.id.tv_zx);
        tv_fx = (TextView) findViewById(R.id.tv_fx);
        tv_my = (TextView) findViewById(R.id.tv_my);
        
        jyIntent = new Intent(this, JYActivity.class);
        zxIntent = new Intent(this, JYActivity.class);
        fxIntent = new Intent(this, FXActivity.class);
        myIntent = new Intent(this, JYActivity.class);
        
        tabHost = getTabHost();
        tabHost.addTab(buildTabSpec(TAB_JIUYI,
                R.drawable.home_tab_jouyi,
                jyIntent));
        tabHost.addTab(buildTabSpec(TAB_ZIXUN,
                R.drawable.home_tab_zixun,
                zxIntent));
        tabHost.addTab(buildTabSpec(TAB_FAXIAN,
                R.drawable.home_tab_faxian,
                fxIntent));
        tabHost.addTab(buildTabSpec(TAB_MYINFO,
                R.drawable.home_tab_myinfo,
                myIntent));
        
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener()
        {
            @Override
            public void onTabChanged(String tabId)
            {
                if (tabId.equals(TAB_JIUYI))
                {
                    
                    iv_jy.setImageResource(R.drawable.home_tab_jouyi_press);
                    tv_jy.setTextColor(getResources().getColor(R.color.orange));
                }
                if (tabId.equals(TAB_ZIXUN))
                { // 第二个标签
                
                    iv_zx.setImageResource(R.drawable.home_tab_zixun_press);
                    tv_zx.setTextColor(getResources().getColor(R.color.orange));
                }
                if (tabId.equals(TAB_FAXIAN))
                { // 第三个标签
                
                    iv_fx.setImageResource(R.drawable.home_tab_faxian_press);
                    tv_fx.setTextColor(getResources().getColor(R.color.orange));
                }
                if (tabId.equals(TAB_MYINFO))
                {
                    
                    iv_my.setImageResource(R.drawable.home_tab_myinfo_press);
                    tv_my.setTextColor(getResources().getColor(R.color.orange));
                }
                
            }
        });
    }
    
    @Override
    public void onClick(View view)
    {
        iv_jy.setImageResource(R.drawable.home_tab_jouyi);
        tv_jy.setTextColor(getResources().getColor(R.color.gray));
        iv_zx.setImageResource(R.drawable.home_tab_zixun);
        tv_zx.setTextColor(getResources().getColor(R.color.gray));
        iv_fx.setImageResource(R.drawable.home_tab_faxian);
        tv_fx.setTextColor(getResources().getColor(R.color.gray));
        iv_my.setImageResource(R.drawable.home_tab_myinfo);
        tv_my.setTextColor(getResources().getColor(R.color.gray));
        
        if (curTabId == view.getId())
        {
            return;
        }
        int checkedId = view.getId();
        final boolean bl;
        // 当前页是否为第一页
        if (curTabId < checkedId)
            bl = true;
        else
            bl = false;
        
        if (bl)
            tabHost.getCurrentView().startAnimation(left_out);
        else
            tabHost.getCurrentView().startAnimation(right_out);
        
        switch (checkedId)
        {
            case R.id.ll_tabhost_jy:
                tabHost.setCurrentTabByTag(TAB_JIUYI);
                
                break;
            case R.id.ll_tabhost_zx:
                tabHost.setCurrentTabByTag(TAB_ZIXUN);
                
                break;
            case R.id.ll_tabhost_fx:
                tabHost.setCurrentTabByTag(TAB_FAXIAN);
                
                break;
            case R.id.ll_tabhost_my:
                tabHost.setCurrentTabByTag(TAB_MYINFO);
                
                break;
        }
        if (bl)
            tabHost.getCurrentView().startAnimation(left_in);
        else
            tabHost.getCurrentView().startAnimation(right_in);
        
        curTabId = checkedId;
    }
    
    private TabHost.TabSpec buildTabSpec(String tag, int resIcon,
            final Intent content)
    {
        return tabHost.newTabSpec(tag)
                .setIndicator("", getResources().getDrawable(resIcon))
                .setContent(content);
    }
}

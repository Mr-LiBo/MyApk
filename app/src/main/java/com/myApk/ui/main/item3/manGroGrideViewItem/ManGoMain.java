package com.myApk.ui.main.item3.manGroGrideViewItem;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;

import com.myApk.R;
import com.myApk.ui.view.SyncHorizontalScroll.SyncHorizontalScrollView;

/**
 * 防芒果
 */
public class ManGoMain extends FragmentActivity{

    public static final String ARGUMENTS_NAME = "arg";
    private RelativeLayout rl_nav;

    private SyncHorizontalScrollView mHsv;

    private RadioGroup rg_nav_content;

    private ImageView iv_nav_indicator;

    private ImageView iv_nav_left;

    private ImageView iv_nav_right;

    private ViewPager viewPager;

    private int indicatorWidth;

    public static String[] tabTitle = {"精选","综艺","电视剧","电影","动漫","少儿","音乐","热榜","新闻","生活","纪录片","直播"};

    private TabFragmentPagerAdapter mAdapter;
    private LayoutInflater mInflater;

    private int currentIndicatorLeft = 0;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mango_main);
        
        findViewById();
        initView();
        setListener();
    }


    private void findViewById() {
        rl_nav = (RelativeLayout) findViewById(R.id.rl_nav);
        mHsv = (SyncHorizontalScrollView) findViewById(R.id.mhsv);
        rg_nav_content = (RadioGroup) findViewById(R.id.rg_nav_content);
        iv_nav_indicator = (ImageView) findViewById(R.id.iv_nav_indicator);
        iv_nav_left = (ImageView) findViewById(R.id.iv_nav_left);
        iv_nav_right = (ImageView) findViewById(R.id.iv_nav_right);
        viewPager = (ViewPager) findViewById(R.id.nav_viewpager);
    }

    private void initView() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        indicatorWidth = dm.widthPixels / 4;
        LayoutParams cursor_Params = iv_nav_indicator.getLayoutParams();
        cursor_Params.width = indicatorWidth;//初始化滑动下标的宽
        iv_nav_indicator.setLayoutParams(cursor_Params);

        mHsv.setSomeParam(rl_nav,iv_nav_left,iv_nav_right,this);
        //获取布局填充器
        mInflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
        //另一种方式获取
//        LayoutInflater mInflater = LayoutInflater.from(this);
        
        initNavigationHSV();
        mAdapter = new TabFragmentPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mAdapter);

    }

    private void initNavigationHSV()
    {
        rg_nav_content.removeAllViews();
        for (int i =0; i<tabTitle.length;i++){
            RadioButton rb = (RadioButton) mInflater.inflate(R.layout.nav_radiogroup_item,
                    null);
            rb.setId(i);
            rb.setText(tabTitle[i]);
            rb.setLayoutParams(new LayoutParams(indicatorWidth,
                    LayoutParams.MATCH_PARENT));
           rg_nav_content.addView(rb);

        }
    }


    private void setListener() {
        viewPager.setOnPageChangeListener(new OnPageChangeListener(){
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (rg_nav_content != null && rg_nav_content.getChildCount() > position)
                {
                    ((RadioButton) rg_nav_content.getChildAt(position)).performClick();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        rg_nav_content.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
              if (rg_nav_content.getChildAt(checkedId) != null)
              {
                  TranslateAnimation animation = new TranslateAnimation(
                          currentIndicatorLeft,
                          ((RadioButton)rg_nav_content.getChildAt(checkedId)).getLeft()
                          ,0f,0f);
                  animation.setInterpolator(new LinearInterpolator());
                  animation.setDuration(100);
                  animation.setFillAfter(true);

                  //执行位移动画
                  iv_nav_indicator.startAnimation(animation);
                  //ViewPager 跟随一起 切换
                  viewPager.setCurrentItem(checkedId);
                  //记录当前下标的距最左侧的距离
                  currentIndicatorLeft = ((RadioButton)rg_nav_content.getChildAt(checkedId)).getLeft();

                  mHsv.smoothScrollTo(
                          (checkedId > 1 ? ((RadioButton)rg_nav_content.getChildAt(checkedId)).getLeft() :0)
                                  -((RadioButton)rg_nav_content.getChildAt(2)).getLeft()
                          ,0);


              }
            }
        });
    }


    public static class  TabFragmentPagerAdapter extends FragmentPagerAdapter{

        public TabFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment ft = null;
            switch (position)
            {
                case 0:
                    ft = new LaunchUIFragment();
                    break;
                default:
                    ft= new CommonUIFragment();
                    Bundle args = new Bundle();
                    args.putString(ARGUMENTS_NAME, tabTitle[position]);
                    ft.setArguments(args);
                    break;
            }
            return ft;
        }

        @Override
        public int getCount() {
            return tabTitle.length;
        }
    }
}

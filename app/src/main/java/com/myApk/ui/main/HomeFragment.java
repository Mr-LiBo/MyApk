package com.myApk.ui.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.Toast;
import com.myApk.R;
import com.myApk.framework.app.BaseV4Fragment;
import com.myApk.logic.news.impl.NewsLogic;
import com.myApk.model.BannerInfo;
import com.myApk.model.News;
import com.myApk.ui.adapter.BannerImageAdapter;
import com.myApk.ui.adapter.ListViewAdapterNews;
import com.myApk.ui.main.item1.NewsWebActivity;
import com.myApk.ui.view.leftSlidingMenu.SlidingMenu;
import com.myApk.ui.view.viewPagerDIY.AutoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * 1
 */
public class HomeFragment extends BaseV4Fragment implements OnPageChangeListener
{
       ListViewAdapterNews myListViewAdapter;
    private static final int MSG_UI_LOAD_DATA_SERVICE = 7;

    private static final int MSG_UI_LOAD_DATA = 8;
    
    private static final int MSG_UI_START_SCROLL_BANNER = 9;
    
    private Context context;
    
    private AutoScrollViewPager viewpager;
    
    private ListView mAppList;
    
    private View mListHeaderView;
    
    private View layoutBanner;
    
    private ImageView[] mIndicatorPoints;
    
    private LinearLayout mIndicatorLayout;
    
    private BannerImageAdapter pageAdapter;

    private List<News> lists =new ArrayList<News>();

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler()
    {
        public void handleMessage(android.os.Message msg)
        {
            switch (msg.what)
            {
                case  MSG_UI_LOAD_DATA_SERVICE:
                    onLoadServiceSuccess();
                case MSG_UI_LOAD_DATA:
                    refreshBanner();

                    break;
                case MSG_UI_START_SCROLL_BANNER:
                    try
                    {
                        int page = viewpager.getCurrentItem() + 1;
                        if (viewpager != null
                                && Math.abs(System.currentTimeMillis()
                                        - viewpager.getLastScrollTime()) > 3000)
                        {
                            viewpager.setCurrentItem(page);
                        }
                        mHandler.sendEmptyMessageDelayed(MSG_UI_START_SCROLL_BANNER,
                                5000);
                        break;
                    }
                    catch (Exception e)
                    {
                    }
                    break;
                
                default:
                    break;
            }
        }


    };

    private void onLoadServiceSuccess() {
        if (lists.size()>0) {
            myListViewAdapter.setDate(lists);
        }
    }

    private SlidingMenu mMenu;
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState)
    {
        context = getActivity();
        View rootview = inflater.inflate(R.layout.fragment_home,
                container,
                false);


//
//        mMenu= (SlidingMenu) rootview.findViewById(R.id.id_menu);
//        mAppList = (ListView) rootview.findViewById(R.id.listview);
//        mAppList.addHeaderView(getHeadView());//listView添加头部布局
//
//        myListViewAdapter = new ListViewAdapterNews(context, lists);
//
//        mAppList.setAdapter(myListViewAdapter);
//
//
//        // 模拟3秒后台查询数据
//        mHandler.sendEmptyMessageDelayed(MSG_UI_LOAD_DATA, 1000);
//
//        rootview.findViewById(R.id.one).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(context, "这是第一个", Toast.LENGTH_LONG).show();
//            }
//        });
        return rootview;
    }

    @Override
    public void onResume() {
        super.onResume();
//        if (lists!= null)
//        {
//            lists.clear();
//        }
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
////                lists =  NewsLogic.getInstance(context).qryNews(true);
////                mHandler.sendEmptyMessageDelayed(MSG_UI_LOAD_DATA_SERVICE, 100);
//            }
//        }).start();
//        myListViewAdapter.notifyDataSetChanged();
//        mAppList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                News news = lists.get(position);
//                Log.e("HOMEFRAGMENT",news.url);
//
//                Intent intent = new Intent(getActivity(), NewsWebActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("news", news);
//                intent.putExtras(bundle);
//                startActivity(intent);
//                news = null;
//            }
//        });
    }

    public void toggleMenu(View view){
        mMenu.toggle();
    }

    @Override
    protected void initLogics() {

    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        if (viewpager != null)
        {
            viewpager.setAdapter(null);
            viewpager = null;
        }
        mHandler.removeCallbacksAndMessages(null);
    }
    

    /**
     * 添加列表头部
     */
    private View getHeadView()
    {
        mListHeaderView = LayoutInflater.from(context)
                .inflate(R.layout.gm_app_list_header_view, null);
        // 广告Banner图
        viewpager = (AutoScrollViewPager) mListHeaderView.findViewById(R.id.gm_banner_viewpager);
        viewpager.setOffscreenPageLimit(1);
        viewpager.setOnPageChangeListener(this);
        pageAdapter = new BannerImageAdapter(getActivity());
        ArrayList<BannerInfo> banners = new ArrayList<BannerInfo>();
        for (int i = 0; i < 1; i++)
        {
            BannerInfo banner = new BannerInfo();
            banners.add(banner);
        }
        pageAdapter.setBanners(banners);
        viewpager.setAdapter(pageAdapter);
        
        // 广告图位置指示角标
        mIndicatorLayout = (LinearLayout) mListHeaderView.findViewById(R.id.gm_banner_points_layout);
        
        layoutBanner = mListHeaderView.findViewById(R.id.gm_layout_banner);
        
        viewpager.setCurrentItem(Integer.MAX_VALUE / 2);
        return mListHeaderView;
        
    }
    
    @Override
    public void onPageScrollStateChanged(int arg0)
    {
        
    }
    
    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2)
    {
        
    }
    
    @Override
    public void onPageSelected(int position)
    {
        setImageBackground(position);
        
    }
    
    /**
     * 设置选中的Banner 指引tip的背景
     *
     * @param selectItem
     */
    private void setImageBackground(int selectItem)
    {
        if (mIndicatorPoints == null)
        {
            return;
        }
        selectItem = selectItem % mIndicatorPoints.length;
        if (mIndicatorPoints == null)
        {
            return;
        }
        int len = mIndicatorPoints.length;
        for (int i = 0; i < len; i++)
        {
            if (i == selectItem)
            {
                mIndicatorPoints[i].setBackgroundResource(R.drawable.gm_img_page_indicator_selected);
            }
            else
            {
                mIndicatorPoints[i].setBackgroundResource(R.drawable.gm_img_page_indicator_normal);
            }
        }
    }
    

    
    /**
     * 刷新Banner图
     */
    private void refreshBanner()
    {
        ArrayList<BannerInfo> banners = getData();
        pageAdapter.setBanners(banners);
        pageAdapter.notifyDataSetChanged();

        int bannerSize = banners.size();
        if (bannerSize > 1)
        {// 当banner数量大于1张时，才显示指引角标图片
            mIndicatorPoints = new ImageView[bannerSize];
            mIndicatorLayout.removeAllViews();
            for (int i = 0; i < mIndicatorPoints.length; i++)
            {
                ImageView imageView = new ImageView(context);
                LayoutParams lp = new LayoutParams(
                        ViewPager.LayoutParams.WRAP_CONTENT,
                        ViewPager.LayoutParams.WRAP_CONTENT);
                lp.setMargins(15, 0, 0, 0);
                imageView.setLayoutParams(lp);
                mIndicatorPoints[i] = imageView;
                mIndicatorLayout.addView(imageView);
            }
            int currentPosition = viewpager.getCurrentItem();
            if (currentPosition <= 0)
            {
                currentPosition = 0;
            }
            int newPosition = currentPosition - currentPosition % bannerSize;
            setImageBackground(newPosition);
            viewpager.setCurrentItem(newPosition);
            // 开始自动切换图片
            mHandler.sendEmptyMessage(MSG_UI_START_SCROLL_BANNER);
        }
        else if (bannerSize == 0)
        {
            layoutBanner.setVisibility(View.GONE);
        }
    }
    
    String[] urls = {
            "http://img4.imgtn.bdimg.com/it/u=4244817567,189862091&fm=11&gp=0.jpg",
            "http://c.hiphotos.baidu.com/image/h%3D200/sign=2a62588652e736d147138b08ab514ffc/94cad1c8a786c917ed524463cf3d70cf3bc75712.jpg",
            "http://img3.imgtn.bdimg.com/it/u=3944572699,2256604572&fm=21&gp=0.jpg",
            "http://img0.imgtn.bdimg.com/it/u=1385429875,2161223081&fm=21&gp=0.jpg" };



    private ArrayList<BannerInfo> getData()
    {
//        if (lists.size()>0)
//        {
//            for (int i=0 ;i<3;i++)
//            {
//                urls[i]= lists.get(i).photoUrl;
//            }
//        }

        ArrayList<BannerInfo> datas = new ArrayList<BannerInfo>();
        for (int i = 0; i <urls.length; i++)
        {
            BannerInfo bi = new BannerInfo("a" + i,urls[i], i, "www.baidu.com");
            datas.add(bi);
        }
        return datas;
    }
}

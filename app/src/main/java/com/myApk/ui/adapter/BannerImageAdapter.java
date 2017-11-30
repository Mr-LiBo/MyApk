
package com.myApk.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Toast;

import com.myApk.R;
import com.myApk.model.BannerInfo;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;


/**
 * 描述:Banner图适配器
 * 
 */
public class BannerImageAdapter extends PagerAdapter {

    private Activity mContext;

    private LayoutInflater mInflater;

    private ArrayList<BannerInfo> mBanners = new ArrayList<BannerInfo>();

    private View mParent;

    private int mCount = 0;

    public BannerImageAdapter(Activity context) {
        mContext = context;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        mCount = mBanners.size();
        if (mCount > 0) {
            return Integer.MAX_VALUE;
        } else {
            return 0;
        }
       
        
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return object == view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        View v = view.findViewById(R.id.gm_banner_item_image);
        if (v != null && v instanceof ImageView) {
            ImageView imgView = (ImageView) v;
            imgView.destroyDrawingCache();
        }
        container.removeView(view);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        if (mParent == null) {
            mParent = container;
        }

        final BannerInfo bi = mBanners.get(position % mCount);
        View view = mInflater.inflate(R.layout.gm_banner_item, null);
        ImageView bannerImage = (ImageView) view.findViewById(R.id.gm_banner_item_image);

        if (bi != null) {
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                	Toast.makeText(mContext, bi.getTitle(), Toast.LENGTH_SHORT).show();
                }
            });

//            String imageUrl = bi.getImageurl();
//            String key = imageUrl + "-" + position;
//            bannerImage.setTag(key);

//            Picasso.with(mContext)
//            .load(imageUrl)
//            .placeholder(R.drawable.gm_banner_bg_default)
//            .error(R.drawable.gm_banner_bg_default)
//            .into(bannerImage);

            DisplayImageOptions simpleOptions = new DisplayImageOptions.Builder()
                    .showImageOnLoading(R.drawable.ic_launcher)
                    .showImageForEmptyUri(R.drawable.ic_launcher)
                    .showImageOnFail(R.drawable.ic_launcher)
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .considerExifParams(true)
                    .bitmapConfig(Bitmap.Config.RGB_565)
                    .build();
            ImageLoader.getInstance().displayImage( bi.getImageurl(), bannerImage, simpleOptions);

            bannerImage.setScaleType(ScaleType.FIT_XY);
            ViewGroup.LayoutParams lp = bannerImage.getLayoutParams();
            lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
            lp.height = ViewGroup.LayoutParams.MATCH_PARENT;
            bannerImage.setLayoutParams(lp);

        } else {
            bannerImage.setScaleType(ScaleType.FIT_CENTER);
            ViewGroup.LayoutParams lp = bannerImage.getLayoutParams();
            lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
            lp.height = ViewGroup.LayoutParams.MATCH_PARENT;
            bannerImage.setLayoutParams(lp);
            bannerImage.setImageResource(R.drawable.gm_banner_bg_default);
        }

        container.addView(view);
        return view;
    }

    public void setBanners(ArrayList<BannerInfo> banners) {
        mBanners.clear();
        mBanners.addAll(banners);
    }

    @Override
    public int getItemPosition(Object object) {
        return PagerAdapter.POSITION_NONE;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }


}

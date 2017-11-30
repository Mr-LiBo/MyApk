
package com.myApk.ui.view.viewPagerDIY;

import android.content.Context;
import android.util.AttributeSet;

/***
 * 描述：记录上一次的滑动时间，用于滑动的banner的滑动
 * 
 */
public class AutoScrollViewPager extends CustomViewPager {

    private long lastScrollTime = System.currentTimeMillis();

    public AutoScrollViewPager(Context context) {
        super(context);
    }

    public AutoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
    }

    @Override
    protected void onPageScrolled(int position, float offset, int offsetPixels) {
        super.onPageScrolled(position, offset, offsetPixels);
        lastScrollTime = System.currentTimeMillis();
    }

    public long getLastScrollTime() {
        return lastScrollTime;
    }

    public void setLastScrollTime(long lastScrollTime) {
        this.lastScrollTime = lastScrollTime;
    }
}

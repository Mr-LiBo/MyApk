package com.myApk.ui.view.leftSlidingMenu;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import com.myApk.R;
import com.myApk.uitl.ScreenUtil;

/**
 * 滑动最左侧菜单
 */
public class SlidingMenu extends HorizontalScrollView{
    /**屏幕宽度 */
    private int mScreenWidth;

    /**dp*/
    private int mMenuRightPadding;

    /**菜单的宽度*/
    private int mMenuWidht;
    private int mHalfMenuWidth;

    private boolean isOpen;
    private boolean once;

    public SlidingMenu(Context context)
    {
       this(context,null,0);
    }

    public SlidingMenu(Context context,AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SlidingMenu(Context context,AttributeSet attributeSet,int defStyle)
    {
        super(context,attributeSet,defStyle);
        mScreenWidth = ScreenUtil.getScreenWidth(context);
        TypedArray a = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.SlidingMenu,defStyle,0);
        int n = a.getIndexCount();
        for (int i =0; i<n;i++)
        {
            int attr = a.getIndex(i);
            switch (attr){
                case R.styleable.SlidingMenu_rightPadding:
                    mMenuRightPadding = a.getDimensionPixelSize(attr,
                            (int) TypedValue.applyDimension(
                                    TypedValue.COMPLEX_UNIT_DIP,50f,getResources().getDisplayMetrics())); //默认为10DP
                    break;
            }
        }
        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //显示的设置一个宽度
        if (!once)
        {
            LinearLayout linearLayout = (LinearLayout) getChildAt(0);
            ViewGroup viewGroup = (ViewGroup) linearLayout.getChildAt(0);
            ViewGroup content = (ViewGroup) linearLayout.getChildAt(1);

            mMenuWidht = mScreenWidth - mMenuRightPadding;
            mHalfMenuWidth = mMenuWidht / 2;
            viewGroup.getLayoutParams().width = mMenuWidht;
            content.getLayoutParams().width =mScreenWidth;
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed)
        {
            //将菜单隐藏
            this.scrollTo(mMenuWidht,0);
            once = true;
        }
    }

    float start,end = 0;

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action)
        {//up时，进行判断，如果显示区域大于菜单宽度一半则完全显示，否则隐藏
            case MotionEvent.ACTION_UP:
                 end = ev.getX();
                Log.i("----------->","start: "+start +"   end: "+end +"isOpen  "+isOpen);

                if (start > end && !isOpen){
                    getParent().requestDisallowInterceptTouchEvent(false);
//                    return false;
                }

                int x = getScrollX();

//                if (x > mHalfMenuWidth /2)//左滑 大于一半 下一页
//                {
//                    return  false;
//                }
                if (x > mHalfMenuWidth)
                {
                    this.smoothScrollTo(mMenuWidht,0);
                    isOpen = false;
                }else
                {
                    this.smoothScrollTo(0,0);
                    isOpen = true;
                }
//                return  true;
            case MotionEvent.ACTION_DOWN:
                start=  ev.getX();
                break;
        }
        return super.onTouchEvent(ev);
    }

    /**
     * 打开菜单
     */
    public void openMenu()
    {
        if (isOpen){
            return;
        }
        this.smoothScrollTo(0,0);
        isOpen = true;
    }

    /**
     * 关闭菜单
     */
    public void closeMenu()
    {
        if (isOpen){
            this.smoothScrollTo(mMenuWidht,0);
            isOpen=false;
        }
    }

    /**
     * 切换菜单状态
     */
    public void toggle()
    {
        if (isOpen)
        {
            closeMenu();
        }else
        {
            openMenu();
        }
    }
}

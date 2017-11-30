
package com.myApk.ui.view.refreshList;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.myApk.R;

public class MyListViewDIY extends ListView implements OnScrollListener {

    /**刷新完成 无操作*/
    private static final int DONE = 0;
    /**下拉刷新*/
    private static final int PULL = 1;
    /**松开刷新*/
    private static final int RELEASE_To_REFRESH = 2;
    /** 正在刷新*/
    private static final int REFRESHING = 3;

    private static final int REFRESH_SUCCESS= 4;
    private static final int REFRESH_FAIL= 5;

    /**加载*/
    private static final int LOADING = 4;

    // 实际的padding的距离与界面上偏移距离的比例
    private final static int RATIO = 1;

    private LayoutInflater inflater;

    /**头部*/
    private LinearLayout headView,headLayout;

    /** listview下面的加载更多 */
    private LinearLayout footerView;

    private ImageView arrow;//箭头
    private ProgressBar progressBar;//进度
    private TextView tipsTextview;
    private TextView lastUpdatedTextView;
    RotateAnimation animation;
    RotateAnimation reverseAnimation;

    // 用于保证startY的值在一个完整的touch事件中只被记录一次
    private boolean isRecored;

    private int headContentHeight,footContentHeight;

    private int startY;

    private int firstItemIndex;

    private int state;

    private OnRefreshListener refreshListener;

    private NewScrollerListener newScrollerListener;
    //设置是否可以下滑刷新
    private boolean isRefreshable;


    public MyListViewDIY(Context context) {
        super(context);
        init(context);
    }

    public MyListViewDIY(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        // 设置箭头特效
        animation = new RotateAnimation(0, -180,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        animation.setInterpolator(new LinearInterpolator());
        animation.setDuration(100);
        animation.setFillAfter(true);

        reverseAnimation = new RotateAnimation(-180, 0,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        reverseAnimation.setInterpolator(new LinearInterpolator());
        reverseAnimation.setDuration(100);
        reverseAnimation.setFillAfter(true);

        inflater = LayoutInflater.from(context);
        footerView = (LinearLayout)inflater.inflate(R.layout.footer, null);
        //设置不可点击
        footerView.setEnabled(false);
        addFooterView(footerView, null, false);

        measureView(footerView);
        footContentHeight = footerView.getMeasuredHeight();
        hideFootView();

        headView = (LinearLayout)inflater.inflate(R.layout.head, null);
        arrow= (ImageView)headView.findViewById(R.id.arrow);
        progressBar = (ProgressBar)headView.findViewById(R.id.refreshing);
        tipsTextview = (TextView)headView.findViewById(R.id.head_tipsTextView);
        lastUpdatedTextView = (TextView)headView.findViewById(R.id.head_lastUpdatedTextView);


        measureView(headView);
        headContentHeight = headView.getMeasuredHeight();
        headView.setPadding(0, -1 * headContentHeight, 0, 0);
        headView.invalidate();

        headLayout = new LinearLayout(getContext());
        headLayout.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
        headLayout.setOrientation(LinearLayout.VERTICAL);
        headLayout.addView(headView);
        addHeaderView(headLayout, null, false);
        state = DONE;
        isRefreshable = false;

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        firstItemIndex = firstVisibleItem;
        if (newScrollerListener != null) {
            newScrollerListener.newScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
        }
    }
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (newScrollerListener != null) {
            newScrollerListener.newScrollChanged(view, scrollState);
        }
    }

    //设置是否可以下滑刷新
    public void setIsRefreshable(boolean isRefreshable) {
        this.isRefreshable = isRefreshable;
    }

    public boolean onTouchEvent(MotionEvent event)
    {
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                if (!isRefreshable)
                {// 不可以刷新 直接退出
                    break;
                }
                if (firstItemIndex == 0 && !isRecored)
                {
                    isRecored = true;
                    // headContentHeight2的含义是为了下拉刷新，需要用户多拖动headContentHeight2的距离,才进入下来刷新
                    startY = (int) event.getY() / 2;
                }
                break;
            
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                if (state != REFRESHING && state != LOADING)
                {
                    if (state == PULL)
                    {
                        state = DONE;
                        changeHeaderViewByState();
                    }
                    if (state == RELEASE_To_REFRESH)
                    {
                        state = REFRESHING;
                        changeHeaderViewByState();
                        postDelayed(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                onRefresh();
                            }
                        }, 500);
                    }
                }
                isRecored = false;

                break;
            
            case MotionEvent.ACTION_MOVE:
                // 将纵坐标除以2，防止下拉的时候，下拉出来的面积增长过快，即手指所点的地方下移时没有跟手指移动一致，速度比手指快
                int tempY = (int) event.getY() / 2;
                // 很多情况下回直接走move，不走down事件，因此这里需要做一个是否可以刷新的判断，
                if (!isRecored && firstItemIndex == 0 && isRefreshable)
                {
                    isRecored = true;
                    startY = tempY;
                }
                //无状态
                if (state == DONE)
                {
                    if (tempY - startY > 0)
                    {
                        state = PULL;
                        changeHeaderViewByState();
                    }
                }

                // 下拉
                if (state == PULL)
                {
                    headView.setPadding(0, -1 * headContentHeight + (tempY - startY) / RATIO, 0, 0);
                    // 下拉到可以进入RELEASE_TO_REFRESH的状态
                    if ((tempY - startY) / RATIO >= headContentHeight)
                    {
                        state = RELEASE_To_REFRESH;

                        changeHeaderViewByState();
                    }
                    // 上推到顶了
                    else if (tempY - startY <= 0)
                    {
                        state = DONE;
                        changeHeaderViewByState();
                    }
                }


                // 松开
                if (state == RELEASE_To_REFRESH)
                {
                    headView.setPadding(0, (tempY - startY) / RATIO   - headContentHeight, 0, 0);
                    // 往上推了，推到了屏幕足够掩盖head的程度，但是还没有推到全部掩盖的地步
                    if (((tempY - startY) / RATIO < headContentHeight)
                            && (tempY - startY) > 0)
                    {
                        state = PULL;
                        changeHeaderViewByState();
                    }
                    // 一下子推到顶了
                    else if (tempY - startY <= 0)
                    {
                        state = DONE;
                        changeHeaderViewByState();
                    }
                }
                break;
        }
        return super.onTouchEvent(event);
    }


    // 当状态改变时候，调用该方法，以更新界面
    private void changeHeaderViewByState() {
        switch (state) {
            case DONE:
                headView.setPadding(0, -1 * headContentHeight, 0, 0);
                progressBar.setVisibility(View.GONE);
                arrow.setVisibility(View.GONE);
                tipsTextview.setText(getContext().getString(R.string.activity_display_basic_refresh_down));
                tipsTextview.setVisibility(View.GONE);
                lastUpdatedTextView.setVisibility(View.GONE);
                arrow.clearAnimation();
                arrow.setAnimation(animation);
                break;

            case PULL:  //下拉
                arrow.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                tipsTextview.setVisibility(View.VISIBLE);
                tipsTextview.setText(getContext().getString(R.string.activity_display_basic_refresh_down));
                lastUpdatedTextView.setVisibility(View.VISIBLE);
                arrow.clearAnimation();
                arrow.setAnimation(reverseAnimation);

                break;

            case RELEASE_To_REFRESH: //松开刷新
                arrow.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                tipsTextview.setVisibility(View.VISIBLE);
                tipsTextview.setText(getContext().getString(R.string.activity_display_basic_refresh_slip));
                lastUpdatedTextView.setVisibility(View.VISIBLE);
                arrow.clearAnimation();
                arrow.setAnimation(animation);

                break;
            case REFRESHING:    //正在刷新
                headView.setPadding(0, 0, 0, 0);
                arrow.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                tipsTextview.setText(getContext().getString(R.string.activity_display_basic_refreshing));
                tipsTextview.setVisibility(View.VISIBLE);
                lastUpdatedTextView.setVisibility(View.VISIBLE);
                arrow.clearAnimation();
                break;

            case REFRESH_SUCCESS:
                arrow.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                tipsTextview.setText(getContext().getString(R.string.activity_display_basic_refreshok));
                SimpleDateFormat format = new SimpleDateFormat(getContext().getString( R.string.activity_display_basic_time_format));
                String date = format.format(new Date());
                lastUpdatedTextView.setText(getContext().getString(R.string.activity_display_basic_refresh_last) + date);
                break;

            case REFRESH_FAIL:
                SimpleDateFormat format2 = new SimpleDateFormat(getContext().getString(
                        R.string.activity_display_basic_time_format));
                String date2 = format2.format(new Date());
                lastUpdatedTextView.setText(getContext().getString(R.string.activity_display_basic_refresh_last) + date2);
                break;
        }
    }



    public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
        this.refreshListener = onRefreshListener;
        isRefreshable = true;
    }

    public interface OnRefreshListener {
        void onRefresh();
    }


    /***
     * 刷新成功
     * 有刷新成功的提示
     */
    public void onRefreshResulet(boolean bl){
        if(bl){
            state = REFRESH_SUCCESS;
        }else {
            state = REFRESH_FAIL;
        }
        changeHeaderViewByState();//刷界面

        state=DONE;
        postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                changeHeaderViewByState();//关闭
            }
        }, 1000);
    }


    public void setAdapter(BaseAdapter adapter) {
        SimpleDateFormat format = new SimpleDateFormat(getContext().getString(
                R.string.activity_display_basic_time_format));
        String date = format.format(new Date());
        lastUpdatedTextView.setText(getContext().getString(R.string.activity_display_basic_refresh_last) + date);
        super.setAdapter(adapter);
    }

    private void onRefresh() {
        if (refreshListener != null) {
            refreshListener.onRefresh();
        }
    }

    // 此方法直接照搬自网络上的一个下拉刷新的demo，此处是“估计”headView的width以及height
    private void measureView(View child) {
        ViewGroup.LayoutParams p = child.getLayoutParams();
        if (p == null) {
            p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
        int lpHeight = p.height;
        int childHeightSpec;
        if (lpHeight > 0) {
            childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight, MeasureSpec.EXACTLY);
        } else {
            childHeightSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        }
        child.measure(childWidthSpec, childHeightSpec);
    }



    public interface NewScrollerListener {

        public void newScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount);

        public void newScrollChanged(AbsListView view, int scrollState);

    }

    public void setNewScrollerListener(NewScrollerListener newScrollerListener) {
        this.newScrollerListener = newScrollerListener;
        setOnScrollListener(this);
    }

    /**
     * <显示加载更多中>
     */
    public void showFootView() {
        footerView.setVisibility(View.VISIBLE);
        footerView.setPadding(0, 0, 0, 0);
    }

    /**
     * <隐藏加载更多中>
     */
    public void hideFootView() {
        footerView.setVisibility(View.INVISIBLE);
        footerView.setVisibility(View.INVISIBLE);
        footerView.setPadding(0, 0, 0, 0);
    }

    /**
     * <加载更多失败>
     */
    public void showLoadFail() {
        showFootView();
        ((TextView) footerView.findViewById(R.id.tv_footer_view)).setText(R.string.common_loadmore_fail);
    }

}
package com.myApk.ui.view.refreshList;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.myApk.R;

import java.util.Date;

/**
 * Created by admin on 2015/7/16.
 */
public class MyListView extends ListView implements OnScrollListener
{
    /**发布刷新*/
    private static final int RELEASE_To_REFRESH = 0;
    /**下拉刷新*/
    private static final int PULL_To_REFRESH = 1;
    /** 正在刷新*/
    private static final int REFRESHING = 2;
    /**刷新完成*/
    private static final int DONE = 3;
    /**加载*/
    private static final int LOADING = 4;

    /** 实际的padding的距离与界面上偏移的距离的比例*/
    private final static int RATIO = 1;

    private LayoutInflater inflater;
    /**头部布局*/
    private LinearLayout headView;
    /**listview 下面的加载更多*/
    private LinearLayout footerView;

    /**箭头*/
    private ImageView arrowImageView;

    /**进度*/
    private ProgressBar progressBar;

    /**提示语*/
    private TextView tipsTextView;

    /**最后更新时间*/
    private TextView lastUpdateTextView;

    /**头部宽*/
    private int headContentWidth;
    /**头部高*/
    private int headContentHeight;

    private RotateAnimation animation;
    private RotateAnimation reverseAnmation;

    private int state;
    private boolean isBack;

    /**是否刷新的*/
    private boolean isRefreshable;
    private int firstItemIndex;
    private  int scrollState;
    private boolean isLoadFull;
    /**用于保证startY的值在一个完整的touch事件中只被记录一次*/
    private boolean isRecored;
    private boolean isLoading;//判断是否在加载
    private int startY;

    private boolean isShowFooter = false;
    private int footContentHeight;

    private boolean loadMoreEnable = true;// 开启或者关闭加载更多功能

    private OnRefreshListener onRefreshListener;//刷新
    private OnNewScrollerListener onNewScrollerListener;//更多

    private boolean shouldHideFooterSpace = false;


    public MyListView(Context context)
    {
        super(context);
        init(context);
    }
    public MyListView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init(context);
    }


    // 下拉刷新监听
    public void setOnRefreshListener(OnRefreshListener refreshListener)
    {
        isRefreshable= true;
        this.onRefreshListener = refreshListener;
    }



    private void init(Context context)
    {
        // 第一个参数表示动画的起始角度，第二个参数表示动画的结束角度，
        // 第三个表示动画的旋转中心x轴，第四个表示动画旋转中心y轴。
        animation = new RotateAnimation(0,-180,
                RotateAnimation.RELATIVE_TO_SELF,0.5f,
                RotateAnimation.RELATIVE_TO_SELF,0.5f);
        animation.setInterpolator(new LinearInterpolator());
        animation.setDuration(100);
        animation.setFillAfter(true);

        reverseAnmation = new RotateAnimation(-180,0,
                RotateAnimation.RELATIVE_TO_SELF,0.5f,
                RotateAnimation.RELATIVE_TO_SELF,0.5f);
        reverseAnmation.setInterpolator(new LinearInterpolator());
        reverseAnmation.setDuration(100);
        reverseAnmation.setFillAfter(true);

        // 自定义listview要设置背景为#0000000防止滚动时出现黑色
        setCacheColorHint((R.color.black));
        inflater = LayoutInflater.from(context);

        footerView = (LinearLayout) inflater.inflate(R.layout.listview_footer,null);
        footerView.setEnabled(false);
        this.addFooterView(footerView, null, false);
        measureView(footerView);
        footContentHeight = footerView.getMeasuredHeight();
        hideFooterView();

        headView = (LinearLayout) inflater.inflate(R.layout.listview_head,null);
        arrowImageView = (ImageView) headView.findViewById(R.id.head_arrow_imageView);
        progressBar = (ProgressBar) headView.findViewById(R.id.head_progressBar);
        tipsTextView = (TextView) headView.findViewById(R.id.head_tip_textView);
        lastUpdateTextView = (TextView) headView.findViewById(R.id.head_last_update_textView);

        measureView(headView);
        headContentWidth = headView.getMeasuredWidth();
        headContentHeight = headView.getMeasuredHeight();

        headView.setPadding(0,-1*headContentHeight,0,0);
        //刷新（重绘）view
        headView.invalidate();

        //添加头文件不被selected
        this.addHeaderView(headView, null, false);
        state = DONE;
        isRefreshable = false;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState)
    {
        if(onNewScrollerListener != null){
            onNewScrollerListener.newScrollChanged(view,firstItemIndex);
        }
    }

    /**
     * @param view
     * @param firstVisibleItem  第一个可见的视图
     * @param visibleItemCount  可见视图总数
     * @param totalItemCount    视图总数
     */
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount)
    {
        firstItemIndex = firstVisibleItem;
        if(onNewScrollerListener != null){
            onNewScrollerListener.newScroll(view,firstVisibleItem,visibleItemCount,totalItemCount);
        }
    }


    /**
     * 监听触摸事件，解读手势
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        //这里不能直接判断可以刷新，有些手机可以相应两个点击事件，导致界面没有执行up事件
        if(isRefreshable)
        {
            switch (event.getAction())
            {
                case MotionEvent.ACTION_DOWN://屏幕被按下
                    if(firstItemIndex == 0 &&  !isRecored)
                    {
                        isRecored = true;
                        startY = (int) event.getY();
                        Log.v("TAG","在down 时候记录当前位置");
                    }
                    break;
                case MotionEvent.ACTION_UP://屏幕被抬起
                    if(state != REFRESHING && state != LOADING)//正在刷新 加载
                    {
                        if(state == DONE)//刷新完成
                        {
                        }
                        if(state == PULL_To_REFRESH)//下拉刷新
                        {
                            state = DONE;//改变状态
                            changeHeaderViewByState();
                            Log.v("TAG","由下拉刷新状态，到done状态");
                        }
                        if(state == RELEASE_To_REFRESH)//发布刷新
                        {
                            state = REFRESHING;
                            changeHeaderViewByState();
                            onRefresh();//调用刷新方法
                            Log.v("this is", "由松开刷新状态，到done状态");
                        }
                    }
                    isRecored = false;
                    isBack = false;
                    break;
                case MotionEvent.ACTION_MOVE://在屏幕中拖动
                    int tempY = (int) event.getY();
                    if(!isRecored && firstItemIndex ==0)
                    {
                        isRecored = true;
                        startY = tempY;
                        Log.v("TAG","在move时候记录下位置");
                    }
                    // 保证在设置padding的过程中，当前的位置一直是在head，否则如果当列表超出屏幕的话，当在上推的时候，列表会同时进行滚动
                    // 状态 正在刷新  加载
                    if(state != REFRESHING && isRecored && state !=LOADING)
                    {
                        //可以松手去刷新了
                        if(state == RELEASE_To_REFRESH)
                        {
                            setSelection(0);
                            //往上推了，推到了屏幕足够掩盖head 的程度，但是还没有推到全部掩盖的地步
                            if(((tempY - startY) / RATIO < headContentHeight)
                                    && (tempY - startY >0))
                            {
                                state = PULL_To_REFRESH;
                                changeHeaderViewByState();
                                Log.v("TAG","由松开刷新状态转变到下拉刷新状态");
                                //一下子推到顶了
                            }else if(tempY - startY <= 0)
                            {
                                state = DONE;
                                changeHeaderViewByState();
                                Log.v("TAG","由松开刷新状态转到done 状态");
                                // 往下拉了，或者还没有上推到屏幕顶部掩盖head的地步
                            }else
                            {
                                // 不用进行特别的操作，只用更新paddingTop的值就行了
                            }
                        }
                        // 还没有到达显示松开刷新的时候,DONE或者是PULL_To_REFRESH状态
                        if(state == PULL_To_REFRESH)
                        {
                            setSelection(0);
                            //下拉到可以进入RELEASE_TO_REFRESH 的状态
                            if((tempY - startY)/RATIO >= headContentHeight)
                            {
                                state = RELEASE_To_REFRESH;
                                isBack =true;
                                changeHeaderViewByState();
                                Log.v("TAG","由done或者下拉刷新状态到松开刷新");
                            }
                            //上推到顶了
                            else if (tempY - startY <= 0)
                            {
                                state = DONE;
                                changeHeaderViewByState();
                                Log.v("TAG","由DONE 或者下拉刷新转变到done状态");
                            }
                        }
                        //done 状态下
                        if(state == DONE)
                        {
                            if(tempY - startY >0)
                            {
                                state = PULL_To_REFRESH;
                                changeHeaderViewByState();
                            }
                        }
                        //更新headView 的sixe
                        if(state == PULL_To_REFRESH)
                        {
                            headView.setPadding(0,-1*headContentHeight+(tempY - startY)/RATIO,0,0);
                        }
                        //更新headView 的paddingTop
                        if(state == RELEASE_To_REFRESH)
                        {
                            headView.setPadding(0,(tempY -startY)/RATIO -headContentHeight,0,0);
                        }
                    }
                    break;
            }
        }
        return super.onTouchEvent(event);
    }

    private void onRefresh()
    {
        if(onRefreshListener != null)
        {
            onRefreshListener.onRefresh();
        }
    }


    // 用于下拉刷新结束后的回调
    public void onRefreshSuccess()
    {
        state = DONE;
        lastUpdateTextView.setText("最近更新：" + new Date().toLocaleString());
        changeHeaderViewByState();
    }


    // 此方法直接照搬自网络上的一个下拉刷新的demo，此处是“估计”headView的width以及height
    private void measureView(View child)
    {
        ViewGroup.LayoutParams p = child.getLayoutParams();
        if(p == null)
        {
            p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        int childWidthSpec = ViewGroup.getChildMeasureSpec(0,0+0,p.width);
        int lpHeight = p.height;
        int childHeightSpec;
        if(lpHeight > 0)
        {
            // MeasureSpec.UNSPECIFIED,
            // 未指定尺寸这种情况不多，一般都是父控件是AdapterView，通过measure方法传入的模式
            // MeasureSpec.EXACTLY,精确尺寸
            // MeasureSpec.AT_MOST最大尺寸
            childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight,MeasureSpec.EXACTLY);
        }else
        {
            childHeightSpec = MeasureSpec.makeMeasureSpec(0,MeasureSpec.UNSPECIFIED);
        }
        child.measure(childWidthSpec,childHeightSpec);
    }


    /**
     * 当状态改变时候，调用该方法，以更新界面
     */
    private void changeHeaderViewByState()
    {
        switch (state)
        {
            case RELEASE_To_REFRESH:
                arrowImageView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                tipsTextView.setVisibility(View.VISIBLE);
                lastUpdateTextView.setVisibility(View.VISIBLE);
                arrowImageView.clearAnimation();
                arrowImageView.startAnimation(animation);
                break;
            case PULL_To_REFRESH:
                progressBar.setVisibility(View.GONE);
                tipsTextView.setVisibility(View.VISIBLE);
                lastUpdateTextView.setVisibility(View.VISIBLE);
                arrowImageView.clearAnimation();
                arrowImageView.setVisibility(View.VISIBLE);
                //是由RELEASE_To_REFRESH状态改变来的

                if(isBack)
                {
                    isBack = false;
                    arrowImageView.clearAnimation();
                    arrowImageView.startAnimation(reverseAnmation);
                    tipsTextView.setText("下拉刷新");
                }else
                {
                    tipsTextView.setText("下拉刷新");
                }
            case REFRESHING:
                headView.setPadding(0,0,0,0);
                progressBar.setVisibility(View.VISIBLE);
                arrowImageView.clearAnimation();
                arrowImageView.setVisibility(View.GONE);
                tipsTextView.setText("正在刷新...");
                lastUpdateTextView.setVisibility(View.VISIBLE);
                break;
            case DONE:
                headView.setPadding(0,-1*headContentHeight,0,0);
                progressBar.setVisibility(View.GONE);
                arrowImageView.clearAnimation();
                arrowImageView.setImageResource(R.drawable.arrow);
                tipsTextView.setText("下拉刷新已经加载完毕... ");
                lastUpdateTextView.setVisibility(View.VISIBLE);
                break;
        }
    }


    /**自定义下拉刷新接口*/
    public interface OnRefreshListener
    {
        public void onRefresh();
    }



    /**自定义上拉加载更多接口*/
    public interface OnNewScrollerListener
    {
        public void newScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount);
        public void newScrollChanged(AbsListView view,int scrollState);
    }
    //加载更多监听
    public void setOnNewScrollerListener(OnNewScrollerListener onNewScrollerListener){
        this.onNewScrollerListener = onNewScrollerListener;
        setOnScrollListener(this);
    }



    /**
     * <显示加载更多中>
     */
    public void showFooterView()
    {
        footerView.setVisibility(View.VISIBLE);
        footerView.setPadding(0, 0, 0, 0);
    }


    /**隐藏加载更多*/
    public void hideFooterView()
    {
        footerView.setVisibility(View.INVISIBLE);
        footerView.setPadding(0,
                isShowFooter ? -footContentHeight : 0,
                0,
                0);
    }


}

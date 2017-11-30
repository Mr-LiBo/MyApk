package com.myApk.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by admin on 2015/7/20.
 */
public class ProgressCircle extends View
{

    private float mStartAngle;

    private float mEndAngle;

    private Paint paint;

    private RectF rt;

    private Context context;

    private int mStrokeWidthPX;

    public static int DEFAULT_STROKE_WIDTH = 2;

    public static int DEFAULT_STROKE_COLOR = 0xffffffff;

    public ProgressCircle(Context context)
    {
        super(context);
        this.context = context;
        init(DEFAULT_STROKE_WIDTH, DEFAULT_STROKE_COLOR);
    }

    public ProgressCircle(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        this.context = context;
        init(DEFAULT_STROKE_WIDTH, DEFAULT_STROKE_COLOR);
    }


    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     * @param context context
     * @param dpValue dpValue
     * @return int
     */
    public static int dip2px(Context context, float dpValue)
    {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 初始化
     * [一句话功能简述]<BR>
     * [功能详细描述]
     * @param strokeWidthDP 圆圈线条宽度（单位DP）
     * @param strokeColor 圆圈线条颜色值
     */
    public void init(int strokeWidthDP, int strokeColor)
    {
        mStrokeWidthPX = dip2px(context, strokeWidthDP);

        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(mStrokeWidthPX);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setColor(strokeColor);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        if (rt == null)
        {
            rt = new RectF(mStrokeWidthPX, mStrokeWidthPX, getWidth()
                    - mStrokeWidthPX / 2, getHeight() - mStrokeWidthPX / 2);
        }
        canvas.drawArc(rt, mStartAngle, mEndAngle, false, paint);
    }

    /**
     * 绘制圆圈
     * [一句话功能简述]<BR>
     * [功能详细描述]
     * @param startAngle 开始角度
     * @param endAngle 终止角度
     */
    public void Draw(float startAngle, float endAngle)
    {
        this.mStartAngle = startAngle;
        this.mEndAngle = endAngle;
    }

}

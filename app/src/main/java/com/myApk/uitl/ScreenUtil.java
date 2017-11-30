package com.myApk.uitl;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

import java.util.Objects;

/**
 * 获得屏幕相关辅助类
 */
public class ScreenUtil {

    public ScreenUtil() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 获得屏幕宽度
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context)
    {
        WindowManager wm = (WindowManager) context.getSystemService(context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels;
    }

    public static int getScreenHeight(Context context)
    {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metrics);
        return  metrics.heightPixels;
    }

    /**
     * 获得状态栏的高度
     * @param context
     * @return
     */
    public static int getStatusHeight(Context context)
    {
        int statusHeight = -1;
        try
        {
            Class<?> cla = Class.forName("com.android.internal.R$dimen");
            Object object = cla.newInstance();
            int heigth = Integer.parseInt(cla.getField("status_bar_height").get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(heigth);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return statusHeight;
    }

    /**
     * 获取当前屏幕截图，包含状态栏
     * @param activity
     * @return
     */
    public static Bitmap snapShotWithStatusBar(Activity activity)
    {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        int width = getScreenWidth(activity);
        int heigth = getScreenHeight(activity);
        Bitmap bp = Bitmap.createBitmap(bmp,0,0,width,heigth);
        view.destroyDrawingCache();;
        return bp;
    }

    /**
     * 获取当前屏幕截图，不包含状态栏
     * @param activity
     * @return
     */
    public static Bitmap snapShotWidthOutStatusBar(Activity activity)
    {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        Rect rect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        int statusBarHeight = rect.top;

        int width = getScreenWidth(activity);
        int heigth = getScreenHeight(activity);
        Bitmap bp = Bitmap.createBitmap(bmp,0,statusBarHeight,width,heigth - statusBarHeight);
        view.destroyDrawingCache();
        return bp;
    }
}

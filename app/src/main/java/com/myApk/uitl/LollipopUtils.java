package com.myApk.uitl;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * 工具类 状态栏隐藏
 * Created by Administrator on 2016/7/30.
 */
public class LollipopUtils {
    public static int getStatusBarHeight(Context context) {
        Context appContext = context.getApplicationContext();
        int result = 0;
        int resourceId = appContext.getResources()
                .getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = appContext.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public static void setStatusbarColor(Activity activity, View view, int color) {
        //对于lollipop的设备，只需要在style.xml 中设置colorPrimaryDark即可
        //对于4.4设备，如果设置padding 即可，颜色同样在style.xml中配置
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            Window window = activity.getWindow();
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            int statusBarHeight = getStatusBarHeight(activity);
            view.setBackgroundColor(color);
            view.setPadding(0, statusBarHeight, 0, 0);
        }
    }
}

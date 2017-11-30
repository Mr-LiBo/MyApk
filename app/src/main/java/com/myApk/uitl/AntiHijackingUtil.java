package com.myApk.uitl;

import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.List;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

/**
 * Description: Author:lucien Time:2015-11-28 15:14
 */
public class AntiHijackingUtil {

    public static final String TAG = "AntiHijackingUtil";

    /**
     * 检测当前Activity是否安全
     */
    public static boolean checkOverlay(Context context) {
        boolean isOverlay = false;

        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        String runningActivityPackageName;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {// 获取系统api版本号,如果是5x系统就用这个方法获取当前运行的包名
            runningActivityPackageName = getCurrentPkgName(context);
            if (TextUtils.isEmpty(runningActivityPackageName)) {
                runningActivityPackageName = getTopAppPkg(context);
            }
        } else {
            runningActivityPackageName = activityManager.getRunningTasks(1)
                    .get(0).topActivity.getPackageName();
        }
        Log.e(TAG, "pkgName:" + runningActivityPackageName);

        if (!TextUtils.isEmpty(runningActivityPackageName)) {

            isOverlay = true;

            if (runningActivityPackageName.equals(context.getPackageName())) {
                isOverlay = false;
            }

            if (runningActivityPackageName.toLowerCase().contains("launcher")) {
                isOverlay = false;
            }
        }

        return isOverlay;
    }

    // 5x系统以后利用反射获取当前栈顶activity的包名.
    public static String getCurrentPkgName(Context context) {
        ActivityManager.RunningAppProcessInfo currentInfo = null;
        Field field = null;
        int START_TASK_TO_FRONT = 2;
        String pkgName = null;

        try {
            field = ActivityManager.RunningAppProcessInfo.class
                    .getDeclaredField("processState");// 通过反射获取进程状态字段.
        } catch (Exception e) {
            e.printStackTrace();
        }
        ActivityManager am = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List appList = am.getRunningAppProcesses();
        ActivityManager.RunningAppProcessInfo app;
        for (int i = 0; i < appList.size(); i++) {
            // ActivityManager.RunningAppProcessInfo app : appList
            app = (ActivityManager.RunningAppProcessInfo) appList.get(i);
            if (app.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {// 表示前台运行进程.
                Integer state = null;
                try {
                    state = field.getInt(app);// 反射调用字段值的方法,获取该进程的状态.
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (state != null && state == START_TASK_TO_FRONT) {// 根据这个判断条件从前台中获取当前切换的进程对象.
                    currentInfo = app;
                    break;
                }
            }
        }

        if (currentInfo != null) {
            pkgName = currentInfo.processName;
        }
        return pkgName;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private static String getTopAppPkg(Context context) {
        String pkgName = null;
        UsageStatsManager usManager = (UsageStatsManager) context
                .getSystemService(Context.USAGE_STATS_SERVICE);
        Calendar startCal = Calendar.getInstance();

        List<UsageStats> usageStats = usManager.queryUsageStats(
                UsageStatsManager.INTERVAL_BEST,
                startCal.getTimeInMillis(),
                startCal.getTimeInMillis() - 60 * 1000);
        int length = usageStats == null ? 0 : usageStats.size();
        if (length > 0) {
            pkgName = usageStats.get(length - 1).getPackageName();
        }
        return pkgName;
    }


}

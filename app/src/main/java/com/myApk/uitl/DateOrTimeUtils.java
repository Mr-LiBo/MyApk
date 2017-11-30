package com.myApk.uitl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 时间日历工具
 */
public class DateOrTimeUtils {


    private static String getCurrentTime(String format) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
        String currentTime = sdf.format(date);
        return currentTime;
    }

    public static String getCurrentTime(){
        String style = "yyyy-MM-dd HH:mm:ss";
        return getCurrentTime(style);
    }


}

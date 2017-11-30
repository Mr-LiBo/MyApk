package com.myApk.uitl;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by admin on 2015/12/9.
 */
public class ApkUtil {
    /**
     * 判断应用是否是前台运行
     * @param context
     * @return
     */
    public static boolean isReception(Context context)
    {
        boolean falg = false;
        try {
            ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            ComponentName topName = manager.getRunningTasks(1).get(0).topActivity;
            String currentPackageName = topName.getPackageName();
            if (StringUtil.isNotEmpty(currentPackageName) && currentPackageName.equals(context.getPackageName())) {
                falg = true;
            }
        }
        catch (Exception e){

        }
        return falg;
    }

    /**
     * 获取当前程序版本号
     * @param context
     * @return
     */
    public static int getVersionCode(Context context)
    {
        int versionCode;
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo info = pm.getPackageInfo(context.getApplicationContext().getPackageName(),0);
            versionCode = info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            versionCode = 000;
        }
        return versionCode;
    }

    /**
     * 获取当前
     * @param context
     * @return
     */
    public static String getVersionName(Context context)
    {
        String versionName;
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getApplicationContext().getPackageName(),0);
            versionName = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            versionName = "XXX";
        }
        return  versionName;
    }

    /**
     * 获取百度时间
     * @return
     */
    public static Date getServerTime()
    {
        try {
            URL url = new URL("http://www.baidu.com/");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            Map<String,List<String>> mapping = conn.getHeaderFields();
            List<String> obj = mapping.get("Data");
            if (null == obj ||  obj.isEmpty())
            {
                obj = mapping.get("date");
            }
            String time = obj.get(0);
            if (StringUtil.isNotEmpty(time))
            {
                time = time.substring(0,time.length());
                DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss z");
                return  df.parse(time);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  new Date();
    }

    /**
     *
     * @return
     */
    public static boolean isPhone()
    {
        try {
            Class.forName("com.google.android.mms.pdu.PduSummary");
            return true;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 判断手机号是否输入正确
     * @param username
     * @return
     */
    public static boolean checkPhoneNumber(String username)
    {
        return  username.trim().length() ==11 &&
                StringUtil.isNotEmpty(username) ? true :false;
    }

    /**
     * 检查密码是否合格
     * @param password
     * @return
     */
    public static boolean checkPassword(String password)
    {
        String reg = "^[a-zA-Z0-9_~@#$^]+$";
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(password);
        return matcher.find() ? true:false;
    }

    /**
     * 隐藏键盘
     * @param activity
     * @param editText
     */
    public static void hideKeyBoard(Activity activity,EditText editText)
    {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.ACTIVITY_SERVICE);
        imm.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(),0);
    }

    /**
     * 生成16进制
     * @param bts
     * @return
     */
    public static String bytes2Hex(byte [] bts)
    {
        StringBuffer des = new StringBuffer();
        String temp = null;
        for (int i = 0;i<bts.length;i++)
        {
            temp =Integer.toHexString (bts[i] & 0xFF);
            if (temp.length() ==1)
            {
                des.append("0");
            }
            des.append(temp);
        }
        return  des.toString();
    }

    public static String getAPKChannl(Context context)
    {
        ApplicationInfo applicationInfo = null;
        String channl = null;
        try {
            applicationInfo = context.getPackageManager().getApplicationInfo(
                    context.getPackageName(),PackageManager.GET_META_DATA);
            int value = applicationInfo.metaData.getInt("apk_channl");
            if (value < 10)
            {
                channl = "00"+value;
            }
            else if (value < 100)
            {
                channl ="0"+ value;
            }else
            {
                channl = String.valueOf(value);
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (!StringUtil.isNotEmpty(channl))
        {
            return "000";
        }
        return  channl;
    }

    public static  String getPhoneName()
    {
        return Build.PRODUCT;
    }




}

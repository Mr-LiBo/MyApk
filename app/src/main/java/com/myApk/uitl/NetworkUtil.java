package com.myApk.uitl;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.telephony.TelephonyManager;

/**
 * 网络工具
 */
public class NetworkUtil {
    /**
     * 检查是否有网络
     * @param context
     * @return
     */
    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager
                    .getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * <检查是否有网络>
     */
    public static boolean checkNetwork(Context context)
    {
        ConnectivityManager conn = (ConnectivityManager) context. getSystemService(Context.CONNECTIVITY_SERVICE);
        if (conn != null)
        {
            NetworkInfo[] nets = conn.getAllNetworkInfo();
            if (nets != null && nets.length > 0)
            {
                for (NetworkInfo net : nets)
                {
                    if (net != null && net.getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }


    /**
     * <打开设置网络界面> <功能详细描述>
     */
    public static void startSetNetworkActivity(Context context)
    {
        Intent mIntent = new Intent();
        ComponentName comp =
                new ComponentName("com.android.settings",
                        "com.android.settings.WirelessSettings");
        mIntent.setComponent(comp);
        mIntent.setAction("android.intent.action.VIEW");
        context.startActivity(mIntent);
    }

    /**
     * 判断网络WiFi状态
     * @param context
     * @return
     */
    public static boolean isWiFiConnected(Context context){
        if (null != context){
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (null != info){
                return info.isAvailable();
            }
        }
        return false;
    }

    /**
     * 判断移动网络
     * @param context
     * @return
     */
    public static boolean isMobileConnected(Context context){
        if (null != context){
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (null != info){
                return info.isAvailable();
            }
        }
        return false;
    }

    /**
     *
     * 是否是移动网络<BR>
     * @param context 上下文
     * @return boolean
     */
    public static boolean isMobileNet(Context context)
    {
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connManager == null || connManager.getActiveNetworkInfo() == null)
        {
            return false;
        }

        NetworkInfo activeNetInfo = connManager.getActiveNetworkInfo();
        if (activeNetInfo == null || !activeNetInfo.isConnected())
        {
            return false;
        }
        if (activeNetInfo.getType() == ConnectivityManager.TYPE_MOBILE && activeNetInfo.isConnected())
        {
            return true;
        }
        else
        {
            return false;
        }
    }



    /** 判断是否是飞行模式
     * @param context context
     * @return boolean
     */
    public static boolean isAirModeOn(Context context)
    {
        return (Settings.System.getInt(context.getContentResolver(),
                Settings.System.AIRPLANE_MODE_ON,
                0) == 1 ? true : false);
    }

    /**
     * 获取连接网络类型
     */
    public static int getConnectedType(Context context)
    {
        if (null != context){
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = manager.getActiveNetworkInfo();
            if (null != info && info.isAvailable()){
                return info.getType();
            }
        }
        return -1;
    }

    /**
     * 获取网络类型名称
     * @param context 上下文
     * @return 网络类型
     */
    public static String getNetworkTypeName(Context context)
    {
        String networkType = "UNKNOWN";
        ConnectivityManager connManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connManager == null || connManager.getActiveNetworkInfo() == null)
        {
            return networkType;
        }

        NetworkInfo activeNetInfo = connManager.getActiveNetworkInfo();
        if (activeNetInfo == null || !activeNetInfo.isConnected())
        {
            return networkType;
        }

        if (activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI)
        {
            networkType = "WIFI";
        }
        else if (activeNetInfo.getType() == ConnectivityManager.TYPE_MOBILE)
        {
            if (isFastMobileNetwork(context))
            {
                networkType = "3G";
            }
            else
            {
                networkType = "2G";
            }
        }
        return networkType;
    }


    private static boolean isFastMobileNetwork(Context context)
    {
        TelephonyManager telephonyManager =
                (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        switch (telephonyManager.getNetworkType())
        {
            case TelephonyManager.NETWORK_TYPE_1xRTT:
                return false; // ~ 50-100 kbps
            case TelephonyManager.NETWORK_TYPE_CDMA:
                return false; // ~ 14-64 kbps
            case TelephonyManager.NETWORK_TYPE_EDGE:
                return false; // ~ 50-100 kbps
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
                return true; // ~ 400-1000 kbps
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
                return true; // ~ 600-1400 kbps
            case TelephonyManager.NETWORK_TYPE_GPRS:
                return false; // ~ 100 kbps
            case TelephonyManager.NETWORK_TYPE_HSDPA:
                return true; // ~ 2-14 Mbps
            case TelephonyManager.NETWORK_TYPE_HSPA:
                return true; // ~ 700-1700 kbps
            case TelephonyManager.NETWORK_TYPE_HSUPA:
                return true; // ~ 1-23 Mbps
            case TelephonyManager.NETWORK_TYPE_UMTS:
                return true; // ~ 400-7000 kbps
            case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                return false;
            default:
                return false;
        }
    }


}

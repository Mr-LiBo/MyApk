package com.myApk.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.widget.Toast;

import com.myApk.uitl.NetworkUtil;

/**
 * 常驻型广播，当你的应用程序关闭了，如果有广播信息来，你写的广播接收器同样的能接收到，
 * 它的注册方式就是在你应用程序的AndroidManifast.xml 中进行注册，这种注册方式通常又被称作静态注册。
 * 这种方式可以理解为通过清单文件注册的广播是交给操作系统去处理的。
 * Created by admin on 2015/9/11.
 */
public class NetReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        //过滤器
        if (ConnectivityManager.CONNECTIVITY_ACTION .equals(action))
        {
            boolean isConnedted = NetworkUtil.isNetworkConnected(context);
            if (isConnedted){
                Toast.makeText(context,"--已连接网络--",Toast.LENGTH_LONG).show();;
            }else{
                Toast.makeText(context,"---已断开网络---",Toast.LENGTH_LONG).show();;
            }
        }
    }
}

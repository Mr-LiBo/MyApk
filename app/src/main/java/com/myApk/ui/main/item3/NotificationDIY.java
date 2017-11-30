package com.myApk.ui.main.item3;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.RemoteViews;

import com.myApk.R;

/**
 * 通知
 */
public class NotificationDIY
{

    // 通知管理器
    private static  NotificationManager nm;

    // 通知显示内容
    private static PendingIntent pd;

   public static  void createNotification(Context context)
   {
       nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);;

       Intent openintent = new Intent(context, TwoMenuActivit.class);
       pd = PendingIntent.getActivity(context, 0, openintent, 0);

       // 自定义下拉视图，比如下载软件时，显示的进度条。
       Notification notification = new Notification();

       // 通知时发出的振动
       // 第一个参数: 振动前等待的时间
       // 第二个参数： 第一次振动的时长、以此类推
       long[] vir = { 0, 100, 200, 300 };
       notification.vibrate = vir;

       // 自定义声音
       notification.sound = Uri.withAppendedPath(MediaStore.Audio.Media.INTERNAL_CONTENT_URI, "6");

       // 通知被点击后，自动消失
       notification.flags |= Notification.FLAG_AUTO_CANCEL;

       // 通知的默认参数 DEFAULT_SOUND, DEFAULT_VIBRATE, DEFAULT_LIGHTS.
       // 如果要全部采用默认值, 用 DEFAULT_ALL.
       // 此处采用默认声音
       notification.defaults |= Notification.DEFAULT_SOUND;
       notification.defaults |= Notification.DEFAULT_VIBRATE;
       notification.defaults |= Notification.DEFAULT_LIGHTS;

       notification.icon = R.drawable.ic_launcher;
       notification.tickerText = "Custom!";

       // 第二个参数 ：下拉状态栏时显示的消息标题 expanded message title
       // 第三个参数：下拉状态栏时显示的消息内容 expanded message text
       // 第四个参数：点击该通知时执行页面跳转
       RemoteViews contentView = new RemoteViews(context.getPackageName(),R.layout.notifi);
       contentView.setImageViewResource(R.id.image, R.drawable.ic_launcher);
       contentView.setTextViewText(R.id.text,"Hello, this message is in a custom expanded view");
       notification.contentView = contentView;



       // 使用自定义下拉视图时，不需要再调用setLatestEventInfo()方法
       // 但是必须定义 contentIntent
       notification.contentIntent = pd;
       nm.notify(3, notification);
   }

}

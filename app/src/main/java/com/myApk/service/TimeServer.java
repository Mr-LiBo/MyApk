package com.myApk.service;

import android.app.AlarmManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by admin on 2015/9/11.
 * Service是在一段不定的时间运行在后台，不和用户交互应用组件。
 * 每个Service必须在manifest中 通过<service>来声明。可以通过contect.startservice和contect.bindserverice来启动。
 * Service和其他的应用组件一样，运行在进程的主线程中。这就是说如果service需要很多耗时或者阻塞的操作，需要在其子线程中实现
 *　service的两种模式(startService()/bindService()不是完全分离的)：
 */
public class TimeServer extends Service {
    private AlarmManager manager;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        manager = (AlarmManager) getSystemService(Service.ALARM_SERVICE);
    }



    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        //当前系统时间
        long currentTime = System.currentTimeMillis();
        Date deta= Calendar.getInstance().getTime();
//        Date deta = new Date();   //当前时间

        Calendar calendar = Calendar.getInstance();  //得到日历
        calendar.setTime(deta);//把当前时间赋给日历
        Log.i("-->","获取星期数值(星期是从周日开始的)：" + calendar.get(Calendar.DAY_OF_WEEK));


        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //设置时间格式
        String defaultEndDate = sdf.format(deta); //格式化当前时间
        Log.i("-->","生成的时间是：" + defaultEndDate);

        if (currentTime % 2 == 0)
        {
            Toast.makeText(getBaseContext(),"今天是周"+(calendar.get(Calendar.DAY_OF_WEEK)-1)+
                    ",北京时间为："+defaultEndDate,Toast.LENGTH_LONG).show();
        }
    }
}

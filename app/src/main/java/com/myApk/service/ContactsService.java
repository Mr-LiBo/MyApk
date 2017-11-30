package com.myApk.service;

import android.app.Service;
import android.content.Intent;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.provider.ContactsContract;
import android.util.Log;


/**
 * Created by admin on 2015/12/17.
 */
public class ContactsService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        // TODO Auto-generated method stub
        super.onStart(intent, startId);
        getContentResolver().registerContentObserver( ContactsContract.Data.CONTENT_URI, true, mObserver);
    }
    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        getContentResolver().unregisterContentObserver(mObserver);
    }

    //监听联系人数据的监听对象
    private static ContentObserver mObserver = new ContentObserver( new Handler())
    {
        @Override
        public void onChange(boolean selfChange) {
            // 当联系人表发生变化时进行相应的操作
            Log.i("----------》","变化");
        }
    };
}

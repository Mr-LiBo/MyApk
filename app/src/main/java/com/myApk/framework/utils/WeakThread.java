package com.myApk.framework.utils;

import com.myApk.framework.app.BaseActivity;

import java.lang.ref.WeakReference;

/**
 * @author lucien(80993453@qq.com) on 2017/4/21.
 * @description:
 */

public class WeakThread<T extends BaseActivity> extends Thread {

    protected WeakReference<T> weakReference;

    public WeakThread(T baseActivity) {
        weakReference = new WeakReference<T>(baseActivity);
    }
}

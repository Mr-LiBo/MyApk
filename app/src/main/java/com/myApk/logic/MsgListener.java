package com.myApk.logic;

/**
 * Created by admin on 2015/9/10.
 */
public interface MsgListener {
    public void onMsg(String msg,boolean success);
    public void onCancel();
}

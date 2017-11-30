package com.myApk.framework.utils;

import java.util.Vector;

import android.os.Handler;
import android.os.Message;

public final class MessageCenter {

    private volatile static MessageCenter mInstance;

    /**
     * handler缓存集合
     */
    private final Vector<Handler> mHandlerList = new Vector<Handler>();

    private MessageCenter() {
    }

    public static MessageCenter getInstance() {
        if (mInstance == null) {
            synchronized (MessageCenter.class) {
                if (mInstance == null) {
                    mInstance = new MessageCenter();
                }
            }
        }

        return mInstance;
    }

    /**
     * 增加handler<BR>
     * 在列表里加入UI的handler
     *
     * @param handler UI传入的handler对象
     */
    public final void addHandler(Handler handler) {
        mHandlerList.add(handler);
    }

    /**
     * 移除handler<BR>
     * 在列表里移除UI的handler
     *
     * @param handler UI传入的handler对象
     */
    public final void removeHandler(Handler handler) {
        mHandlerList.remove(handler);
    }

    /**
     * 发送消息给UI<BR>
     * 通过监听回调，通知在该logic对象中所有注册了handler的UI消息message对象
     *
     * @param what 返回的消息标识
     * @param obj  返回的消息数据对象
     */
    public final void sendMessage(int what, Object obj) {
        Message message = new Message();
        message.what = what;
        message.obj = obj;
        sendMessage(message);
    }

    /**
     * 发送消息给UI<BR>
     * 通过监听回调，通知在该logic对象中所有注册了handler的UI消息message对象
     *
     * @param msg 所要发送的消息
     */
    public final void sendMessage(Message msg) {
        synchronized (MessageCenter.class) {
            for (int i = 0; i < mHandlerList.size(); ++i) {
                Handler handler = mHandlerList.get(i);
                if (handler != null) {
                    handler.sendMessage(Message.obtain(msg));
                }
            }
        }
    }

    /**
     * 发送消息给UI<BR>
     * 通过监听回调，通知在该logic对象中所有注册了handler的UI消息message对象
     *
     * @param msg         所要发送的消息
     * @param delayMillis 延迟发送的毫秒数
     */
    public final void sendMessageDelayed(Message msg, long delayMillis) {
        synchronized (MessageCenter.class) {
            for (int i = 0; i < mHandlerList.size(); ++i) {
                Handler handler = mHandlerList.get(i);
                if (handler != null) {
                    handler.sendMessageDelayed(Message.obtain(msg), delayMillis);
                }
            }
        }
    }

    /**
     * 延迟发送消息给UI<BR>
     * 通过监听回调，延迟通知在该logic对象中所有注册了handler的UI消息message对象
     *
     * @param what        返回的消息标识
     * @param obj         返回的消息数据对象
     * @param delayMillis 延迟时间，单位: 毫秒
     */
    public final void sendMessageDelayed(int what, Object obj, long delayMillis) {
        Message message = new Message();
        message.what = what;
        message.obj = obj;
        sendMessageDelayed(message, delayMillis);
    }

    /**
     * 发送无数据对象消息给UI<BR>
     * 通过监听回调，通知在该logic对象中所有注册了handler的UI消息message对象
     *
     * @param what 返回的消息标识
     */
    public final void sendEmptyMessage(int what) {
        synchronized (MessageCenter.class) {
            for (int i = 0; i < mHandlerList.size(); ++i) {
                Handler handler = mHandlerList.get(i);
                if (handler != null) {
                    handler.sendEmptyMessage(what);
                }
            }
        }
    }

    /**
     * 延迟发送空消息给UI<BR>
     * 通过监听回调，延迟通知在该logic对象中所有注册了handler的UI消息message对象
     *
     * @param what        返回的消息标识
     * @param delayMillis 延迟时间，单位: 毫秒
     */
    public final void sendEmptyMessageDelayed(int what, long delayMillis) {
        synchronized (MessageCenter.class) {
            for (int i = 0; i < mHandlerList.size(); ++i) {
                Handler handler = mHandlerList.get(i);
                if (handler != null) {
                    handler.sendEmptyMessageDelayed(what, delayMillis);
                }
            }
        }
    }
}

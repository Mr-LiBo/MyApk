package com.myApk.framework.app;

import java.util.HashSet;
import java.util.Set;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huawei.tep.utils.Logger;
import com.myApk.R;
import com.myApk.framework.logic.BaseLogicBuilder;
import com.myApk.framework.logic.ILogic;

/**
 * Activity的抽象基类<BR>
 *
 * @author 杨建通
 * @version [RCS Client V100R001C03, Feb 11, 2012]
 */
public abstract class BaseFragmentActivity extends FragmentActivity {
    private static final String TAG = "BaseFragmentActivity";

    /**
     * 系统的所有logic的缓存创建管理类
     */
    private static BaseLogicBuilder sLogicBuilder = null;

    /**
     * 该activity持有的handler类
     */
    private Handler mHandler = null;

    /**
     * 缓存持有的logic对象的集合<br>
     * 只有在 {@link #isPrivateHandler()} 返回 true 时，才会对logic进行缓存
     */
    private final Set<ILogic> mLogicSet = new HashSet<ILogic>();

    /**
     * Acitivity的初始化方法<BR>
     *
     * @param savedInstanceState Bundle对象
     * @see android.app.ActivityGroup#onCreate(android.os.Bundle)
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (sLogicBuilder == null) {
            sLogicBuilder = ((BaseApplication) getApplication()).getLogicBuilder();
        }

        if (!isPrivateHandler()) {
            sLogicBuilder.addHandlerToAllLogics(getHandler());
        }

        try {
            initLogics();
        } catch (Exception e) {
            Logger.e(TAG, "Init logics failed :" + e.getMessage(), e);
        }
    }

    /**
     * 获取hander对象<BR>
     *
     * @return 返回handler对象
     */
    protected final Handler getHandler() {
        if (mHandler == null) {
            mHandler = new Handler() {
                public void handleMessage(Message msg) {
                    handleStateMessage(msg);
                }
            };
        }
        return mHandler;
    }

    /**
     * 发送消息
     *
     * @param what 消息标识
     */
    protected final void sendEmptyMessage(int what) {
        if (mHandler != null) {
            mHandler.sendEmptyMessage(what);
        }
    }

    /**
     * 延迟发送空消息
     *
     * @param what        消息标识
     * @param delayMillis 延迟时间
     */
    protected final void sendEmptyMessageDelayed(int what, long delayMillis) {
        if (mHandler != null) {
            mHandler.sendEmptyMessageDelayed(what, delayMillis);
        }
    }

    /**
     * 发送消息
     *
     * @param msg 消息对象
     */
    protected final void sendMessage(Message msg) {
        if (mHandler != null) {
            mHandler.sendMessage(msg);
        }
    }

    /**
     * 延迟发送消息
     *
     * @param msg         消息对象
     * @param delayMillis 延迟时间
     */
    protected final void sendMessageDelayed(Message msg, long delayMillis) {
        if (mHandler != null) {
            mHandler.sendMessageDelayed(msg, delayMillis);
        }
    }

    /**
     * 判断UI是否独自管理对logic的handler监听<BR>
     *
     * @return 是否是私有监听的handler
     */
    protected boolean isPrivateHandler() {
        return false;
    }

    /**
     * 初始化logic的方法，由子类实现<BR>
     * 在该方法里通过getLogicByInterfaceClass获取logic对象
     */
    protected abstract void initLogics();

    /**
     * 通过接口类获取logic对象
     *
     * @param interfaceClass 接口类型
     * @return logic对象
     */
    protected final ILogic getLogicByInterfaceClass(Class<?> interfaceClass) {
        ILogic logic = sLogicBuilder.getLogicByInterfaceClass(interfaceClass);
        if (logic == null) {
            Logger.e(TAG, "Not found logic by interface class (" + interfaceClass + ")", new Throwable());
            return null;
        }
        if (isPrivateHandler() && !mLogicSet.contains(logic)) {
            logic.addHandler(getHandler());
            mLogicSet.add(logic);
        }
        return logic;
    }

    /**
     * logic通过handler回调的方法<BR>
     * 通过子类重载可以实现各个logic的sendMessage到handler里的回调方法
     *
     * @param msg Message对象
     */
    protected void handleStateMessage(Message msg) {
    }

    /**
     * 结束Activity
     *
     * @see android.app.Activity#finish()
     */
    public void finish() {
        removeHandler();
        super.finish();
    }

    /**
     * activity的释放的方法<BR>
     * 在这里对所有加载到logic中的handler进行释放
     *
     * @see android.app.ActivityGroup#onDestroy()
     */
    protected void onDestroy() {
        removeHandler();
        super.onDestroy();
    }

    /**
     * 注销UI的Handler
     */
    void removeHandler() {
        if (mHandler != null) {
            if (mLogicSet.size() > 0 && isPrivateHandler()) {
                for (ILogic logic : mLogicSet) {
                    logic.removeHandler(mHandler);
                }
            } else if (sLogicBuilder != null) {
                sLogicBuilder.removeHandlerToAllLogics(mHandler);
            }
            mHandler = null;
        }
    }


    public void setTitle(int stringId) {
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText(stringId);
    }
    public void isShowBack(boolean bl) {
        LinearLayout ll_back = (LinearLayout) findViewById(R.id.btn_back);
        ll_back.setVisibility(bl ? View.VISIBLE : View.GONE);
        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}

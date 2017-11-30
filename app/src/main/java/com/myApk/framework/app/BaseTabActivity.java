/*
 * 文件名: BaseTabActivity.java
 * 版    权：  Copyright Huawei Tech. Co. Ltd. All Rights Reserved.
 * 描    述: TabActivity基类
 * 创建人: 刘鲁宁
 * 创建时间:Mar 22, 2012
 * 
 * 修改人：
 * 修改时间:
 * 修改内容：[修改内容]
 */

package com.myApk.framework.app;

import java.util.HashSet;
import java.util.Set;

import android.app.TabActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.huawei.tep.utils.Logger;
import com.myApk.framework.logic.BaseLogicBuilder;
import com.myApk.framework.logic.ILogic;

/**
 * TabActivity基类
 *
 * @author 刘鲁宁
 * @version [RCS Client V100R001C03, Mar 22, 2012]
 */
public abstract class BaseTabActivity extends TabActivity {
    private static final String TAG = "BaseTabActivity";

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
}

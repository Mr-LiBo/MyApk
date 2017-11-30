/**
 * Huawei Software Technologies Co., Ltd. Copyright 2011-2012,  All rights reserved
 */

package com.myApk.framework.app;

import android.app.Application;
import android.content.Context;

import com.myApk.framework.logic.BaseLogicBuilder;


/**
 * Application基类
 *
 * @author Fan Hongtao/150373
 */
public abstract class BaseApplication extends Application {

    /**
     * 系统的所有logic的缓存创建管理类
     */
    private static BaseLogicBuilder sLogicBuilder = null;
    ;

    final BaseLogicBuilder getLogicBuilder() {
        if (sLogicBuilder == null) {
            sLogicBuilder = createLogicBuilder(this.getApplicationContext());
        }
        return sLogicBuilder;
    }

    /**
     * Logic建造管理类需要创建的接口<BR>
     * 需要子类继承后，指定Logic建造管理类具体实例
     *
     * @param context 系统的context对象
     * @return Logic建造管理类具体实例
     */
    protected abstract BaseLogicBuilder createLogicBuilder(Context context);

    ///----------


    public static BaseApplication sInstance = null;

    public BaseApplication() {
        sInstance = this;
    }

    public static BaseApplication getInstance() {
        if (sInstance == null) {
            throw new IllegalStateException("Application not exits.");
        }
        return sInstance;
    }
}

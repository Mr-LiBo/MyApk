package com.myApk.framework.logic;

/*
 * 文件名: LogicBuilder.java
 * 版    权：  Copyright Huawei Tech. Co. Ltd. All Rights Reserved.
 * 描    述: [该类的简要描述]
 * 创建人: kuaidc
 * 创建时间:Feb 11, 2012
 * 
 * 修改人：
 * 修改时间:
 * 修改内容：[修改内容]
 */



import android.content.Context;


/**
 * [一句话功能简述]<BR>
 * [功能详细描述]
 *
 * @author 刘鲁宁
 * @version [RCS Client V100R001C03, Feb 11, 2012]
 */
public class LogicBuilder extends BaseLogicBuilder {

    private static BaseLogicBuilder instance;

    /**
     * 构造方法，继承BaseLogicBuilder的构造方法，由父类BaseLogicBuilder对所有logic进行初始化。
     *
     * @param context 系统的context对象
     */
    private LogicBuilder(Context context) {
        super(context);
    }


    /**
     * 获取BaseLogicBuilder单例<BR>
     * 单例模式
     *
     * @param context 系统的context对象
     * @return BaseLogicBuilder 单例对象
     */
    public static synchronized BaseLogicBuilder getInstance(Context context) {
        if (null == instance) {
            instance = new LogicBuilder(context);
        }
        return instance;
    }

    /**
     * LogicBuidler的初始化方法，系统初始化的时候执行<BR>
     *
     * @param context 系统的context对象
     */
    protected void init(Context context) {

        registerAllLogics(context);
    }

    /**
     * 所有logic对象初始化及注册的方法<BR>
     *
     * @param context
     */
    private void registerAllLogics(Context context) {
//        registerLogic(IUserInfoLogic.class, new UserInfoLogic());
//
//
//        registerLogic(IAccessTokenLogic.class, new AccessTokenLogic());
//
//
//        registerLogic(IQueryCategoryInfoLogic.class, new QueryCategoryInfoLogic());
//
//
//        registerLogic(ICardLogic.class, new CardLogic());
//
//        registerLogic(IPayLogic.class, new PayLogic());
//
//        registerLogic(IOnlineLogic.class, new GetOnlineLogic());
//
//        registerLogic(IOrderLogic.class,new OrderLogic());


    }
}

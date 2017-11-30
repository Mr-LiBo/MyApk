/*
 * 文件名: BaseActivity.java
 * 版    权：  Copyright Huawei Tech. Co. Ltd. All Rights Reserved.
 * 描    述: [该类的简要描述]
 * 创建人: 刘鲁宁
 * 创建时间:Feb 11, 2012
 * 
 * 修改人：
 * 修改时间:
 * 修改内容：[修改内容]
 */

package com.myApk.framework.app;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import android.support.annotation.StringRes;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.huawei.tep.utils.Logger;
import com.myApk.R;
import com.myApk.framework.logic.BaseLogicBuilder;
import com.myApk.framework.logic.ILogic;
import com.myApk.uitl.AntiHijackingUtil;
import com.myApk.uitl.LollipopUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * Activity的抽象基类<BR>
 *
 * @author 刘鲁宁
 * @version [RCS Client V100R001C03, Feb 11, 2012]
 */
public abstract class BaseActivity extends AppCompatActivity {
    private static final String TAG = "BaseActivity";

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

//    private ConfirmDialog permissionConfirmDlg;

    /**
     * Acitivity的初始化方法<BR>
     *
     * @param savedInstanceState Bundle对象
     * @see android.app.ActivityGroup#onCreate(android.os.Bundle)
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setStatusBarUpperAPI21();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setStatusBarUpperAPI19();
        }

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


    public void InitActionBarHide() {
        ActionBar actionBar = getSupportActionBar();//高版本可以换成 ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.hide();
    }


    private void setStatusBarUpperAPI21() {
        Window window = getWindow();
        //取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏
//        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//
//        //透明导航栏
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //设置状态栏颜色
        //由于setStatusBarColor()这个API最低版本支持21, 本人的是15,所以如果要设置颜色,自行到style中通过配置文件设置
//        getWindow().setStatusBarColor(getResources().getColor(R.color.blue));
        ViewGroup mContentView = (ViewGroup) findViewById(Window.ID_ANDROID_CONTENT);
        View mChildView = mContentView.getChildAt(0);
        if (mChildView != null) {

            //注意不是设置 ContentView 的 FitsSystemWindows, 而是设置 ContentView 的第一个子 View . 预留出系统 View 的空间.
            ViewCompat.setFitsSystemWindows(mChildView, true);
        }
    }
    // 4.4 - 5.0版本

    private void setStatusBarUpperAPI19() {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        ViewGroup mContentView = (ViewGroup) findViewById(
                Window.ID_ANDROID_CONTENT);
        View statusBarView = mContentView.getChildAt(0);
        // 移除假的 View
        if (statusBarView != null && statusBarView.getLayoutParams() != null
                && statusBarView.getLayoutParams().height == LollipopUtils.getStatusBarHeight(
                this)) {
            mContentView.removeView(statusBarView);
        }
        // 不预留空间
        if (mContentView.getChildAt(0) != null) {
            ViewCompat.setFitsSystemWindows(mContentView.getChildAt(0), false);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
//    }
//
//    @Override
//    public void onPermissionsGranted(int requestCode, List<String> perms) {
//
//    }
//
//    @Override
//    public void onPermissionsDenied(int requestCode, List<String> perms) {
//
//    }
//
//    protected void showPermissionConfirmDlg(String title, String message, String leftBtnText, String rightBtnText, ConfirmDialog.DialogCallback callback) {
//        if(permissionConfirmDlg==null){
//            permissionConfirmDlg = new ConfirmDialog(this, R.style.dialog);
//        }
//        permissionConfirmDlg.setTitle(title);
//        permissionConfirmDlg.setText(message);
//        permissionConfirmDlg.setCanBack(false);
//        if(!TextUtils.isEmpty(leftBtnText)){
//            permissionConfirmDlg.setLeftBtn(leftBtnText);
//        }
//        if(!TextUtils.isEmpty(rightBtnText)){
//            permissionConfirmDlg.setLeftBtn(rightBtnText);
//        }
//        if(callback!=null){
//            permissionConfirmDlg.setCallback(callback);
//        }
//        if(!permissionConfirmDlg.isShowing()&&!isFinishing()){
//            permissionConfirmDlg.show();
//        }
//    }
//
//    protected void handlePermissionDeny(final Activity activity, final int requestCode, final String permission){
//        if (!PermissionHelper.shouldShowRequestPermissionRationale(activity, permission)) {
//            showPermissionConfirmDlg(PermissionHelper.getPermissionRetionaleTitle(activity,""),
//                    PermissionHelper.getPermissionRetionale(activity,permission),
//                    "取消",
//                    "去设置"
//                    ,new ConfirmDialog.DialogCallback(){
//                        @Override
//                        public void submit() {
//                            try {
//                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
//                                        .setData(Uri.parse("package:" + BaseActivity.this.getPackageName()));
//                                startActivityForResult(intent, requestCode);
//                            } catch (ActivityNotFoundException e) {
//                                e.printStackTrace();
//                                Intent intent = new Intent(Settings.ACTION_MANAGE_APPLICATIONS_SETTINGS);
//                                startActivityForResult(intent, requestCode);
//                            }
//                        }
//
//                        @Override
//                        public void cancel() {
//                            activity.finish();
//                        }
//                    });
//        }else{
//            showPermissionConfirmDlg(PermissionHelper.getPermissionRetionaleTitle(activity,""),
//                    PermissionHelper.getPermissionTip(activity,permission),
//                    "取消",
//                    "确定",
//                    new ConfirmDialog.DialogCallback(){
//                        @Override
//                        public void submit() {
//                            PermissionHelper.requestPermission(activity,"",requestCode,permission);
//                        }
//
//                        @Override
//                        public void cancel() {
//                            activity.finish();
//                        }
//                    });
//        }
//    }
//
//    public void requestPermissions(final Activity act, final int requestCode, String[] perms, String[] permissionNames, boolean isShowRetionale){
//        if(perms==null||perms.length<=0){
//            return ;
//        }
//        if(permissionNames==null||permissionNames.length<=0||permissionNames.length<perms.length){
//            throw new RuntimeException("permissionName not mach perms");
//        }
//        final ArrayList<String> permissionList = new ArrayList<>(perms.length);
//        boolean isGoPermissionManager = false;
//        StringBuilder permissionSb = new StringBuilder();
//        for(int i=0;i<perms.length;i++) {
//            String permission = perms[i];
//            String permissionName = permissionNames[i];
//            if (PermissionHelper.checkPermissions(this,
//                    permission)) {
//            } else {
//                permissionList.add(permission);
//                permissionSb.append(permissionName);
//                if (!PermissionHelper.shouldShowRequestPermissionRationale(this, permission)) {
//                    isGoPermissionManager = true;
//                }
//            }
//        }
//        if(permissionList!=null&&permissionList.size()>0){
//            if(isGoPermissionManager){
//                showPermissionConfirmDlg(PermissionHelper
//                                .getPermissionRetionaleTitle(act, ""),
//                        act.getString(R.string.dlg_content_request_reason, permissionSb.toString()),
//                        "取消",
//                        "去设置",
//                        new ConfirmDialog.DialogCallback()
//                        {
//                            @Override
//                            public void submit()
//                            {
//                                try
//                                {
//                                    Intent intent = new Intent(
//                                            Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
//                                            .setData(Uri
//                                                    .parse("package:"
//                                                            + act
//                                                            .getPackageName()));
//                                    startActivityForResult(intent,
//                                            requestCode);
//                                }
//                                catch (ActivityNotFoundException e)
//                                {
//                                    e.printStackTrace();
//                                    Intent intent = new Intent(
//                                            Settings.ACTION_MANAGE_APPLICATIONS_SETTINGS);
//                                    startActivityForResult(intent,
//                                            requestCode);
//                                }
//                            }
//
//                            @Override
//                            public void cancel()
//                            {
//                                act.finish();
//                            }
//                        });
//            }else{
//                if(isShowRetionale) {
//                    showPermissionConfirmDlg(PermissionHelper
//                                    .getPermissionRetionaleTitle(act, ""),
//                            act.getString(R.string.dlg_content_request_tip, permissionSb.toString()),
//                            "取消",
//                            "确定",
//                            new ConfirmDialog.DialogCallback() {
//                                @Override
//                                public void submit() {
//                                    permissionList.trimToSize();
//                                    String[] permissions = new String[permissionList.size()];
//                                    permissionList.toArray(permissions);
//                                    PermissionHelper.requestPermissions(act, "", requestCode, permissions);
//                                }
//
//                                @Override
//                                public void cancel() {
//                                    act.finish();
//                                }
//                            });
//                }else{
//                    permissionList.trimToSize();
//                    String[] permissions = new String[permissionList.size()];
//                    permissionList.toArray(permissions);
//                    PermissionHelper.requestPermissions(act, "", requestCode, permissions);
//                }
//            }
//        }
//    }


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

    public void setTitle(int resource, String title) {
        TextView tv_title = (TextView) findViewById(resource);
        tv_title.setText(title);
    }

    public void setTitle(@StringRes int stringId) {
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText(stringId);
    }


    /**
     * 设置全屏
     *
     * @param bool
     */
    public void IsWillScreen(Boolean bool) {
        if (bool) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        try {
            if (AntiHijackingUtil.checkOverlay(this)) {
                Toast.makeText(this, "全球卡app进入后台运行", Toast.LENGTH_SHORT).show();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package com.myApk.ui.main.item3;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ListView;

import com.myApk.common.Constants;
import com.myApk.model.ShareItem;
import com.myApk.ui.adapter.ShareSelectAdapter;
import com.myApk.uitl.wbAPI.ShareToWeiBoInformation;
import com.myApk.uitl.ShareUtil;
import com.myApk.R;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2015/8/25.
 */
public class ShareSelectDialog extends Dialog implements View.OnClickListener
{
    /** 全局上下文 */
    private Activity mActivity;

    private Fragment mFragment;

    ShareSelectCallBack callBack;

    /** APP列表信息 */
    private List<ShareItem> applist = new ArrayList<ShareItem>();
    /**扫描App 出来列表信息*/
    private ListView appListView;

    private ShareSelectAdapter shareSelectAdapter;

    public static final int SHARE_SMS_INVITE= 888;

    private IWXAPI api;

    private  boolean isInvite = false;

    public ShareSelectDialog(Activity mActivity)
    {
        super(mActivity);
        this.mActivity = mActivity;
    }

    public ShareSelectDialog(Fragment fragment)
    {
        super(fragment.getActivity());
        this.mFragment = fragment;
    }

    public ShareSelectDialog(Activity activity,int theme,ShareSelectCallBack callBack)
    {
        super(activity,theme);
        api = WXAPIFactory.createWXAPI(activity, Constants.APP_ID,true);
        api.registerApp(Constants.APP_ID);
        this.mActivity = activity;
        this.callBack = callBack;
        this.isInvite = false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_share);
        if (mActivity != null )
        {
            getListViewInformation(mActivity);
        }else{
            getListViewInformation(mFragment.getActivity());
        }
    }

    private void getListViewInformation(Context context)
    {
        applist.clear();
        appListView = (ListView) findViewById(R.id.share_list);

        ShareItem shareItem = new ShareItem(ShareItem.TYPE_SMS);
        shareItem.shareTitle ="短信分享好友";
        if (isInvite){
            shareItem.shareTitle ="短信邀请好友";
        }
        applist.add(shareItem);

        if (api.isWXAppInstalled())
        {
            ShareItem item = new ShareItem(ShareItem.TYPE_WEIXIN);
            item.shareTitle ="分享给微信好友";
            if (isInvite){
                item.shareTitle ="分享给微信好友";
            }
            applist.add(item);
        }
        if (api.isWXAppInstalled())
        {
            ShareItem item = new ShareItem(ShareItem.TYPE_WEIXIN);
            item.shareTitle ="分享到微信朋友圈";
            if (isInvite){
                item.shareTitle ="邀请微信朋友圈";
            }
            applist.add(item);
        }
        if (ShareUtil.isWeiBoInstalled(context))
        {
            ShareToWeiBoInformation weiBoUtil = ShareToWeiBoInformation.instance(context);
            weiBoUtil.registerWeiBoApp(context);
            ShareItem item = new ShareItem(ShareItem.TYPE_WEIBO);
            item.shareTitle ="分享给微博好友";
            if (isInvite){
                item.shareTitle="邀请微博好友";
            }
            applist.add(item);
        }

        if (0 != applist.size() && null != applist)
        {
            shareSelectAdapter = new ShareSelectAdapter(context,applist,this,callBack);
            appListView.setAdapter(shareSelectAdapter);
        }
    }

    @Override
    public void onClick(View view)
    {

    }

    /**
     * 点击系统返回键关闭窗口
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        callBack.back();
        return super.onKeyDown(keyCode, event);
    }

    public interface ShareSelectCallBack
    {
        void shareSMS();//短信分享
        void shareWX();//微信分享
        void shareWXFriend();//微信朋友圈
        void shareWB();//微博分享
        void back();//返回键
    }
}

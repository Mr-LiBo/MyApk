package com.myApk.uitl;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.view.View;

import com.myApk.R;
import com.myApk.ui.main.item3.ShareSelectDialog;
import com.myApk.uitl.wbAPI.ShareToWeiBoInformation;
import com.myApk.uitl.wxAPI.ShareToWXInformation;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

/**
 * 分享工具
 */
public class ShareUtil
{
    public static final int WX =1;//微信好友
    public static final int WXFRIEND =2;//微信朋友圈
    public static final int WB = 3;//新浪微博
    public static final int SMS =5;//短信分享



    /**
     *
     * @param activity 上下文
     * @param share_url 分享路径--单个网页wap地址或服务器下发的地址
     * @param share_content  内容---短信与微博用到
     * @param share_title 标题---只有微信和微信朋友圈用到
     * @param path 图片路径---只有微信和微信朋友圈用到调用拉起的图片用到
     */
    public static void share(final Activity activity,
                      final String share_url,
                      final String share_content,
                      final String share_title,
                      final String  path)
    {
        final ShareToWXInformation shareToWXInformation = ShareToWXInformation.instance(activity);
        ShareSelectDialog.ShareSelectCallBack callBack = new ShareSelectDialog.ShareSelectCallBack() {
            @Override
            public void shareSMS() {


            }

            @Override
            public void shareWX() {
              ImageLoader.getInstance().loadImage(path, new ImageSize(99, 99),new SimpleImageLoadingListener()
              {
                  @Override
                  public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
//                      super.onLoadingFailed(imageUri, view, failReason);
                      shareToWXInformation.checkXWInformation(ShareToWXInformation.WXSceneTimeline,
                              share_url,
                              share_title,
                              share_content,
                              null);
                  }

                  @Override
                  public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
//                      super.onLoadingComplete(imageUri, view, loadedImage);
                      shareToWXInformation.checkXWInformation(ShareToWXInformation.WXSceneTimeline,
                              share_url,
                              share_title,
                              share_content,
                              loadedImage == null ? null :loadedImage);
                  }
              });
            }

            @Override
            public void shareWXFriend() {
                ImageLoader.getInstance().loadImage(path, new ImageSize(99, 99),new SimpleImageLoadingListener()
                {
                    @Override
                    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
//                      super.onLoadingFailed(imageUri, view, failReason);
                        shareToWXInformation.checkXWInformation(ShareToWXInformation.WXSceneTimeline,
                                share_url,
                                share_title,
                                share_content,
                                null);
                    }

                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
//                      super.onLoadingComplete(imageUri, view, loadedImage);
                        shareToWXInformation.checkXWInformation(ShareToWXInformation.WXSceneTimeline,
                                share_url,
                                share_title,
                                share_content,
                                loadedImage == null ? null :loadedImage);
                    }
                });
            }

            @Override
            public void shareWB() {
                final ShareToWeiBoInformation wb = ShareToWeiBoInformation.instance(activity);
                final  String str ;
                if (share_content == null)
                {
                    str= share_url;
                }else
                {
                    str = share_content +"\n "+ share_url;
                }

                ImageLoader.getInstance().loadImage(path, new ImageSize(99, 99),new SimpleImageLoadingListener()
                {
                    @Override
                    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
//                      super.onLoadingFailed(imageUri, view, failReason);
                        wb.sendMultiMessagesHasImage(str,null);
                    }

                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
//                      super.onLoadingComplete(imageUri, view, loadedImage);
                        wb.sendMultiMessagesHasImage(str,loadedImage);
                    }
                });
            }

            @Override
            public void back() {

            }
        };
        ShareSelectDialog dialog = new ShareSelectDialog(activity, R.style.dialog,callBack);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }


    public static final String WEIBO_NORMAL_PACKAGE_NAME = "com.sina.weibo";
    public static final String WEIBO_G3_PACKAGE_NAME = "com.sina.weibog3";
    public static final String WX_PACKAGE_NAME = "com.tencent.mm";

    /**
     * 判断手机是否安装了新浪微博
     * @param context
     * @return
     */
    public static boolean isWeiBoInstalled(Context context)
    {
        return  (isAppInstalled(context,WEIBO_NORMAL_PACKAGE_NAME) || isAppInstalled(context,WEIBO_G3_PACKAGE_NAME));
    }

    /**
     *
     * @param context
     * @param packageName
     * @return
     */
    public static boolean isAppInstalled(Context context,String packageName)
    {
        PackageManager pm =context.getPackageManager();
        boolean isStalled = false;
        try {
            pm.getPackageInfo(packageName,PackageManager.GET_ACTIVITIES);
            isStalled = true;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            isStalled = false;
        }
        return isStalled;
    }
}

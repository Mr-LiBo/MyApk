package com.myApk.uitl.wxAPI;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import com.myApk.R;
import com.myApk.common.Constants;

import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.SendMessageToWX;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.mm.sdk.openapi.WXMediaMessage;
import com.tencent.mm.sdk.openapi.WXWebpageObject;

/**
 * Created by admin on 2015/8/26.
 */
public class ShareToWXInformation
{
    public static final int WXSceneSession =0;//分享到微信好友
    public static final int WXSceneTimeline =1;//分享到微信朋友圈
    public static final int WB = 3; //新浪微博
    public static final int SMS = 5;//短信分享

    private static ShareToWXInformation shareToWXInformation;

    private static IWXAPI api;

    private static Context context;

    /**
     * 微信0x21020001版本以上支持朋友圈
     */
    private static final int TIMELINE_SUPPORTED_VERSION = 0x21020001;

    public static ShareToWXInformation instance(Context mContext)
    {
       context = mContext;
        if (null == shareToWXInformation)
        {
            shareToWXInformation = new ShareToWXInformation();
            api = WXAPIFactory.createWXAPI(context, Constants.APP_ID,true);
            api.registerApp(Constants.APP_ID);
        }
        return  shareToWXInformation;
    }

    public  void checkXWInformation(int scene, String url, String title, String body,Bitmap bitmap)
    {
        switch (scene)
        {
            case WXSceneSession:
                if (!api.isWXAppSupportAPI())//是否支持好友会话
                {
                    Toast.makeText(context,"微信版本过低，请安装微信最新版本",Toast.LENGTH_LONG).show();;
                }else
                {
                    sendWXMessageToFriend(scene, url, title, body, bitmap);
                }
                break;

            case WXSceneTimeline:
                if (api.getWXAppSupportAPI() < TIMELINE_SUPPORTED_VERSION)//是否支持朋友圈
                {
                    Toast.makeText(context,"微信版本过低，请安装微信最新版本",Toast.LENGTH_LONG).show();;
                }else
                {
                    sendWXMessageToFriendCircle(scene, url, title, body, bitmap);
                }
                break;
        }
    }

    private void sendWXMessageToFriend(int scene, String url, String title, String description,Bitmap bitmap)
    {
        WXWebpageObject wxWebpageObject = new WXWebpageObject();
        wxWebpageObject.webpageUrl = url;
        WXMediaMessage msg = new WXMediaMessage(wxWebpageObject);
        msg.title = title;
        msg.description = description;
        if (null == bitmap)
        {//设置默认图
            bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.star_icon);
        }
        msg.thumbData = Util.bmpToByteArray(bitmap,true);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        String type = "webpage";
        // transaction字段用于唯一标识一个请求
        req.transaction = (type == null) ? String.valueOf(System.currentTimeMillis())
                : type + System.currentTimeMillis();
        req.message = msg;
        if (scene == WXSceneSession)
        {
            req.scene =SendMessageToWX.Req.WXSceneSession;
            WX_SCENE = req.scene;
        }
        if(scene == WXSceneTimeline)
        {
            req.scene = SendMessageToWX.Req.WXSceneTimeline;
            WX_SCENE = req.scene;
        }
        api.sendReq(req);//调用API接口发送数据到微信
    }


    public static int  WX_SCENE = -1;
    private void sendWXMessageToFriendCircle(int scene, String url, String title, String description,Bitmap bitmap)
    {
        WXWebpageObject wxWebpageObject = new WXWebpageObject();
        wxWebpageObject.webpageUrl = url;
        WXMediaMessage msg = new WXMediaMessage(wxWebpageObject);
        msg.title = title;
        msg.description = description;
        if (null == bitmap)
        {//设置默认图
            bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.star_icon);
        }
        msg.thumbData = Util.bmpToByteArray(bitmap,false);

        int imageSize =msg.thumbData.length/1024;
        if (imageSize > 32)
        {
            Toast.makeText(context,"你分享图片过大",Toast.LENGTH_LONG).show();
            return;
        }
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        String type = "webpage";
        // transaction字段用于唯一标识一个请求
        req.transaction = (type == null) ? String.valueOf(System.currentTimeMillis())
                : type + System.currentTimeMillis();
        req.message = msg;
        if (scene == WXSceneSession)
        {
            req.scene =SendMessageToWX.Req.WXSceneSession;
            WX_SCENE = req.scene;
        }
        if(scene == WXSceneTimeline)
        {
            req.scene = SendMessageToWX.Req.WXSceneTimeline;
            WX_SCENE = req.scene;
        }
        api.sendReq(req);//调用API接口发送数据到微信
    }


}

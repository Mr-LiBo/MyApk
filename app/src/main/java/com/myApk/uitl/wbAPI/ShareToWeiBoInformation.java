package com.myApk.uitl.wbAPI;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import com.myApk.R;

import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WeiboMessage;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.api.share.BaseResponse;
import com.sina.weibo.sdk.api.share.IWeiboHandler;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.SendMessageToWeiboRequest;
import com.sina.weibo.sdk.api.share.SendMultiMessageToWeiboRequest;
import com.sina.weibo.sdk.api.share.WeiboShareSDK;
import com.sina.weibo.sdk.constant.WBConstants;

/**
 * Created by admin on 2015/8/25.
 */
public class ShareToWeiBoInformation implements IWeiboHandler.Response {

    private static ShareToWeiBoInformation shareToWeiBoUtil;

    /**微博分享接口实例*/
    private static IWeiboShareAPI iWeiboShareAPI;

    private static Context context;

    public static ShareToWeiBoInformation instance(Context context)
    {
         context = context;
        if (null == shareToWeiBoUtil)
        {
            shareToWeiBoUtil = new ShareToWeiBoInformation();
        }
        return shareToWeiBoUtil;
    }

    public void registerWeiBoApp(Context context)
    {
        iWeiboShareAPI = WeiboShareSDK.createWeiboAPI(context, WeiBoContants.APP_KEY,false);
        iWeiboShareAPI.registerApp();     //将应用注册到微博客户端
    }


    /**
     *  通过 IWeiboShareAPI#sendRequest 唤起微博客户端发博器进行分享
     * @param body
     */
    public void sendMultiMessage(String body)
    {
        WeiboMessage weiboMessage = new WeiboMessage();

        TextObject textObject = new TextObject();
        textObject.text = body;
        weiboMessage.mediaObject = textObject;
        SendMessageToWeiboRequest request = new SendMessageToWeiboRequest();
        // 用transaction唯一标识一个请求
        request.transaction = String.valueOf(System.currentTimeMillis());
        request.message = weiboMessage;
        iWeiboShareAPI.sendRequest(request);
    }

    public void sendMultiMessagesHasImage(String body,Bitmap bitmap)
    {
        long tempStr = System.currentTimeMillis();
        ImageObject imageObject = new ImageObject();
        if (null != bitmap)
        {
            imageObject.setImageObject(bitmap);
        }else
        {
            imageObject.setImageObject(BitmapFactory.decodeResource(context.getResources(), R.drawable.star_icon));
        }
          /*微博数据的message对象*/
        WeiboMultiMessage multiMessage = new WeiboMultiMessage();
        TextObject textObject = new TextObject();
        textObject.text = body;
        multiMessage.textObject = textObject;
        multiMessage.imageObject = imageObject;
          /*微博发送的Request请求*/
        SendMultiMessageToWeiboRequest multiMessageToWeiboRequest = new SendMultiMessageToWeiboRequest();
        multiMessageToWeiboRequest.multiMessage = multiMessage;
        //以当前时间戳为唯一识别符
        multiMessageToWeiboRequest.transaction = String.valueOf(tempStr);
        iWeiboShareAPI.sendRequest(multiMessageToWeiboRequest);
    }

    /**
     * 接收微客户端博请求的数据。
     * 当微博客户端唤起当前应用并进行分享时，该方法被调用。
     *
     * @param
     * @see {@link IWeiboShareAPI#handleWeiboRequest}
     */
    @Override
    public void onResponse(BaseResponse baseResponse) {
        String result = null;
        String errorStr = "asdasd";
        switch (baseResponse.errCode)
        {
            case WBConstants.ErrorCode.ERR_OK://分享成功
                result = "分享成功";
                Toast.makeText(context, result + errorStr, Toast.LENGTH_SHORT).show();
                        break;
            case WBConstants.ErrorCode.ERR_CANCEL://取消分享
                result ="取消分享";
                Toast.makeText(context, result + errorStr, Toast.LENGTH_SHORT).show();
                break;
            case WBConstants.ErrorCode.ERR_FAIL://分享失败
                result="分享失败";
                errorStr = baseResponse.errMsg;
                Toast.makeText(context, result + errorStr, Toast.LENGTH_SHORT).show();
        }

    }
}

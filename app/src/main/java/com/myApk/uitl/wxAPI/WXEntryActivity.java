package com.myApk.uitl.wxAPI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;


import com.myApk.common.Constants;
import com.tencent.mm.sdk.openapi.BaseReq;
import com.tencent.mm.sdk.openapi.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler
{

    // IWXAPI 是第三方app和微信通信的openapi接口
    private IWXAPI api;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        api = WXAPIFactory.createWXAPI(this, Constants.APP_ID, true);
        // 将该app注册到微信
        api.registerApp(Constants.APP_ID);
        api.handleIntent(getIntent(), this);
    }
    
    @Override
    protected void onNewIntent(Intent intent)
    {
        super.onNewIntent(intent);
        
        setIntent(intent);
        
        //等待微信的回调
        api.handleIntent(intent, this);
    }
    
    // 微信发送请求到第三方应用时，会回调到该方法
    @Override
    public void onReq(BaseReq req)
    {
        //TODO
        
    }

    // 第三方应用发送到微信的请求处理后的响应结果，会回调到该方法
    @Override
    public void onResp(BaseResp resp)
    {
        String result = null;
        
        switch (resp.errCode)
        {
            case BaseResp.ErrCode.ERR_OK://发送成功

                result ="发送成功";

                break;
                
            case BaseResp.ErrCode.ERR_USER_CANCEL://发送取消
                result = "发送取消";
                break;
                
            case BaseResp.ErrCode.ERR_AUTH_DENIED://发送被拒绝
                result ="发送被拒绝";
                break;
                
            case BaseResp.ErrCode.ERR_SENT_FAILED://发送失败
                result ="发送失败";
                break;
                
            default:
                result ="发送返回";
                break;
        }
        Toast.makeText(this, result, Toast.LENGTH_LONG).show();
        finish();
    }
}

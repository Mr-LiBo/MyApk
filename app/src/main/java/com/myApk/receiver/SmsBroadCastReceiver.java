package com.myApk.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsMessage;

import com.myApk.logic.MsgListener;
import com.myApk.uitl.StringUtil;



/**
 * Created by admin on 2015/9/10.
 */
public class SmsBroadCastReceiver extends BroadcastReceiver {

    private  static MsgListener msgListener;

    public static  void setMsgListener(MsgListener Listener)
    {
        msgListener = Listener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // 解析广播
        // "pdus  protocal data units  多条短信"
        Object [] pdus = (Object[]) intent.getExtras().get("pdus");
        //遍历短信
        for (Object pdu : pdus){
            SmsMessage sms = SmsMessage.createFromPdu((byte[]) pdu);
            //获取发信人
            String number = sms.getOriginatingAddress();
            //获取内容

//            [139短信提醒]
//            发件人：15728675460
//            主题：回复:qw
//            正文：qwewqeqwe验证码:123456AADADA
            String body =sms.getMessageBody();
//            if (number.equals("106581391128911738")){
                if (StringUtil.isNotEmpty(body)){
                    int index = body.indexOf("验证码:");
                    if (index != -1)
                    {
                        String code = body.substring(index + 4,index +4 +6);
                        msgListener.onMsg(code,true);
                    }

                }
//            }

        }
    }
}

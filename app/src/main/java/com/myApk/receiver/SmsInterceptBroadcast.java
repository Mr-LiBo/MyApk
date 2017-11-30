package com.myApk.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

import com.myApk.logic.MsgListener;
import com.myApk.uitl.StringUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * 非常驻型广播，当应用程序结束了，广播自然就没有了，
 * 比如在 Activity 中的 onCreate 或者 onResume 中注册广播接收者，在 onDestory 中注销广播接收者。
 * 这样你的广播接收者就一个非常驻型的了，这种注册方式也叫动态注册。
 * 这种方式可以理解为通过代码注册的广播是和注册者关联在一起的。
 */
public class SmsInterceptBroadcast extends BroadcastReceiver
{
    
    /**
     * 是否拦截
     */
    private static boolean isIntercept = false;
    
    private  static MsgListener msgListener;
    
    public static  void setMsgListener(MsgListener Listener)
    {
       msgListener = Listener;
    }
    
    public static void setIsIntercept(boolean isIntercept)
    {
        SmsInterceptBroadcast.isIntercept = isIntercept;
    }
    
    @Override
    public void onReceive(Context context, Intent intent)
    {
        if (!isIntercept)
        {
            return;
        }
        StringBuilder body = new StringBuilder();
        StringBuilder number = new StringBuilder();
        Bundle bundle = intent.getExtras();
        if (bundle != null)
        {
            Object[] pdus = (Object[]) bundle.get("pdus");
            String from = bundle.getString("from");
            SmsMessage[] messages = new SmsMessage[pdus.length];
            for (int i = 0; i < pdus.length; i++)
            {
                messages[i] = createFromPdu(from, pdus[i]);
            }

            for (SmsMessage currentMeg : messages)
            {
                if (currentMeg != null)
                {
                    body.append(currentMeg.getMessageBody());
                }
            }
            if (messages[0] != null){
                number.append(messages[0].getDisplayOriginatingAddress());
            }

            String smsBody = body.toString();
            String smsNumber = number.toString();

            if (StringUtil.isNotEmpty(smsNumber))
            {
//                for (String phone :MSG_LOGIN_PHONENUMBERS )
//                {
//                    if (smsNumber.equals(phone)){
                        String password =getCode(smsBody);
//                       this.abortBroadcast();
                       this.isIntercept = false;
                       if (msgListener != null)
                       {
                           msgListener.onMsg(password,true);
                       }
//                    }
//                }
            }
        }
        
    }

    /**
     * 指定的短信登录发送者手机号码
     */
    private static final String[] MSG_LOGIN_PHONENUMBERS = new String[] {"106581037019", "10658126","10658102","1252004411","106581391128911738"};

    /**
     * 截取短信验证码
     * @param msg
     * @return
     */
    private static String getCode (String msg){
        if (StringUtil.isNotEmpty(msg)){
            int index = msg.indexOf("验证码:");
            if (index != -1)
            {
                String code = msg.substring(index + 4,index +4 +6);
                return code;
            }
        }
        return  null;
    }


    public static SmsMessage createFromPdu(String from, Object pdus)
    {
        try
        {
            Class smsMessageClass = null;
            if (from != null && from.equalsIgnoreCase("CDMA"))
            {
                smsMessageClass = Class.forName("com.android.internal.telephony.cdma.SmsMessage");
            }
            else
            {
                smsMessageClass = Class.forName("com.android.internal.telephony.gsm.SmsMessage");
            }
            
            Class types[] = new Class[1];
            types[0] = pdus.getClass();
            
            Method method = smsMessageClass.getMethod("createFromPdu", types[0]);
            
            Object object = method.invoke(smsMessageClass, pdus);
            
            Class smsMessageBaseClass = Class.forName("com.android.internal.telephony.SmsMessageBase");
            
            Class[] classType = new Class[] { smsMessageBaseClass };
            
            Constructor[] SmsMessageConstructors = SmsMessage.class.getDeclaredConstructors();

            if (SmsMessageConstructors.length == 1)
            {
                SmsMessageConstructors[0].setAccessible(true);
                
                SmsMessage smsMessage = (SmsMessage) SmsMessageConstructors[0].newInstance(object);
                
                return smsMessage;
            }
            else
            {
                SmsMessageConstructors[1].setAccessible(true);
                
                SmsMessage smsMessage = (SmsMessage) SmsMessageConstructors[1].newInstance(object);
                
                return smsMessage;
            }
            
        }
        catch (Exception e)
        {
            
        }
        
        return null;
    }
}

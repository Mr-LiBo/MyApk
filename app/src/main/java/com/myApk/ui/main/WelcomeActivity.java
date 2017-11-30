package com.myApk.ui.main;



import com.myApk.R;
import com.myApk.common.GlobalMessageType;
import com.myApk.model.UpdateInfo;
import com.myApk.service.TimeServer;
import com.myApk.uitl.ConfigUtil;
import com.myApk.uitl.NetworkUtil;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.Xml;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 欢迎界面
 * 主要处理升级
 */
public class WelcomeActivity extends Activity
{

    private static final String TAG = WelcomeActivity.class.getSimpleName();
    Context mContext;
    private  int delayMillis = 4000;
    private UpdateInfo updateInfo = new UpdateInfo();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
//        Log.i("----------》","——————————————————————————");
//        Intent service2 = new Intent(WelcomeActivity.this, ContactsService.class);
//        startService(service2);

        mContext = getBaseContext();
        LinearLayout   ll_splash = (LinearLayout) findViewById(R.id.ll_splash);

        //透明度变化的动画 透明度变化的动画 0.0-1.0
        AlphaAnimation anima = new AlphaAnimation(0.5f,1.0f);
        anima.setDuration(3000);
        //保持状态
        anima.setFillAfter(true);
        ll_splash.startAnimation(anima);


        //背景动化
        ImageView img = (ImageView) findViewById(R.id.iv_anim);
        img.setImageResource(R.drawable.init_bg_anim);
        AnimationDrawable animationDrawable = (AnimationDrawable) img.getDrawable();
        animationDrawable.start();

       boolean bl= NetworkUtil.isNetworkConnected(mContext);
       boolean bl2 = NetworkUtil.checkNetwork(mContext);

        boolean update_software = ConfigUtil.getBoolean(this, GlobalMessageType.SharedFileKey.UPDATE_SOFTWARE);
        if (bl == bl2 && update_software) {
            checkAPK();
        }else{
            goSystem();
        }
    }

    public void checkAPK(){
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    String urlStr = "http://download.caiyun.cloudcdn.net/updateinfo.xml";
                    //1 请求服务端返回最新版本信息
                    //创建地址
                    URL url = new URL(urlStr);
                    //打开链接
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setConnectTimeout(5000);
                    if (200 == conn.getResponseCode())
                    {
                        InputStream inputStream = conn.getInputStream();
                        //2解析xml 获得信息
                        pullParserXml(inputStream);

                        //3 判断本地版本与服务端版本
                        int versionCode = getLocalVersionCode();
                        int serverCode = Integer.parseInt(updateInfo.versionCode);
                        if (serverCode > versionCode)
                        {
                            //提示下载
                            Message msg = new Message();
                            msg.what = 100;
                            handler.sendMessage(msg);
                        }
                        else
                        {
                            //进入主页
                            Message msg = new Message();
                            msg.what = -100;
                            handler.sendMessage(msg);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Message msg = new Message();
                    msg.what = 404;// 下载出错
                    handler.sendMessage(msg);
                }

            }
        }.start();
    }

    /**
     *   //2 pull解析返回xml,获取实体数据
     */
    private void pullParserXml(InputStream input)
    {
        try {
            InputStream   inputStream = input;
            //2 pull解析返回xml,获取实体数据
            //创建解析器
            XmlPullParser parser = Xml.newPullParser();
            //载入内容
            parser.setInput(inputStream,"UTF-8");
            //初始化类型
            int eventType = parser.getEventType();
            //逐行解析
            while (eventType != XmlPullParser.END_DOCUMENT)
            {
                // 读进一行
                switch (eventType)
                {
                    case XmlPullParser.START_TAG:
                        switch (parser.getName())
                        {
                            case "versionCode":
                                // 读取内容
                                updateInfo.versionCode = parser.nextText();
                                break;
                            case "versionName":
                                updateInfo.versionName = parser.nextText();
                                break;
                            case "url":
                                updateInfo.url = parser.nextText();
                                break;
                            case "message":
                                updateInfo.message = parser.nextText();
                                break;
                        }
                        break;
                }
                // 往下移动行
                eventType = parser.next();
            }
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 100://提示更新
                    AlertDialog.Builder builder = new AlertDialog.Builder(WelcomeActivity.this);
                    builder.setTitle("更新提示");
                    builder.setMessage(updateInfo.message);
                    builder.setPositiveButton("更新",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            downLoadAPK();
                        }
                    });

                    builder.setNegativeButton("取消",new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            goSystem();
                        }
                    });
                    builder.create().show();
                    break;
                case -100://进入主页
                    goSystem();
                    break;
                case 205://安装
                    builder = new AlertDialog.Builder(WelcomeActivity.this);
                    builder.setTitle("安装提示");
                    builder.setMessage("是否安装最新版本");
                    //安装按钮
                    builder.setPositiveButton("安装",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int i) {
                            dialog.dismiss();
                            //调用意思进行安装
                            //调用系统的一个Activity 帮助安装
                            Intent intent = new Intent();
                            //参数action 动作
                            intent.setAction(Intent.ACTION_VIEW);
                            //参数category 类别
                            intent.addCategory(Intent.CATEGORY_DEFAULT);
                            //参数data type
                            intent.setDataAndType(Uri.fromFile(apkFile),//安装文件
                                    "application/vnd.android.package-archive" //多媒体类型
                                    );
                            startActivity(intent);
                            finish();

                        }
                    });

                    builder.setNegativeButton("取消",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog , int i) {
                            dialog.dismiss();
                            goSystem();
                        }
                    });
                    builder.create().show();
                    break;
                case 404://下载出错 进入主页
                    Toast.makeText(getBaseContext(),"检测失败",Toast.LENGTH_LONG).show();
                    goSystem();

                    break;
            }
        }
    };

    private  File apkFile;

    private void downLoadAPK() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("进度更新");
        //只有设置该值才能显示进度条
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setMax(100);
        progressDialog.setProgress(0);
        progressDialog.show();
        //4 请求文件路径，返回最新apk
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    URL url = new URL(updateInfo.url);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setConnectTimeout(5000);
                    //最在值
                    int maxByte = 1;
                    //当前进度
                    int progressValue = 0;
                    if (200 == conn.getResponseCode())
                    {
                        InputStream inputStream = conn.getInputStream();
                       //服务文件大小
                        maxByte = conn.getContentLength();
                        // 6本地创建文件保存apk
                       //创建文件
                        String dataDir = "data/data/"+getPackageName()+"/new.apk";
                        //sd卡挂载正常
                        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
                        {
                            dataDir = Environment.getExternalStorageDirectory().getAbsolutePath()+"/new.apk";
                        }
                        System.out.print(dataDir);

                         apkFile = new File(dataDir);
                        //不存在就创建
                        if (!apkFile.exists())
                        {
                            apkFile.createNewFile();
                        }
                        FileOutputStream out = new FileOutputStream(apkFile);
                        //边读边写
                        byte[] buffer = new byte[1024 * 5];
                        int length =0;
                        while (-1 != (length = inputStream.read(buffer)))
                        {
                            //读取数据
                            out.write(buffer,0,length);
                            //累加
                            progressValue += length;
                            //模拟：实际
//                            Thread.sleep(100);
                            //5 进度变化显示在进度条
                            //百分比
                            int current = progressValue * 100/maxByte;
                            progressDialog.setProgress(current);
                        }
                        progressDialog.dismiss();
                        out.flush();
                        out.close();
                        inputStream.close();

                        Message msg = new Message();
                        msg.what =205;
                        handler.sendMessage(msg);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }

    // 获取本地版本信息
    public int getLocalVersionCode() throws PackageManager.NameNotFoundException
    {
        PackageManager pm = getPackageManager();
        PackageInfo info = pm.getPackageInfo(getPackageName(), 0);
        int versionCode = info.versionCode;
        return versionCode;
    }

    private void goSystem() {

        Log.i(TAG,"进入主页，Android机型：" + Build.MODEL + "  系统版本："
                        + Build.VERSION.RELEASE + "  网络类型："
                        + NetworkUtil.getNetworkTypeName(mContext));

        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                Intent service = new Intent(WelcomeActivity.this, TimeServer.class);
                startService(service);



                Intent intent = new Intent(WelcomeActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, delayMillis);
    }

}

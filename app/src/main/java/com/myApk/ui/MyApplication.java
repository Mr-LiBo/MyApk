package com.myApk.ui;

import java.util.UUID;


import com.myApk.framework.app.BaseApplication;
import com.myApk.framework.logic.BaseLogicBuilder;
import com.myApk.framework.logic.LogicBuilder;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import android.content.Context;

/**
 * @Example
 * @Auth : LiBo on 2017/5/3
 * @Describe :
 */
public class MyApplication extends BaseApplication {
//    private DaoSession daoSession;

    @Override
    protected BaseLogicBuilder createLogicBuilder(Context context) {
        return LogicBuilder.getInstance(context);
    }

    public static MyApplication getAPP() {
        return (MyApplication) sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
//        OkhttpSender.init(this);
//        UserConfig.init(this);
//        MccUtils.init(this);
        // ClearableCookieJar cookieJar1 = new PersistentCookieJar(new
        // SetCookieCache(), new
        // SharedPrefsCookiePersistor(getApplicationContext()));
        initDb();
//        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null,
//                null, null);

        // CookieJarImpl cookieJar1 = new CookieJarImpl(new
        // MemoryCookieStore());
//        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
//                .readTimeout(10000L, TimeUnit.MILLISECONDS)
//                .addInterceptor(new LoggerInterceptor("TAG"))
//                .addNetworkInterceptor(new AutuInterceptor())
//                // .cookieJar(cookieJar1)
//                .hostnameVerifier(new HostnameVerifier() {
//                    @Override
//                    public boolean verify(String hostname, SSLSession session) {
//                        return true;
//                    }
//                })
//                .sslSocketFactory(sslParams.sSLSocketFactory,
//                        sslParams.trustManager)
//                .build();
//        OkHttpUtils.initClient(okHttpClient);


        initImageLoader(getApplicationContext());

//        String openId = UserConfig.getInstance().getOpenID();
//        if (TextUtils.isEmpty(openId)) {
//            openId = UUID.randomUUID().toString();
//            openId = openId.replace("-", "");
//            UserConfig.getInstance().setOpenId(openId);
//        }

//        File root = Environment.getExternalStorageDirectory();
//        File dir = new File(root, "globalcard");
//        try {
//            Logger.initLogger(Logger.DEBUG, true, dir.getAbsolutePath() + File.separator + "log.txt");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        Thread.setDefaultUncaughtExceptionHandler(new UnExpectedExceptionHandler());


//        if(TextUtils.isEmpty(uuid)){
//            uuid = FilesUtil.readFromCache(getApplicationContext());
//            if(!TextUtils.isEmpty(uuid)){
//                ConfigUtil.setUUID(getApplicationContext(), uuid);
//            }else {
//
//                ConfigUtil.setUUID(getApplicationContext(), uuid);
//                FilesUtil.saveToCache(uuid,this);
//
//            }
//        }
    }


    public static String getUUID() {
        String uuidStr = UUID.randomUUID().toString();
        uuidStr = uuidStr.substring(0, 8) + uuidStr.substring(9, 13)
                + uuidStr.substring(14, 18) + uuidStr.substring(19, 23)
                + uuidStr.substring(24);
        return uuidStr;
    }


    public static void initImageLoader(Context context) {
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.threadPoolSize(3);
        config.denyCacheImageMultipleSizesInMemory();
//            config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheFileNameGenerator(new HashCodeFileNameGenerator());
//        config.diskCache(new UnlimitedDiscCache(new File(GlobalAction.TEMP_LOC_IMAGE_PATH)));
//        config.diskCacheSize(100 * 1024 * 1024);
//        config.memoryCache(new LRULimitedMemoryCache(3 * 1024 * 1024));
//        config.imageDownloader(new OkHttpImageDownloader(context));
//            config.defaultDisplayImageOptions(options);
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
//            config.imageDecoder(new BaseImageDecoder(false));
//            config.writeDebugLogs(); // Remove for release app

        ImageLoader.getInstance().init(config.build());
//        }
//    catch(Exception e){
//            LogUtil.e("CCloudApplication","imageLoader init fail");
//            e.printStackTrace();
//        }
    }


    private void initDb() {
//        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "cache-db");
//        Database db = helper.getWritableDb();
//        daoSession = new DaoMaster(db).newSession();
//        ;
    }

//    public DaoSession getDaoSession() {
//        return daoSession;
//    }
}

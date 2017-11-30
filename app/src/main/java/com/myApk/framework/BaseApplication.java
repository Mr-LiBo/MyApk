package com.myApk.framework;

import android.app.Application;
import android.content.Context;
import android.os.Environment;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LRULimitedMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.io.File;

/**
 * Created by admin on 2015/8/26.
 */
public class BaseApplication extends Application
{
    @Override
    public void onCreate() {
        super.onCreate();
        initImageLoader(this.getApplicationContext());

    }

    /** 本地图片缓存路径 **/
    public static final String TEMP_LOC_IMAGE_PATH = Environment.getExternalStorageDirectory()
            + "/M_Cloud/temp/image/";
    private void initImageLoader(Context context) {
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
//            config.threadPoolSize(3);
        config.denyCacheImageMultipleSizesInMemory();
//            config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheFileNameGenerator(new HashCodeFileNameGenerator());
        config.diskCache(new UnlimitedDiscCache(new File(this.TEMP_LOC_IMAGE_PATH)));
        config.diskCacheSize(100*1024*1024);
        config.memoryCache(new LRULimitedMemoryCache(3 * 1024 * 1024));
//        config.imageDownloader(new OkHttpImageDownloader(context));
//            config.defaultDisplayImageOptions(options);
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
//            config.imageDecoder(new BaseImageDecoder(false));
//            config.writeDebugLogs(); // Remove for release app
        ImageLoader.getInstance().init(config.build());
    }



}

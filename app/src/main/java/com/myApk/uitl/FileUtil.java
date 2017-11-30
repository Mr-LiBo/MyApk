package com.myApk.uitl;

import android.os.Environment;

/**
 * 文件管理工具
 */
public class FileUtil {






//    获取手机中外置内存卡、内置内存卡、手机内存路径。
// 思路是：先用 Environment.getExternalStorageDirectory()获得外部存储卡路径（某些机型也表现为内部存储卡路径），
// 如没有获 取到有效sd卡插入，
// 则使用安卓的配置文件system/etc/vold.fstab读取全部挂载信息，假如也没有可写入的sd卡，
// 则使用 getFilesDir()方式获得当前应用所在存储路径。
// 为适应不同手机的内存情况，
// 先分三种情况获得可存储路径phonePicsPath，后面代码较长是因为有两个工具类，复制即可，代码如下：


    //首先判断是否有外部存储卡，如没有判断是否有内部存储卡，如没有，继续读取应用程序所在存储

    //起始目录
    String mRootPath = java.io.File.separator;
    // SD卡根目录
    String mSDCard = Environment.getExternalStorageDirectory() .toString();

}

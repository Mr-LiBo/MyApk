package com.myApk.model;

/**
 * 更新信息
 */
public class UpdateInfo {
    public String versionCode;
    public String versionName;
    public String url;
    public String message;

    public UpdateInfo(){
        super();
    }

    @Override
    public String toString() {
        return " [versionCode=" + versionCode + ", versionName=" + versionName
                + ", url=" + url + ", message=" + message + "]";
    }
}

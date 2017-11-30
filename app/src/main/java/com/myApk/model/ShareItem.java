package com.myApk.model;

/**
 * 分享item
 */
public class ShareItem
{
    public static final int TYPE_WEIXIN =1;
    public static final int TYPE_WEIXIN_FRIEND =2;
    public static final int TYPE_WEIBO =3;
    public static final int TYPE_SMS =4;

    public int shareType =0;
    public String shareTitle ="";


    public ShareItem(int shareType){
        this.shareType = shareType;
    }

    public ShareItem(int shareType,String shareTitle){
        this.shareType = shareType;
        this.shareTitle = shareTitle;
    }
}

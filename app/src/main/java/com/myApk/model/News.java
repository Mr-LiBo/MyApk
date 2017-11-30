package com.myApk.model;

import java.io.Serializable;

/**
 * 新闻列表的bean类，一个News对象对应于一条item
 * Created by admin on 2015/9/11.
 */
public class News implements Serializable {
    public String title;
    public String url;
    public String photoUrl;
    public String source;
    public String date;
    public String abs;


}

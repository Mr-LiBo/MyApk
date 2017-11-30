package com.myApk.logic.news;

import com.myApk.model.News;

import java.util.List;

/**
 * Created by admin on 2015/9/16.
 */
public interface INewsLogic{
    public List<News> qryNews(boolean isServer);
}

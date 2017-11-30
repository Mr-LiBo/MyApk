package com.myApk.db.news;



import com.myApk.model.News;

import java.util.List;

/**
 * Created by admin on 2015/9/16.
 */
public interface INewsDao {
    public static final String TABLE_Name ="News";

    /**
     * 表字段
     */
    public  interface Column{
          String NEWS_ID ="_id";
          String TITLE = "title";
          String URL = "url";
          String PHOTOURL = "photoUrl";
          String SOURCE = "source";
          String DATE = "date";
          String ABS = "abs";
    }

    /**建表语句*/
    public static final String CREATE_TABLE = "create table "+ TABLE_Name
            + "(" + Column.TITLE + " text,"
            + Column.URL + " text,"
            + Column.PHOTOURL + " text,"
            + Column.SOURCE + " text,"
            + Column.DATE + " text,"
            + Column.ABS + " text)";

    /**
     * 保存
     * @param list
     */
    public void saveNews(List<News> list);

    public List<News> getNews();


}

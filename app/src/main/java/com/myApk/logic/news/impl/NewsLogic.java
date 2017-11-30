package com.myApk.logic.news.impl;

import android.content.Context;

import com.myApk.db.news.NewsDaoImpl;
import com.myApk.http.NetUtil;
import com.myApk.logic.news.INewsLogic;
import com.myApk.model.News;

import java.util.List;

/**
 * Created by admin on 2015/9/14.
 */
public class NewsLogic   implements INewsLogic {
    /** 系统的context对象  */
    protected Context mContext;

    private static NewsLogic newsLogic = null;

    public NewsLogic(Context context){
        this.mContext = context;
    }

    public static NewsLogic getInstance(Context context){
        if (null == newsLogic){

            newsLogic = new NewsLogic(context);
        }
        return  newsLogic;
    }




    @Override
    public List<News> qryNews(boolean isServer) {
        if (isServer)
        {
            List<News> list = NetUtil.getJsonObjectsByUrl("广州", 1);
            NewsDaoImpl.getInstance(mContext, "1").saveNews(list);
//            if (list.size() > 0)
//            {
//                Message msg = new Message();
//                msg.what = GlobalMessageType.TypeMsg.load_service_success;
//                msg.obj = list;
//                return list;
//            }
//            return   NewsDaoImpl.getInstance(mContext,"1").getNews();
            return list;
        }else{
          return   NewsDaoImpl.getInstance(mContext,"1").getNews();
        }
    }
}

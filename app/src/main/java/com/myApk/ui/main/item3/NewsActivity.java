package com.myApk.ui.main.item3;
import android.os.Bundle;

import android.os.Handler;
import android.os.Message;

import com.myApk.model.Recipe;

/**
 * Created by admin on 2015/4/1.
 */

public class NewsActivity extends MoreBaseMyListViewDIYActivity
{
    private boolean hasNextPage = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
         
    }

    public Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case  1:
                    handleLoadPage(true,true,msg.obj,false);
                    break;
                case  2:
                    handleLoadPage(false,true,msg.obj,true);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void handleLoadPage(boolean isFirstPage, boolean isSuccess, Object obj,boolean bl) {

        super.handleLoadPage(isFirstPage, isSuccess, obj,bl);
        if(isSuccess){
            if (isFirstPage){
                lists=  recipeLogic.qryRecipe(mContent);
                adapter.setDate(lists);
            }else{

                Recipe news = new Recipe();
                news.id = 100;
                news.name = "招牌菜";
                news.price = 2000;
                adapter.addDate(news);
            }
            hasNextPage = true;
        }
    }

    @Override
    protected boolean hasNextPage() {
        return hasNextPage;
    }



    @Override
    protected void loadAlbumFromServer(int pageNum) {
        Message  msg = new Message();
        if(pageNum == 1){//刷新
            msg.what=1;
            mHandler.sendMessage(msg);
        }else{
            msg.what=2;
            mHandler.sendMessage(msg);
        }
    }

}

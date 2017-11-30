package com.myApk.ui.main.item3;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.EditText;

import com.myApk.model.Recipe;
import com.myApk.logic.news.impl.RecipeLogic;
import com.myApk.ui.adapter.MyListViewAdapter;
import com.myApk.ui.view.refreshList.MyListViewDIY;
import com.myApk.uitl.NetworkUtil;
import com.myApk.R;

import java.util.ArrayList;
import java.util.List;

/**MYListViewDIY
 * Created by admin on 2015/7/23.
 */
public abstract class MoreBaseMyListViewDIYActivity extends Activity implements MyListViewDIY.OnRefreshListener
{
    Context mContent;
    private MyListViewDIY listView;

    protected MyListViewAdapter adapter;

    List<Recipe> lists;

    RecipeLogic recipeLogic =  RecipeLogic.getInstance();
    private boolean isRefreshing = false;

    private int currentPageNum = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_advert);
        mContent=this;
        initView();
        initList();
    }



    private void initView()
    {
        listView = (MyListViewDIY) findViewById(R.id.activity_moreSquareAlvert_listview);
//        listView.hasBottomLine(false);
    }


    private void initList()
    {
//        recipeLogic.deleteRecipe(mContent,null);
        lists = new ArrayList<Recipe>();
        for (int i = 0; i < 20; i++)
        {
            Recipe recipe = new Recipe();
            recipe.id = i;
            recipe.name = "霸刀斩弘扬" + i + "式";
            recipe.desic="我是中国人";
            recipe.price = 3*i;
            recipeLogic.addRecipe(mContent,recipe);
        }
        lists=  recipeLogic.qryRecipe(mContent);
        adapter = new MyListViewAdapter(this.getApplicationContext(),lists);
        listView.setAdapter(adapter);
        listView.setIsRefreshable(true);
        listView.setOnRefreshListener(this);
        listView.setNewScrollerListener(scroll);
    }

    /** list滚动监听 */
    MyListViewDIY.NewScrollerListener scroll = new MyListViewDIY.NewScrollerListener()
    {
        private boolean isEnd = false;
        @Override
        public void newScrollChanged(AbsListView view, int scrollState)
        {
            if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE
                    || scrollState == AbsListView.OnScrollListener.SCROLL_STATE_FLING)
            {
                if (isEnd && !isRefreshing)
                {
                    isRefreshing = true;
                    scrollMore();
                }
            }

            // 隐藏输入法
            if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)
            {
//                    hideKeyboard(mContent, null);
            }
        }

        @Override
        public void newScroll(AbsListView view, int firstVisibleItem,
                              int visibleItemCount, int totalItemCount)
        {
            isEnd = (firstVisibleItem + visibleItemCount == totalItemCount)&& !isRefreshing;
        }
    };

    @Override
    public void onRefresh()
    {
        isRefreshing = true;
        if (!isNetworkAvailable())
        {
            listView.onRefreshResulet(false);
        }
        else
        {
            loadAlbumFromServer(1);
        }

    }

    private void scrollMore()
    {
        if (hasNextPage())
        {
            listView.showFootView();
            loadAlbumFromServer(currentPageNum+1);
        }
        else
        {
            listView.hideFootView();
            isRefreshing = false;
        }
    }

    protected abstract boolean hasNextPage();

    /**
     * 处理页面加载
     *
     * @param isFirstPage 是否第一页
     * @param isSuccess 是否成功
     * @param obj 用于数据传递
     */
    protected void handleLoadPage(boolean isFirstPage, boolean isSuccess,Object obj,boolean bl)
    {
        if(isSuccess) {
            if (isFirstPage) {
                currentPageNum = isFirstPage ? 1 : (currentPageNum + 1);
                listView.onRefreshResulet(true);
            } else {
                listView.onRefreshResulet(false);
            }

            if (bl) {//加载更多沉睡
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            listView.hideFootView();
            isRefreshing = false;
        }
    }



    protected abstract void loadAlbumFromServer(int pageNum);

    private boolean isNetworkAvailable()
    {
        return NetworkUtil.checkNetwork(this.getApplicationContext());
    }





    /**
     * 隐藏键盘
     *
     * @param activity activity
     * @param editText editText
     */
    public   void hideKeyboard(Activity activity, EditText editText)
    {
        if (activity != null)
        {
            // 隐藏键盘
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);

            imm.hideSoftInputFromWindow(activity.getWindow()
                    .getDecorView()
                    .getWindowToken(), 0);
        }
    }
}

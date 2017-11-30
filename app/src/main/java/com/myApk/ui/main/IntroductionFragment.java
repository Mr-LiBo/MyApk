package com.myApk.ui.main;

import com.myApk.framework.app.BaseV4Fragment;
import com.myApk.model.Recipe;
import com.myApk.logic.news.impl.RecipeLogic;

import com.myApk.ui.adapter.MyListViewAdapter;
import com.myApk.ui.view.refreshList.MyListViewDIY;
import com.myApk.R;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * 2
 */
public class IntroductionFragment extends BaseV4Fragment implements MyListViewDIY.OnRefreshListener,View.OnClickListener
{
    private Context mContext;
    
    private MyListViewDIY myListView;
    
    private List<Recipe> lists =new ArrayList<Recipe>();

    RecipeLogic recipeLogic =null;
    
    private MyListViewAdapter myListViewAdapter;

    private int currentPageNum = 0;

    private boolean isRefreshing = false;

    public     boolean hasNextPage;

    private Dialog confirmDialog;//对话框

    private LinearLayout tv_switch_menu;//菜单开关

    private LinearLayout ll_menu;//菜单

    private LinearLayout menu_increase;
    private LinearLayout menu_delete;
    private LinearLayout menu_change;
    private LinearLayout menu_search;
    private LinearLayout menu_other;

    private static final int MENU_TYPE_INCREASE = 1;
    private static final int MENU_TYPE_DELETE = 2;
    private static final int MENU_TYPE_CHANGE = 3;
    private static final int MENU_TYPE_SEARCH = 4;
    private static final int MENU_TYPE_OTHER = 5;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState)
    {
        View rootview = inflater.inflate(R.layout.fragment_introduction, container, false);
        myListView = (MyListViewDIY) rootview.findViewById(R.id.myListView);
        tv_switch_menu = (LinearLayout) rootview.findViewById(R.id.tv_switch_menu);
        tv_switch_menu.setOnClickListener(this);
        ll_menu = (LinearLayout) rootview.findViewById(R.id.ll_menu);

        View include_menu= rootview.findViewById(R.id.include_menu);
        menu_increase = (LinearLayout) include_menu.findViewById(R.id.menu_increase);
        menu_delete = (LinearLayout) include_menu.findViewById(R.id.menu_delete);
        menu_change = (LinearLayout) include_menu.findViewById(R.id.menu_change);
        menu_search = (LinearLayout) include_menu.findViewById(R.id.menu_search);
        menu_other = (LinearLayout) include_menu.findViewById(R.id.menu_other);
        menu_increase.setOnClickListener(this);
        menu_delete.setOnClickListener(this);
        menu_change.setOnClickListener(this);
        menu_search.setOnClickListener(this);
        menu_other.setOnClickListener(this);

        mContext = getActivity();
        confirmDialog = new Dialog(mContext,R.style.dialog);
        initListView();
        return rootview;
    }


    private void initListView()
    {
        recipeLogic =  RecipeLogic.getInstance();
        Recipe recipe =null;
        for (int i = 1; i > 20; i++)
        {
            recipe = new Recipe();
            recipe.id = i;
            recipe.name = "霸刀斩弘扬" + i + "式";
            recipe.desic = "要练些功必先废功" + i;
            recipe.price=20;
//            recipeLogic.addRecipe(mContext,recipe);
        }
        lists =  recipeLogic.qryRecipe(mContext);
        myListViewAdapter = new MyListViewAdapter(mContext, lists);
        myListView.setAdapter(myListViewAdapter);
        myListView.setIsRefreshable(true);
        myListView.setOnRefreshListener(this);
        myListView.setNewScrollerListener(scroll);
        myListView.setOnItemClickListener( new MyListViewOnItemClickListener());
    }



    public  Recipe temp_Recipe;

    @Override
    protected void initLogics() {

    }

    /**点击事件 */
    public class  MyListViewOnItemClickListener implements AdapterView.OnItemClickListener
    {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
        {
            Recipe recipe = (Recipe) myListViewAdapter.getItem((int) l);
            if (recipe != null)
            {
                if (ll_menu.getVisibility() == View.GONE)
                {
                    ll_menu.setVisibility(View.VISIBLE);
                    temp_Recipe=recipe;
                }
            }
        }
    }

    /**弹窗*/
    private void showDialogRecipeInfo(final Recipe recipe,int type) {
        Log.i("---->",type+"");
        View dlgDiy = LayoutInflater.from(mContext).inflate(R.layout.dialog_editor,null);
        final EditText et_name = (EditText) dlgDiy.findViewById(R.id.et_name);
        final EditText et_price = (EditText) dlgDiy.findViewById(R.id.et_price);
        final EditText et_desc = (EditText) dlgDiy.findViewById(R.id.et_desc);

        if(MENU_TYPE_INCREASE == type)
        {
            Toast.makeText(mContext,"菜单增加",Toast.LENGTH_LONG).show();
            et_name.setText("");
            et_price.setText("");
            et_desc.setText("");
            //确定
            dlgDiy.findViewById(R.id.btn_ok).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Recipe newwRecipe = new Recipe();
                    newwRecipe = recipe;
                    newwRecipe.name = et_name.getText().toString().trim();
                    newwRecipe.price=  Double.parseDouble(et_price.getText().toString().trim());
                    newwRecipe.desic = et_desc.getText().toString().trim();
                    recipeLogic.addRecipe(mContext, newwRecipe);
                    confirmDialog.dismiss();
                    ll_menu.setVisibility(View.GONE);
                    lists =  recipeLogic.qryRecipe(mContext);
                    myListViewAdapter.setDate(lists);
                }
            });
        }
        if(MENU_TYPE_DELETE == type)
        {
            Toast.makeText(mContext,"菜单删除",Toast.LENGTH_LONG).show();
            et_name.setText(recipe.name);
            et_price.setText(recipe.price+"");
            et_desc.setText(recipe.desic);
            //确定
            dlgDiy.findViewById(R.id.btn_ok).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Recipe newwRecipe = new Recipe();
                    newwRecipe = recipe;
                    recipeLogic.deleteRecipe(mContext, newwRecipe);
                    confirmDialog.dismiss();
                    ll_menu.setVisibility(View.GONE);
                    lists =  recipeLogic.qryRecipe(mContext);
                    myListViewAdapter.setDate(lists);

                }
            });
        }
        if(MENU_TYPE_CHANGE == type)
        {
            Toast.makeText(mContext,"菜单修改",Toast.LENGTH_LONG).show();
            et_name.setText(recipe.name);
            et_price.setText(recipe.price+"");
            et_desc.setText(recipe.desic);
            //确定
            dlgDiy.findViewById(R.id.btn_ok).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Recipe newwRecipe = new Recipe();
                    newwRecipe = recipe;
                    newwRecipe.name = et_name.getText().toString().trim();
                    newwRecipe.price=  Double.parseDouble(et_price.getText().toString().trim());
                    newwRecipe.desic = et_desc.getText().toString().trim();
                    recipeLogic.updateRecipe(mContext, newwRecipe);
                    confirmDialog.dismiss();
                    ll_menu.setVisibility(View.GONE);
                    lists =  recipeLogic.qryRecipe(mContext);
                    myListViewAdapter.setDate(lists);

                }
            });
        }
        if(MENU_TYPE_SEARCH == type)
        {
            Toast.makeText(mContext,"菜单查看",Toast.LENGTH_LONG).show();
            et_name.setText(recipe.name);
            et_price.setText(recipe.price+"");
            et_desc.setText(recipe.desic);
            //确定
            dlgDiy.findViewById(R.id.btn_ok).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Recipe newwRecipe = new Recipe();
                    newwRecipe = recipe;
                    newwRecipe.name = et_name.getText().toString().trim();
                    newwRecipe.price=  Double.parseDouble(et_price.getText().toString().trim());
                    newwRecipe.desic = et_desc.getText().toString().trim();
//                    recipeLogic.updateRecipe(mContext, newwRecipe);
                    confirmDialog.dismiss();
                    ll_menu.setVisibility(View.GONE);

                }
            });
        }

        //取消
        dlgDiy.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog.dismiss();
                ll_menu.setVisibility(View.GONE);
            }
        });

        ActionBar.LayoutParams lp = new ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        WindowManager wm = (WindowManager)mContext.getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        lp.width = width * 203 /240;
        confirmDialog.setContentView(dlgDiy,lp);
        confirmDialog.setCanceledOnTouchOutside(false);
        confirmDialog.show();

    }



    MyListViewDIY.NewScrollerListener scroll = new MyListViewDIY.NewScrollerListener()
    {
        private boolean isEnd = false;
        @Override
        public void newScroll(AbsListView view, int firstVisibleItem,
                              int visibleItemCount, int totalItemCount)
        {
            isEnd = (firstVisibleItem + visibleItemCount == totalItemCount)
                    && !isRefreshing;
            if(firstVisibleItem == 0 && visibleItemCount == totalItemCount){
                isEnd =false;
            }
        }

        @Override
        public void newScrollChanged(AbsListView view, int scrollState)
        {
            if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE || scrollState == AbsListView.OnScrollListener.SCROLL_STATE_FLING)
            {
                if (isEnd && !isRefreshing)
                {
                    isRefreshing = true;
                    loadMore();
                }
            }
            
            // 隐藏输入法
            if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)
            {
                hideKeyboard(getActivity(), null);
            }
        }
    };


    @Override
    public void onRefresh()
    {
        Message msg = new Message();
        msg.what =1;
        mHandler.sendMessage(msg);
    }


    public void loadMore()
    {
        Message msg = new Message();
        msg.what =2;
        mHandler.sendMessage(msg);


    }


    public  Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    handleLoadPage( true, true, msg.obj,false);

                    break;
                case 2:
                    handleLoadPage( false, true, msg.obj,true);
                    break;
            }
        }
    };

    public void  handleLoadPage(boolean isFirstPage,boolean isSuccess,Object obj,boolean bl)
    {
        if (isSuccess)
        {
            currentPageNum = isFirstPage ? 1 : (currentPageNum + 1);

            if(isFirstPage){//刷新
                myListView.onRefreshResulet(true);
                lists =  recipeLogic.qryRecipe(mContext);
                myListViewAdapter.setDate(lists);
            }else{//加载更多
                if(hasNextPage) {
                    myListView.showFootView();
                    Recipe recipe = new Recipe();
                    recipe.id = 2000;
                    recipe.name = "霸刀斩";
                    recipe.desic = "要练些功必先废功";
                    lists.add(recipe);
                    myListViewAdapter.setDate(lists);
                }
            }
        }
        else
        {
            myListView.onRefreshResulet(false);
        }
        if(bl) {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    myListView.hideFootView();
                }
            }, 2000);
        }
        hasNextPage = true;
        isRefreshing = false;
    }

    /**
     * 隐藏键盘
     *
     * @param activity activity
     * @param editText editText
     */
    public static void hideKeyboard(Activity activity, EditText editText)
    {
        if (activity != null)
        {
            // 隐藏键盘
            InputMethodManager imm =
                    (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);

            imm.hideSoftInputFromWindow(activity.getWindow()
                    .getDecorView()
                    .getWindowToken(), 0);
        }
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.menu_increase:

                showDialogRecipeInfo(temp_Recipe,MENU_TYPE_INCREASE);
                break;
            case R.id.menu_delete:

                showDialogRecipeInfo(temp_Recipe,MENU_TYPE_DELETE);
                break;
            case R.id.menu_change:

                showDialogRecipeInfo(temp_Recipe,MENU_TYPE_CHANGE);
                break;
            case R.id.menu_search:

                showDialogRecipeInfo(temp_Recipe,MENU_TYPE_SEARCH);
                break;
            case R.id.menu_other:
                Toast.makeText(mContext,"菜单其它",Toast.LENGTH_LONG).show();
                showDialogRecipeInfo(temp_Recipe,MENU_TYPE_OTHER);
                break;
        }
    }

}

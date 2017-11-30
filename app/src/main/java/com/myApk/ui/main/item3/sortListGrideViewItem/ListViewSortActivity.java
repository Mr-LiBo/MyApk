package com.myApk.ui.main.item3.sortListGrideViewItem;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.myApk.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by admin on 2015/9/28.
 */
public class ListViewSortActivity extends Activity{
    private ListView sortListView;
    private SideBar sideBar;
    private TextView tv_Dialog;
    private SortAdapter adapter;
    private ClearEditText cet_text;

    private List<SortModel> listDate = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort_listview);
        initView();

    }

    private void initView() {
        sideBar = (SideBar) findViewById(R.id.sidebar);
        tv_Dialog = (TextView) findViewById(R.id.tv_dialog);
        sortListView = (ListView) findViewById(R.id.lv_listview);
        sideBar.setTvDialog(tv_Dialog);
        //设置右侧触摸监听
        sideBar.setOnTouchLetterCheckedChangeListener(new SideBar.OnTouchLetterCheckedChangeListener() {
            @Override
            public void OnTouchLetterCheckedChangeListener(String str) {
                //该字母首次出现的位置
                int postion = adapter.getPositionForSection(str.charAt(0));
                if (postion != -1){
                    sortListView.setSelection(postion);
                }
            }
        });
        sortListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int posiont, long id) {
                Toast.makeText(getApplicationContext(),
                        ((SortModel)adapter.getItem(posiont)).name,Toast.LENGTH_SHORT).show();
            }
        });

//        listDate = filledData(getResources().getStringArray(R.array.date));

        //根据A-Z进行排序源数据
        Collections.sort(listDate, new PinyinComparator());
        adapter = new SortAdapter(this,listDate);
        sortListView.setAdapter(adapter);

        cet_text = (ClearEditText) findViewById(R.id.cet_filter);
        //根据输入框输入的值改变来过滤搜索
        cet_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                //当输入框的值为空，更新为原业的列表，否则过滤源数据列表
                filterData(charSequence.toString());
            }



            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    /**
     * 根据输入框中的值来过滤数据并更新ListView
     * @param filterStr
     */
    private void filterData(String filterStr) {
        List<SortModel> filterDataList = new ArrayList<>();
        if (TextUtils.isEmpty(filterStr)){
            filterDataList = listDate;
        }else{
            filterDataList.clear();
            for (SortModel sortModel : listDate){
                String name = sortModel.name;
                //判断 name是否有搜索的空肉
                if (name.indexOf(filterStr.toString()) != -1 ||
                        CharacterParser.getInstance().getSelling(name).startsWith(filterStr.toString()) )
                {
                    filterDataList.add(sortModel);
                }
            }
        }
        Collections.sort(filterDataList,new PinyinComparator());
        adapter.upDateListView(filterDataList);
    }

    /**
     * 为listview填充数据
     * @param date
     * @return
     */
    private List<SortModel> filledData(String[] date) {
        List<SortModel> list = new ArrayList<SortModel>();
        for (int i=0;i< date.length;i++){
            SortModel sortModel = new SortModel();
            sortModel.name= date[i];
            //汉字转换成拼音
            String pinyin =CharacterParser.getInstance().getSelling(date[i]);
            String sortString = pinyin.substring(0,1).toUpperCase();

            //正则表达式，判断首字母是否是英文字母
            if (sortString.matches("[A-Z]"))
            {
                sortModel.sortLetters=sortString;
            }else{
                sortModel.sortLetters = "#";
            }
            list.add(sortModel);
        }
        return list;
    }
}

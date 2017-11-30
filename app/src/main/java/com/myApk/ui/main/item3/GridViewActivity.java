package com.myApk.ui.main.item3;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.myApk.R;
import com.myApk.model.GridViewItemInfo;
import com.myApk.ui.adapter.GridViewAdapter;
import com.myApk.ui.main.item3.checkBoxListGrideView.ListviewCheckboxActivity;
import com.myApk.ui.main.item3.contactsGridViewItem.ContactsActivity;
import com.myApk.ui.main.item3.manGroGrideViewItem.ManGoMain;
import com.myApk.ui.main.item3.sortListGrideViewItem.ListViewSortActivity;
import com.myApk.ui.main.item3.tabGridViewItem.TabActivity;
import com.myApk.ui.main.item3.listViewAddViewGrideView.FlowInfoActivity;
import com.myApk.ui.main.item3.tabHostGridViewItem.TabHostMainAcivity;
import com.myApk.ui.view.gifView.GifTestActivity2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2015/9/6.
 */
public class GridViewActivity extends Activity
{
    private GridView gridView;
    private List<GridViewItemInfo> listData = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gridview);
        gridView = (GridView) findViewById(R.id.gridview);
        GridViewAdapter adapter = new GridViewAdapter();
        final Context context = this;
        initData();
        adapter.setDataGridView(context,listData);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                GridViewItemInfo  item = listData.get(position);
                Toast.makeText(context,""+getResources().getString(item.text),Toast.LENGTH_LONG).show();
                startActivity(new Intent(GridViewActivity.this,item.cls));
            }
        });
    }


    public void initData()
    {
        GridViewItemInfo temp = new GridViewItemInfo();
        temp.icon=R.drawable.games;
        temp.text= R.string.setting_recommend_app_mm;
        temp.cls = ManGoMain.class;
        listData.add(temp);

        temp = new GridViewItemInfo();
        temp.icon=R.drawable.mmbuy;
        temp.text= R.string.setting_recommend_app_note;
        temp.cls = TabActivity.class;
        listData.add(temp);

        temp = new GridViewItemInfo();
        temp.icon=R.drawable.notes;
        temp.text= R.string.setting_recommend_app_weibo;
        temp.cls = ListViewSortActivity.class;
        listData.add(temp);

        temp = new GridViewItemInfo();
        temp.icon=R.drawable.weibo;
        temp.cls=ListviewCheckboxActivity.class;
        temp.text= R.string.setting_recommend_app_game;
        listData.add(temp);

        temp = new GridViewItemInfo();
        temp.icon=R.drawable.music_app;
        temp.cls = FlowInfoActivity.class;
        temp.text= R.string.setting_recommend_app_music;
        listData.add(temp);

        temp = new GridViewItemInfo();
        temp.icon=R.drawable.pay;
        temp.cls = TabHostMainAcivity.class;
        temp.text= R.string.setting_recommend_app_pay;
        listData.add(temp);

        temp = new GridViewItemInfo();
        temp.icon=R.drawable.mail;
        temp.cls = ContactsActivity.class;
        temp.text= R.string.setting_recommend_app_email;
        listData.add(temp);

        temp = new GridViewItemInfo();
        temp.icon=R.drawable.mcloud_contacts;
        temp.text= R.string.setting_recommend_app_contacts;
        listData.add(temp);


        temp = new GridViewItemInfo();
        temp.icon=R.drawable.cartoon;
        temp.cls = GifTestActivity2.class;
        temp.text= R.string.setting_recommend_app_cartoon;
        listData.add(temp);


        temp = new GridViewItemInfo();
        temp.icon=R.drawable.mcloud_lingxi;
        temp.text= R.string.setting_recommend_app_lingxi;
        temp.cls = TestHttpActivity.class;
        listData.add(temp);

        temp = new GridViewItemInfo();
        temp.icon=R.drawable.app_aipai;
        temp.text= R.string.setting_recommend_app_aipai;
        temp.cls = PeopleParserActivity.class;
        listData.add(temp);

        temp = new GridViewItemInfo();
        temp.icon=R.drawable.mobile_sucurity_pioneer;
        temp.text= R.string.setting_recommend_app_mobilesucuritypioneer;
        listData.add(temp);

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("--------------->","finsh()关闭GridViewActivity");

    }

}


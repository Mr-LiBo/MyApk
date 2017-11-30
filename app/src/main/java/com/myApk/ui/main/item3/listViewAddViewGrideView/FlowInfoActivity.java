package com.myApk.ui.main.item3.listViewAddViewGrideView;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import android.widget.ListView;

import com.myApk.R;

public class FlowInfoActivity extends FragmentActivity
{
    
    private Button backBtn;
    
    private ListView flowList;
    
    private View headView;
    
    private View footView;
    
    Context context;
    
    // private FlowAdapter Adapter;
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flow_info_activity);
        context = this;
        buildView();
        
        ArrayList<String> mylist = new ArrayList<>();
        mylist.add("信息审核");
        mylist.add("指派人员");
        mylist.add("预约上门");
        
        TempAdapter mSchedule = new TempAdapter(context, mylist);
        flowList.setAdapter(mSchedule);
    }
    
    private void buildView()
    {
        flowList = (ListView) findViewById(R.id.temp_list);
        LayoutInflater layout = LayoutInflater.from(context);
        headView = layout.inflate(R.layout.item_header, null);
        flowList.addHeaderView(headView);
        
        LayoutInflater layoutMain = LayoutInflater.from(context);
        footView = layoutMain.inflate(R.layout.item_footer, null);
        flowList.addFooterView(footView);
    }
    
}

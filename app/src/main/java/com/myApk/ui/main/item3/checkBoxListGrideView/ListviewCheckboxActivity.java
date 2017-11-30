package com.myApk.ui.main.item3.checkBoxListGrideView;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.myApk.R;
import android.widget.AdapterView.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ListView全选/全不选
 */
public class ListviewCheckboxActivity extends Activity implements
        OnClickListener, OnItemClickListener
{
    
    private ListView listView;
    
    private BeanAdapter beanAdapter;
    
    private Button btnSelectAall;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview_checkbox);
        // 初始化
        initView();
        initData();
    }
    
    private void initView()
    {
        RelativeLayout btncanle = (RelativeLayout) findViewById(R.id.btnCancle);
        btncanle.setOnClickListener(this);
        
        RelativeLayout btnAdd = (RelativeLayout) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);
        
        btnSelectAall = (Button) findViewById(R.id.btnSelectAll);
        btnSelectAall.setOnClickListener(this);
        
        Button btnDelete = (Button) findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(this);
        
        listView = (ListView) findViewById(R.id.lvListView);
        listView.setOnItemClickListener(this);
    }
    
    private void initData()
    {
        List<Bean> datas = new ArrayList<>();
        datas.add(new Bean("张三", true));
        datas.add(new Bean("李四", true));
        datas.add(new Bean("王五", false));
        datas.add(new Bean("赵六", true));
        beanAdapter = new BeanAdapter(this, datas);
        listView.setAdapter(beanAdapter);
    }
    
    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.btnCancle:
                finish();
                break;
            case R.id.btnAdd:
                beanAdapter.add(new Bean("麻子", true));
                beanAdapter.notifyDataSetChanged();
                break;
            case R.id.btnSelectAll:
                if (btnSelectAall.getText().toString().trim().equals("全选"))
                {
                    beanAdapter.configCheckMap(true);
                    beanAdapter.notifyDataSetChanged();
                    btnSelectAall.setText("全不选");
                }
                else
                {
                    beanAdapter.configCheckMap(false);
                    beanAdapter.notifyDataSetChanged();
                    btnSelectAall.setText("全选");
                }
                
                break;
            case R.id.btnDelete:
                // 拿到checkBox选择寄存map
                Map<Integer, Boolean> map = beanAdapter.getIsCheckMap();
                int count = beanAdapter.getCount();
                for (int i = 0; i < count; i++)
                {
                    int posiotn = i - (count - beanAdapter.getCount());
                    if (map.get(i) != null && map.get(i))
                    {
                        Bean bean = (Bean) beanAdapter.getItem(posiotn);
                        if (bean.isCanRemove())
                        {
                            beanAdapter.getIsCheckMap().remove(i);
                            beanAdapter.remove(posiotn);
                        }
                        else
                        {
                            map.put(posiotn, false);
                        }
                    }
                }
                beanAdapter.notifyDataSetChanged();
                break;
        }
    }
    
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view,
            int position, long id)
    {
        if (view.getTag() instanceof BeanAdapter.ViewHolder)
        {
            BeanAdapter.ViewHolder holder = (BeanAdapter.ViewHolder) view.getTag();
            holder.cb_check.toggle();
        }
    }
}

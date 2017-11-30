package com.myApk.ui.main.item3.checkBoxListGrideView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.myApk.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2015/10/8.
 */
public class BeanAdapter extends BaseAdapter {
    /**上下文对象 */
    private Context context = null;

    /**
     * 数据集合
     */
    private List<Bean> datas =null;

    /**CheckBox 是否选择的存储集合，key 是positoin ,value是该positoin是否选中*/
    private Map<Integer,Boolean> isCheckMap = new HashMap<Integer,Boolean>();

    public BeanAdapter(Context context, List<Bean> datas) {
        this.context = context;
        this.datas = datas;
        configCheckMap(false);//全不选
    }

    /**
     * 首先，默认情况下，所有项目都是没有先中的，这里进行初始化
     * @param bool
     */
    public void configCheckMap(boolean bool) {
        for (int i =0;i<datas.size();i++){
            isCheckMap.put(i,bool);
        }
    }

    @Override
    public int getCount() {
        return datas == null ? 0 : datas.size();
    }

    @Override
    public Object getItem(int positoon) {
        return  datas.get(positoon);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if (convertView == null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.listview_checkbox_item,viewGroup,false);
            holder.tv_title = (TextView) convertView.findViewById(R.id.listview_checkbox_tv_title);
            holder.cb_check = (CheckBox) convertView.findViewById(R.id.listview_checkbox_cb_checkbox);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        Bean bean = datas.get(position);
        holder.tv_title.setText(bean.getTitle());
        holder.cb_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                /**
                 * 将选择项加载到map里面寄存
                 */
                isCheckMap.put(position,isChecked);
            }
        });

        /*
         *获得该item ,是否允许删除
         */
        boolean canRemove = bean.isCanRemove();
        if (canRemove){ //允许删除就显示checkbox
            holder.cb_check.setVisibility(View.VISIBLE);
            if (isCheckMap.get(position) == null){
                isCheckMap.put(position,false);
            }
            holder.cb_check.setChecked(isCheckMap.get(position));

        }else{
            //隐藏单选按钮，因为是不可删除的
            holder.cb_check.setVisibility(View.GONE);
            holder.cb_check.setChecked(false);
        }
        return convertView;
    }

    public static class ViewHolder{
        public TextView tv_title = null;
        public CheckBox cb_check =null;
        public Object data = null;
    }

    /**
     * 增加一项
     * @param bean
     */
    public void add(Bean bean){
        this.datas.add(0,bean);
        configCheckMap(false);//让所有项目都为不选择
    }

    /**
     * 删除一项
     * @param postion
     */
    public void remove(int postion){
        this.datas.remove(postion);
    }

    public Map<Integer, Boolean> getIsCheckMap() {
        return isCheckMap;
    }

    public List<Bean> getDatas() {
        return datas;
    }
}

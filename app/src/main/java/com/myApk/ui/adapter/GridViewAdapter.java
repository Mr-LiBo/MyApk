package com.myApk.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.myApk.R;
import com.myApk.model.GridViewItemInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2015/9/6.
 */
public class GridViewAdapter extends BaseAdapter {
    Context context;
    private List<GridViewItemInfo> listData = new ArrayList<>();

    public void setDataGridView(Context context,List<GridViewItemInfo> listData){
        this.context = context;
        this.listData = listData;
    }


    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int i) {
        return listData.get(i);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (convertView == null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_gridview_item, null);
            holder.icon = (ImageView) convertView.findViewById(R.id.adapter_gv_im_icon);
            holder.text = (TextView) convertView.findViewById(R.id.adapter_gv_tv_name);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        GridViewItemInfo item = listData.get(position);
        holder.icon.setImageResource(item.icon);
        holder.text.setText(item.text);

        return convertView;
    }

    private static class ViewHolder
    {
        ImageView icon;
        TextView text;
    }
}

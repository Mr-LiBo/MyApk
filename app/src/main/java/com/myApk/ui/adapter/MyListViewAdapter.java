package com.myApk.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.myApk.R;
import com.myApk.model.Recipe;

import java.util.List;

/**
 * Created by admin on 2015/7/17.
 */
public class MyListViewAdapter extends BaseAdapter
{
    private Context mContext;
    
    private List<Recipe> mList;
    
    public MyListViewAdapter(Context context, List<Recipe> list)
    {
        this.mContext = context;
        this.mList = list;
    }

    public void setDate( List<Recipe> list){
        this.mList=list;
        notifyDataSetChanged();
    }

    public void addDate(Recipe news){
        this.mList.add(news);
        notifyDataSetChanged();
    }
    @Override
    public int getCount()
    {
        return  mList.size() != 0 ? mList.size() : 0;
    }
    
    @Override
    public Object getItem(int i)
    {
        return mList.get(i);
    }
    
    @Override
    public long getItemId(int i)
    {
        return i;
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup)
    {
        ViewHolder holder;
        if(convertView == null)
        {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_home_listview_item,null,false);
            holder.tv_id= (TextView) convertView.findViewById(R.id.adapter_tv_id);
            holder.tv_title= (TextView) convertView.findViewById(R.id.adapter_tv_title);
            holder.tv_desic= (TextView) convertView.findViewById(R.id.adapter_tv_desic);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        setHolder(holder,position);

        return convertView;
    }

    private void setHolder(ViewHolder holder, int position)
    {
        Recipe news = mList.get(position);
        holder.tv_id.setText(news.id+"");
        holder.tv_title.setText(news.name);
        holder.tv_desic.setText(news.price+"");
    }


    class ViewHolder
    {
        TextView tv_id;
        TextView tv_title;
        TextView tv_desic;
    }
}

package com.myApk.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.myApk.R;
import com.myApk.model.News;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by admin on 2015/7/17.
 */
public class ListViewAdapterNews extends BaseAdapter
{
    private Context mContext;
    
    private List<News> mList;
    
    public ListViewAdapterNews(Context context, List<News> list)
    {
        this.mContext = context;
        this.mList = list;
    }

    public void setDate( List<News> list){
        this.mList=list;
        notifyDataSetChanged();
    }

    public void addDate(News news){
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_home_listview_news_item,null,false);
            holder.tv_title= (TextView) convertView.findViewById(R.id.adapter_new_title);
            holder.tv_abs= (TextView) convertView.findViewById(R.id.adapter_new_abs);
            holder.tv_source = (TextView) convertView.findViewById(R.id.adapter_new_author);
            holder.tv_date = (TextView) convertView.findViewById(R.id.adapter_new_sorttime);
            holder.iv_photoUrl=(ImageView) convertView.findViewById(R.id.adapter_new_img);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        setHolder(holder,position);

        return convertView;
    }

    private void setHolder(ViewHolder holder, int position)
    {
        News news = mList.get(position);
        holder.tv_title.setText(news.title);
        holder.tv_abs.setText(news.abs);
        holder.tv_source .setText(news.source);
        holder.tv_date .setText(news.date);
        if (news.photoUrl != null && news.photoUrl.length()>0) {
            DisplayImageOptions simpleOptions = new DisplayImageOptions.Builder()
                    .showImageOnLoading(R.drawable.ic_launcher)
                    .showImageForEmptyUri(R.drawable.ic_launcher)
                    .showImageOnFail(R.drawable.ic_launcher)
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .considerExifParams(true)
                    .bitmapConfig(Bitmap.Config.RGB_565)
                    .build();
            ImageLoader.getInstance().displayImage(news.photoUrl, holder.iv_photoUrl, simpleOptions);

            Picasso.with(mContext)
                    .load(news.photoUrl)
                    .placeholder(R.drawable.ic_launcher)
                    .error(R.drawable.ic_launcher)
                    .into(holder.iv_photoUrl);
        }
//        holder.tv_photoUrl.setBackgroundResource(news.photoUrl);
    }


    class ViewHolder
    {


        TextView tv_title;
//        TextView url;
        TextView tv_abs;
        ImageView iv_photoUrl;
        TextView tv_source;
        TextView tv_date;
    }
}

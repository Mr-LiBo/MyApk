package com.myApk.ui.main.item3.sortListGrideViewItem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;
import com.myApk.R;

import java.util.List;

/**
 * Created by admin on 2015/9/28.
 */
public class SortAdapter extends BaseAdapter implements SectionIndexer {

    private List<SortModel> list = null;
    private Context context;

    public SortAdapter(Context mContext ,List<SortModel> mList) {
        this.context = mContext;
        this.list = mList;
    }

    /**
     * 当listView数据发生变化时，调用些方法来更新Listview
     * @return
     */
    public void upDateListView(List<SortModel> list){
        this.list= list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return this.list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        SortModel model = list.get(position);
        if (view == null){
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.srot_item,null);
            viewHolder.tv_title = (TextView) view.findViewById(R.id.srot_item_title);
            viewHolder.tv_catalog= (TextView) view.findViewById(R.id.srot_item_catalog);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        //根据positoin 获取分类的首字母的char的位置，则认为是第一次出现
        int section= getSectionForPosition(position);
        if (position == getPositionForSection(section)){
            viewHolder.tv_catalog.setVisibility(View.VISIBLE);
            viewHolder.tv_catalog.setText(model.sortLetters);
        }else {
            viewHolder.tv_catalog.setVisibility(View.GONE);
        }
        viewHolder.tv_title.setText(model.name);
        return view;
    }

    private final static class ViewHolder{
        TextView tv_catalog;
        TextView tv_title;
    }

    @Override
    public Object[] getSections() {
        return null;
    }
    /**
     * 根据ListView的当前位置获取分类的首字母的Char ascii值
     */
    @Override
    public int getPositionForSection(int section) {

        for (int i=0;i<getCount();i++){
            String sortStr = list.get(i).sortLetters;
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section){
                return  i;
            }
        }
        return -1;
    }

    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
     */
    @Override
    public int getSectionForPosition(int position) {

        return list.get(position).sortLetters.charAt(0);
    }
}

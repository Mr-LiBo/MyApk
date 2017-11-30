package com.myApk.ui.main.item3.listViewAddViewGrideView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.myApk.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2015/10/19.
 */
public class TempAdapter extends BaseAdapter {
    private Context context;

    private List<String> list;

    public TempAdapter(Context context, ArrayList<String> mylist) {
        this.context = context;
        this.list = mylist;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        View view = LayoutInflater.from(context).inflate(
                R.layout.item_flow_info, null);
        TextView text = (TextView) view.findViewById(R.id.ItemText);
        text.setText(list.get(position) + "");
        return view;
    }
}

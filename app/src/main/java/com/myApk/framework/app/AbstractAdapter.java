package com.myApk.framework.app;

import java.util.ArrayList;
import java.util.List;

import android.widget.BaseAdapter;

/**
 * 抽象适配器.提供修改适配器数据的方法
 *
 * @author 杨建通
 *         DATE:2015/4/2 14:14
 */
public abstract class AbstractAdapter<T> extends BaseAdapter {

    private ArrayList<T> mDatas = new ArrayList<T>();

    public AbstractAdapter() {
    }

    public AbstractAdapter(List<T> datas) {
        setData(datas);
    }

    public AbstractAdapter(T[] datas) {
        setData(datas);
    }

    public void setData(List<T> datas) {
        this.mDatas.clear();
        if (datas != null) {
            this.mDatas.addAll(datas);
        }
        notifyDataSetChanged();
    }

    public void setData(T[] datas) {
        this.mDatas.clear();
        if (datas != null) {
            for (T t : datas) {
                this.mDatas.add(t);
            }
        }
        notifyDataSetChanged();
    }

    public void addData(List<T> datas) {
        if (datas != null && datas.size() > 0) {
            this.mDatas.addAll(datas);
            notifyDataSetChanged();
        }
    }

    public void addData(T[] datas) {
        if (datas != null && datas.length > 0) {
            for (T data : datas) {
                this.mDatas.add(data);
            }
            notifyDataSetChanged();
        }
    }

    public void addData(T data) {
        if (data != null) {
            this.mDatas.add(data);
            notifyDataSetChanged();
        }
    }

    public void addData(int index, T data) {
        if (index >= 0 && index <= mDatas.size()) {
            if (data != null) {
                this.mDatas.add(index, data);
                notifyDataSetChanged();
            }
        }
    }

    public void addData(int index, List<T> datas) {
        if (index >= 0 && index <= mDatas.size()) {
            if (datas != null && datas.size() > 0) {
                this.mDatas.addAll(index, datas);
                notifyDataSetChanged();
            }
        }
    }

    public void removeData(T data) {
        if (data != null) {
            this.mDatas.remove(data);
            notifyDataSetChanged();
        }
    }

    public void removeData(int index) {
        if (index >= 0 && index < mDatas.size()) {
            this.mDatas.remove(index);
            notifyDataSetChanged();
        }
    }

    public void clearData() {
        this.mDatas.clear();
    }

    public ArrayList<T> getDatas() {
        return this.mDatas;
    }

    public abstract T[] getDatasOfArray();

    @Override
    public int getCount() {
        return this.mDatas.size();
    }

    @Override
    public T getItem(int position) {
        if (position >= 0 && position < this.mDatas.size()) {
            return this.mDatas.get(position);
        } else {
            return null;
        }
    }

    @Override
    public long getItemId(int position) {
        // 简单返回数据索引号，可以重写本方法，返回真实数据对应的ID
        return position;
    }
}

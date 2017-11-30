package com.myApk.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.myApk.R;
import com.myApk.model.ShareItem;

import com.myApk.ui.main.item3.ShareSelectDialog;

import java.util.List;

import java.util.ArrayList;

/**
 * 分享试配器
 */
public class ShareSelectAdapter extends BaseAdapter
{
    private Context context;
    /**App列表信息*/
    private List<ShareItem> appList = new ArrayList<ShareItem>();
    /**自定义Dialog*/
    private ShareSelectDialog shareSelectDialog;
    private ShareSelectDialog.ShareSelectCallBack callBack;

    public ShareSelectAdapter(Context context,List<ShareItem> appList,ShareSelectDialog shareSelectDialog,ShareSelectDialog.ShareSelectCallBack callBack)
    {
        this.context = context;
        this.appList = appList;
        this.shareSelectDialog = shareSelectDialog;
        this.callBack = callBack;
    }

    @Override
    public int getCount() {
        return appList.size();
    }

    @Override
    public ShareItem getItem(int position) {
        return appList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder=null;
        if (null == convertView)
        {
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_share_select_item,null);
            holder = new ViewHolder();
            holder.imageView= (ImageView) convertView.findViewById(R.id.adapter_share_im);
            holder.textView= (TextView) convertView.findViewById(R.id.adapter_share_tv);
            convertView.setTag(holder);
        }
        else{
            holder= (ViewHolder) convertView.getTag();
        }
        ShareItem item = getItem(position);
        holder.textView.setText(item.shareTitle);
        if (ShareItem.TYPE_SMS == item.shareType)
        {
            holder.imageView.setBackgroundResource(R.drawable.mcloud_share_icon_user);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    shareSelectDialog.dismiss();
                    callBack.shareSMS();
                }
            });
        }

        if (ShareItem.TYPE_WEIXIN == item.shareType)
        {
            holder.imageView.setBackgroundResource(R.drawable.mcloud_share_icon_wx);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    shareSelectDialog.dismiss();
                    callBack.shareWX();
                }
            });
        }

        if (ShareItem.TYPE_WEIXIN_FRIEND == item.shareType)
        {
            holder.imageView.setBackgroundResource(R.drawable.mcloud_share_icon_pyq);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    shareSelectDialog.dismiss();
                    callBack.shareWXFriend();
                }
            });
        }
        if (ShareItem.TYPE_WEIBO == item.shareType)
        {
            holder.imageView.setBackgroundResource(R.drawable.mcloud_share_icon_xnwb);
            convertView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    shareSelectDialog.dismiss();
                    callBack.shareWB();
                }
            });
        }

        return convertView;
    }

    public final class ViewHolder
    {
        public ImageView imageView;
        public TextView textView;
    }
}

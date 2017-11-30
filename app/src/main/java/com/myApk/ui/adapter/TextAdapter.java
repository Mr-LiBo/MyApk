package com.myApk.ui.adapter;
import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.myApk.R;


/**
 * Created by admin on 2015/8/18.
 */
public class TextAdapter extends ArrayAdapter<String>
{
    private Context mContext;
    private List<String> mListData;
    private String [] mArrayData;
    private int selectedPos = -1;
    private String selectedText ="";
    private int normalDrawbledId;
    private Drawable selectedDrawble;
    private float textSize;
    private OnClickListener mOnClickListener;
    private OnItemClickListener mOnItemClickListener;

    public TextAdapter(Context context,List<String> listData, int sId,int nId)
    {
        super(context,R.string.nodata,listData);
        mContext = context;
        mListData = listData;
        selectedDrawble = mContext.getResources().getDrawable(sId);
        normalDrawbledId = nId;
        init();
    }

    private void init()
    {
        mOnClickListener = new OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedPos = (int) view.getTag();
                setSelectedPosition(selectedPos);
                if (mOnItemClickListener != null)
                {
                    mOnItemClickListener.onItemClick(view,selectedPos);
                }
            }
        };
    }

    public TextAdapter(Context context,String [] arrayData,int sId,int nId)
    {
        super(context,R.string.nodata,arrayData);
        mContext = context;
        mArrayData = arrayData;
        selectedDrawble =mContext.getResources().getDrawable(sId);
        normalDrawbledId = nId;
        init();
    }

    /**
     * 设置先选中的position ，并通知列表刷新
     * @param pos
     */
    public void setSelectedPosition(int pos)
    {
        if (mListData != null && pos < mListData.size())
        {
            selectedPos = pos;
            selectedText = mListData.get(pos);
            notifyDataSetChanged();
        }else if(mArrayData != null && pos < mArrayData.length)
        {
            selectedPos = pos;
            selectedText = mArrayData[pos];
            notifyDataSetChanged();
        }

    }

    /**
     * 设置选中的position，但不通知刷新
     */
    public void setSelectedPositionNoNotify(int pos)
    {
        selectedPos = pos;
        if (mListData != null && pos < mListData.size())
        {
            selectedText = mListData.get(pos);
        }else if(mArrayData != null && pos < mArrayData.length)
        {
            selectedText = mArrayData[pos];
        }
    }

    /**
     * 获取选中的position
     */
    public int getSelectedPosition()
    {
        if(mListData != null && selectedPos < mListData.size())
        {
            return selectedPos;
        }
        if (mArrayData != null && selectedPos < mArrayData.length)
        {
            return selectedPos;
        }
        return -1;
    }

    /**
     * 设置列表字体大小
     */
    public void setTextSize(float size){
        textSize =size;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        TextView view;
        if (convertView == null)
        {
            view = (TextView) LayoutInflater.from(mContext).inflate(R.layout.choose_item,parent,false);
        }else
        {
            view = (TextView) convertView;
        }
        view.setTag(position);
        String mstr = "";
        if(mListData != null)
        {
            if (position < mListData.size())
            {
                mstr = mListData.get(position);
            }
        }else if (mArrayData != null)
        {
            if (position < mArrayData.length)
            {
                mstr = mArrayData[position];
            }
        }
        if (mstr.contains("不限")) {
            view.setText("不限");
        }else{
            view.setText(mstr);
        }
        view.setTextSize(TypedValue.COMPLEX_UNIT_SP,textSize);
        if (selectedText != null && selectedText.equals(mstr))
        {
            view.setBackgroundDrawable(selectedDrawble);//设置先中的背景图
        }else
        {
            view.setBackgroundDrawable(mContext.getResources().getDrawable(normalDrawbledId));//设置未选中状态背景图片
        }
        view.setPadding(20,0,0,0);
        view.setOnClickListener(mOnClickListener);
        return view;
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mOnItemClickListener = listener;
    }

    /**
     * 重新定义菜单选项接口
     */
    public interface OnItemClickListener{
        public void onItemClick(View view,int position);
    }
}

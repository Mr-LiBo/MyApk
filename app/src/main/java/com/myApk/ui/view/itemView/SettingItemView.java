package com.myApk.ui.view.itemView;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.myApk.R;

/**
 * 生成复合控件的标签
 */
public class SettingItemView extends LinearLayout{
    public SettingItemView(Context context) {
        super(context);
        inti(context,null);
    }

    public SettingItemView(Context context,AttributeSet atttibute) {
        super(context,atttibute);
        inti(context,atttibute);
    }

    public SettingItemView(Context context,AttributeSet atttibute,int defStyle) {
        super(context,atttibute,defStyle);
        inti(context,atttibute);
    }

    private View view = null;
    private TextView tv_title = null;
    private  TextView tv_desc = null;
    private CheckBox cb_state = null;

    public TextView getTv_title() {
        return tv_title;
    }

    public TextView getTv_desc() {
        return tv_desc;
    }

    public CheckBox getCb_state() {
        return cb_state;
    }

    private String tit = "";
    private  String contentOn= "";
    private String contentOff ="";
    private boolean check = false;

    private  void inti(Context context ,AttributeSet attribute){
        view = View.inflate(context, R.layout.ui_setting_view,this);
        tv_title = (TextView) view.findViewById(R.id.tv_setting_title);
        tv_desc = (TextView) view.findViewById(R.id.tv_setting_desc);
        cb_state = (CheckBox) view.findViewById(R.id.cb_setting_state);
        // 解析出配置的变量值

        TypedArray array = context.obtainStyledAttributes(attribute,R.styleable.SettingItemView);

        tit = array.getString(R.styleable.SettingItemView_tit);
        contentOn = array.getString(R.styleable.SettingItemView_contentOn);
        contentOff = array.getString(R.styleable.SettingItemView_contentOff);
        check = array.getBoolean(R.styleable.SettingItemView_check,false);

        if (check){
            tv_desc.setText(contentOn);
        }else {
            tv_desc.setText(contentOff);
        }
        tv_title.setText(tit);;
        cb_state.setChecked(check);
        array.recycle();//回收

    }

    /**
     * 响应点击的更新
     */
    public void updateStatus(){
        cb_state.setChecked(!cb_state.isChecked());
        if (cb_state.isChecked()){
            tv_desc.setText(contentOn);
        }else{
            tv_desc.setText(contentOff);
        }
    }
}

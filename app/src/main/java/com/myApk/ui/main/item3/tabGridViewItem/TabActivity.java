package com.myApk.ui.main.item3.tabGridViewItem;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.Display;
import android.view.LayoutInflater;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.myApk.R;

import java.util.ArrayList;
import java.util.List;

/**
 *左右点击Tab
 */
public class TabActivity extends FragmentActivity{
    private Context context;

    private RadioGroup radioGroup;

    private RadioButton rb_btn_a;
    private RadioButton rb_btn_b;

    public List<Fragment> fragments = new ArrayList<Fragment>();

    private LayoutInflater inflater;
    private Display display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radiogroup_tab);
        context = this;
        inflater = LayoutInflater.from(context);
        display = getWindowManager().getDefaultDisplay();

        initView();
    }

    private void initView() {
        fragments.add(new TabAFragment());
        fragments.add(new TabBFragment());
        radioGroup = (RadioGroup) findViewById(R.id.rg_tabs_rg);
        rb_btn_a = (RadioButton) findViewById(R.id.rb_tab_a);
        rb_btn_b = (RadioButton) findViewById(R.id.rb_tab_b);
        rb_btn_a.setTextColor(getResources().getColor(R.color.red));
        rb_btn_b.setTextColor(getResources().getColor(R.color.gray));

        FragmentTabAdapter tabAdapter = new FragmentTabAdapter(this,fragments,R.id.fl_tab_conent,radioGroup);
        tabAdapter.setOnRgsCheckedChangedListener(new FragmentTabAdapter.OnRgsCheckedChangedListener(){
            @Override
            public void OnRgsCheckedChanged(RadioGroup rg, int checkedId, int index) {
                super.OnRgsCheckedChanged(rg, checkedId, index);

                if (index == 0){
                    rb_btn_a.setTextColor(getResources().getColor(R.color.red));
                    rb_btn_b.setTextColor(getResources().getColor(R.color.gray));
                }
                if (index == 1){
                    rb_btn_a.setTextColor(getResources().getColor(R.color.gray));
                    rb_btn_b.setTextColor(getResources().getColor(R.color.red));
                }
            }
        });
    }
}

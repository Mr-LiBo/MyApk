package com.myApk.ui.main.item3.tabGridViewItem;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.myApk.R;

/**
 * Created by admin on 2015/9/24.
 */
public class TabAFragment extends Fragment {

    private LayoutInflater inflater;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        System.out.println("AAAAAAAAAA____onCreateView");
        this.inflater = inflater;
        view = inflater.inflate(R.layout.fragment_tab_a, container, false);


        return view;
    }
}

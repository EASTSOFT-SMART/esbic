package com.eastsoft.android.esbic.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eastsoft.android.esbic.R;

/**
 * Created by sofa on 2016/1/22.
 */
public class SmartHomeFragmentTwo extends Fragment {
    private View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         rootView=inflater.inflate(R.layout.smart_home_list_fragment_two,null);
         return rootView;
    }
}

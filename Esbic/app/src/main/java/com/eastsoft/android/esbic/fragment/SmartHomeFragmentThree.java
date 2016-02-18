package com.eastsoft.android.esbic.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eastsoft.android.esbic.R;

/**
 * Created by sofa on 2016/1/22.
 */
public class SmartHomeFragmentThree extends Fragment {
    private View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView=inflater.inflate(R.layout.smart_home_list_fragment_three,null);
        return rootView;
    }
}

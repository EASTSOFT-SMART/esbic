package com.eastsoft.android.esbic.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.eastsoft.android.esbic.R;
import com.eastsoft.android.esbic.adapter.MyBrightnessAdapter;

/**
 * Created by sofa on 2016/1/27.
 */
public class BrightnessFragment extends Fragment implements AdapterView.OnItemClickListener {
    private ListView listView;
    private String[] strings;
    private View rootView;
    private MyBrightnessAdapter myBrightnessAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView=inflater.inflate(R.layout.volume_bright_fragment_brightness,null);
        iniyData();
        return rootView;
    }
    private void iniyData(){
        listView=(ListView)rootView.findViewById(R.id.brightness_item_setting);
        strings=new String[]{"屏幕亮度","锁屏时间"};
        myBrightnessAdapter=new MyBrightnessAdapter(this.getActivity(),strings);
        listView.setAdapter(myBrightnessAdapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}

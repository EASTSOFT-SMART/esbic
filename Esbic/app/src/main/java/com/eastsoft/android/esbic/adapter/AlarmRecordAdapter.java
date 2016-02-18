package com.eastsoft.android.esbic.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.eastsoft.android.esbic.R;

/**
 * Created by Mr Wang on 2016/2/6.
 */
public class AlarmRecordAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    public AlarmRecordAdapter(Context context){
        this.context=context;
    }
    @Override
    public int getCount() {
        return 8;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        inflater=LayoutInflater.from(context);
        view=inflater.inflate(R.layout.alarm_record_item,null);
        return view;
    }
}

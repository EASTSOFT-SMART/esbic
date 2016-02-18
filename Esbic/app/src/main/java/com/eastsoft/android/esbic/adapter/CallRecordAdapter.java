package com.eastsoft.android.esbic.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.eastsoft.android.esbic.R;

import java.util.List;

/**
 * Created by Mr Wang on 2016/2/6.
 */
public class CallRecordAdapter extends BaseAdapter {
    private List<Object> objectList;
    private Context context;
    private LayoutInflater inflater;
    public CallRecordAdapter(List<Object> objectList,Context context){
        this.context=context;
        this.objectList=objectList;
    }
    @Override
    public int getCount() {
        return 6;
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
        view=inflater.inflate(R.layout.call_record_item,null);
        return view;
    }
}

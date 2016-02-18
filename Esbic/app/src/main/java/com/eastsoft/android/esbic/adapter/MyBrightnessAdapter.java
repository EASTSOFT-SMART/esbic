package com.eastsoft.android.esbic.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.eastsoft.android.esbic.R;

/**
 * Created by sofa on 2016/1/27.
 */
public class MyBrightnessAdapter extends BaseAdapter {
    private String[] strings;
    private LayoutInflater inflater;

    public MyBrightnessAdapter(Context context,String[] strings){
        this.strings=strings;
        inflater=LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        return strings.length;
    }

    @Override
    public Object getItem(int i) {
        if (strings==null){
            return null;
        }else {
            return strings[i];
        }

    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder=new ViewHolder();
        String item=strings[i];
        view=inflater.inflate(R.layout.brightness_fragment_item,null);
        viewHolder.textView=(TextView)view.findViewById(R.id.brightness_setting_list);
        view.setTag(viewHolder);
        viewHolder.textView.setText(item);
        return view;
    }
    class  ViewHolder{
        TextView textView;
    }
}

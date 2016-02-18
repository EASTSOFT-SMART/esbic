package com.eastsoft.android.esbic.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.eastsoft.android.esbic.R;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by sofa on 2016/1/26.
 */
public class MyVolumeAdapter extends BaseAdapter {
    private String[] strings;
    private LayoutInflater inflater;

    public MyVolumeAdapter(Context context,String[] s ){
        inflater=LayoutInflater.from(context);
        this.strings=s;
    }
    @Override
    public int getCount() {
        return strings.length;
    }

    @Override
    public Object getItem(int i) {
        if (strings==null){
            return null;
        }else{
            return strings[i];
        }
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewObject viewObject=new ViewObject();
        String item=strings[i];
        view=inflater.inflate(R.layout.volume_fragment_item,null);
        viewObject.text=(TextView)view.findViewById(R.id.volume_setting_list);
        view.setTag(viewObject);
        viewObject.text.setText(item);
        return view;
    }

    class ViewObject{
        TextView text;
    }
}

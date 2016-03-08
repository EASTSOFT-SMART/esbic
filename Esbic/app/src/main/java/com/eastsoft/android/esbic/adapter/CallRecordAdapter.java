package com.eastsoft.android.esbic.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.eastsoft.android.esbic.R;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Mr Wang on 2016/2/6.
 */
public class CallRecordAdapter extends BaseAdapter {
    private List<Object> objectList;
    private Context context;
    private LayoutInflater inflater;
    private ViewAttribute viewAttribute;
    public CallRecordAdapter(List<Object> objectList,Context context){
        this.context=context;
        this.objectList=objectList;
        viewAttribute=new ViewAttribute();
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
        viewAttribute.recordIcon=(ImageView)view.findViewById(R.id.record_icon);
        viewAttribute.recordFrom=(TextView)view.findViewById(R.id.record_from);
        viewAttribute.recordTime=(TextView)view.findViewById(R.id.record_time);
        viewAttribute.recordTimeTwo=(TextView)view.findViewById(R.id.record_time_two);
        return view;
    }

    class ViewAttribute{
        ImageView recordIcon;
        TextView recordFrom;
        TextView recordTime;
        TextView recordTimeTwo;
    }
}

package com.eastsoft.android.esbic.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.eastsoft.android.esbic.R;
import com.eastsoft.android.esbic.util.MessageUtil;

import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by Mr Wang on 2016/2/5.
 */
public class MessageAdapter extends BaseAdapter {
    private List<MessageUtil> messageList;
    private Context context;
    private LayoutInflater inflate;
    private ViewClass viewClass;
    public MessageAdapter(List<MessageUtil> messageList, Context context){
        this.messageList=messageList;
        this.context=context;
        viewClass=new ViewClass();
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
        inflate=LayoutInflater.from(context);
        view=inflate.inflate(R.layout.message_content_item,null);
        viewClass.readStatus=(ImageView)view.findViewById(R.id.read_status);
        viewClass.title=(TextView)view.findViewById(R.id.item_content);
        viewClass.time=(TextView)view.findViewById(R.id.message_time);
        return view;
    }


    class ViewClass{
        ImageView readStatus;
        TextView title;
        TextView time;
    }
}
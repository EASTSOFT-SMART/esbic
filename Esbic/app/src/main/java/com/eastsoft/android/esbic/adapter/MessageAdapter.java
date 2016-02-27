package com.eastsoft.android.esbic.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.eastsoft.android.esbic.R;
import com.eastsoft.android.esbic.table.MessageInfo;

import java.util.List;

/**
 * Created by Mr Wang on 2016/2/5.
 */
public class MessageAdapter extends BaseAdapter {
    private List<MessageInfo> messageList;
    private Context context;
    private LayoutInflater inflate;
    private TextView itemTitle, itemDate;
    public MessageAdapter(List<MessageInfo> messageList, Context context){
        this.messageList=messageList;
        this.context=context;
    }
    @Override
    public int getCount() {
        return messageList.size();
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
        itemTitle = (TextView)view.findViewById(R.id.item_title);
        itemDate = (TextView)view.findViewById(R.id.item_date);
        itemTitle.setText(messageList.get(i).getType()==0?"消息":"广告");
        itemDate.setText(messageList.get(i).getTime());
        return view;
    }


    class ViewClass{

    }
}
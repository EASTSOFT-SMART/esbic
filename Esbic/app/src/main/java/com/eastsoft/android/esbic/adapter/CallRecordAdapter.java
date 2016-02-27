package com.eastsoft.android.esbic.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.eastsoft.android.esbic.R;
import com.eastsoft.android.esbic.jni.DeviceInfo;
import com.eastsoft.android.esbic.jni.DeviceTypeEnum;
import com.eastsoft.android.esbic.jni.IntercomTypeEnum;
import com.eastsoft.android.esbic.table.IntercomInfo;
import com.eastsoft.android.esbic.util.JsonUtil;

import java.util.List;

/**
 * Created by Mr Wang on 2016/2/6.
 */
public class CallRecordAdapter extends BaseAdapter {
    private List<IntercomInfo> intercomInfos;
    private Context context;
    private LayoutInflater inflater;
    private TextView device,time,type;
    public CallRecordAdapter(List<IntercomInfo> intercomInfos,Context context){
        this.context=context;
        this.intercomInfos =intercomInfos;
    }
    @Override
    public int getCount() {
        return intercomInfos.size();
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
        device = (TextView)view.findViewById(R.id.record_from);
        time = (TextView)view.findViewById(R.id.record_time);
        type = (TextView)view.findViewById(R.id.record_status);
        if(intercomInfos.size() == 0)
        {
            return view;
        }
        IntercomInfo intercomInfo = intercomInfos.get(i);
        DeviceInfo deviceInfo = JsonUtil.fromJson(intercomInfo.getDevice(), DeviceInfo.class);
        if(deviceInfo == null)
        {
            return view;
        }
        device.setText(DeviceTypeEnum.find(deviceInfo.getDevice_type()).getName());
        time.setText(intercomInfos.get(i).getTime());
        type.setText(IntercomTypeEnum.find(intercomInfos.get(i).getType()).getName());
        return view;
    }
}

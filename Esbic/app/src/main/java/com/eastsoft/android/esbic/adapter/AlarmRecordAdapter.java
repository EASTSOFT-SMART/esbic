package com.eastsoft.android.esbic.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.eastsoft.android.esbic.R;
import com.eastsoft.android.esbic.table.AlarmInfo;

import java.util.List;

/**
 * Created by Mr Wang on 2016/2/6.
 */
public class AlarmRecordAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private List<AlarmInfo> alarmInfos;
    private TextView alarmType, alarmTime, alarmHandleTime;
    public AlarmRecordAdapter(List<AlarmInfo> alarmInfos, Context context){
        this.alarmInfos = alarmInfos;
        this.context=context;
    }
    @Override
    public int getCount() {
        return alarmInfos.size();
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
        alarmType = (TextView) view.findViewById(R.id.alarm_type);
        alarmTime = (TextView) view.findViewById(R.id.alarm_time);
        alarmHandleTime = (TextView) view.findViewById(R.id.alarm_handle_time);
        AlarmInfo alarmInfo = alarmInfos.get(i);
        alarmType.setText("报警通道：" + alarmInfo.getType());
        alarmTime.setText(alarmInfo.getTime());
        alarmHandleTime.setText(alarmInfo.getTime());
        return view;
    }
}

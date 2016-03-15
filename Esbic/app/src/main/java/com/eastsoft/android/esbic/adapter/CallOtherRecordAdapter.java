package com.eastsoft.android.esbic.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.eastsoft.android.esbic.R;
import com.eastsoft.android.esbic.jni.DeviceInfo;
import com.eastsoft.android.esbic.jni.DeviceTypeEnum;
import com.eastsoft.android.esbic.jni.IntercomTypeEnum;
import com.eastsoft.android.esbic.table.IntercomInfo;
import com.eastsoft.android.esbic.util.JsonUtil;
import com.eastsoft.android.esbic.util.TimeUtil;

import java.util.List;

/**
 * Created by Mr Wang on 2016/2/6.
 */
public class CallOtherRecordAdapter extends BaseAdapter {
    private List<IntercomInfo> intercomInfos;
    private Context context;
    private LayoutInflater inflater;
    private ViewAttribute viewAttribute;
    public CallOtherRecordAdapter(List<IntercomInfo> intercomInfos, Context context){
        this.context=context;
        this.intercomInfos =intercomInfos;
        viewAttribute=new ViewAttribute();
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
        view=inflater.inflate(R.layout.call_other_record_item,null);
        viewAttribute.recordIcon=(ImageView)view.findViewById(R.id.call_other_record_icon);
        viewAttribute.recordFrom=(TextView)view.findViewById(R.id.call_other_record_device);
        viewAttribute.recordTime=(TextView)view.findViewById(R.id.call_other_record_time);
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
        String deviceStr="未知设备";
        DeviceTypeEnum deviceTypeEnum = DeviceTypeEnum.find(deviceInfo.getDevice_type());
        if(deviceTypeEnum == DeviceTypeEnum.DT_ROOM_MACHINE)
        {
            deviceStr = String.format("%02d", deviceInfo.getBuilding_no())+"楼"
                    + String.format("%02d", deviceInfo.getUnit_no())+"单元"
                    + String.format("%02d", deviceInfo.getLayer_no())+"层"
                    + String.format("%02d", deviceInfo.getRoom_no())+"房间";
        }else if(deviceTypeEnum == DeviceTypeEnum.DT_UNIT_DOOR_MACHINE)
        {
            deviceStr = String.format("%02d", deviceInfo.getBuilding_no())+"楼"
                    + String.format("%02d", deviceInfo.getUnit_no())+"单元"
                    + String.format("%02d", deviceInfo.getDev_no())+"号"
                    + deviceTypeEnum.getName();
        }
        viewAttribute.recordFrom.setText(deviceStr);
        viewAttribute.recordTime.setText(intercomInfo.getTime().substring(5) +"\t\t"+ TimeUtil.formatTime(intercomInfo.getTalkTime()));
        IntercomTypeEnum intercomTypeEnum = IntercomTypeEnum.find(intercomInfo.getType());
        switch (intercomTypeEnum)
        {
            case RECEIVED   :viewAttribute.recordIcon.setBackgroundResource(R.drawable.call_in);break;
            case MISSED     :viewAttribute.recordIcon.setBackgroundResource(R.drawable.call_in);break;
            case CALL_ROOM  :viewAttribute.recordIcon.setBackgroundResource(R.drawable.call_out);break;
        }
        return view;
    }

    class ViewAttribute{
        ImageView recordIcon;
        TextView recordFrom;
        TextView recordTime;
    }
}

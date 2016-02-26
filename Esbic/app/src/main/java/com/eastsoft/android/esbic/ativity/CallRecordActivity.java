package com.eastsoft.android.esbic.ativity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.eastsoft.android.esbic.R;
import com.eastsoft.android.esbic.adapter.CallRecordAdapter;
import com.eastsoft.android.esbic.jni.DeviceInfo;
import com.eastsoft.android.esbic.jni.DeviceTypeEnum;
import com.eastsoft.android.esbic.service.IModelService;
import com.eastsoft.android.esbic.table.IntercomInfo;
import com.eastsoft.android.esbic.util.JsonUtil;
import com.eastsoft.android.esbic.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sofa on 2016/1/26.
 */
public class CallRecordActivity extends BaseActivity implements View.OnClickListener,AdapterView.OnItemClickListener {
    private Intent intent;
    private ImageButton back;
    private ImageView recordImage;
    private ListView recordList;
    private TextView recordDevice;
    private CallRecordAdapter callRecordAdapter;
    private List<IntercomInfo> intercomInfos = new ArrayList<>();
    private IModelService modelService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.call_record);
        initData();
    }
    private void  initData(){
        modelService = ((MyApplication)getApplication()).getModelService();
        intercomInfos = modelService.getIntecomInfo();
        recordDevice =(TextView)this.findViewById(R.id.record_device);
        if(intercomInfos.size() > 0)
        {
            showInterconInfo(0);
        }
        back=(ImageButton)this.findViewById(R.id.call_record_back);
        recordList=(ListView)this.findViewById(R.id.record_contents);
        back.setOnClickListener(this);
        callRecordAdapter=new CallRecordAdapter(intercomInfos,CallRecordActivity.this);
        recordList.setAdapter(callRecordAdapter);
        recordList.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId()==back.getId()){
            this.finish();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        showInterconInfo(i);
    }

    private void showInterconInfo(int index)
    {
        IntercomInfo intercomInfo = intercomInfos.get(index);
        DeviceInfo deviceInfo = JsonUtil.fromJson(intercomInfo.getDevice(), DeviceInfo.class);
        if(deviceInfo == null)
        {
            LogUtil.print(LogUtil.LogPriorityEnum.CORE_LOG_PRI_ERROR, "deviceInfo fromJson is null");
            recordDevice.setText("获取设备信息失败！");
            return;
        }
        String str = "设备号：" + deviceInfo.getDev_no() +"\n"
                   + "楼栋号：" + deviceInfo.getBuilding_no() +"\n"
                   + "单元号：" + deviceInfo.getUnit_no() + "\n";
        if(deviceInfo.getDevice_type() == DeviceTypeEnum.DT_ROOM_MACHINE.getType()) {
              str += "楼层号：" + deviceInfo.getLayer_no() + "\n"
                   + "房间号：" + deviceInfo.getRoom_no();
        }
        recordDevice.setText(str);
    }

}

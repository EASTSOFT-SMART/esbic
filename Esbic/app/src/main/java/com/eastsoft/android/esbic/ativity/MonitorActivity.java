package com.eastsoft.android.esbic.ativity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.SurfaceView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Chronometer;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.eastsoft.android.esbic.R;
import com.eastsoft.android.esbic.adapter.MonitorItemAdapter;
import com.eastsoft.android.esbic.jni.DeviceInfo;
import com.eastsoft.android.esbic.jni.DeviceTypeEnum;
import com.eastsoft.android.esbic.service.BroadcastTypeEnum;
import com.eastsoft.android.esbic.service.IModelService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sofa on 2016/1/25.
 */
public class MonitorActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener,
        AdapterView.OnItemClickListener{
    private SurfaceView surfaceView;
    private GridView gridView;
    private ImageButton back;
    private TextView back2;
    private String placeName[];
    private List<Map<String,Object>> mapList;
    private MonitorItemAdapter monitorItemAdapter;
    private boolean[] state;
    private IModelService modelService;
    private boolean isMonitoring;
    private int currentMonitorIndex;
    private DeviceInfo currentDeviceInfo;
    private View currentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.monitor);
        initData();

    }
    private void initData(){
        state=new boolean[]{false,false,false,false,false,false,false,false};
        back=(ImageButton)this.findViewById(R.id.monitor_back);
        back2=(TextView) this.findViewById(R.id.monitor_back2);
        surfaceView=(SurfaceView)this.findViewById(R.id.monitor_video);
        gridView=(GridView)this.findViewById(R.id.exchange_monitor);
        placeName=new String[]{"单元正门","单元车库","单元侧门","单元右门","小区正门","小区侧门","小区","小区"};
        mapList=getData();
        monitorItemAdapter=new MonitorItemAdapter(placeName, state, this);
        gridView.setAdapter(monitorItemAdapter);
        gridView.setOnItemClickListener(this);
        gridView.setOnItemSelectedListener(this);
        back.setOnClickListener(this);
        back2.setOnClickListener(this);

        modelService = ((MyApplication)getApplication()).getModelService();

        MyMonitorReceiver myReceiver = new MyMonitorReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.eastsoft.android.esbic.model");
        registerReceiver(myReceiver, intentFilter);
    }

    @Override
    public void onClick(View view) {
        if (view.getId()==back.getId() || view.getId() == back2.getId())
        {
            playButtonMusic(musicButtonId);
            if(currentDeviceInfo != null)
            {
                modelService.active_hang_up_monitor(currentDeviceInfo);
            }
            finish();
        }
    }

    //获取List<Map<String,Object>>数据
    private List<Map<String,Object>> getData(){
        List<Map<String,Object>> mapList=new ArrayList<Map<String,Object>>();
          for (int i=0;i<placeName.length;i++){
              Map<String,Object> map=new HashMap<String,Object>();
              map.put("place",placeName[i]);
              mapList.add(map);
        }
     return mapList;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        playMusic();
        currentView = view;
        DeviceInfo deviceInfo = ((MyApplication)getApplication()).getModelService().getDeviceInfo();
        if(deviceInfo == null)
        {
            showLongToast("您尚未设置设备信息！");
            return;
        }
        DeviceTypeEnum deviceTypeEnum;
        int devNo;
        if(i%2 == 0)
        {
            //  door machine
            deviceTypeEnum = DeviceTypeEnum.DT_UNIT_DOOR_MACHINE;
            devNo = (i+1+1)/2;
        }else
        {
            //  wall machine
            deviceTypeEnum = DeviceTypeEnum.DT_WALL_MACHINE;
            devNo = (i+1)/2;
        }
        DeviceInfo info = new DeviceInfo(deviceTypeEnum.getType(), deviceInfo.getBuilding_no(), deviceInfo.getUnit_no(), (byte)0, (byte)0, devNo);
        if(isMonitoring == false)
        {
            state[i] = true;
            monitorItemAdapter.notifyDataSetChanged();
            isMonitoring = true;
            currentMonitorIndex = i;
            currentDeviceInfo = info;
            ((MyApplication)getApplication()).getModelService().ui_req_monitor(info);
        }else if(currentMonitorIndex == i){
            state[i] = false;
            monitorItemAdapter.notifyDataSetChanged();
            isMonitoring = false;
            currentMonitorIndex = -1;
            modelService.active_hang_up_monitor(currentDeviceInfo);
            currentDeviceInfo = null;
        }else{
            modelService.active_hang_up_monitor(currentDeviceInfo);
            SystemClock.sleep(500);
            state[currentMonitorIndex] = false;
            state[i] = true;
            monitorItemAdapter.notifyDataSetChanged();
            isMonitoring = true;
            currentMonitorIndex = i;
            currentDeviceInfo = info;
            ((MyApplication)getApplication()).getModelService().ui_req_monitor(info);
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public class MyMonitorReceiver extends BroadcastReceiver
    {
        @Override
        public void onReceive(Context context, Intent intent2)
        {
            Bundle bundle = intent2.getExtras();
            int cmd = bundle.getInt("cmd");
            BroadcastTypeEnum e = BroadcastTypeEnum.find(cmd);
            String value = bundle.getString("value");
            switch (e)
            {
                case PLAY_VIDEO :
                    showLongToast("视频url" + value);
                    break;
                case MONITOR_CONFIRM :
                    showLongToast("您监视的设备已经找到！");
                    break;
                case DEVICE_BUSY :
                    showLongToast("您呼叫的设备正在忙，请稍后再拨！");
                    state[currentMonitorIndex] = false;
                    monitorItemAdapter.notifyDataSetChanged();
                    isMonitoring = false;
                    currentMonitorIndex = -1;
                    currentDeviceInfo = null;
                    break;
                case MONITOR_HANG_UP :
                    showLongToast("门口机挂断监视！");
                    state[currentMonitorIndex] = false;
                    monitorItemAdapter.notifyDataSetChanged();
                    isMonitoring = false;
                    currentMonitorIndex = -1;
                    currentDeviceInfo = null;
                    break;
                default:
                    break;
            }
        }
    }
}

package com.eastsoft.android.esbic.ativity;

import android.app.Activity;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.eastsoft.android.esbic.R;
import com.eastsoft.android.esbic.adapter.MonitorItemAdapter;
import com.eastsoft.android.esbic.jni.DeviceInfo;
import com.eastsoft.android.esbic.jni.DeviceTypeEnum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.monitor);
        initData();

    }
    private void initData(){
        state=new boolean[]{true,false,false,false,false,false,false,false};
        back=(ImageButton)this.findViewById(R.id.monitor_back);
        back2=(TextView) this.findViewById(R.id.monitor_back2);
        surfaceView=(SurfaceView)this.findViewById(R.id.monitor_video);
        gridView=(GridView)this.findViewById(R.id.exchange_monitor);
        placeName=new String[]{"单元正门","单元车库","单元侧门","单元右门","小区正门","小区侧门","小区","小区"};
        mapList=getData();
        monitorItemAdapter=new MonitorItemAdapter(placeName,this);
        monitorItemAdapter.initState(state);
        gridView.setAdapter(monitorItemAdapter);
        gridView.setOnItemClickListener(this);
        gridView.setOnItemSelectedListener(this);
        back.setOnClickListener(this);
        back2.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId()==back.getId() || view.getId() == back2.getId())
        {
            playButtonMusic(musicButtonId);
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
        monitorItemAdapter.changeState(i);
        monitorItemAdapter.notifyDataSetChanged();

        DeviceInfo deviceInfo = ((MyApplication)getApplication()).getModelService().getDeviceInfo();
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
        ((MyApplication)getApplication()).getModelService().ui_req_monitor(info);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}

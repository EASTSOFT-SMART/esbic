package com.eastsoft.android.esbic.ativity;

import android.app.Activity;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.SimpleAdapter;

import com.eastsoft.android.esbic.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by sofa on 2016/1/25.
 */
public class MonitorActivity extends Activity implements AdapterView.OnItemSelectedListener,
        AdapterView.OnItemClickListener{
    private SurfaceView surfaceView;
    private GridView gridView;
    private int hangUpIcon[];
    private ImageButton back;
    private String placeName[],monitorTime[],state[];
    private List<Map<String,Object>> mapList;
    private SimpleAdapter simpleAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.monitor);
        initData();

    }
    private void initData(){
        back=(ImageButton)this.findViewById(R.id.monitor_back);
        surfaceView=(SurfaceView)this.findViewById(R.id.monitor_place);
        gridView=(GridView)this.findViewById(R.id.exchange_monitor);
        hangUpIcon=initIcon();
        placeName=new String[]{"单元正门","单元车库","单元侧门","单元右门","小区正门","小区侧门","小区","小区"};
        monitorTime=new String[]{"00:00","00:00","00:00","00:00","00:00","00:00","00:00","00:00"};
        state=new String[]{"空闲","空闲","空闲","空闲","空闲","空闲","空闲","空闲"};
        mapList=getData();
        simpleAdapter=new SimpleAdapter(this,mapList,R.layout.monitor_exchange,new String[]{"image"
                ,"place","monitorTime"},new int[]{R.id.close_monitor,R.id.place,R.id.monitor_time});
        gridView.setAdapter(simpleAdapter);
        gridView.setOnItemClickListener(this);
        gridView.setOnItemSelectedListener(this);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MonitorActivity.this.finish();
            }
        });
    }


    //获取挂断的图标
    private int[] initIcon(){
        int[] icon=new int[8];
        for (int i=0;i<icon.length;i++){
            icon[i]=R.drawable.ic_launcher;
        }
        return icon;
    }
    //获取List<Map<String,Object>>数据
    private List<Map<String,Object>> getData(){
        List<Map<String,Object>> mapList=new ArrayList<Map<String,Object>>();
          for (int i=0;i<hangUpIcon.length;i++){
              Map<String,Object> map=new HashMap<String,Object>();
              map.put("image",hangUpIcon[i]);
              map.put("place",placeName[i]);
              map.put("state",state[i]);
              map.put("monitorTime",monitorTime[i]);
              mapList.add(map);
        }
     return mapList;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}

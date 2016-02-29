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
    private ImageButton back;
    private String placeName[];
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
        surfaceView=(SurfaceView)this.findViewById(R.id.monitor);
        gridView=(GridView)this.findViewById(R.id.exchange_monitor);
        placeName=new String[]{"单元正门","单元车库","单元侧门","单元右门","小区正门","小区侧门","小区","小区"};
        mapList=getData();
        simpleAdapter=new SimpleAdapter(this,mapList,R.layout.monitor_exchange,new String[]{
                "place"},new int[]{R.id.monitor});
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

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}

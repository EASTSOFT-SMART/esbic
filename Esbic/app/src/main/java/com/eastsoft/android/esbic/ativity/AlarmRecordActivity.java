package com.eastsoft.android.esbic.ativity;

import android.content.Intent;
import android.graphics.ImageFormat;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.eastsoft.android.esbic.R;
import com.eastsoft.android.esbic.adapter.AlarmRecordAdapter;
import com.eastsoft.android.esbic.table.AlarmInfo;

import java.util.List;

/**
 * Created by sofa on 2016/1/26.
 */
public class AlarmRecordActivity extends BaseActivity implements View.OnClickListener{
    private ImageButton back;
    private TextView back2;
    private ListView alarmRecordContent;
    private Intent intent;
    private AlarmRecordAdapter alarmRecordAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_record);
        initData();
    }
    private void initData(){
        List<AlarmInfo> alarmInfoList = ((MyApplication)getApplication()).getModelService().getAlarmInfo();
        back=(ImageButton)this.findViewById(R.id.alarm_record_back);
        back.setOnClickListener(this);
        back2 = (TextView)this.findViewById(R.id.alarm_record_back2);
        back2.setOnClickListener(this);
        alarmRecordContent=(ListView)this.findViewById(R.id.record_list);
        alarmRecordAdapter=new AlarmRecordAdapter(alarmInfoList, this);
        alarmRecordContent.setAdapter(alarmRecordAdapter);
    }

    @Override
    public void onClick(View view) {
        if (view.getId()==back.getId() || view.getId() == back2.getId()){
            playButtonMusic(musicButtonId);
            this.finish();
        }
    }
}


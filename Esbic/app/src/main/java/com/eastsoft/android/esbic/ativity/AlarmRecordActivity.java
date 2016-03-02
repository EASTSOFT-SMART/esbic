package com.eastsoft.android.esbic.ativity;

import android.content.Intent;
import android.graphics.ImageFormat;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import com.eastsoft.android.esbic.R;
import com.eastsoft.android.esbic.adapter.AlarmRecordAdapter;

/**
 * Created by sofa on 2016/1/26.
 */
public class AlarmRecordActivity extends BaseActivity implements View.OnClickListener{
    private ImageButton back;
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
        back=(ImageButton)this.findViewById(R.id.alarm_record_back);
        back.setOnClickListener(this);
        alarmRecordContent=(ListView)this.findViewById(R.id.record_list);
        alarmRecordAdapter=new AlarmRecordAdapter(this);
        alarmRecordContent.setAdapter(alarmRecordAdapter);
    }

    @Override
    public void onClick(View view) {
        if (view.getId()==back.getId()){
            this.finish();
        }
    }
}


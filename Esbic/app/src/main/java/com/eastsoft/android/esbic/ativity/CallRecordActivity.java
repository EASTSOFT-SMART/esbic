package com.eastsoft.android.esbic.ativity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import com.eastsoft.android.esbic.R;
import com.eastsoft.android.esbic.adapter.CallRecordAdapter;

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
    private CallRecordAdapter callRecordAdapter;
    private List<Object> objectList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.call_record);
        initData();
    }
    private void  initData(){
        back=(ImageButton)this.findViewById(R.id.call_record_back);
        recordList=(ListView)this.findViewById(R.id.record_contents);
        back.setOnClickListener(this);
        objectList=new ArrayList<>();
        callRecordAdapter=new CallRecordAdapter(objectList,CallRecordActivity.this);
        recordList.setAdapter(callRecordAdapter);

    }

    @Override
    public void onClick(View view) {
        if (view.getId()==back.getId()){
            this.finish();
        }

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}

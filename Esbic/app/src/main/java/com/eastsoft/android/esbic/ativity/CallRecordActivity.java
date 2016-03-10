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
import com.eastsoft.android.esbic.service.IModelService;
import com.eastsoft.android.esbic.table.IntercomInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sofa on 2016/1/26.
 */
public class CallRecordActivity extends BaseActivity implements View.OnClickListener,AdapterView.OnItemClickListener {
    private Intent intent;
    private ImageButton back;
    private TextView back2;
    private ImageView recordImage;
    private ListView recordList;
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

        recordList=(ListView)this.findViewById(R.id.record_contents);
        callRecordAdapter=new CallRecordAdapter(intercomInfos, CallRecordActivity.this);
        recordList.setAdapter(callRecordAdapter);

        back=(ImageButton)this.findViewById(R.id.call_record_back);
        back.setOnClickListener(this);
        back2 = (TextView)this.findViewById(R.id.call_record_back2);
        back2.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        if (view.getId()==back.getId() || view.getId() == back2.getId())
        {
            playButtonMusic(musicButtonId);
            this.finish();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}

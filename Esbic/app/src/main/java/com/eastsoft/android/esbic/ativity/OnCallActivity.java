package com.eastsoft.android.esbic.ativity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;


import com.eastsoft.android.esbic.R;
import com.eastsoft.android.esbic.jni.DeviceInfo;
import com.eastsoft.android.esbic.service.IModelService;
import com.eastsoft.android.esbic.util.JsonUtil;
import com.zhy.autolayout.AutoLayoutActivity;

/**
 * Created by Mr Wang on 2016/2/19.
 */
public class OnCallActivity extends BaseActivity implements View.OnClickListener{
    private SurfaceView surfaceView;
    private TextView onCallAddrOne,onCallAddrTwo, back2;
    private Button hangUp,accept,unLock;
    private ImageButton back;
    private SurfaceHolder surfaceHolder;
    private IModelService modelService;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.on_call);
        initData();
    }

    private void initData(){
        surfaceView=(SurfaceView)this.findViewById(R.id.on_call_player);
        surfaceHolder=surfaceView.getHolder();
        onCallAddrOne=(TextView)this.findViewById(R.id.on_call_addr_one);
        onCallAddrTwo=(TextView)this.findViewById(R.id.on_call_addr_two);
        hangUp=(Button)this.findViewById(R.id.on_call_hang_up);
        accept=(Button)this.findViewById(R.id.on_call_accept);
        unLock=(Button)this.findViewById(R.id.on_call_unlock);
        back=(ImageButton)this.findViewById(R.id.on_call_back);
        back2=(TextView) this.findViewById(R.id.on_call_back2);
        hangUp.setOnClickListener(this);
        accept.setOnClickListener(this);
        unLock.setOnClickListener(this);
        back.setOnClickListener(this);
        back2.setOnClickListener(this);
        modelService = ((MyApplication)getApplication()).getModelService();
        intent = getIntent();
        String tmp = intent.getStringExtra("value");
        DeviceInfo deviceInfo = JsonUtil.fromJson(tmp, DeviceInfo.class);
        onCallAddrOne.setText(deviceInfo.getBuilding_no() + "楼" + deviceInfo.getUnit_no() + "单元");
        onCallAddrTwo.setText(deviceInfo.getDev_no()+"号门口机呼叫");
    }

    @Override
    public void onClick(View view) {
        if (view.getId()==back.getId()||view.getId()==back2.getId()){
            playButtonMusic(musicButtonId);
            finish();
        }
        if (view.getId()==accept.getId()){
            modelService.ui_talk_answer();
        }
        if (view.getId()==hangUp.getId()){
            modelService.active_hang_up();
            finish();
        }
        if (view.getId()==unLock.getId()){
            modelService.unlock_door();
        }
    }
}

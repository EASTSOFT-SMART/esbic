package com.eastsoft.android.esbic.ativity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.TextView;

import com.eastsoft.android.esbic.R;
import com.eastsoft.android.esbic.jni.DeviceInfo;
import com.eastsoft.android.esbic.jni.DeviceTypeEnum;
import com.eastsoft.android.esbic.service.BroadcastTypeEnum;
import com.eastsoft.android.esbic.service.IModelService;
import com.eastsoft.android.esbic.util.JsonUtil;

public class RoomCallActivity extends BaseActivity implements View.OnClickListener {
    private TextView isConversation,device, back2;
    private ImageButton back;
    private Button accept, hangUp;
    private Intent intent;
    private Chronometer timer;
    private static final int STATEWAIT=0;
    private static final int STATECONVERSION=1;
    private static final int HANGUPSTATE=2;
    private DeviceInfo deviceInfo;
    private IModelService modelService;
    private boolean isTalking;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.roomcall);
        initData();
    }
    private void  initData(){
        isConversation=(TextView)this.findViewById(R.id.room_call_going);
        timer=(Chronometer)this.findViewById(R.id.room_call_time);

        back=(ImageButton)this.findViewById(R.id.room_call_back);
        back.setOnClickListener(this);
        back2=(TextView) this.findViewById(R.id.room_call_back2);
        back2.setOnClickListener(this);
        device=(TextView)this.findViewById(R.id.room_call_device);

        accept=(Button)this.findViewById(R.id.room_call_accept);
        accept.setOnClickListener(this);
        hangUp=(Button)this.findViewById(R.id.room_call_hang_up);
        hangUp.setOnClickListener(this);
        intent=getIntent();
        String tmp = intent.getStringExtra("value");
        DeviceInfo deviceInfo = JsonUtil.fromJson(tmp, DeviceInfo.class);
        device.setText(deviceInfo.getBuilding_no() + "楼" + deviceInfo.getUnit_no() + "单元"
                    + deviceInfo.getLayer_no() +"层"+deviceInfo.getRoom_no()+"房间");

        MyReceiver myReceiver = new MyReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.eastsoft.android.esbic.model");
        registerReceiver(myReceiver, intentFilter);

        modelService = ((MyApplication)getApplication()).getModelService();
    }

    @Override
    public void onClick(View view) {
        playMusic();
        if (view.getId()==back.getId() || view.getId()==back2.getId()){
            if(isTalking == true)
            {
                modelService.active_hang_up();
            }
            this.finish();
        }
        if (view.getId()==accept.getId()){
            modelService.ui_talk_answer();
            isTalking = true;
        }
        if (view.getId()==hangUp.getId()){
            modelService.active_hang_up();
            finish();
        }
    }

    public class MyReceiver extends BroadcastReceiver
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
                case HANG_UP :
                    showLongToast("对方已挂断！");
                    finish();
                    break;
                default:
                    break;
            }
        }
    }

}

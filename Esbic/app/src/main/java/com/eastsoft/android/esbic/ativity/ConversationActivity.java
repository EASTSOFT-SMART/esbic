package com.eastsoft.android.esbic.ativity;

import android.content.Intent;
import android.graphics.ImageFormat;
import android.media.Image;
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

/**
 * Created by sofa on 2016/1/26.
 */
public class ConversationActivity extends BaseActivity implements View.OnClickListener {
    private TextView isConversation,roomName, back2;
    private ImageButton back;
    private Button hangUp;
    private Intent intent;
    private Chronometer timer;
    private static final int STATEWAIT=0;
    private static final int STATECONVERSION=1;
    private static final int HANGUPSTATE=2;
    private String stopCvsTime="";
    private String startCvsTime="";
    private DeviceInfo deviceInfo;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conversation);
        initData();
    }
    private void  initData(){
        isConversation=(TextView)this.findViewById(R.id.conversation_going);
        roomName=(TextView)this.findViewById(R.id.room_name);
        timer=(Chronometer)this.findViewById(R.id.conversation_time);
        back=(ImageButton)this.findViewById(R.id.conversation_back);
        back.setOnClickListener(this);
        back2=(TextView) this.findViewById(R.id.conversation_back2);
        back2.setOnClickListener(this);
        hangUp=(Button)this.findViewById(R.id.conversation_hang_up);
        hangUp.setOnClickListener(this);
        intent=getIntent();
        String roomNumber=intent.getStringExtra("roomNumber");
        if(roomNumber.length() == 4)
        {
            DeviceInfo info = ((MyApplication)getApplication()).getModelService().getDeviceInfo();
            if(info == null)
            {
                showLongToast("您先设置本机的设备信息！");
                return;
            }
            int layerNo = Integer.parseInt(roomNumber.substring(0,2));
            int roomNo = Integer.parseInt(roomNumber.substring(2));
            deviceInfo = new DeviceInfo(DeviceTypeEnum.DT_ROOM_MACHINE.getType(), info.getBuilding_no(), info.getUnit_no(), (byte)layerNo, (byte)roomNo, 1);
        }else if(roomNumber.length() == 8)
        {
            int buildNo = Integer.parseInt(roomNumber.substring(0,2));
            int unitNo = Integer.parseInt(roomNumber.substring(2,4));
            int layerNo = Integer.parseInt(roomNumber.substring(4,6));
            int roomNo = Integer.parseInt(roomNumber.substring(6));
            deviceInfo = new DeviceInfo(DeviceTypeEnum.DT_ROOM_MACHINE.getType(), buildNo, (byte)unitNo, (byte)layerNo, (byte)roomNo, 1);
        }else
        {
            showLongToast("地址格式错误！");
            return;
        }
        roomName.setText(String.format("%02d", deviceInfo.getBuilding_no()) + "楼"
                + String.format("%02d", deviceInfo.getUnit_no()) + "单元"
                + String.format("%02d", deviceInfo.getLayer_no()) + "层"
                + String.format("%02d", deviceInfo.getRoom_no()) + "房间");
        ((MyApplication)getApplication()).getModelService().active_call_user(deviceInfo);
        timer.start();
        Log.i("ElapsedRealtime()的数值是",String.valueOf(SystemClock.elapsedRealtime()));
    }

    @Override
    public void onClick(View view) {
        if (view.getId()==hangUp.getId()){
            playButtonMusic(musicButtonId);
            ((MyApplication)getApplication()).getModelService().active_hang_up();
            intent.setClass(ConversationActivity.this, CallMain.class);
            startActivity(intent);
            this.finish();
        }
        if (view.getId()==back.getId() || view.getId()==back2.getId()){
            playButtonMusic(musicButtonId);
            this.finish();
        }
    }


    //记录通话时间并且保存在本地
    private void saveRecordTime(int state){
        if (state==STATECONVERSION){

        }
    }

}

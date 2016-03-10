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
        roomName.setText(roomNumber);
        timer.start();
        Log.i("ElapsedRealtime()的数值是",String.valueOf(SystemClock.elapsedRealtime()));
    }

    @Override
    public void onClick(View view) {
        if (view.getId()==hangUp.getId()){
            playButtonMusic(musicButtonId);
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

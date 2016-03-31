package com.eastsoft.android.esbic.ativity;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.TextView;

import com.eastsoft.android.esbic.R;
import com.eastsoft.android.esbic.jni.IpAddressInfo;
import com.eastsoft.android.esbic.service.IModelService;

/**
 * Created by sofa on 2016/1/25.
 */
public class CallManagementCenterActivity extends BaseActivity implements View.OnClickListener {
    private Button hangUp;
    private Chronometer timer,timerConversation;
    private ImageButton back;
    private TextView back2, centerAddr;
    private IModelService modelService;
    private String centerAddress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.call_management_center);
        initData();

    }
    private void initData(){
        timer=(Chronometer) this.findViewById(R.id.timer);
        timerConversation=(Chronometer)this.findViewById(R.id.timer_conversation);
        hangUp=(Button)this.findViewById(R.id.call_center_management_hang_up);
        back=(ImageButton)this.findViewById(R.id.call_center_management_back);
        centerAddr = (TextView)this.findViewById(R.id.call_center_management_addr);
        hangUp.setOnClickListener(this);
        back.setOnClickListener(this);
        back2=(TextView) this.findViewById(R.id.call_center_management_back2);
        back2.setOnClickListener(this);
        timer.setBase(SystemClock.elapsedRealtime());
        timer.start();
        modelService = ((MyApplication)getApplication()).getModelService();
        IpAddressInfo ipAddressInfo = modelService.getIpAddressInfo();
        centerAddress = ipAddressInfo.getCenterAddress();
        centerAddr.setText(centerAddress);
        modelService.call_center_manager(centerAddress);
    }

    @Override
    public void onClick(View view) {
        if (view.getId()==hangUp.getId() || view.getId()==back.getId() || view.getId()==back2.getId())
        {
            playButtonMusic(musicButtonId);
            modelService.active_hang_up();
            this.finish();
        }
    }
}

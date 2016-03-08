package com.eastsoft.android.esbic.ativity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.eastsoft.android.esbic.R;

import org.w3c.dom.Text;

/**
 * Created by Mr Wang on 2016/2/17.
 */
public class SettingDeviceInfoActivity extends BaseActivity implements View.OnClickListener {
    private TextView sn,mac,plcMac,softVersion,hardWareVersion;
    private ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.device_information);
        sn=(TextView)this.findViewById(R.id.sn);
        mac=(TextView)this.findViewById(R.id.mac);
        softVersion=(TextView)this.findViewById(R.id.software_version);
        hardWareVersion=(TextView)this.findViewById(R.id.hardware_version);
        plcMac=(TextView)this.findViewById(R.id.plc_mac);
        back=(ImageButton)this.findViewById(R.id.device_information_back);
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId()==back.getId()){
            this.onDestroy();
        }
    }
}

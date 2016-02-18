package com.eastsoft.android.esbic.ativity;

import android.os.Bundle;
import android.widget.TextView;

import com.eastsoft.android.esbic.R;

import org.w3c.dom.Text;

/**
 * Created by Mr Wang on 2016/2/17.
 */
public class SettingProjectQueryActivity extends BaseActivity {
    private TextView deviceAddress,ip,subnetMask,gateway,server,centerManagement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.project_query);
        initData();
    }
    private void initData(){
        deviceAddress=(TextView)this.findViewById(R.id.project_device_address);
        ip=(TextView)this.findViewById(R.id.project_ip);
        subnetMask=(TextView)this.findViewById(R.id.project_subnet_mask);
        gateway=(TextView)this.findViewById(R.id.project_gateway);
        server=(TextView)this.findViewById(R.id.project_server);
        centerManagement=(TextView)this.findViewById(R.id.project_center_management);
    }
}

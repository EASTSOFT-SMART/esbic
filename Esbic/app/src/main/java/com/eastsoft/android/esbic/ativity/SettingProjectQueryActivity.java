package com.eastsoft.android.esbic.ativity;

import android.os.Bundle;
import android.text.TextPaint;
import android.widget.TextView;

import com.eastsoft.android.esbic.R;

import org.w3c.dom.Text;

/**
 * Created by Mr Wang on 2016/2/17.
 */
public class SettingProjectQueryActivity extends BaseActivity {
    private TextView deviceAddress,ip,subnetMask,gateway,server,centerManagement;
    private TextView deviceAddressName,ipName,subnetMaskName,gatewayName,serverName,centerManagementName;
    private TextView[] textViews;
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
        deviceAddressName=(TextView)this.findViewById(R.id.device_addr);
        ipName=(TextView)this.findViewById(R.id.ip);
        subnetMaskName=(TextView)this.findViewById(R.id.subnet_mask);
        gatewayName=(TextView)this.findViewById(R.id.gateway);
        serverName=(TextView)this.findViewById(R.id.server);
        centerManagement=(TextView)this.findViewById(R.id.center_management);
        textViews=new TextView[]{deviceAddress,ip,subnetMask,gateway,server,centerManagement,
                deviceAddressName,ipName,subnetMaskName,gatewayName,serverName,centerManagementName};
        initTextViewStyle(textViews);
    }

    private void initTextViewStyle(TextView[] textViews){
          for (int i=0;i<textViews.length;i++){
              TextPaint tp=textViews[i].getPaint();
              tp.setFakeBoldText(true);
          }
    }
}

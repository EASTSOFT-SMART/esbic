package com.eastsoft.android.esbic.ativity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.eastsoft.android.esbic.R;
import com.eastsoft.android.esbic.jni.DeviceInfo;
import com.eastsoft.android.esbic.jni.IpAddressInfo;
import com.eastsoft.android.esbic.service.IModelService;
import com.eastsoft.android.esbic.service.ModelServiceImpl;

import org.w3c.dom.Text;

/**
 * Created by Mr Wang on 2016/2/17.
 */
public class SettingProjectQueryActivity extends BaseActivity implements View.OnClickListener{
    private TextView deviceAddress,ip,subnetMask,gateway,server,centerManagement;
    private ImageButton back;
    private TextView back2;

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

        back = (ImageButton)this.findViewById(R.id.project_query_back);
        back.setOnClickListener(this);
        back2 = (TextView)this.findViewById(R.id.project_query_back2);
        back2.setOnClickListener(this);

        IModelService modelService = ((MyApplication)getApplication()).getModelService();
        DeviceInfo deviceInfo = modelService.getDeviceInfo();
        if(deviceInfo != null)
        {
            deviceAddress.setText(deviceInfo.getBuilding_no()+"楼"+deviceInfo.getUnit_no()+"单元"+deviceInfo.getLayer_no()+"层"+deviceInfo.getRoom_no()+"房");
        }else{
            deviceAddress.setText("设备地址尚未设置");
        }
        IpAddressInfo ipAddressInfo = modelService.getIpAddressInfo();
        if(ipAddressInfo != null)
        {
            ip.setText(ipAddressInfo.getIp());
            subnetMask.setText(ipAddressInfo.getSubnetMask());
            gateway.setText(ipAddressInfo.getGateway());
            server.setText(ipAddressInfo.getImpAdress());
            centerManagement.setText(ipAddressInfo.getCenterAddress());
        }else
        {
            ip.setText("IP地址尚未设置");
            subnetMask.setText("子网掩码尚未设置");
            gateway.setText("网关尚未设置");
            server.setText("服务器地址尚未设置");
            centerManagement.setText("中心管理机尚未设置");
        }

    }

    @Override
    public void onClick(View view)
    {
        if (view.getId() == back.getId() || view.getId() == back2.getId())
        {
            playButtonMusic(musicButtonId);
            finish();
        }
    }
}

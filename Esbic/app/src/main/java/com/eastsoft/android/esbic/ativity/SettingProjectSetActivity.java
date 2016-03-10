package com.eastsoft.android.esbic.ativity;

import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.eastsoft.android.esbic.R;
import com.eastsoft.android.esbic.adapter.InputKeyBoardAdapter;
import com.eastsoft.android.esbic.jni.DeviceInfo;
import com.eastsoft.android.esbic.jni.DeviceTypeEnum;
import com.eastsoft.android.esbic.jni.IpAddressInfo;
import com.eastsoft.android.esbic.util.LogUtil;

import java.util.List;
import java.util.Map;

/**
 * Created by Mr Wang on 2016/2/17.
 */
public class SettingProjectSetActivity extends BaseActivity implements View.OnFocusChangeListener, View.OnClickListener, AdapterView.OnItemClickListener {
    private EditText deviceAddress, ip, subnetMask, gateway, impServerAddr, centerManagementAddr;
    private EditText currentEditText;
    private TextView back2;
    private ImageButton back;
    private GridView inputKeyBoard;
    private int position=0;
    private int[] icon;
    private List<Map<String,Object>> mapList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.project_setting);
        back=(ImageButton)this.findViewById(R.id.project_setting_back);
        back.setOnClickListener(this);
        back2=(TextView)this.findViewById(R.id.project_setting_back2);
        back2.setOnClickListener(this);
        inputKeyBoard=(GridView)this.findViewById(R.id.project_setting_input_keyboard);
        icon=new int[]{R.drawable.num_delete,R.drawable.button_icon};
        InputKeyBoardAdapter inputKeyBoardAdapter=new InputKeyBoardAdapter(this,icon,"确认");
        inputKeyBoard.setAdapter(inputKeyBoardAdapter);
        inputKeyBoard.setOnItemClickListener(this);

        deviceAddress=(EditText)this.findViewById(R.id.project_setting_device_address);
        deviceAddress.setOnFocusChangeListener(this);
        ip=(EditText)this.findViewById(R.id.project_setting_ip);
        ip.setOnFocusChangeListener(this);
        subnetMask=(EditText)this.findViewById(R.id.project_setting_subnet_mask);
        subnetMask.setOnFocusChangeListener(this);
        gateway=(EditText)this.findViewById(R.id.project_setting_gateway);
        gateway.setOnFocusChangeListener(this);
        impServerAddr =(EditText)this.findViewById(R.id.project_setting_imp_server);
        impServerAddr.setOnFocusChangeListener(this);
        centerManagementAddr =(EditText)this.findViewById(R.id.project_setting_center_management);
        centerManagementAddr.setOnFocusChangeListener(this);

        currentEditText = deviceAddress;
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

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        textHowToShow(i);
    }

    //设置TextView从左往右显示
    public void textHowToShow(int position){
        if(position<9 || position==10){

            String tmp = position==10?"0":(String.valueOf(position+1));
            Editable edit = currentEditText.getEditableText();
            int index = currentEditText.getSelectionStart();
            edit.insert(index, tmp);

            edit = currentEditText.getEditableText();
            int len = edit.length();
            if(currentEditText == deviceAddress)
            {
                switch(len)
                {
                    case 2 : currentEditText.append("楼");break;
                    case 5 : currentEditText.append("单元");break;
                    case 9 : currentEditText.append("层");break;
                    case 12 : currentEditText.append("房间");break;
                    default: break;
                }
            }else
            {
                switch(len)
                {
                    case 3 : currentEditText.append(".");break;
                    case 7 : currentEditText.append(".");break;
                    case 11 : currentEditText.append(".");break;
                    default: break;
                }
            }
        }else if (position==9){
            String addr = deviceAddress.getText().toString();
            if(addr.length() != 14)
            {
                showLongToast("设备地址错误，请重新设置！");
            }
            DeviceInfo deviceInfo = new DeviceInfo(DeviceTypeEnum.DT_ROOM_MACHINE.getType(),
                    Integer.parseInt(addr.substring(0,2)),
                    (byte)Integer.parseInt(addr.substring(3,5)),
                    (byte)Integer.parseInt(addr.substring(7,9)),
                    (byte)Integer.parseInt(addr.substring(10,12)),1);
            String ipStr = ip.getText().toString();
            if(ipStr.length() !=0 && ipStr.length() != 15)
            {
                LogUtil.print("IP 地址【"+ipStr+"】" + ipStr.length());
                showLongToast("IP地址错误，请重新设置！");
            }
            String subnetMaskStr = subnetMask.getText().toString();
            if(subnetMaskStr.length() !=0 && subnetMaskStr.length() != 15)
            {
                showLongToast("子网掩码错误，请重新设置！");
            }
            String gatewayStr = gateway.getText().toString();
            if(gatewayStr.length() !=0 && gatewayStr.length() != 15)
            {
                showLongToast("网关地址错误，请重新设置！");
            }
            String imp = impServerAddr.getText().toString();
            if(imp.length() !=0 && imp.length() != 15)
            {
                showLongToast("服务器地址错误，请重新设置！");
            }
            String center = centerManagementAddr.getText().toString();
            if(center.length() !=0 && center.length() != 15)
            {
                showLongToast("中心管理机地址错误，请重新设置！");
            }
            IpAddressInfo ipAddressInfo = new IpAddressInfo(ipStr, subnetMaskStr, gatewayStr, imp, center);
            ((MyApplication)getApplication()).getModelService().setDeviceInfo(deviceInfo);
            ((MyApplication)getApplication()).getModelService().setIpAddressInfo(ipAddressInfo);
        }else if (position==11)
        {
            int index = currentEditText.getSelectionStart();
            String str = currentEditText.getText().toString();
            if (!str.equals(""))
            {
                currentEditText.getText().delete(index - 1, index);
            }
        }
    }

    @Override
    public void onFocusChange(View view, boolean hasFocus)
    {
        if(hasFocus == false)
        {
            return;
        }
        if (view.getId() == R.id.project_setting_device_address)
        {
            currentEditText = deviceAddress;
        }
        else if (view.getId() == R.id.project_setting_ip)
        {
            currentEditText = ip;
        }
        else if (view.getId() == R.id.project_setting_subnet_mask)
        {
            currentEditText = subnetMask;
        }
        else if (view.getId() == R.id.project_setting_gateway)
        {
            currentEditText = gateway;
        }
        else if (view.getId() == R.id.project_setting_imp_server)
        {
            currentEditText = impServerAddr;
        }
        else if (view.getId() == R.id.project_setting_center_management)
        {
            currentEditText = centerManagementAddr;
        }
    }
}

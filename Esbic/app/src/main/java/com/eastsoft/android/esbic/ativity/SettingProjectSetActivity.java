package com.eastsoft.android.esbic.ativity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.eastsoft.android.esbic.R;
import com.eastsoft.android.esbic.jni.DeviceInfo;
import com.eastsoft.android.esbic.jni.DeviceTypeEnum;
import com.eastsoft.android.esbic.jni.IpAddressInfo;
import com.eastsoft.android.esbic.jni.JniUtil;
import com.eastsoft.android.esbic.service.IModelService;
import com.eastsoft.android.esbic.util.LogUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Mr Wang on 2016/2/17.
 */
public class SettingProjectSetActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private EditText buildingNo,unitNo,layerNo,roomNo;
    private EditText ipOne,ipTwo,ipThree,ipFour;
    private EditText subnetMaskOne,subnetMaskTwo,subnetMaskThree,subnetMaskFour;
    private EditText gateWayOne,gateWayTwo,gateWayThree,gateWayFour;
    private EditText serverOne,serverTwo,serverThree,serverFour;
    private EditText centerManagementOne,centerManagementTwo,centerManagementTree,centerManagementFour;
    private ImageButton back;
    private GridView inputKeyBoard;
    private int position=0;
    private int[] icon;
    private List<Map<String,Object>> mapList;
    private IModelService modelService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.project_setting);
        back=(ImageButton)this.findViewById(R.id.project_setting_back);
        back.setOnClickListener(this);
        inputKeyBoard=(GridView)this.findViewById(R.id.project_setting_input_keyboard);
        icon=new int[]{R.drawable.input_keyboard_zero_button};
        mapList=new ArrayList<Map<String, Object>>();
        for (int i = 0; i < 12; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("numButton", icon[0]);
            mapList.add(map);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, mapList, R.layout.input_keyboard_item, new String[]
                {"numButton"}, new int[]{R.id.keyboard_child});
        inputKeyBoard.setAdapter(simpleAdapter);
        inputKeyBoard.setOnItemClickListener(this);
        buildingNo = (EditText)this.findViewById(R.id.project_set_buildingNo);
        unitNo = (EditText)this.findViewById(R.id.project_set_unitNo);
        layerNo = (EditText)this.findViewById(R.id.project_set_layerNo);
        roomNo = (EditText)this.findViewById(R.id.project_set_roomNo);
        ipOne = (EditText)this.findViewById(R.id.project_set_ipOne);
        ipTwo = (EditText)this.findViewById(R.id.project_set_ipTwo);
        ipThree = (EditText)this.findViewById(R.id.project_set_ipThree);
        ipFour = (EditText)this.findViewById(R.id.project_set_ipFour);
        subnetMaskOne = (EditText)this.findViewById(R.id.project_set_subOne);
        subnetMaskTwo = (EditText)this.findViewById(R.id.project_set_subTwo);
        subnetMaskThree = (EditText)this.findViewById(R.id.project_set_subThree);
        subnetMaskFour = (EditText)this.findViewById(R.id.project_set_subFour);
        gateWayOne = (EditText)this.findViewById(R.id.project_set_gatewayOne);
        gateWayTwo = (EditText)this.findViewById(R.id.project_set_gatewayTwo);
        gateWayThree = (EditText)this.findViewById(R.id.project_set_gatewayThree);
        gateWayFour = (EditText)this.findViewById(R.id.project_set_gatewayFour);
        serverOne = (EditText)this.findViewById(R.id.project_set_impOne);
        serverTwo = (EditText)this.findViewById(R.id.project_set_impTwo);
        serverThree = (EditText)this.findViewById(R.id.project_set_impThree);
        serverFour = (EditText)this.findViewById(R.id.project_set_impFour);
        centerManagementOne = (EditText)this.findViewById(R.id.project_set_centerOne);
        centerManagementTwo = (EditText)this.findViewById(R.id.project_set_centerTwo);
        centerManagementTree = (EditText)this.findViewById(R.id.project_set_centerThree);
        centerManagementFour = (EditText)this.findViewById(R.id.project_set_centerFour);

        modelService = ((MyApplication)getApplication()).getModelService();
        DeviceInfo deviceInfo = modelService.getDeviceInfo();
        if(deviceInfo != null)
        {
            buildingNo.setText(deviceInfo.getBuilding_no() +"");
            unitNo.setText(deviceInfo.getUnit_no() +"");
            layerNo.setText(deviceInfo.getLayer_no() +"");
            roomNo.setText(deviceInfo.getRoom_no() +"");
        }
        IpAddressInfo ipAddressInfo = modelService.getIpAddressInfo();
        if(ipAddressInfo != null)
        {
            String[] ips = ipAddressInfo.getIp().split("\\.");
            ipOne.setText(ips[0]);
            ipTwo.setText(ips[1]);
            ipThree.setText(ips[2]);
            ipFour.setText(ips[3]);
            String[] subnetMasks = ipAddressInfo.getSubnetMask().split("\\.");
            subnetMaskOne.setText(subnetMasks[0]);
            subnetMaskTwo.setText(subnetMasks[1]);
            subnetMaskThree.setText(subnetMasks[2]);
            subnetMaskFour.setText(subnetMasks[3]);
            String[] gateways = ipAddressInfo.getGateway().split("\\.");
            gateWayOne.setText(gateways[0]);
            gateWayTwo.setText(gateways[1]);
            gateWayThree.setText(gateways[2]);
            gateWayFour.setText(gateways[3]);
            String[] imps = ipAddressInfo.getImpAdress().split("\\.");
            serverOne.setText(imps[0]);
            serverTwo.setText(imps[1]);
            serverThree.setText(imps[2]);
            serverFour.setText(imps[3]);
            String[] centers = ipAddressInfo.getCenterAddress().split("\\.");
            centerManagementOne.setText(centers[0]);
            centerManagementTwo.setText(centers[1]);
            centerManagementTree.setText(centers[2]);
            centerManagementFour.setText(centers[3]);
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.project_setting_back) {
            DeviceInfo newDeviceInfo = new DeviceInfo(DeviceTypeEnum.DT_ROOM_MACHINE.getType(),
                    Integer.parseInt(buildingNo.getText().toString().trim()),
                    (byte)Integer.parseInt(unitNo.getText().toString().trim()),
                    (byte)Integer.parseInt(layerNo.getText().toString().trim()),
                    (byte)Integer.parseInt(roomNo.getText().toString().trim()),1);
            modelService.setDeviceInfo(newDeviceInfo);
            String ip = fromatAddress(ipOne, ipTwo, ipThree, ipFour);
            String subnetMask = fromatAddress(subnetMaskOne, subnetMaskTwo, subnetMaskThree, subnetMaskFour);
            String gateway = fromatAddress(gateWayOne, gateWayTwo, gateWayThree, gateWayFour);
            String imp = fromatAddress(serverOne, serverTwo, serverThree, serverFour);
            String center = fromatAddress(centerManagementOne, centerManagementTwo, centerManagementTree, centerManagementFour);
            IpAddressInfo ipAddressInfo = new IpAddressInfo(ip, subnetMask, gateway, imp, center);
            modelService.setIpAddressInfo(ipAddressInfo);
            finish();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    private String fromatAddress(EditText one,EditText two,EditText three,EditText four)
    {
        return one.getText().toString().trim()+"."
             + two.getText().toString().trim()+"."
             + three.getText().toString().trim()+"."
             + four.getText().toString().trim();
    }
}

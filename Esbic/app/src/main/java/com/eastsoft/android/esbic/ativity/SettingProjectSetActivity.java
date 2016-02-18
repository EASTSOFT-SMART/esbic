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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Mr Wang on 2016/2/17.
 */
public class SettingProjectSetActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    private EditText deviceAddressOne,deviceAddressTwo,deviceAddressThree,deviceAddressFour;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.project_setting);
        back=(ImageButton)this.findViewById(R.id.project_setting_back);
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
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}

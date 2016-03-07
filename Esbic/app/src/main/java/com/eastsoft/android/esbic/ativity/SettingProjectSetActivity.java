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
import com.eastsoft.android.esbic.adapter.InputKeyBoardAdapter;

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
        icon=new int[]{R.drawable.num_delete,R.drawable.button_icon};
        InputKeyBoardAdapter inputKeyBoardAdapter=new InputKeyBoardAdapter(this,icon);
        inputKeyBoard.setAdapter(inputKeyBoardAdapter);
        inputKeyBoard.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}

package com.eastsoft.android.esbic.ativity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.eastsoft.android.esbic.R;

/**
 * Created by sofa on 2016/1/27.
 */
public class SettingActivity extends BaseActivity implements View.OnClickListener {
    private Intent intent;
    private Button security,userPassword,date,recover,projectQuery,
            projectSetting,projectPassword,deviceInfo;
    private Button[] buttonArray;
    private ImageButton settingBack;
    private TextView settingBack2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);
        initData();
    }
    private void initData(){
        settingBack=(ImageButton) this.findViewById(R.id.setting_back);
        settingBack2=(TextView) this.findViewById(R.id.setting_back2);
        security=(Button)this.findViewById(R.id.security_setting);
        userPassword=(Button)this.findViewById(R.id.user_password);
        date=(Button)this.findViewById(R.id.date_setting);
        recover=(Button)this.findViewById(R.id.recover);
        projectQuery=(Button)this.findViewById(R.id.project_query);
        projectSetting=(Button)this.findViewById(R.id.project_setting);
        projectPassword=(Button)this.findViewById(R.id.project_password);
        deviceInfo=(Button)this.findViewById(R.id.device_information);
        settingBack.setOnClickListener(this);
        settingBack2.setOnClickListener(this);
        buttonArray=new Button[]{
                security,userPassword,date,recover,projectQuery,
                projectSetting,projectPassword,deviceInfo
        };
        initOnclickListener(buttonArray);
    }
    //循环设置控件的监听
    private void initOnclickListener(Button[] buttons){
        for (int i=0;i<buttons.length;i++){
            buttons[i].setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.setting_back || view.getId()==R.id.setting_back2){
            playButtonMusic(musicButtonId);
            finish();
        }
        if (view.getId()==security.getId()){
            intent=getIntents();
            intent.setClass(SettingActivity.this,SettingSecurityNeedPwdActivity.class);
            startActivity(intent);
        }
        if (view.getId()==userPassword.getId()){
            intent=getIntents();
            intent.setClass(SettingActivity.this,SecurityAlterUserPwdActivity.class);
            startActivity(intent);
        }
        if (view.getId()==date.getId()){
            intent=getIntents();
            intent.setClass(SettingActivity.this,SettingDateActivity.class);
            startActivity(intent);
        }
        if (view.getId()==recover.getId()){
            intent=getIntents();
            intent.setClass(SettingActivity.this,SettingRecoveryFactoryActivity.class);
            startActivity(intent);
        }
        if (view.getId()==projectQuery.getId()){
            intent=getIntents();
            intent.setClass(SettingActivity.this,SettingProjectQueryActivity.class);
            startActivity(intent);
        }
        if (view.getId()==projectSetting.getId()){
            intent=getIntents();
            intent.setClass(SettingActivity.this,SettingProjectSetActivity.class);
            startActivity(intent);
        }
        if (view.getId()==projectPassword.getId()){
            intent=getIntents();
            intent.setClass(SettingActivity.this,SettingProjectPassWordActivity.class);
            startActivity(intent);
        }
        if (view.getId()==deviceInfo.getId()){
            intent=getIntents();
            intent.setClass(SettingActivity.this,SettingDeviceInfoActivity.class);
            startActivity(intent);
        }

    }
}

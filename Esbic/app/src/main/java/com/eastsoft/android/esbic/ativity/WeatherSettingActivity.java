package com.eastsoft.android.esbic.ativity;

import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.eastsoft.android.esbic.R;

/**
 * Created by Mr Wang on 2016/3/10.
 */
public class WeatherSettingActivity extends BaseActivity implements AdapterView.OnItemClickListener,View.OnClickListener {

    private LinearLayout weather_setting_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weater_setting);
        init();
    }

    private void init()
    {
        weather_setting_back = (LinearLayout)findViewById(R.id.weather_setting_back);
        weather_setting_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        playMusic();
        if(view.getId() == weather_setting_back.getId())
        {
            finish();
        }

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}

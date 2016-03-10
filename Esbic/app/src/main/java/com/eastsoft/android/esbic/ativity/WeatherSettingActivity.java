package com.eastsoft.android.esbic.ativity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.eastsoft.android.esbic.R;

/**
 * Created by Mr Wang on 2016/3/10.
 */
public class WeatherSettingActivity extends BaseActivity implements AdapterView.OnItemClickListener,View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weater_setting);
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}

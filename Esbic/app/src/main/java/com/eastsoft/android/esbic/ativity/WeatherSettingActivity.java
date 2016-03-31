package com.eastsoft.android.esbic.ativity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.eastsoft.android.esbic.R;
import com.eastsoft.android.esbic.adapter.CityAdapter;
import com.eastsoft.android.esbic.service.IModelService;
import com.eastsoft.android.esbic.table.ParaInfo;
import com.eastsoft.android.esbic.util.CityUtil;
import com.eastsoft.android.esbic.util.LogUtil;
import com.eastsoft.android.esbic.weather.WeatherEnum;
import com.eastsoft.android.esbic.weather.WeatherUtil;
import com.eastsoft.android.esbic.weather.WeatherInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mr Wang on 2016/3/10.
 */
public class WeatherSettingActivity extends BaseActivity implements AdapterView.OnItemClickListener,View.OnClickListener {

    private TextView city_name, weater_content, temp, wind_status;
    private ImageView weater_setting_icon;
    private LinearLayout weather_setting_back;
    private ListView province, city;
    private CityAdapter proAdaptor, cityAdaptor;
    private List<String> proList, cityList;
    private boolean[] proStatus;
    private int index;
    private String currentPro, currentCity;
    private IModelService modelService;

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
        city_name = (TextView)findViewById(R.id.city_setting_name);
        weater_content = (TextView)findViewById(R.id.weater_content);
        temp = (TextView)findViewById(R.id.temp);
        wind_status = (TextView)findViewById(R.id.wind_status);
        weater_setting_icon = (ImageView)findViewById(R.id.weater_setting_icon);

        province = (ListView)findViewById(R.id.pro_list);
        city = (ListView)findViewById(R.id.city_list);
        province.setOnItemClickListener(this);
        city.setOnItemClickListener(this);

        CityUtil.getInstance().setContext(getApplicationContext());
        proList = CityUtil.getInstance().getProvince();
        proStatus = new boolean[proList.size()];
        currentPro = proList.get(0);
        index = 0;
        proStatus[index] = true;
        proAdaptor = new CityAdapter(proList, proStatus, this);
        province.setAdapter(proAdaptor);
        cityList = CityUtil.getInstance().getCity(currentPro);
        cityAdaptor = new CityAdapter(cityList, new boolean[100],this);
        city.setAdapter(cityAdaptor);

        modelService = ((MyApplication)getApplication()).getModelService();
        String city = "青岛";
        ParaInfo paraInfo = modelService.getParaInfoByName("city");
        if(paraInfo!= null && paraInfo.getValue().compareTo("")!=0)
        {
            city = paraInfo.getValue();
        }
        try {
            WeatherInfo info = WeatherUtil.getWeather(city);
            city_name.setText(city);
            weater_content.setText(info.description);
            temp.setText(info.lowTemperate+"～"+ info.hightTemperate + "℃");
            wind_status.setText(info.wind);
            weater_setting_icon.setBackgroundResource(WeatherEnum.find(info.description).icon);
        }catch(Exception e)
        {
            LogUtil.print("获取天气失败");
        }
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
        switch (adapterView.getId())
        {
            case R.id.pro_list:
                currentPro = proList.get(i);
                cityList.clear();
                ArrayList<String> tmp = CityUtil.getInstance().getCity(currentPro);
                cityList.addAll(tmp);
                cityAdaptor.notifyDataSetChanged();
                proStatus[index] = false;
                index = i;
                proStatus[index] = true;
                proAdaptor.notifyDataSetChanged();
                break;
            case R.id.city_list:
                currentCity = cityList.get(i);
                city_name.setText(currentCity);
                weater_content.setText("loading...");
                temp.setText("loading...");
                wind_status.setText("loading...");
                WeatherInfo info = WeatherUtil.getWeather(currentCity);
                if(info == null)
                {
                    weater_content.setText("获取失败");
                    temp.setText("获取失败");
                    wind_status.setText("获取失败");
                    break;
                }
                weater_content.setText(info.description);
                temp.setText(info.lowTemperate+"～"+ info.hightTemperate + "℃");
                wind_status.setText(info.wind);
                weater_setting_icon.setBackgroundResource(WeatherEnum.find(info.description).icon);
                ParaInfo paraInfo = new ParaInfo("city", currentCity);
                modelService.saveParaInfo(paraInfo);
                sendBroadcast();
                showLongToast("天气设置成功！");
                break;
            default:
                break;
        }
    }

    private void sendBroadcast()
    {
        Intent intent = new Intent();
        intent.putExtra("cmd", 1);
        intent.setAction("com.eastsoft.android.esbic.app");
        getApplicationContext().sendBroadcast(intent);
    }
}

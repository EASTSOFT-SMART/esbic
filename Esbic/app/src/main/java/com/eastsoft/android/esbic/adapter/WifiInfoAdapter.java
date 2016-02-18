package com.eastsoft.android.esbic.adapter;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.eastsoft.android.esbic.R;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by Mr Wang on 2016/2/14.
 */
public class WifiInfoAdapter extends BaseAdapter {
    private List<ScanResult> scanResultList;
    private List<WifiConfiguration> wifiConfigurationList;
    private Context context;
    private LayoutInflater inflater;


    public WifiInfoAdapter(List<ScanResult> scanResultList, Context context,
                           List<WifiConfiguration> wifiConfigurationList){
        this.scanResultList=new ArrayList<ScanResult>();
        this.wifiConfigurationList=new ArrayList<WifiConfiguration>();
        this.wifiConfigurationList=wifiConfigurationList;
        this.scanResultList=scanResultList;
        this.context=context;
        inflater=LayoutInflater.from(context);
    }



    @Override
    public int getCount() {
        return scanResultList.size();
    }

    @Override
    public Object getItem(int i) {
        return scanResultList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View view1=null;
        view1=inflater.inflate(R.layout.wifi_setting_item,null);
        ScanResult scanResult=scanResultList.get(i);
        TextView wifiName=(TextView)view1.findViewById(R.id.wifi_list_name);
        ImageView isLock=(ImageView)view1.findViewById(R.id.wifi_list_lock);
        ImageView wifiIntensity=(ImageView)view1.findViewById(R.id.wifi_list_intensity);
        wifiName.setText(scanResult.SSID);
        String lockState=scanResult.capabilities;
        if (lockState.contains("WPA")||lockState.contains("wpa")||
                lockState.contains("WEP") || lockState.contains("wep")){
        isLock.setVisibility(View.VISIBLE);
        }else{
        isLock.setVisibility(View.INVISIBLE);
        }
        return view1;

    }


}

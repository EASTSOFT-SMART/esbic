package com.eastsoft.android.esbic.adapter;

import android.content.Context;
import android.graphics.Color;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.text.TextPaint;
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
    private boolean[] wifiState;


    public WifiInfoAdapter(List<ScanResult> scanResultList, Context context,
                           List<WifiConfiguration> wifiConfigurationList,boolean[] wifiState){
        this.scanResultList=new ArrayList<ScanResult>();
        this.wifiConfigurationList=new ArrayList<WifiConfiguration>();
        this.wifiConfigurationList=wifiConfigurationList;
        this.scanResultList=scanResultList;
        this.context=context;
        inflater=LayoutInflater.from(context);
        this.wifiState=wifiState;

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
        View view1=inflater.inflate(R.layout.wifi_setting_item,null);
        ScanResult scanResult=scanResultList.get(i);
        TextView wifiName=(TextView)view1.findViewById(R.id.wifi_list_name);
        TextPaint tp=wifiName.getPaint();
        tp.setFakeBoldText(true);
        ImageView isLock=(ImageView)view1.findViewById(R.id.wifi_lock);
        ViewAttribute viewAttribute=new ViewAttribute();
        viewAttribute.wifistate=(TextView)view1.findViewById(R.id.wifi_state);
        wifiName.setText(scanResult.SSID);
        if (wifiState[i]){
            viewAttribute.wifistate.setText("已连接");
            wifiName.setTextColor(Color.rgb(0, 0, 0));
            viewAttribute.wifistate.setTextColor(Color.rgb(0, 0, 0));
        }else{
            viewAttribute.wifistate.setText("未连接");
        }
        String lockState=scanResult.capabilities;
        if (lockState.contains("WPA")||lockState.contains("wpa")||
                lockState.contains("WEP") || lockState.contains("wep")){
        isLock.setBackgroundResource(R.drawable.wifi_icon_lock);
        }else{
        isLock.setBackgroundResource(R.drawable.wifi_icon);
        }
        return view1;
    }


    class ViewAttribute{
       TextView wifistate;
    }

}

package com.eastsoft.android.esbic.util;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import java.util.List;

/**
 * Created by sofa on 2016/1/29.
 */
public class WifiScan {
    //定义一个WifiManagement对象
    private WifiManager wifiManager;
    //定义一个WifiInfo对象
    private WifiInfo wifiInfo;
    //扫描出的网络连接列表
    private List<ScanResult> mWifiList;
    //网络连接列表
    private List<WifiConfiguration> mWifiConfiguration;
    WifiManager.WifiLock wifiLock;

    public WifiScan(Context context){
        //取得WifiManagement对象
        wifiManager=(WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        //取得WifiInfo对象
        wifiInfo=wifiManager.getConnectionInfo();
    }
    //打开wifi
    public void openWifi(){
        if (!wifiManager.isWifiEnabled()){
            wifiManager.setWifiEnabled(true);
        }
    }
    //关闭wifi
    public void closeWifi(){
        if (wifiManager.isWifiEnabled()){
            wifiManager.setWifiEnabled(false);
        }
    }
    //检查当前wifi状态
    public int checkState(){
        return wifiManager.getWifiState();
    }
    //锁定wifiLock
    public void acquireWifiLock(){
        wifiLock.acquire();
    }
    //解锁wifiLock
    public void releaseWifiLock(){
        //判断是否锁定
        if (wifiLock.isHeld()){
            wifiLock.acquire();
        }
    }
    //创建一个wifiLock
    public void  createWifiLock(){
        wifiLock=wifiManager.createWifiLock("wifi");
    }
    //得到配置好的网络
    public List<WifiConfiguration> getConfiguration(){
        return mWifiConfiguration;
    }
    //指定配置好的网络进行连接
    public void connetionConfiguration(int index){
        if (index>mWifiConfiguration.size()){
            return;
        }
        //连接配置好指定ID的网络
        wifiManager.enableNetwork(mWifiConfiguration.get(index).networkId,true);
    }
    //扫描Wifi网络
    public void startScan(){
        wifiManager.startScan();
        //得到扫描结果
        mWifiList=wifiManager.getScanResults();
        //得到配置好的网络
        mWifiConfiguration=wifiManager.getConfiguredNetworks();
    }
    //得到网络列表
    public List<ScanResult> getmWifiList(){
        return mWifiList;
    }

    //查看扫描结果
    public StringBuffer lookUpScan(){
        StringBuffer sb=new StringBuffer();
        for (int i=0;i<mWifiList.size();i++){
            sb.append("Index_"+new Integer(i+1).toString()+":");
            //将ScanResult信息转换成一个字符串包
            //其中包括：BSSID,SSID,capabilities,frequency.level
            sb.append((mWifiList.get(i).toString())).append("\n");
        }
        return sb;
    }
    //断开指定ID的网络
    public void disConnectionWifi(int netId){
        wifiManager.disableNetwork(netId);
        wifiManager.disconnect();
    }
    //添加一个网络并连接
    public void addNetWork(WifiConfiguration configuration){
        int wcgId=wifiManager.addNetwork(configuration);
        wifiManager.enableNetwork(wcgId,true);
    }


    //判定制定WIFI是否已经配置好，依据WIFI的地址BSSID，返回NetId
    public int getNetWordId(String SSID){
        Log.v("IsConfiguration",String.valueOf(mWifiConfiguration.size()));
        for (int i=0;i<mWifiConfiguration.size();i++){
            Log.v(mWifiConfiguration.get(i).SSID+"的网络ID",String.valueOf(mWifiConfiguration.get(i).networkId));
            if (mWifiConfiguration.get(i).SSID.equals(SSID)){
                return mWifiConfiguration.get(i).networkId;
            }
        }
        return -1;
    }

    //连接指定某个id的Wifi
    public int  connectWifi(int wifiId){
        for(int i = 0; i < mWifiConfiguration.size(); i++){
            WifiConfiguration wifi =mWifiConfiguration.get(i);
            if(wifi.networkId == wifiId){
                if(wifiManager.enableNetwork(wifiId, true)){//激活该Id，建立连接
                    //status:0--已经连接，1--不可连接，2--可以连接
                    mWifiConfiguration = wifiManager.getConfiguredNetworks();
                }
                return mWifiConfiguration.get(wifiId).status;
            }
        }
        return mWifiConfiguration.get(wifiId).status;
    }


    //添加指定WIFI的配置信息,原列表不存在此SSID
    public int AddWifiConfig(List<ScanResult> wifiList,String ssid,String pwd){
        int wifiId = -1;
        for(int i = 0;i < wifiList.size(); i++){
            ScanResult wifi = wifiList.get(i);
            if(wifi.SSID.equals(ssid)){
                Log.i("AddWifiConfig","equals");
                WifiConfiguration wifiCong = new WifiConfiguration();
                wifiCong.SSID = "\""+wifi.SSID+"\"";//\"转义字符，代表"
                wifiCong.preSharedKey = "\""+pwd+"\"";//WPA-PSK密码
                wifiCong.hiddenSSID = false;
                wifiCong.status = WifiConfiguration.Status.ENABLED;
                wifiId =wifiManager.addNetwork(wifiCong);//将配置好的特定WIFI密码信息添加,添加完成后默认是不激活状态，成功返回ID，否则为-1
                wifiManager.saveConfiguration();        // 修改wifi配置后（添加或删除），必须调用此函数保存当前配置信息，否则此时断电的话，设置的信息是不会保存的
                if(wifiId!= -1)
                {
                    return wifiId;
                }
            }
        }
        return wifiId;
    }

    //重新获取WifiConfiguration
    public void reGetConfiguration(){
        mWifiConfiguration = wifiManager.getConfiguredNetworks();//得到配置好的网络信息
    }

    //得到WifiInfo的所有信息
    public WifiInfo getWifiInfo(){
        wifiInfo=wifiManager.getConnectionInfo();
        return wifiInfo;
    }
    //得到连接的ID
    public int getNetWorkId(){
        return (wifiInfo==null)?0:wifiInfo.getNetworkId();
    }

    public String getMacAddress(){
        return (wifiInfo==null)?"NULL":wifiInfo.getMacAddress();
    }

    public String getBSSID(){
        return (wifiInfo==null)?"NULL":wifiInfo.getBSSID();
    }

    public int getIpAddress(){
        return (wifiInfo==null)?0:wifiInfo.getIpAddress();
    }
}

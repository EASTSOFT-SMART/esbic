package com.eastsoft.android.esbic.ativity;

import android.app.Dialog;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.eastsoft.android.esbic.R;
import com.eastsoft.android.esbic.adapter.WifiInfoAdapter;
import com.eastsoft.android.esbic.dialog.MyDialog;
import com.eastsoft.android.esbic.util.WifiScan;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sofa on 2016/1/27.
 */
public class WifiSettingActivity extends BaseActivity implements View.OnClickListener,AdapterView.OnItemClickListener,
        AdapterView.OnItemSelectedListener{
    private ImageButton back,scanWifi;
    private TextView wifiName,wifiStatus;
    private ImageView lock,wifiIntensity;
    private ListView listView;
    private Intent intent;
    private WifiScan wifiScan;
    private int wifiState;
    private  String wifiSSID;
    private EditText editText;
    private String wifiPassword;
    private WifiInfoAdapter wifiInfoAdapter;
    //扫描结果列表
    private List<ScanResult> scanResultList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wifi_setting);
        initWifiConfig();
        initData();
    }
    private void initData(){
        back=(ImageButton)this.findViewById(R.id.wifi_setting_back);
        wifiName=(TextView)this.findViewById(R.id.wifi_name);
        wifiIntensity=(ImageView) this.findViewById(R.id.wifi_intensity);
        wifiStatus=(TextView)this.findViewById(R.id.wifi_status);
        lock=(ImageView)this.findViewById(R.id.add_lock);
        listView=(ListView)this.findViewById(R.id.wifi_list);
        listView.setOnItemClickListener(this);
        scanWifi=(ImageButton)this.findViewById(R.id.rescan_wifi);
        back.setOnClickListener(this);
        scanWifi.setOnClickListener(this);
        if (scanResultList!=null&&scanResultList.size()>=0){
            wifiInfoAdapter=new WifiInfoAdapter(scanResultList,this,null);
        }else{
            showShortToast("获取wifi信息失败，请重试！");
        }

        listView.setAdapter(wifiInfoAdapter);
    }

    private void initWifiConfig(){
        wifiScan=new WifiScan(this);
        wifiScan.openWifi();
        wifiScan.startScan();
        //0正在关闭，1WIFI不可用，2正在打开，3可用，4状态不可知。
        if (wifiScan.checkState()==0&&wifiScan.checkState()==1&&
                wifiScan.checkState()==4){
            showShortToast("Wifi不可用");
        }else if (wifiScan.checkState()==2){
            showShortToast("wifi正在打开!");
            try {
                Thread.sleep(1000);
                Log.v("Wifi正在打开","让主线程等待1秒钟");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (wifiScan.checkState()==3){
            //每次都新的创建一个List,用来清除上一次List储存的ScanResult数据。
            scanResultList=new ArrayList<ScanResult>();
            scanResultList=wifiScan.getmWifiList();
            showShortToast("wifi已经打开!");
        }


    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Object wifiInformation=wifiInfoAdapter.getItem(i);
        wifiSSID=((ScanResult)wifiInformation).SSID;
        int netWordId=wifiScan.getNetWordId("\""+wifiSSID+"\"");
        Log.v("获取到的某个Wifi的SSID",String.valueOf(netWordId));
        if (netWordId!=-1){
          int status=wifiScan.connectWifi(netWordId);
            if (status==0){
                wifiName.setText(wifiSSID);
                wifiStatus.setText("已连接");
            }else if (status==1){
                showShortToast("此网络不可以连接!");
            }else{
                wifiName.setText(wifiSSID);
                wifiStatus.setText("可以连接");
            }
        }else {
            showInputWifiPasswordDialog();
        }

    }

    @Override
    public void onClick(View view) {
        if (view.getId()==back.getId()){
            this.finish();
        }

    }

    private void showInputWifiPasswordDialog(){
        MyDialog myDialog=new MyDialog(WifiSettingActivity.this);
        //获取Dialog实例
        final Dialog dialog=myDialog.getDialog();
        //获取Dialog中需要包含的xml文件
        View view=myDialog.getDialogView(R.layout.wifi_setting_dialog);
        //把这个view放入dialog中去
        dialog.setContentView(view);
        //使用Window类来设置Dialog显示时的各种属性
        Window dialogWindow=dialog.getWindow();
        //获取WindowManager.LayoutParams内部类实例
        WindowManager.LayoutParams lp=dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL);
        lp.width=ViewGroup.LayoutParams.WRAP_CONTENT;
        lp.height=ViewGroup.LayoutParams.WRAP_CONTENT;
        dialogWindow.setAttributes(lp);
        ImageButton showPassword=(ImageButton)view.findViewById(R.id.show_password);
        Button cancel=(Button)view.findViewById(R.id.wifi_setting_cancel);
        editText=(EditText) view.findViewById(R.id.input_password);
        Button connect=(Button)view.findViewById(R.id.wifi_setting_connect);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wifiPassword=editText.getText().toString();
                if (!wifiPassword.equals("")){
                    int netId =wifiScan.AddWifiConfig(scanResultList,wifiSSID,wifiPassword);
                    if (netId!=-1){
                        wifiScan.reGetConfiguration();
                        int status=wifiScan.connectWifi(netId);
                        if (status==0){
                            wifiName.setText(wifiSSID);
                            wifiStatus.setText("已连接");
                        }else if (status==1){
                            showShortToast("未连接");
                        }else{
                            showShortToast("此网络不可以连接!");
                        }
                    }else{

                    }
                }else{
                    showShortToast("请输入密码!");
                }
            }
        });

        dialog.show();



    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Object wifiInformation=wifiInfoAdapter.getItem(i);
        wifiSSID=((ScanResult)wifiInformation).SSID;
        int netWordId=wifiScan.getNetWordId("\""+wifiSSID+"\"");
        Log.v("获取到的某个Wifi的SSID",String.valueOf(netWordId));
        if (netWordId!=-1){
            int status=wifiScan.connectWifi(netWordId);
            if (status==0){
                wifiName.setText(wifiSSID);
                wifiStatus.setText("已连接");
            }else if (status==1){
                showShortToast("未连接");
            }else{
                showShortToast("此网络不可以连接!");
            }
        }else {
            showInputWifiPasswordDialog();
        }

    }


    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}

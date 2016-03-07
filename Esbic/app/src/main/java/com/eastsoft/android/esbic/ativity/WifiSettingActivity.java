package com.eastsoft.android.esbic.ativity;

import android.app.Dialog;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.text.InputType;
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
import com.eastsoft.android.esbic.view.MySlipButton;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sofa on 2016/1/27.
 */
public class WifiSettingActivity extends BaseActivity implements View.OnClickListener,AdapterView.OnItemClickListener,
        AdapterView.OnItemSelectedListener{
    private ImageButton back,scanWifi;
    private MySlipButton mySlipButton;
    private ListView listView;
    private Intent intent;
    private int state;
    private WifiScan wifiScan;
    private boolean[] wifiState;
    private  String wifiSSID;
    private EditText editText;
    private String wifiPassword;
    private int position;
    private WifiInfoAdapter wifiInfoAdapter;
    private boolean isHidden=true;
    //扫描结果列表
    private List<ScanResult> scanResultList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wifi_setting);
        initData();
    }
    private void initData(){
        mySlipButton=(MySlipButton)this.findViewById(R.id.wifi_switch);
        back=(ImageButton)this.findViewById(R.id.wifi_setting_back);
        listView=(ListView)this.findViewById(R.id.wifi_list);
        listView.setOnItemClickListener(this);
        scanWifi=(ImageButton)this.findViewById(R.id.scan_wifi);
        back.setOnClickListener(this);
        scanWifi.setOnClickListener(this);
        wifiScan=new WifiScan(this);
        initWifiConfig();
        state=wifiScan.checkState();
        if (state==1||state==0||state==2||state==4){
            mySlipButton.setChecked(false);
        }else if (state==3){
            mySlipButton.setChecked(true);
        }
        mySlipButton.setOnChangedListener(new MySlipButton.OnChangedListener() {
            @Override
            public void OnChanged(MySlipButton wiperSwitch, boolean checkState) {
                if (checkState){
                    initWifiConfig();
                }else{
                    wifiScan.closeWifi();
                }
            }
        });
    }

    private void initWifiConfig(){
        wifiScan.openWifi();
        wifiScan.startScan();
        try {
            Thread.sleep(2000);
            Log.v("Wifi正在打开","让主线程等待1秒钟");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //0正在关闭，1WIFI不可用，2正在打开，3可用，4状态不可知。
        if (wifiScan.checkState()==0&&wifiScan.checkState()==1&&
                wifiScan.checkState()==4){
            showShortToast("Wifi不可用");
        }else if (wifiScan.checkState()==2){
            showShortToast("wifi正在打开!");

        }
        if (wifiScan.checkState()==3){
            //每次都新的创建一个List,用来清除上一次List储存的ScanResult数据。
            scanResultList=new ArrayList<ScanResult>();
            scanResultList=wifiScan.getmWifiList();
            wifiState=new boolean[scanResultList.size()];
            for(int i=0;i<wifiState.length;i++){
                wifiState[i]=false;
                if (scanResultList!=null&&scanResultList.size()>=0){
                    wifiInfoAdapter=new WifiInfoAdapter(scanResultList,this,null,wifiState);
                }else{
                    scanResultList=new ArrayList<ScanResult>();
                    wifiInfoAdapter=new WifiInfoAdapter(scanResultList,this,null,wifiState);
                    showShortToast("数据获取中请稍等！");
                }
                listView.setAdapter(wifiInfoAdapter);
            }
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
                this.wifiState[i]=true;
                wifiInfoAdapter.notifyDataSetChanged();
            }else if (status==1){
                showShortToast("此网络不可以连接!");
            }else{
                this.wifiState[i]=false;
            }
        }else {
            showInputWifiPasswordDialog(i);
        }

    }

    @Override
    public void onClick(View view) {
        if (view.getId()==back.getId()){
            this.finish();
        }
        if (view.getId()==scanWifi.getId()){
            wifiScan.startScan();
            scanResultList=wifiScan.getmWifiList();
            wifiInfoAdapter.notifyDataSetChanged();
        }

    }

    private void showInputWifiPasswordDialog(int i){
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
        position=i;
        ImageButton showPassword=(ImageButton)view.findViewById(R.id.show_password);
        Button cancel=(Button)view.findViewById(R.id.wifi_setting_cancel);
        editText=(EditText) view.findViewById(R.id.input_password);
        Button connect=(Button)view.findViewById(R.id.wifi_setting_connect);
        showPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isHidden){
                    editText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }else{
                    editText.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                isHidden=!isHidden;
            }
        });
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
                           wifiState[WifiSettingActivity.this.position]=true;
                            WifiSettingActivity.this.wifiInfoAdapter.notifyDataSetChanged();
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

    }


    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}

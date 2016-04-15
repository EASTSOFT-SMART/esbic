package com.eastsoft.android.esbic.ativity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
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
import android.view.inputmethod.InputMethodManager;
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
public class WifiSettingActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener,
        AdapterView.OnItemSelectedListener
{
    private ImageButton back, scanWifi;
    private TextView back2, scanWifi2;
    private MySlipButton mySlipButton;
    private ListView listView;
    private Intent intent;
    private int state;
    private WifiScan wifiScan;
    private boolean[] wifiState = new boolean[100];
    private String wifiSSID;
    private EditText editText;
    private String wifiPassword;
    private int position;
    private WifiInfoAdapter wifiInfoAdapter;
    private boolean isHidden = true;
    //扫描结果列表
    private List<ScanResult> scanResultList = new ArrayList<>();
    private List<WifiConfiguration> mWifiConfiguration = new ArrayList<>();
    private WifiInfo wifiInfo;
    private int currentIndex;
    private boolean isReopen;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wifi_setting);
        mySlipButton = (MySlipButton) this.findViewById(R.id.wifi_switch);
        listView = (ListView) this.findViewById(R.id.wifi_list);
        listView.setOnItemClickListener(this);
        scanWifi = (ImageButton) this.findViewById(R.id.scan_wifi);
        scanWifi2 = (TextView) this.findViewById(R.id.scan_wifi2);
        back = (ImageButton) this.findViewById(R.id.wifi_setting_back);
        back.setOnClickListener(this);
        back2 = (TextView) this.findViewById(R.id.wifi_setting_back2);
        back2.setOnClickListener(this);
        scanWifi.setOnClickListener(this);
        scanWifi2.setOnClickListener(this);
        initData();
    }

    private void initData()
    {
        wifiScan = new WifiScan(this);
        wifiInfoAdapter = new WifiInfoAdapter(scanResultList, this, null, wifiState);
        listView.setAdapter(wifiInfoAdapter);
        initWifiConfig();

        state = wifiScan.checkState();
        if (state == 1 || state == 0 || state == 2 || state == 4)
        {
            mySlipButton.setChecked(false);
        } else if (state == 3)
        {
            mySlipButton.setChecked(true);
        }
        mySlipButton.setOnChangedListener(new MySlipButton.OnChangedListener()
        {
            @Override
            public void OnChanged(MySlipButton wiperSwitch, boolean checkState)
            {
                if (checkState)
                {
                    isReopen = true;
                    initWifiConfig();
                } else
                {
                    scanResultList.clear();
                    wifiInfoAdapter.notifyDataSetChanged();
                    wifiScan.closeWifi();
                }
            }
        });
    }

    private void initWifiConfig()
    {
        wifiScan.openWifi();
        if(isReopen)
        {
            try
            {
                new Thread().sleep(4000);
            }catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        wifiScan.startScan();
        //0正在关闭，1WIFI不可用，2正在打开，3可用，4状态不可知。
        if (wifiScan.checkState() == 0 && wifiScan.checkState() == 1 &&
                wifiScan.checkState() == 4)
        {
            showShortToast("Wifi不可用");
        } else if (wifiScan.checkState() == 2)
        {
            showShortToast("wifi正在打开 。。。");
        }
        while(wifiScan.checkState() != 3)
        {
            showShortToast("wifi正在打开 。。。");
        }
        if (wifiScan.checkState() == 3)
        {
            List<ScanResult> tmp = wifiScan.getmWifiList();
            scanResultList.clear();
            scanResultList.addAll(tmp);
            mWifiConfiguration = wifiScan.getConfiguration();
            wifiInfo = wifiScan.getWifiInfo();
            for (int i = 0; i < scanResultList.size(); i++)
            {
                wifiState[i] = false;
                String str = scanResultList.get(i).BSSID;
                if (wifiInfo != null && str.compareTo(wifiInfo.getBSSID()) == 0)
                {
                    currentIndex = i;
                    wifiState[i] = true;
                }
            }
            wifiInfoAdapter.notifyDataSetChanged();
            showLongToast("WIFI开启成功！");
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
    {
        if(i == currentIndex)
        {
            showShortToast("已连接此无线！");
            return;
        }
        Object wifiInformation = wifiInfoAdapter.getItem(i);
        wifiSSID = ((ScanResult) wifiInformation).SSID;
        int netWordId = wifiScan.getNetWordId("\"" + wifiSSID + "\"");
        Log.v("获取到的某个Wifi的SSID", String.valueOf(netWordId));
        if (netWordId != -1)
        {
            showShortToast("正在连接 "+wifiSSID +" ...");
            int status = wifiScan.connectWifi(netWordId);
            if (status == 0 || status == 2)
            {
                showShortToast("连接成功！");
                wifiState[currentIndex] = false;
                currentIndex = i;
                wifiState[currentIndex] = true;
                wifiInfoAdapter.notifyDataSetChanged();
            } else if (status == 1)
            {
                showShortToast("此网络不可以连接!");
            }
        } else
        {
            showInputWifiPasswordDialog(i);
        }

    }

    @Override
    public void onClick(View view)
    {
        playMusic();
        if (view.getId() == back.getId() || view.getId() == back2.getId())
        {
            this.finish();
        }
        if (view.getId() == scanWifi.getId() || view.getId() == scanWifi2.getId())
        {
            wifiScan.startScan();
            List<ScanResult> tmp = wifiScan.getmWifiList();
            scanResultList.clear();
            scanResultList.addAll(tmp);
            wifiInfo = wifiScan.getWifiInfo();
            for (int i = 0; i < scanResultList.size(); i++)
            {
                String str = scanResultList.get(i).BSSID;
                if (wifiInfo != null && str.compareTo(wifiInfo.getBSSID()) == 0)
                {
                    wifiState[currentIndex] = false;
                    currentIndex = i;
                    wifiState[currentIndex] = true;
                    break;
                }
            }
            wifiInfoAdapter.notifyDataSetChanged();
        }
    }

    private void showInputWifiPasswordDialog(int i)
    {
        MyDialog myDialog = new MyDialog(WifiSettingActivity.this);
        //获取Dialog实例
        final Dialog dialog = myDialog.getDialog();
        //获取Dialog中需要包含的xml文件
        View view = myDialog.getDialogView(R.layout.wifi_setting_dialog);
        //把这个view放入dialog中去
        dialog.setContentView(view);
        //使用Window类来设置Dialog显示时的各种属性
        Window dialogWindow = dialog.getWindow();
        //获取WindowManager.LayoutParams内部类实例
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
        lp.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        dialogWindow.setAttributes(lp);
        position = i;
        ImageButton showPassword = (ImageButton) view.findViewById(R.id.show_wifi_password);
        editText = (EditText) view.findViewById(R.id.input_password);
        Button cancel = (Button) view.findViewById(R.id.wifi_setting_cancel);
        Button connect = (Button) view.findViewById(R.id.wifi_setting_connect);
        showPassword.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (isHidden)
                {
                    editText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else
                {
                    editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                isHidden = !isHidden;
            }
        });
        cancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                closeDialog(dialog, view);
            }
        });
        connect.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                wifiPassword = editText.getText().toString();
                if (!wifiPassword.equals(""))
                {
                    int netId = wifiScan.AddWifiConfig(scanResultList, wifiSSID, wifiPassword);
                    if (netId != -1)
                    {
                        showShortToast("正在连接 "+wifiSSID +" ...");
                        wifiScan.reGetConfiguration();
                        int status = wifiScan.connectWifi(netId);
                        if (status == 0 || status == 2)
                        {
                            showShortToast("连接成功！");
                            wifiState[currentIndex] = false;
                            currentIndex = WifiSettingActivity.this.position;
                            wifiState[currentIndex] = true;
                            WifiSettingActivity.this.wifiInfoAdapter.notifyDataSetChanged();
                            closeDialog(dialog, view);
                        } else if (status == 1)
                        {
                            showShortToast("连接失败！");
                        }
                    }else
                    {
                        showLongToast("请检查密码是否正确！");
                    }
                } else
                {
                    showShortToast("密码不能为空!");
                }
            }
        });
        dialog.show();
    }

    private void closeDialog(Dialog dialog, View view)
    {
        dialog.dismiss();
        // 隐藏输入法软键盘
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
        ((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(WifiSettingActivity.this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
    {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView)
    {

    }
}

package com.eastsoft.android.esbic.ativity;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.eastsoft.android.esbic.R;
import com.eastsoft.android.esbic.jni.DeviceInfo;
import com.eastsoft.android.esbic.jni.DeviceTypeEnum;
import com.eastsoft.android.esbic.jni.IpAddressInfo;
import com.eastsoft.android.esbic.jni.JniUtil;
import com.eastsoft.android.esbic.service.BroadcastTypeEnum;
import com.eastsoft.android.esbic.service.IModelService;
import com.eastsoft.android.esbic.service.ModelServiceImpl;
import com.eastsoft.android.esbic.table.AlarmInfo;
import com.eastsoft.android.esbic.table.MessageInfo;
import com.eastsoft.android.esbic.util.JsonUtil;
import com.eastsoft.android.esbic.util.QueryWeatherInformation;
import com.eastsoft.android.esbic.util.TimeUtil;
import com.eastsoft.android.esbic.util.Weather;
import com.eastsoft.android.esbic.util.WeatherIcon;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by sofa on 2016/1/22.
 */
public class MainActivity extends BaseActivity implements View.OnClickListener {
    private Button message,callRecord,alarmRecord,voice,screenBrightness,wifi,
            leaveHome,callManagement,monitor,callOtherUser,setting;
    private TextView weather,week,yearMonthDay;
    private ImageView weatherIcon,hourFront,hourAfter,timeIcon,minuteFront,minuterAfter;
    private Dialog progressDialog;
    private String cityName;
    private Handler handler;
    private Intent intent;
    private Date now;
    private TextClock clock;
    private String time;
    public  Weather weathers;
    private SimpleDateFormat simpleDateFormat;
    private QueryWeatherInformation queryWeather;
    private IModelService modelService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        initData();
        new Thread(new QueryWeather()).start();
        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if (msg.what==1){
                    if (weathers!=null){
                        weather.setText(weathers.getStatus());
                        initWeatherIcon();
                    }
                }
            }
    };
      //initClock();
       // while (true){
       //     try {
        //        Thread.sleep(30000);
        //        handler=new Handler(){
        //            @Override
        //            public void handleMessage(Message msg) {
        //                if (msg.what==1){
        //                    setMinute(time);
        //                    setHour(time);
        //                }
        //            }
         //       };
         //   } catch (InterruptedException e) {
         //       e.printStackTrace();
         //   }

        //}

   }

    private void  initData(){
        message=(Button)this.findViewById(R.id.message);
        callRecord=(Button)this.findViewById(R.id.call_record);
        alarmRecord=(Button)this.findViewById(R.id.alarm_record);
        voice=(Button)this.findViewById(R.id.volume);
        screenBrightness=(Button)this.findViewById(R.id.brightness);
        wifi=(Button)this.findViewById(R.id.wifi);
        leaveHome=(Button)this.findViewById(R.id.leave_home);
        callManagement=(Button)this.findViewById(R.id.call_center_management);
        monitor=(Button)this.findViewById(R.id.monitor_main);
        callOtherUser=(Button)this.findViewById(R.id.call_other_user);
        setting=(Button)this.findViewById(R.id.set);
        yearMonthDay=(TextView) this.findViewById(R.id.year_mouth_day);
        week=(TextView)this.findViewById(R.id.week);
        message.setOnClickListener(this);
        weather=(TextView)this.findViewById(R.id.weather);
        weatherIcon=(ImageView)this.findViewById(R.id.weather_icon);
        //clock=(TextClock)this.findViewById(R.id.main_time);
        //clock.setFormat24Hour("hh:mm");
        callRecord.setOnClickListener(this);
        alarmRecord.setOnClickListener(this);
        voice.setOnClickListener(this);
        screenBrightness.setOnClickListener(this);
        wifi.setOnClickListener(this);
        leaveHome.setOnClickListener(this);
        callManagement.setOnClickListener(this);
        monitor.setOnClickListener(this);
        setting.setOnClickListener(this);
        callOtherUser.setOnClickListener(this);
        //progressDialog=new AlertDialog.Builder(this).setTitle("数据读取中").
        //        setMessage("正在读取数据").create();
        intent=getIntents();
        simpleDateFormat=new SimpleDateFormat("yyyy年MM月dd日");
        now =new Date();
        yearMonthDay.setText(simpleDateFormat.format(now));
        week.setText(new SimpleDateFormat("E").format(now));

        MyApplication myApplication = (MyApplication) getApplication();
        myApplication.setModelService(new ModelServiceImpl(getApplicationContext()));

        MyReceiver myReceiver = new MyReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.eastsoft.android.esbic.model");
        registerReceiver(myReceiver, intentFilter);
        modelService = ((MyApplication)getApplication()).getModelService();
        DeviceInfo deviceInfo = modelService.getDeviceInfo();
        if(deviceInfo != null)
        {
            modelService.init_intercom_core(deviceInfo);
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId()==message.getId()){
            playMusic();
            intent.setClass(MainActivity.this,MessageContentActivity.class);
            startActivity(intent);
        }
        if (view.getId()==callRecord.getId()){
            playMusic();
            intent.setClass(MainActivity.this,CallRecordActivity.class);
            startActivity(intent);
        }
        if(view.getId()==alarmRecord.getId()){
            playMusic();
            intent.setClass(MainActivity.this,AlarmRecordActivity.class);
            startActivity(intent);
        }
        if(view.getId()==voice.getId()){
            playMusic();
            intent.setClass(MainActivity.this,VolumeActivity.class);
            startActivity(intent);
        }
        if(view.getId()==screenBrightness.getId()){
            playMusic();
            intent.setClass(MainActivity.this,ScreenLightActivity.class);
            startActivity(intent);
        }
        if(view.getId()==wifi.getId()){
            playMusic();
            intent.setClass(MainActivity.this,WifiSettingActivity.class);
            startActivity(intent);
        }

        if (view.getId()==leaveHome.getId()){
            playMusic();
            intent.setClass(MainActivity.this,LeaveHome.class);
            startActivity(intent);
        }
        if (view.getId()==callManagement.getId()){
            playMusic();
            IpAddressInfo ipAddressInfo = modelService.getIpAddressInfo();
            if(ipAddressInfo == null || ipAddressInfo.getCenterAddress() == null)
            {
                showLongToast("请先设置中心管理机的地址！");
            }else
            {
                intent.setClass(MainActivity.this,CallManagementCenterActivity.class);
                startActivity(intent);
            }
        }
        if (view.getId()==monitor.getId()){
            playMusic();
            intent.setClass(MainActivity.this,MonitorActivity.class);
            startActivity(intent);
        }
        if (view.getId()==setting.getId()){
            playMusic();
            intent.setClass(MainActivity.this,SettingActivity.class);
            startActivity(intent);
        }
        if (view.getId()==callOtherUser.getId()){
            playMusic();
            intent.setClass(MainActivity.this,CallMain.class);
            startActivity(intent);
        }
    }

  //查询天气
    private void getWeatherInformation(){

        queryWeather=new QueryWeatherInformation();
        weathers=queryWeather.getXml();

    }
    //非空判断
    private void checkCityName(String cityName){
        if (null==cityName||cityName.equals("")){
            new AlertDialog.Builder(this).setTitle("输入不正确").
                    setMessage("城市名不能为空").setPositiveButton("确定",null).show();
            return;
        }
    }
    //初始化天气图标
    private void initWeatherIcon(){
        if(weathers.getStatus().equals(WeatherIcon.CLOUDY)){
            weatherIcon.setBackgroundResource(R.drawable.weather_9);
        }else if (weathers.getStatus().equals(WeatherIcon.HEAVYRAIN)){
            weatherIcon.setBackgroundResource(R.drawable.weather_4);
        }else if (weathers.getStatus().equals(WeatherIcon.HEAVYSNOW)){
            weatherIcon.setBackgroundResource(R.drawable.weather_3);
        }else if (weathers.getStatus().equals(WeatherIcon.LIGHTRAIN)){
            weatherIcon.setBackgroundResource(R.drawable.weather_10);
        }else if (weathers.getStatus().equals(WeatherIcon.LIGHTSNOW)){
            weatherIcon.setBackgroundResource(R.drawable.weather_12);
        }else if (weathers.getStatus().equals(WeatherIcon.MIST)){
            weatherIcon.setBackgroundResource(R.drawable.weather_1);
        }else if (weathers.getStatus().equals(WeatherIcon.MISTANDSNOW)){
            weatherIcon.setBackgroundResource(R.drawable.weather_2);
        }else if (weathers.getStatus().equals(WeatherIcon.MODERATERAIN)){
            weatherIcon.setBackgroundResource(R.drawable.weather_11);
        }else if (weathers.getStatus().equals(WeatherIcon.MODERATESNOW)){
            weatherIcon.setBackgroundResource(R.drawable.weather_6);
        }else if (weathers.getStatus().equals(WeatherIcon.SHADE)){
            weatherIcon.setBackgroundResource(R.drawable.weather_9);
        }else if (weathers.getStatus().equals(WeatherIcon.SUNNY)){
            weatherIcon.setBackgroundResource(R.drawable.weather_8);
        }else if (weathers.getStatus().equals(WeatherIcon.THUNDERRAIN)){
            weatherIcon.setBackgroundResource(R.drawable.weather_7);
        }else if (weathers.getStatus().equals(WeatherIcon.THUNDERSHOWER)){
            weatherIcon.setBackgroundResource(R.drawable.weather_13);
        }else{
            weatherIcon.setBackgroundResource(R.drawable.weather_8);
        }
    }
   class QueryWeather implements Runnable{

       @Override
       public void run() {
           Message message=new Message();
           getWeatherInformation();
           Bundle bundle=new Bundle();
           bundle.putBoolean("1",false);
           message.setData(bundle);
           message.what=1;
           handler.sendMessageDelayed(message,200);
       }
   }

    private void setMinute(String time){
        if ((String.valueOf(time.charAt(3)).equals("0"))){
            minuterAfter.setBackgroundResource(R.drawable.num_0);
        }
        if ((String.valueOf(time.charAt(3)).equals("1"))){
            minuterAfter.setBackgroundResource(R.drawable.num_1);
        }
        if ((String.valueOf(time.charAt(3)).equals("2"))){
            minuterAfter.setBackgroundResource(R.drawable.num_2);
        }
        if ((String.valueOf(time.charAt(3)).equals("3"))){
            minuterAfter.setBackgroundResource(R.drawable.num_3);
        }
        if ((String.valueOf(time.charAt(3)).equals("4"))){
            minuterAfter.setBackgroundResource(R.drawable.num_4);
        }
        if ((String.valueOf(time.charAt(3)).equals("5"))){
            minuterAfter.setBackgroundResource(R.drawable.num_5);
        }
        if ((String.valueOf(time.charAt(3)).equals("6"))){
            minuterAfter.setBackgroundResource(R.drawable.num_6);
        }
        if ((String.valueOf(time.charAt(3)).equals("7"))){
            minuterAfter.setBackgroundResource(R.drawable.num_7);
        }
        if ((String.valueOf(time.charAt(3)).equals("8"))){
            minuterAfter.setBackgroundResource(R.drawable.num_8);
        }
        if ((String.valueOf(time.charAt(3)).equals("9"))){
            minuterAfter.setBackgroundResource(R.drawable.num_9);
        }

        if ((String.valueOf(time.charAt(2)).equals("0"))){
            minuteFront.setBackgroundResource(R.drawable.num_0);
        }
        if ((String.valueOf(time.charAt(2)).equals("1"))){
            minuteFront.setBackgroundResource(R.drawable.num_1);
        }
        if ((String.valueOf(time.charAt(2)).equals("2"))){
            minuteFront.setBackgroundResource(R.drawable.num_2);
        }
        if ((String.valueOf(time.charAt(2)).equals("3"))){
            minuteFront.setBackgroundResource(R.drawable.num_3);
        }
        if ((String.valueOf(time.charAt(2)).equals("4"))){
            minuteFront.setBackgroundResource(R.drawable.num_4);
        }
        if ((String.valueOf(time.charAt(2)).equals("5"))){
            minuteFront.setBackgroundResource(R.drawable.num_5);
        }
        if ((String.valueOf(time.charAt(2)).equals("6"))){
            minuteFront.setBackgroundResource(R.drawable.num_6);
        }
        if ((String.valueOf(time.charAt(2)).equals("7"))){
            minuteFront.setBackgroundResource(R.drawable.num_7);
        }
        if ((String.valueOf(time.charAt(2)).equals("8"))){
            minuteFront.setBackgroundResource(R.drawable.num_8);
        }
        if ((String.valueOf(time.charAt(2)).equals("9"))){
            minuteFront.setBackgroundResource(R.drawable.num_9);
        }

    }

    private void setHour(String time){
        if ((String.valueOf(time.charAt(0)).equals("0"))){
            hourFront.setBackgroundResource(R.drawable.num_0);
        }
        if ((String.valueOf(time.charAt(0)).equals("1"))){
            hourFront.setBackgroundResource(R.drawable.num_1);
        }
        if ((String.valueOf(time.charAt(0)).equals("2"))){
            hourFront.setBackgroundResource(R.drawable.num_2);
        }
        if ((String.valueOf(time.charAt(0)).equals("3"))){
            hourFront.setBackgroundResource(R.drawable.num_3);
        }
        if ((String.valueOf(time.charAt(0)).equals("4"))){
            hourFront.setBackgroundResource(R.drawable.num_4);
        }
        if ((String.valueOf(time.charAt(0)).equals("5"))){
            hourFront.setBackgroundResource(R.drawable.num_5);
        }
        if ((String.valueOf(time.charAt(0)).equals("6"))){
            hourFront.setBackgroundResource(R.drawable.num_6);
        }
        if ((String.valueOf(time.charAt(0)).equals("7"))){
            hourFront.setBackgroundResource(R.drawable.num_7);
        }
        if ((String.valueOf(time.charAt(0)).equals("8"))){
            hourFront.setBackgroundResource(R.drawable.num_8);
        }
        if ((String.valueOf(time.charAt(0)).equals("9"))){
            hourFront.setBackgroundResource(R.drawable.num_9);
        }
        if ((String.valueOf(time.charAt(1)).equals("0"))){
            hourAfter.setBackgroundResource(R.drawable.num_0);
        }
        if ((String.valueOf(time.charAt(1)).equals("1"))){
            hourAfter.setBackgroundResource(R.drawable.num_1);
        }
        if ((String.valueOf(time.charAt(1)).equals("2"))){
            hourAfter.setBackgroundResource(R.drawable.num_2);
        }
        if ((String.valueOf(time.charAt(1)).equals("3"))){
            hourAfter.setBackgroundResource(R.drawable.num_3);
        }
        if ((String.valueOf(time.charAt(1)).equals("4"))){
            hourAfter.setBackgroundResource(R.drawable.num_4);
        }
        if ((String.valueOf(time.charAt(1)).equals("5"))){
            hourAfter.setBackgroundResource(R.drawable.num_5);
        }
        if ((String.valueOf(time.charAt(1)).equals("6"))){
            hourAfter.setBackgroundResource(R.drawable.num_6);
        }
        if ((String.valueOf(time.charAt(1)).equals("7"))){
            hourAfter.setBackgroundResource(R.drawable.num_7);
        }
        if ((String.valueOf(time.charAt(1)).equals("8"))){
            hourAfter.setBackgroundResource(R.drawable.num_8);
        }
        if ((String.valueOf(time.charAt(1)).equals("9"))){
            hourAfter.setBackgroundResource(R.drawable.num_9);
        }
    }
   private void initClock(){

     Thread thread=new Thread(new Runnable() {
         @Override
         public void run() {
             while(true){
                 Message message=new Message();
                 time=new SimpleDateFormat("HHmm").format(new Date());
                 Bundle bundle=new Bundle();
                 bundle.putBoolean("1",false);
                 message.setData(bundle);
                 message.what=1;
                 handler.sendMessageDelayed(message,60000);
             }
         }

     });
       thread.start();
   }

    public class MyReceiver extends BroadcastReceiver
    {
        @Override
        public void onReceive(Context context, Intent intent2)
        {
            Bundle bundle = intent2.getExtras();
            int cmd = bundle.getInt("cmd");
            BroadcastTypeEnum e = BroadcastTypeEnum.find(cmd);
            String value = bundle.getString("value");
            switch (e)
            {
                case CALL_REQUEST :
                    showLongToast("收到呼叫请求！" + value);
                    playMusic();
                    intent.setClass(MainActivity.this,ConversationActivity.class);
                    intent.putExtra("value", value);
                    startActivity(intent);
                    break;
                case PLAY_VIDEO :
                    showLongToast("视频url" + value);
                    break;
                case HANG_UP :
                    showLongToast("对方已挂断！");
                    break;
                case CALL_CONFIRM :
                    showLongToast("您呼叫的设备已经找到！");
                    break;
                case OPEN_LOCK_CONFIRM :
                    showLongToast("锁已开！");
                    break;
                case MONITOR_CONFIRM :
                    showLongToast("您监视的设备已经找到！");
                    break;
                case DEVICE_BUSY :
                    showLongToast("您呼叫的设备正在忙，请稍后再拨！");
                    break;
                case MONITOR_HANG_UP :
                    showLongToast("门口机挂断监视！");
                    break;
                case CALL_ANSWER_CONFIRM :
                    showLongToast("您呼叫的设备已接听！");
                    break;
                case RECEIVE_MESSAGE :
                    showLongToast("收到新的消息：" + value);
                    break;
                case RECEIVE_AD :
                    showLongToast("收到新的广告" + value);
                    break;
                case RECEIVE_ALARM :
                    showLongToast("收到新的警报" + value);
                    break;
                default:
                    break;
            }
        }
    }
}

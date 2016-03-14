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
import com.eastsoft.android.esbic.R;
import com.eastsoft.android.esbic.jni.DeviceInfo;
import com.eastsoft.android.esbic.jni.IpAddressInfo;
import com.eastsoft.android.esbic.jni.MessageInfoEnum;
import com.eastsoft.android.esbic.service.BroadcastTypeEnum;
import com.eastsoft.android.esbic.service.IModelService;
import com.eastsoft.android.esbic.service.ModelServiceImpl;
import com.eastsoft.android.esbic.table.AlarmInfo;
import com.eastsoft.android.esbic.table.MessageInfo;
import com.eastsoft.android.esbic.table.ParaInfo;
import com.eastsoft.android.esbic.util.BoardCastFilterInfo;
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
            leaveHome,callManagement,monitor,callOtherUser,setting,callElevator;
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
    private BoardCastFilterInfo boardCastFilterInfo;
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
                        weather.setText(weathers.getLowTemperature() +"～"+ weathers.getHighTemperature() + "℃   " + weathers.getStatus());
                        initWeatherIcon();
                    }
                }
            }
    };

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
        callElevator=(Button)this.findViewById(R.id.call_elevator);
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
        callElevator.setOnClickListener(this);
        callOtherUser.setOnClickListener(this);
        weatherIcon.setOnClickListener(this);
        //progressDialog=new AlertDialog.Builder(this).setTitle("数据读取中").
        //        setMessage("正在读取数据").create();
        intent=getIntents();
        simpleDateFormat=new SimpleDateFormat("yyyy年MM月dd日");
        now =new Date();
        yearMonthDay.setText(simpleDateFormat.format(now));
        week.setText(new SimpleDateFormat("E").format(now));
        boardCastFilterInfo=new BoardCastFilterInfo();

        ((MyApplication)getApplication()).setModelService(new ModelServiceImpl(getApplicationContext()));

        MyReceiver myReceiver = new MyReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.eastsoft.android.esbic.model");
        registerReceiver(myReceiver, intentFilter);
        modelService = ((MyApplication)getApplication()).getModelService();
        DeviceInfo deviceInfo = modelService.getDeviceInfo();
        if(deviceInfo != null)
        {
            modelService.init_intercom_core(deviceInfo);
            IpAddressInfo ipAddressInfo = modelService.getIpAddressInfo();
            if(ipAddressInfo != null && ipAddressInfo.getImpAdress().compareTo("") != 0)
            {
                modelService.init_imp_task(ipAddressInfo.getImpAdress());
            }
        }
//        MessageInfo info = new MessageInfo(MessageInfoEnum.MESSAGE.getType(), 0, "这是消息内容 " + TimeUtil.getDateTimeofNow2());
//        info.save();
//        AlarmInfo alarmInfo = new AlarmInfo(1);
//        alarmInfo.save();
//        alarmInfo = new AlarmInfo(4);
//        alarmInfo.save();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //注册自定义动态广播信息
        IntentFilter filter=new IntentFilter();
        filter.addAction(boardCastFilterInfo.ONCALLBYDOOR);
        registerReceiver(listenDoorDeviceCall,filter);

        IntentFilter filter1=new IntentFilter();
        filter1.addAction(Intent.ACTION_SCREEN_OFF);
        registerReceiver(listenScreenClose,filter1);
    }


    //使用BroadcastReceiver创建广播监听，监听来自门口机的呼叫等。
    protected BroadcastReceiver listenDoorDeviceCall=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(boardCastFilterInfo.ONCALLBYDOOR)){
                startOncallActivity(context);
            }
        }
    };
    //使用BroadcastReceiver创建广播监听，监听屏幕的关闭。
    protected BroadcastReceiver listenScreenClose=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Intent i=new Intent();
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.setClass(context,StandByActivity.class);
            context.startActivity(i);
        }
    };

    //启动被呼叫页面,来响应门口机的呼叫
    protected void startOncallActivity(Context context){
        intent=getIntents();
        intent.setClass(context,OnCallActivity.class);
        startActivity(intent);
    }



    @Override
    public void onClick(View view) {
        playMusic();

        if (view.getId()==weatherIcon.getId()){
            intent.setClass(MainActivity.this,WeatherSettingActivity.class);
            startActivity(intent);
        }

        if (view.getId()==message.getId()){
            intent.setClass(MainActivity.this,MessageContentActivity.class);
            startActivity(intent);
        }
        if (view.getId()==callRecord.getId()){
            intent.setClass(MainActivity.this,CallRecordActivity.class);
            startActivity(intent);

        }
        if(view.getId()==alarmRecord.getId()){
            intent.setClass(MainActivity.this,AlarmRecordActivity.class);
            startActivity(intent);
        }
        if(view.getId()==voice.getId()){
            intent.setClass(MainActivity.this,VolumeActivity.class);
            startActivity(intent);
        }
        if(view.getId()==screenBrightness.getId()){
            intent.setClass(MainActivity.this,ScreenLightActivity.class);
            startActivity(intent);
        }
        if(view.getId()==wifi.getId()){
            intent.setClass(MainActivity.this,WifiSettingActivity.class);
            startActivity(intent);
        }

        if (view.getId()==leaveHome.getId()){
            intent.setClass(MainActivity.this,StandByActivity.class);
            startActivity(intent);
        }
        if (view.getId()==callManagement.getId()){
            IpAddressInfo ipAddressInfo = modelService.getIpAddressInfo();
            if(ipAddressInfo == null || ipAddressInfo.getCenterAddress() == null || ipAddressInfo.getCenterAddress().compareTo("") == 0)
            {
                showLongToast("请先设置中心管理机的地址！");
                return;
            }
            intent.setClass(MainActivity.this,CallManagementCenterActivity.class);
            startActivity(intent);
        }
        if (view.getId()==monitor.getId()){
            intent.setClass(MainActivity.this,MonitorActivity.class);
            startActivity(intent);
        }
        if (view.getId()==setting.getId()){
            intent.setClass(MainActivity.this,SettingActivity.class);
            startActivity(intent);
        }
        if (view.getId()==callOtherUser.getId()){
            intent.setClass(MainActivity.this,CallMain.class);
            startActivity(intent);
        }
        if (view.getId()==callElevator.getId()){
//            intent.setClass(MainActivity.this,OnCallActivity.class);
//            startActivity(intent);
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
                    intent.setClass(MainActivity.this, OnCallActivity.class);
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
                    showLongToast("门已开！");
                    break;
                case DEVICE_BUSY :
                    showLongToast("您呼叫的设备正在忙，请稍后再拨！");
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

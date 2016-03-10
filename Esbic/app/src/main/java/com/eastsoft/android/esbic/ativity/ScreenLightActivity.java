package com.eastsoft.android.esbic.ativity;

import android.app.Activity;

import android.media.audiofx.EnvironmentalReverb;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.eastsoft.android.esbic.R;

/**
 * Created by sofa on 2016/1/26.
 */
public class ScreenLightActivity extends BaseActivity implements View.OnClickListener,SeekBar.OnSeekBarChangeListener {
    private ImageButton back,lightAdd,lightDecrease;
    private TextView back2;
    private SeekBar screenLightSeekBar;
    private int brightnessModel,brightness;
    private int postion=0;
    //private String seekBarPositionString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_light_setting);
        initData();

    }

    /**
     * 安卓亮度调节分为3个部分：安卓系统亮度调节，当前APP的亮度调节，当前页面亮度调节（Window）
     * 调节时请注意。
     */
    private void initData() {
        back=(ImageButton)this.findViewById(R.id.screen_light_back);
        back.setOnClickListener(this);
        back2=(TextView) this.findViewById(R.id.screen_light_back2);
        back2.setOnClickListener(this);
        lightAdd=(ImageButton)this.findViewById(R.id.screen_light_add);
        lightDecrease=(ImageButton)this.findViewById(R.id.screen_light_decrease);
        screenLightSeekBar=(SeekBar)this.findViewById(R.id.screen_light_seekbar);
        screenLightSeekBar.setMax(255);
        try {
            brightnessModel= Settings.System.getInt(getContentResolver(),Settings.System.SCREEN_BRIGHTNESS_MODE);
            if (brightnessModel==Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC){
               Settings.System.putInt(getContentResolver(),Settings.System.SCREEN_BRIGHTNESS_MODE,
                       Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
            }
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        /*
         *这只是修改当前页面的亮度
         */
        //WindowManager.LayoutParams layoutParams=getWindow().getAttributes();//获取Window属性
        //seekBarPositionString=String.valueOf(layoutParams.screenBrightness*10);
        //Log.v("当前亮度值*100后的值",seekBarPositionString);
        //seekBarNowPosition=Integer.valueOf(seekBarPositionString.substring(0,1));

        //获取系统当前亮度
        brightness=Settings.System.getInt(getContentResolver(),Settings.System.SCREEN_BRIGHTNESS,255);
        postion=brightness;
        screenLightSeekBar.setProgress(brightness);
        screenLightSeekBar.setOnSeekBarChangeListener(this);
        lightDecrease.setOnClickListener(this);
        lightAdd.setOnClickListener(this);
    }

     //获取系统亮度
      //private void setScreenBrightness(float num){
      //    WindowManager.LayoutParams layoutParams=getWindow().getAttributes();//获取window属性
      //    layoutParams.screenBrightness=num;//num已经除以100
      //  super.getWindow().setAttributes(layoutParams);//0-1之间
      //}

    @Override
    public void onClick(View view) {
        if (view.getId()==back.getId() || view.getId()==back2.getId()){
            playButtonMusic(musicButtonId);
            ScreenLightActivity.this.finish();
        }
        if (view.getId()==lightDecrease.getId()){
            playButtonMusic(musicButtonId);
            postion-=25;
            if (postion>0){
                screenLightSeekBar.setProgress(postion);
                settingAndroidSystemBrightness(postion);
            }else{
                postion=1;
                screenLightSeekBar.setProgress(postion);
                settingAndroidSystemBrightness(postion);
            }


        }
        if(view.getId()==lightAdd.getId()){
            playButtonMusic(musicButtonId);
            postion+=25;
            if (postion<=255){
                screenLightSeekBar.setProgress(postion);
                settingAndroidSystemBrightness(postion);
            }else{
                postion=255;
                screenLightSeekBar.setProgress(postion);
                settingAndroidSystemBrightness(postion);
            }
        }

    }

    private void settingAndroidSystemBrightness(int num){
        Settings.System.putInt(getContentResolver(),Settings.System.SCREEN_BRIGHTNESS,num);
    }
    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        int cur=seekBar.getProgress();
        settingAndroidSystemBrightness(cur);
        postion=cur;
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}

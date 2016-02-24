package com.eastsoft.android.esbic.ativity;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Chronometer;
import android.widget.Toast;

import com.eastsoft.android.esbic.R;
import com.eastsoft.android.esbic.dialog.MyDialog;
import com.eastsoft.android.esbic.util.BoardCastFilterInfo;

/**
 * Created by ll on 2016/1/9.
 */
public class BaseActivity extends Activity{
    protected MediaPlayer music = null;// 播放器引用
    protected SoundPool sp;
    protected int musicButtonId,numOneMusicId,numTwoMusicId,numThreeMusicId,numFourMusicId,numFiveMusicId,
            numSixMusicId,numSevenMusicId,numEightMusicId,numNineMusicId,numZeroMusicId;
    protected int alarmId,alarmVoiceId,alterSuccessId,passwordWrongId,alterFailedId;
    protected Intent intent;
    protected BoardCastFilterInfo boardCastFilterInfo;
    //初始化GrideView自定义的输入键盘

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initSoundPool();
        boardCastFilterInfo=new BoardCastFilterInfo();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //注册自定义动态广播信息
        IntentFilter filter=new IntentFilter();
        filter.addAction(boardCastFilterInfo.ONCALLBYDOOR);
        registerReceiver(listenDoorDeviceCall,filter);

    }


    //使用BroadcastReceiver创建广播监听，监听来自门口机的呼叫等。
    protected BroadcastReceiver listenDoorDeviceCall=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
           if (intent.getAction().equals(boardCastFilterInfo.ONCALLBYDOOR)){
               startOncallActivity();
           }
        }
    };

    //启动被呼叫页面,来响应门口机的呼叫
    protected void startOncallActivity(){
        intent=getIntents();
        intent.setClass(BaseActivity.this,OnCallActivity.class);
        startActivity(intent);
    }

    //单例化Intent
    protected Intent getIntents(){
        Intent intents=new Intent();
        if (intents==null){
            intents=new Intent();
        }else{
            return intents;
        }
        return null;
    }
    //显示Dialog
    protected void showDialogs(Context context, int id){
        MyDialog myDialog=new MyDialog(context);
        Dialog dialog=myDialog.getDialog();
        View view=myDialog.getDialogView(id);
        dialog.setContentView(view);
        Window window=dialog.getWindow();
        WindowManager.LayoutParams lp=window.getAttributes();
        window.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL);
        lp.height= ViewGroup.LayoutParams.WRAP_CONTENT;
        lp.width= ViewGroup.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        dialog.show();
    }
    //统一显示ShortToast
    protected void showShortToast(String string){
        Toast.makeText(this,string,Toast.LENGTH_SHORT).show();
    }
    //统一显示LongToast
    protected void showLongToast(String string){
        Toast.makeText(this,string,Toast.LENGTH_LONG).show();
    }
    //用于播放按键音
    protected void playMusic() {
        //music = MediaPlayer.create(this,R.raw.ring1);
        //music.start();
    }
    //使用SoundPool来播放按键声，为了降低延迟时间。
    protected void initSoundPool(){
        sp= new SoundPool(16, AudioManager.STREAM_SYSTEM, 5);//第一个参数为同时播放数据流的最大个数，第二数据流类型，第三为声音质量
        musicButtonId= sp.load(this, R.raw.sound1, 1); //把你的声音素材放到res/raw里，第2个参数即为资源文件，第3个为音乐的优先级
        numOneMusicId=sp.load(this,R.raw.one,1);
        numTwoMusicId=sp.load(this,R.raw.two,1);
        numThreeMusicId=sp.load(this,R.raw.three,1);
        numFourMusicId=sp.load(this,R.raw.four,1);
        numFiveMusicId=sp.load(this,R.raw.five,1);
        numSixMusicId=sp.load(this,R.raw.six,1);
        numSevenMusicId=sp.load(this,R.raw.seven,1);
        numEightMusicId=sp.load(this,R.raw.eight,1);
        numNineMusicId=sp.load(this,R.raw.nine,1);
        numZeroMusicId=sp.load(this,R.raw.zero,1);
        alarmId=sp.load(this,R.raw.alarm,1);
        alarmVoiceId=sp.load(this,R.raw.alarm1,1);
        alterSuccessId=sp.load(this,R.raw.modisucc,1);
        alterFailedId=sp.load(this,R.raw.modifail,1);
        passwordWrongId=sp.load(this,R.raw.passerror,1);
    }
    protected void playButtonMusic(int numsicId){
       sp.play(numsicId,1,1,0,0,1);
    }

    //通话连接成功时，计时器清空，从新开始计时
    protected void timerRestart(Chronometer timer){
        timer.setBase(SystemClock.elapsedRealtime());
    }
    //
}

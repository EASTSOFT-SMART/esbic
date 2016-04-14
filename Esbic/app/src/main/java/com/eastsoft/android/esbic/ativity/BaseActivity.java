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
import android.os.Handler;
import android.os.HandlerThread;
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
import com.eastsoft.android.esbic.util.TimeUtil;

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
    protected Toast toast;
    private HandlerThread handlerThread;
    protected Handler handler;

    public BaseActivity()
    {
        this.handlerThread = new HandlerThread(this.getClass().getSimpleName());
        handlerThread.start();
        this.handler = new Handler(handlerThread.getLooper());
    }

    //初始化GrideView自定义的输入键盘

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toast = Toast.makeText(BaseActivity.this, "", Toast.LENGTH_SHORT);//成员变量toast
        handler.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                initSoundPool();
            }
        },10);
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
        toast.setText(string);//刷新文字内容
        toast.show();//显示toast
    }
    //统一显示LongToast
    protected void showLongToast(String string){
        toast.setText(string);//刷新文字内容
        toast.show();//显示toast
    }
    //用于播放按键音
    protected void playMusic() {
        //music = MediaPlayer.create(this,R.raw.ring1);
        //music.start();
        playButtonMusic(musicButtonId);
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

    //判断输入键盘按的是哪一个按键
    protected void playNumberSingByNumber(int num){
        if (num==0){
            playButtonMusic(numOneMusicId);
        }
        if (num==1){
            playButtonMusic(numTwoMusicId);
        }
        if (num==2){
            playButtonMusic(numThreeMusicId);
        }
        if (num==3){
            playButtonMusic(numFourMusicId);
        }
        if (num==4){
            playButtonMusic(numFiveMusicId);
        }
        if (num==5){
            playButtonMusic(numSixMusicId);
        }
        if (num==6){
            playButtonMusic(numSevenMusicId);
        }
        if (num==7){
            playButtonMusic(numEightMusicId);
        }
        if (num==8){
            playButtonMusic(numNineMusicId);
        }
        if (num==9){
            playButtonMusic(musicButtonId);
        }
        if (num==10){
            playButtonMusic(numZeroMusicId);
        }
        if (num==11){
            playButtonMusic(musicButtonId);
        }

    }
}

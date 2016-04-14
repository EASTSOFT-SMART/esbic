package com.eastsoft.android.esbic.ativity;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.eastsoft.android.esbic.R;
import com.eastsoft.android.esbic.util.LogUtil;
import com.eastsoft.android.esbic.util.TimeUtil;

/**
 * Created by Mr Wang on 2016/2/16.
 */
public class VolumeActivity extends BaseActivity implements SeekBar.OnSeekBarChangeListener,View.OnClickListener {
    private AudioManager audioManager;
    private ImageButton back,volumeDecrease,volumeAdd;
    private TextView back2;
    private SeekBar seekBar;
    private int maxVolume,currentVolume;
    private int volume=0;//初始化声音

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.volume_setting);
        initData();
    }

    private void initData()
    {
        back=(ImageButton)this.findViewById(R.id.volume_setting_back);
        back2=(TextView) this.findViewById(R.id.volume_setting_back2);
        volumeDecrease=(ImageButton)this.findViewById(R.id.volume_setting_decrease);
        volumeAdd=(ImageButton)this.findViewById(R.id.volume_setting_add);
        seekBar=(SeekBar)this.findViewById(R.id.volume_setting_seekbar);
        audioManager=(AudioManager)this.getSystemService(Context.AUDIO_SERVICE);
        maxVolume=audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);//获取当前最大的音量;
        seekBar.setMax(maxVolume);
        currentVolume=audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);//获取当前的音量;
        volume=currentVolume;
        seekBar.setProgress(currentVolume);
        back.setOnClickListener(this);
        back2.setOnClickListener(this);
        volumeAdd.setOnClickListener(this);
        volumeDecrease.setOnClickListener(this);
        seekBar.setOnSeekBarChangeListener(this);
    }

    @Override
    protected void playMusic() {
        super.playMusic();
    }

    @Override
    public void onClick(View view) {
        if (view.getId()==back.getId() || view.getId()==back2.getId()){
            playButtonMusic(musicButtonId);
            VolumeActivity.this.finish();
        }
        if (view.getId()==volumeDecrease.getId()){
            playMusic();
            volume-=2;
            if (volume<=0){
                volume=0;
                setVolume(volume);
                seekBar.setProgress(volume);
            }else{
                setVolume(volume);
                seekBar.setProgress(volume);
            }
        }
        if (view.getId()==volumeAdd.getId()){
            playMusic();
            volume+=2;
            if (volume<=maxVolume){
                setVolume(volume);
                seekBar.setProgress(volume);
            }else{
                volume=maxVolume;
                setVolume(volume);
                seekBar.setProgress(volume);
            }
        }

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,i,0);
        currentVolume=audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        seekBar.setProgress(currentVolume);
        volume=currentVolume;
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        playMusic();
    }

    private void setVolume(int volume)
    {
        audioManager.setStreamVolume(AudioManager.STREAM_VOICE_CALL,volume,0);
        audioManager.setStreamVolume(AudioManager.STREAM_SYSTEM,volume,0);
        audioManager.setStreamVolume(AudioManager.STREAM_RING,volume,0);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,volume,0);
        audioManager.setStreamVolume(AudioManager.STREAM_ALARM,volume,0);
        audioManager.setStreamVolume(AudioManager.STREAM_NOTIFICATION,volume,0);
    }
}

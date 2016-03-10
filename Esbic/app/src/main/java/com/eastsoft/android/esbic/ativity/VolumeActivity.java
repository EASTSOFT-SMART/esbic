package com.eastsoft.android.esbic.ativity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.eastsoft.android.esbic.R;

/**
 * Created by Mr Wang on 2016/2/16.
 */
public class VolumeActivity extends BaseActivity implements SeekBar.OnSeekBarChangeListener,View.OnClickListener {
    private MediaPlayer mediaPlayer;
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

    private void initData(){
        mediaPlayer=new MediaPlayer();
        back=(ImageButton)this.findViewById(R.id.volume_setting_back);
        back2=(TextView) this.findViewById(R.id.volume_setting_back2);
        volumeDecrease=(ImageButton)this.findViewById(R.id.volume_setting_decrease);
        volumeAdd=(ImageButton)this.findViewById(R.id.volume_setting_add);
        seekBar=(SeekBar)this.findViewById(R.id.volume_setting_seekbar);
        audioManager=(AudioManager)this.getSystemService(Context.AUDIO_SERVICE);
        maxVolume=audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);//获取当前最大的音量;
        Log.i("系统最大音量",String.valueOf(maxVolume));
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
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,volume,0);
                seekBar.setProgress(volume);
            }else{
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,volume,0);
                seekBar.setProgress(volume);
            }
        }
        if (view.getId()==volumeAdd.getId()){
            playMusic();
            volume+=2;
            if (volume<=maxVolume){
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,volume,0);
                seekBar.setProgress(volume);
            }else{
                volume=maxVolume;
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,volume,0);
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

    }
}

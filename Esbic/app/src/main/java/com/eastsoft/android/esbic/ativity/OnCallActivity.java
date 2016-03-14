package com.eastsoft.android.esbic.ativity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;


import com.eastsoft.android.esbic.R;
import com.eastsoft.android.esbic.jni.DeviceInfo;
import com.eastsoft.android.esbic.service.BroadcastTypeEnum;
import com.eastsoft.android.esbic.service.IModelService;
import com.eastsoft.android.esbic.util.JsonUtil;

import org.videolan.libvlc.EventHandler;
import org.videolan.libvlc.LibVLC;
import org.videolan.libvlc.LibVlcException;
import org.videolan.vlc.util.VLCInstance;
import org.videolan.libvlc.IVideoPlayer;

/**
 * Created by Mr Wang on 2016/2/19.
 */
public class OnCallActivity extends BaseActivity implements View.OnClickListener, SurfaceHolder.Callback, IVideoPlayer{
    private SurfaceView mSurfaceView;
    private TextView onCallAddrOne,onCallAddrTwo, back2;
    private Button hangUp,accept,unLock;
    private ImageButton back;
    private SurfaceHolder surfaceHolder;
    private IModelService modelService;
    private Intent intent;

    private LibVLC mMediaPlayer;
    private SurfaceHolder mSurfaceHolder;
    private int mVideoHeight;
    private int mVideoWidth;
    private int mVideoVisibleHeight;
    private int mVideoVisibleWidth;
    private int mSarNum;
    private int mSarDen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.on_call);
        initData();
    }

    private void initData(){
        onCallAddrOne=(TextView)this.findViewById(R.id.on_call_addr_one);
        onCallAddrTwo=(TextView)this.findViewById(R.id.on_call_addr_two);
        hangUp=(Button)this.findViewById(R.id.on_call_hang_up);
        accept=(Button)this.findViewById(R.id.on_call_accept);
        unLock=(Button)this.findViewById(R.id.on_call_unlock);
        back=(ImageButton)this.findViewById(R.id.on_call_back);
        back2=(TextView) this.findViewById(R.id.on_call_back2);
        hangUp.setOnClickListener(this);
        accept.setOnClickListener(this);
        unLock.setOnClickListener(this);
        back.setOnClickListener(this);
        back2.setOnClickListener(this);
        modelService = ((MyApplication)getApplication()).getModelService();
        intent = getIntent();
        String tmp = intent.getStringExtra("value");
        DeviceInfo deviceInfo = JsonUtil.fromJson(tmp, DeviceInfo.class);
        onCallAddrOne.setText(deviceInfo.getBuilding_no() + "楼" + deviceInfo.getUnit_no() + "单元");
        onCallAddrTwo.setText(deviceInfo.getDev_no()+"号门口机呼叫");

        MyReceiver myReceiver = new MyReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.eastsoft.android.esbic.model");
        registerReceiver(myReceiver, intentFilter);

        mSurfaceView =(SurfaceView)this.findViewById(R.id.on_call_player);
//        mSurfaceView.setZOrderOnTop(true);      //  设置透明背景
        mSurfaceView.setKeepScreenOn(true);     //  屏幕保持常量
        mSurfaceHolder = mSurfaceView.getHolder();
        mSurfaceHolder.setFormat(PixelFormat.RGBX_8888);
        mSurfaceHolder.addCallback(this);
        try {
            mMediaPlayer = VLCInstance.getLibVlcInstance();
        } catch (LibVlcException e) {
            e.printStackTrace();
        }
        mMediaPlayer.eventVideoPlayerActivityCreated(true);
        EventHandler em = EventHandler.getInstance();
        em.addHandler(mVlcHandler);
        this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
        mMediaPlayer.playMRL("rtsp://218.204.223.237:554/live/1/66251FC11353191F/e7ooqwcfbqjoo80j.sdp");
    }

    @Override
    public void onClick(View view) {
        if (view.getId()==back.getId()||view.getId()==back2.getId()){
            playButtonMusic(musicButtonId);
            finish();
        }
        if (view.getId()==accept.getId()){
            modelService.ui_talk_answer();
        }
        if (view.getId()==hangUp.getId()){
            modelService.active_hang_up();
            finish();
        }
        if (view.getId()==unLock.getId()){
            modelService.unlock_door();
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
                case PLAY_VIDEO :
                    mMediaPlayer.playMRL(value);
                    break;
                case HANG_UP :
                    showLongToast("对方已挂断！");
                    finish();
                    break;
                case OPEN_LOCK_CONFIRM :
                    showLongToast("门已开！");
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mSurfaceView.setKeepScreenOn(false);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mMediaPlayer != null) {
            mMediaPlayer.eventVideoPlayerActivityCreated(false);
            EventHandler em = EventHandler.getInstance();
            em.removeHandler(mVlcHandler);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        setSurfaceSize(mVideoWidth, mVideoHeight, mVideoVisibleWidth, mVideoVisibleHeight, mSarNum, mSarDen);
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (mMediaPlayer != null) {
            mSurfaceHolder = holder;
            mMediaPlayer.attachSurface(holder.getSurface(), this);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        mSurfaceHolder = holder;
        if (mMediaPlayer != null) {
            mMediaPlayer.attachSurface(holder.getSurface(), this);//, width, height
        }
        if (width > 0) {
            mVideoHeight = height;
            mVideoWidth = width;
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (mMediaPlayer != null) {
            mMediaPlayer.detachSurface();
        }
    }

    @Override
    public void setSurfaceSize(int width, int height, int visible_width, int visible_height, int sar_num, int sar_den) {
        mVideoHeight = height;
        mVideoWidth = width;
        mVideoVisibleHeight = visible_height;
        mVideoVisibleWidth = visible_width;
        mSarNum = sar_num;
        mSarDen = sar_den;
        mHandler.removeMessages(HANDLER_SURFACE_SIZE);
        mHandler.sendEmptyMessage(HANDLER_SURFACE_SIZE);
    }

    private static final int HANDLER_BUFFER_START = 1;
    private static final int HANDLER_BUFFER_END = 2;
    private static final int HANDLER_SURFACE_SIZE = 3;

    private static final int SURFACE_BEST_FIT = 0;  //  最佳比例
    private static final int SURFACE_FIT_HORIZONTAL = 1;    //  水平铺满
    private static final int SURFACE_FIT_VERTICAL = 2;      //  垂直铺满
    private static final int SURFACE_FILL = 3;
    private static final int SURFACE_16_9 = 4;
    private static final int SURFACE_4_3 = 5;
    private static final int SURFACE_ORIGINAL = 6;
    private int mCurrentSize = SURFACE_FILL;

    private Handler mVlcHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg == null || msg.getData() == null)
                return;

            switch (msg.getData().getInt("event")) {
                case EventHandler.MediaPlayerTimeChanged:
                    break;
                case EventHandler.MediaPlayerPositionChanged:
                    break;
                case EventHandler.MediaPlayerPlaying:
                    mHandler.removeMessages(HANDLER_BUFFER_END);
                    mHandler.sendEmptyMessage(HANDLER_BUFFER_END);
                    break;
                case EventHandler.MediaPlayerBuffering:
                    break;
                case EventHandler.MediaPlayerLengthChanged:
                    break;
                case EventHandler.MediaPlayerEndReached:
                    //播放完成
                    break;
            }

        }
    };

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case HANDLER_BUFFER_START:
                    showLoading();
                    break;
                case HANDLER_BUFFER_END:
                    hideLoading();
                    break;
                case HANDLER_SURFACE_SIZE:
                    changeSurfaceSize();
                    break;
            }
        }
    };

    private void showLoading() {
//        mLoadingView.setVisibility(View.VISIBLE);
    }

    private void hideLoading() {
//        mLoadingView.setVisibility(View.GONE);
    }

    private void changeSurfaceSize() {
        // get screen size
//        int dw = getWindowManager().getDefaultDisplay().getWidth();
//        int dh = getWindowManager().getDefaultDisplay().getHeight();
        int dw = mSurfaceView.getWidth();
        int dh = mSurfaceView.getHeight();
        // calculate aspect ratio
        double ar = (double) mVideoWidth / (double) mVideoHeight;
        // calculate display aspect ratio
        double dar = (double) dw / (double) dh;

        switch (mCurrentSize) {
            case SURFACE_BEST_FIT:
                if (dar < ar)
                    dh = (int) (dw / ar);
                else
                    dw = (int) (dh * ar);
                break;
            case SURFACE_FIT_HORIZONTAL:
                dh = (int) (dw / ar);
                break;
            case SURFACE_FIT_VERTICAL:
                dw = (int) (dh * ar);
                break;
            case SURFACE_FILL:
                break;
            case SURFACE_16_9:
                ar = 16.0 / 9.0;
                if (dar < ar)
                    dh = (int) (dw / ar);
                else
                    dw = (int) (dh * ar);
                break;
            case SURFACE_4_3:
                ar = 4.0 / 3.0;
                if (dar < ar)
                    dh = (int) (dw / ar);
                else
                    dw = (int) (dh * ar);
                break;
            case SURFACE_ORIGINAL:
                dh = mVideoHeight;
                dw = mVideoWidth;
                break;
        }

        mSurfaceHolder.setFixedSize(mVideoWidth, mVideoHeight);
        ViewGroup.LayoutParams lp = mSurfaceView.getLayoutParams();
        lp.width = dw;
        lp.height = dh;
        mSurfaceView.setLayoutParams(lp);
        mSurfaceView.invalidate();
    }
}

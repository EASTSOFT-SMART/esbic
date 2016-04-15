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
import android.os.SystemClock;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.eastsoft.android.esbic.R;
import com.eastsoft.android.esbic.adapter.MonitorItemAdapter;
import com.eastsoft.android.esbic.jni.DeviceInfo;
import com.eastsoft.android.esbic.jni.DeviceTypeEnum;
import com.eastsoft.android.esbic.service.BroadcastTypeEnum;
import com.eastsoft.android.esbic.service.IModelService;
import com.eastsoft.android.esbic.util.LogUtil;

import org.videolan.libvlc.EventHandler;
import org.videolan.libvlc.IVideoPlayer;
import org.videolan.libvlc.LibVLC;
import org.videolan.libvlc.LibVlcException;
import org.videolan.vlc.util.VLCInstance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sofa on 2016/1/25.
 */
public class MonitorActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener,
        AdapterView.OnItemClickListener, SurfaceHolder.Callback, IVideoPlayer {
    private SurfaceView mSurfaceView;
    private GridView gridView;
    private ImageButton back;
    private TextView back2;
    private String placeName[];
    private List<Map<String,Object>> mapList;
    private MonitorItemAdapter monitorItemAdapter;
    private boolean[] state, isStart;
    private IModelService modelService;
    private boolean isMonitoring;
    private int currentMonitorIndex;
    private DeviceInfo currentDeviceInfo;
    private View currentView;
    private MyMonitorReceiver myReceiver;
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
        setContentView(R.layout.monitor);
        initData();

    }
    private void initData(){
        state = new boolean[]{false,false,false,false,false,false,false,false};
        isStart = new boolean[]{false,false,false,false,false,false,false,false};
        back=(ImageButton)this.findViewById(R.id.monitor_back);
        back2=(TextView) this.findViewById(R.id.monitor_back2);
        gridView=(GridView)this.findViewById(R.id.exchange_monitor);
        placeName=new String[]{"单元正门","单元车库","单元侧门","单元右门","小区正门","小区侧门","小区","小区"};
        mapList=getData();
        monitorItemAdapter=new MonitorItemAdapter(placeName, state, isStart, this);
        gridView.setAdapter(monitorItemAdapter);
        gridView.setOnItemClickListener(this);
        gridView.setOnItemSelectedListener(this);
        back.setOnClickListener(this);
        back2.setOnClickListener(this);

        modelService = ((MyApplication)getApplication()).getModelService();

        myReceiver = new MyMonitorReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.eastsoft.android.esbic.model");
        registerReceiver(myReceiver, intentFilter);

        mSurfaceView =(SurfaceView)this.findViewById(R.id.monitor_video);
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
        String testUrl = "rtsp://218.204.223.237:554/live/1/66251FC11353191F/e7ooqwcfbqjoo80j.sdp";
        mMediaPlayer.playMRL(testUrl);
    }

    @Override
    public void onClick(View view) {
        if (view.getId()==back.getId() || view.getId() == back2.getId())
        {
            playButtonMusic(musicButtonId);
            if(currentDeviceInfo != null)
            {
                modelService.active_hang_up_monitor(currentDeviceInfo);
            }
            finish();
        }
    }

    //获取List<Map<String,Object>>数据
    private List<Map<String,Object>> getData(){
        List<Map<String,Object>> mapList=new ArrayList<Map<String,Object>>();
          for (int i=0;i<placeName.length;i++){
              Map<String,Object> map=new HashMap<String,Object>();
              map.put("place",placeName[i]);
              mapList.add(map);
        }
     return mapList;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        playMusic();
        currentView = view;
        DeviceInfo deviceInfo = ((MyApplication)getApplication()).getModelService().getDeviceInfo();
        if(deviceInfo == null)
        {
            showLongToast("您尚未设置设备信息！");
            return;
        }
        DeviceTypeEnum deviceTypeEnum;
        int devNo;
        if(i%2 == 0)
        {
            //  door machine
            deviceTypeEnum = DeviceTypeEnum.DT_UNIT_DOOR_MACHINE;
            devNo = (i+1+1)/2;
        }else
        {
            //  wall machine
            deviceTypeEnum = DeviceTypeEnum.DT_WALL_MACHINE;
            devNo = (i+1)/2;
        }
        DeviceInfo info = new DeviceInfo(deviceTypeEnum.getType(), deviceInfo.getBuilding_no(), deviceInfo.getUnit_no(), (byte)0, (byte)0, devNo);
        if(isMonitoring == false)
        {
            state[i] = true;
            monitorItemAdapter.notifyDataSetChanged();
            isMonitoring = true;
            currentMonitorIndex = i;
            currentDeviceInfo = info;
            ((MyApplication)getApplication()).getModelService().ui_req_monitor(info);
        }else if(currentMonitorIndex == i){
            state[i] = false;
            isStart[i] = false;
            monitorItemAdapter.notifyDataSetChanged();
            isMonitoring = false;
            currentMonitorIndex = -1;
            modelService.active_hang_up_monitor(currentDeviceInfo);
            currentDeviceInfo = null;
        }else{
            modelService.active_hang_up_monitor(currentDeviceInfo);
            SystemClock.sleep(500);
            state[currentMonitorIndex] = false;
            isStart[currentMonitorIndex] = false;
            state[i] = true;
            monitorItemAdapter.notifyDataSetChanged();
            isMonitoring = true;
            currentMonitorIndex = i;
            currentDeviceInfo = info;
            ((MyApplication)getApplication()).getModelService().ui_req_monitor(info);
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public class MyMonitorReceiver extends BroadcastReceiver
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
                case MONITOR_CONFIRM :
                    showLongToast("您监视的设备已经找到,正在获取视频！");
                    isStart[currentMonitorIndex] = true;
                    monitorItemAdapter.notifyDataSetChanged();
                    break;
                case DEVICE_BUSY :
                    showLongToast("您呼叫的设备正在忙，请稍后再拨！");
                    state[currentMonitorIndex] = false;
                    monitorItemAdapter.notifyDataSetChanged();
                    isMonitoring = false;
                    currentMonitorIndex = -1;
                    currentDeviceInfo = null;
                    break;
                case MONITOR_HANG_UP :
                    showLongToast("门口机挂断监视！");
                    state[currentMonitorIndex] = false;
                    monitorItemAdapter.notifyDataSetChanged();
                    isMonitoring = false;
                    currentMonitorIndex = -1;
                    currentDeviceInfo = null;
                    break;
                default:
                    break;
            }
        }
    }

    //  vlc
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
        unregisterReceiver(myReceiver);
        LogUtil.print(this.getClass().getSimpleName() + " onDestroy !");
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

    private static final int SURFACE_BEST_FIT = 0;
    private static final int SURFACE_FIT_HORIZONTAL = 1;
    private static final int SURFACE_FIT_VERTICAL = 2;
    private static final int SURFACE_FILL = 3;
    private static final int SURFACE_16_9 = 4;
    private static final int SURFACE_4_3 = 5;
    private static final int SURFACE_ORIGINAL = 6;
    private int mCurrentSize = SURFACE_FILL;    //  设置视频显示的比例

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

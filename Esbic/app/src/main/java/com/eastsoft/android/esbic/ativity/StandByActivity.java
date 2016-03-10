package com.eastsoft.android.esbic.ativity;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.eastsoft.android.esbic.R;

/**
 * Created by Mr Wang on 2016/3/1.
 */
public class StandByActivity extends BaseActivity {
    private int item=0;//记录上一次点的位置
    private int currentItem=0;//当前页面
    private ScheduledExecutorService scheduledExecutorService;
    private List<ImageView> imageViewList;
    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;
    private int[] imageId;
    private Intent intent;
    private LinearLayout screen;
    private PowerManager pm;
    private PowerManager.WakeLock wakeLock;
    private KeyguardManager mKeyguardManager;
    private KeyguardManager.KeyguardLock mKeyguardLock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_saver);
        initData();
    }
    private void initData(){
        intent=getIntents();
        screen=(LinearLayout)this.findViewById(R.id.screen);
        pm=(PowerManager)getSystemService(POWER_SERVICE);
        wakeLock=pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP|
                PowerManager.SCREEN_DIM_WAKE_LOCK|PowerManager.ON_AFTER_RELEASE,"SimpleTimer");

        imageId=new int[]{R.drawable.screensaver1,R.drawable.screensaver2,R.drawable.screensaver1
        ,R.drawable.screensaver2,R.drawable.screensaver1,R.drawable.screensaver2};
        imageViewList=new ArrayList<ImageView>();
        for (int i=0;i<imageId.length;i++){
            ImageView imageView=new ImageView(this);
            imageView.setBackgroundResource(imageId[i]);
            imageViewList.add(imageView);
        }
        viewPager=(ViewPager)this.findViewById(R.id.pager);
        pagerAdapter=new MyPagerAdapter();
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                currentItem=position;
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mKeyguardManager=(KeyguardManager)getSystemService(Context.KEYGUARD_SERVICE);
        mKeyguardLock=mKeyguardManager.newKeyguardLock("tag");
        mKeyguardLock.disableKeyguard();
        //mKeyguardLock.reenableKeyguard();

        screen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.setClass(StandByActivity.this,LeaveHome.class);
                startActivity(intent);
                StandByActivity.this.finish();
            }
        });




    }

    @Override
    protected void onResume() {
        super.onResume();
        wakeLock.acquire();
    }

    @Override
    protected void onPause() {
        super.onPause();
        wakeLock.release();
    }



    private class MyPagerAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return imageViewList.size();
        }

        //是否是同一张图片
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView=imageViewList.get(position);
            container.addView(imageView);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=getIntents();
                    intent.setClass(StandByActivity.this,LeaveHome.class);
                    startActivity(intent);
                    StandByActivity.this.finish();
                }
            });
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //container.removeView(imageViewList.get(position));
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        scheduledExecutorService= Executors.newSingleThreadScheduledExecutor();
        //每隔5秒切换一张图片
        scheduledExecutorService.scheduleWithFixedDelay(new ViewPagerTask(),5,5, TimeUnit.SECONDS);
    }

    private class ViewPagerTask implements Runnable{

        @Override
        public void run() {
            currentItem=currentItem%imageViewList.size();
            //更新界面
            //handler.sendEmptyMessage(0);
            handler.obtainMessage().sendToTarget();
        }
    }

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            viewPager.setCurrentItem(currentItem);
        }
    };

    @Override
    protected void onStop() {
        super.onStop();
    }
}

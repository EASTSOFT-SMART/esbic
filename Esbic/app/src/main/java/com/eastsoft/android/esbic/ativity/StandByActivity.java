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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.eastsoft.android.esbic.R;
import com.eastsoft.android.esbic.view.MyViewPager;

/**
 * Created by Mr Wang on 2016/3/1.
 */
public class StandByActivity extends BaseActivity {
    private int item=0;//记录上一次点的位置
    private int currentItem;//当前页面
    private ScheduledExecutorService scheduledExecutorService;
    private MyViewPager viewPager;
    private PagerAdapter pagerAdapter;
    private List<View> viewList;
    private Intent intent;
    private LinearLayout screen;
    private View viewOne,viewTwo;
    private PowerManager pm;
    private PowerManager.WakeLock wakeLock;
    private KeyguardManager mKeyguardManager;
    private KeyguardManager.KeyguardLock mKeyguardLock;
    private String userPasswd;

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
        viewList=new ArrayList<View>();
        viewOne=LayoutInflater.from(this).inflate(R.layout.standby_item_one,null);
        viewTwo=LayoutInflater.from(this).inflate(R.layout.standby_item_two,null);

        userPasswd = ((MyApplication)getApplication()).getModelService().getUserPassword();

        ImageButton buttonOne=(ImageButton)viewOne.findViewById(R.id.lock_one);
        ImageButton buttonTwo=(ImageButton)viewTwo.findViewById(R.id.lock_two);
        buttonOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(userPasswd != null && userPasswd.compareTo("") != 0)
                {
                    Intent intent=getIntents();
                    intent.setClass(StandByActivity.this,LeaveHome.class);
                    startActivity(intent);
                }
                StandByActivity.this.finish();
            }
        });
        buttonTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(userPasswd != null && userPasswd.compareTo("") != 0)
                {
                    Intent intent=getIntents();
                    intent.setClass(StandByActivity.this,LeaveHome.class);
                    startActivity(intent);
                }
                StandByActivity.this.finish();
            }
        });
        viewList.add(viewOne);
        viewList.add(viewTwo);
        viewPager=(MyViewPager)this.findViewById(R.id.pager);
        viewPager.setScanScroll(false);
        pagerAdapter=new MyPagerAdapter();
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(viewList.size()*50);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentItem=position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mKeyguardManager=(KeyguardManager)getSystemService(Context.KEYGUARD_SERVICE);
        mKeyguardLock=mKeyguardManager.newKeyguardLock("tag");
        mKeyguardLock.disableKeyguard();
        //mKeyguardLock.reenableKeyguard();

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
            return viewList.size()*100;
        }

        //是否是同一张图片
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view=viewList.get(position%viewList.size());

            ViewParent viewParent=view.getParent();
            if (viewParent!=null){
                ViewGroup viewGroup=(ViewGroup)viewPager;
                viewGroup.removeView(view);
            }
            container.addView(view);
            //imageView.setOnClickListener(new View.OnClickListener() {
            //    @Override
            //    public void onClick(View view) {
            //        Intent intent=getIntents();
            //        intent.setClass(StandByActivity.this,LeaveHome.class);
            //        startActivity(intent);
            //        StandByActivity.this.finish();
            //    }
            //});
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {

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
            currentItem=currentItem+1;
            if (currentItem==viewList.size()*100){
                currentItem=0;
            }
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

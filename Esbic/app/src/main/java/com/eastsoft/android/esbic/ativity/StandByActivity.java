package com.eastsoft.android.esbic.ativity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;

import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import com.eastsoft.android.esbic.R;

/**
 * Created by Mr Wang on 2016/3/1.
 */
public class StandByActivity extends BaseActivity {
    private int oldPosition=0;//记录上一次点的位置
    private int currentItem;//当前页面
    private ScheduledExecutorService scheduledExecutorService;
    private List<ImageView> imageViewList;
    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;
    private int[] imageId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_saver);

    }
    private void initData(){
        imageId=new int[]{R.drawable.screensaver1,R.drawable.screensaver2};
    }

}

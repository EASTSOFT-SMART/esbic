package com.eastsoft.android.esbic.ativity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.widget.Button;

import com.eastsoft.android.esbic.R;
import com.eastsoft.android.esbic.fragment.SmartHomeFragment;
import com.eastsoft.android.esbic.fragment.SmartHomeFragmentThree;
import com.eastsoft.android.esbic.fragment.SmartHomeFragmentTwo;
import com.eastsoft.android.esbic.model.util.adapter.MyFragmentPagerAdapter;
import com.eastsoft.android.esbic.presenter.getviewadapter.GetMyFragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sofa on 2016/1/22.
 */
public class SmartHomeActivity extends FragmentActivity {
    private ViewPager viewPager;
    private List<Fragment> viewList;
    private MyFragmentPagerAdapter myFragmentPagerAdapter;
    private int currentTab=0;
    private Button circleOne,circleTwo,circleThree;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.smart_home);
        initButton();
        initViewPager();
        updateButtonBackgroud(currentTab);

    }
    //从presenter层获取PageAdapter
    private MyFragmentPagerAdapter getMyFragmentPagerAdapter(FragmentManager fm, List<Fragment> views) {
        GetMyFragmentPagerAdapter getMyPageAdapter = new GetMyFragmentPagerAdapter();
       return getMyPageAdapter.getAdapter(fm,views);

    }
    private void initButton(){
        circleOne=(Button)this.findViewById(R.id.button_one);
        circleTwo=(Button)this.findViewById(R.id.button_two);
        circleThree=(Button)this.findViewById(R.id.button_three);

    }
    private void updateButtonBackgroud(int i){
        if (i==0){
            circleOne.setBackgroundResource(R.drawable.select_small_circle);
            circleTwo.setBackgroundResource(R.drawable.small_circle);
            circleThree.setBackgroundResource(R.drawable.small_circle);
        }else if (i==1){
            circleOne.setBackgroundResource(R.drawable.small_circle);
            circleThree.setBackgroundResource(R.drawable.small_circle);
            circleTwo.setBackgroundResource(R.drawable.select_small_circle);
        }else if (i==2){
            circleOne.setBackgroundResource(R.drawable.small_circle);
            circleTwo.setBackgroundResource(R.drawable.small_circle);
            circleThree.setBackgroundResource(R.drawable.select_small_circle);
        }
    }
    private void initViewPager(){
        viewPager=(ViewPager)this.findViewById(R.id.view_pager);
        SmartHomeFragment smartHomeFragment=new SmartHomeFragment();
        SmartHomeFragmentTwo smartHomeFragmentTwo=new SmartHomeFragmentTwo();
        SmartHomeFragmentThree smartHomeFragmentThree=new SmartHomeFragmentThree();
        viewList=new ArrayList<>();
        viewList.add(smartHomeFragment);
        viewList.add(smartHomeFragmentTwo);
        viewList.add(smartHomeFragmentThree);
        myFragmentPagerAdapter=getMyFragmentPagerAdapter(this.getSupportFragmentManager(),viewList);
        viewPager.setAdapter(myFragmentPagerAdapter);
        viewPager.setCurrentItem(0);//设置当前页为第一页
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
             updateButtonBackgroud(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });




    }
}

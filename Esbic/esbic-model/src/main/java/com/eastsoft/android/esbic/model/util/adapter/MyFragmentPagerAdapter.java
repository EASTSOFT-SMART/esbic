package com.eastsoft.android.esbic.model.util.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import java.util.List;

/**
 * Created by sofa on 2016/1/22.
 */
public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> viewList;

    public MyFragmentPagerAdapter(FragmentManager fm,List<Fragment>viewList) {
        super(fm);
        this.viewList=viewList;
    }


    @Override
    public int getCount() {
        return viewList.size();
    }



    @Override
    public Fragment getItem(int position) {
        return viewList.get(position);
    }


}

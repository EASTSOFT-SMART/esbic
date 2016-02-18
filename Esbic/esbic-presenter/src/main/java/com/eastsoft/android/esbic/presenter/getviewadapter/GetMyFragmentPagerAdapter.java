package com.eastsoft.android.esbic.presenter.getviewadapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.eastsoft.android.esbic.model.util.adapter.MyFragmentPagerAdapter;
import java.util.List;

/**
 * Created by sofa on 2016/1/22.
 */
public class GetMyFragmentPagerAdapter {

    public MyFragmentPagerAdapter getAdapter(FragmentManager fm, List<Fragment> viewList){
        MyFragmentPagerAdapter myPageAdapter=new MyFragmentPagerAdapter(fm,viewList);
        return myPageAdapter;
    }
}

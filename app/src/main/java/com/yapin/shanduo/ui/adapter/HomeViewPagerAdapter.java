package com.yapin.shanduo.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.yapin.shanduo.ui.fragment.HomeActivityFragment;
import com.yapin.shanduo.ui.fragment.HomeTrendFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：L on 2018/4/25 0025 18:19
 */
public class HomeViewPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments = new ArrayList<>();

    public HomeViewPagerAdapter(FragmentManager fm) {
        super(fm);
        fragments.clear();
        fragments.add(HomeActivityFragment.newInstance());
        fragments.add(HomeTrendFragment.newInstance());
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}

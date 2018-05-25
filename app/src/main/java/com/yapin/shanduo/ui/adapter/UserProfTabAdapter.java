package com.yapin.shanduo.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.yapin.shanduo.ui.fragment.ActivityFragment;
import com.yapin.shanduo.ui.fragment.TrendFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：L on 2018/5/25 0025 10:47
 */
public class UserProfTabAdapter extends FragmentPagerAdapter {

    private List<String> list;
    private List<Fragment> fragments = new ArrayList<>();

    public UserProfTabAdapter(FragmentManager fm , List<String> list , String userId) {
        super(fm);
        fragments.clear();
        fragments.add(ActivityFragment.newInstance(6 , userId));
        fragments.add(TrendFragment.newInstance(3 , userId));
        this.list = list;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return list.get(position);
    }
}

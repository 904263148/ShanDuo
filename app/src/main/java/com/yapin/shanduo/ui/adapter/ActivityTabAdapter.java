package com.yapin.shanduo.ui.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.yapin.shanduo.ui.fragment.ActivityFragment;

import java.util.List;

/**
 * 作者：L on 2018/4/26 0026 09:52
 */
public class ActivityTabAdapter extends FragmentPagerAdapter {
    private List<String> list;

    public ActivityTabAdapter(FragmentManager fm, List<String> list) {
        super(fm);
        this.list = list;
    }

    @Override
    public Fragment getItem(int position) {
        return ActivityFragment.newInstance();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return list.get(position);
    }
}

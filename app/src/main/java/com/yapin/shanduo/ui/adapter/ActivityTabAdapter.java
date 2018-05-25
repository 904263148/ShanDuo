package com.yapin.shanduo.ui.adapter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.ViewGroup;

import com.yapin.shanduo.ui.fragment.ActivityFragment;

import java.util.List;

/**
 * 作者：L on 2018/4/26 0026 09:52
 */
public class ActivityTabAdapter extends FragmentPagerAdapter {
    private List<String> list;
    private String userId;

    public ActivityTabAdapter(FragmentManager fm, List<String> list , String userId) {
        super(fm);
        this.list = list;
        this.userId = userId;
    }

    @Override
    public Fragment getItem(int position) {
        return ActivityFragment.newInstance(position , userId);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ActivityFragment fragment = (ActivityFragment) super.instantiateItem(container, position);
        return fragment;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return PagerAdapter.POSITION_NONE;
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

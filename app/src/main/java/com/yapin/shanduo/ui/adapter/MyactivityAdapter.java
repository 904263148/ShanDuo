package com.yapin.shanduo.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.ViewGroup;

import com.yapin.shanduo.ui.fragment.MyactivityFragment;

import java.util.List;

/**
 * Created by dell on 2018/5/19.
 */

public class MyactivityAdapter extends FragmentPagerAdapter {
    private String[] mTitles = new String[]{"已参加", "已报名","我的"};

    public MyactivityAdapter(FragmentManager fm ) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return MyactivityFragment.newInstance(position);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        MyactivityFragment fragment = (MyactivityFragment) super.instantiateItem(container, position);
        return fragment;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return PagerAdapter.POSITION_NONE;
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
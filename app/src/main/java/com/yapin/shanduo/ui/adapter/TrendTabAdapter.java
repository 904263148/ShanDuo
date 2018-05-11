package com.yapin.shanduo.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.ViewGroup;

import com.yapin.shanduo.ui.fragment.ActivityFragment;
import com.yapin.shanduo.ui.fragment.TrendFragment;

import java.util.List;

/**
 * 作者：L on 2018/5/9 0009 11:35
 */
public class TrendTabAdapter extends FragmentPagerAdapter {
    private List<String> list;

    public TrendTabAdapter(FragmentManager fm, List<String> list) {
        super(fm);
        this.list = list;
    }

    @Override
    public Fragment getItem(int position) {
        return TrendFragment.newInstance(position);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        TrendFragment fragment = (TrendFragment) super.instantiateItem(container, position);
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

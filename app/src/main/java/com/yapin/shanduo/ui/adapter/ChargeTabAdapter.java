package com.yapin.shanduo.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.ViewGroup;

import com.yapin.shanduo.ui.fragment.ChargeFragment;

import java.util.List;

/**
 * 作者：L on 2018/6/5 0005 17:39
 */
public class ChargeTabAdapter extends FragmentPagerAdapter {

    public ChargeTabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return ChargeFragment.newInstance(position);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ChargeFragment fragment = (ChargeFragment) super.instantiateItem(container, position);
        return fragment;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return PagerAdapter.POSITION_NONE;
    }

    @Override
    public int getCount() {
        return 2;
    }

}
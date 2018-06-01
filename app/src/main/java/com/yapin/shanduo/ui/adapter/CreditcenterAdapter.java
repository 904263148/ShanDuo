package com.yapin.shanduo.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.ViewGroup;

import com.yapin.shanduo.ui.fragment.CreditcenterFragment;
import com.yapin.shanduo.ui.fragment.MyactivityFragment;

/**
 * Created by dell on 2018/5/29.
 */
//extends FragmentPagerAdapter
public class CreditcenterAdapter extends FragmentPagerAdapter{

    private int mCount = 2 ;
    private int[] mColors = new int[]{android.R.color.holo_orange_dark,android.R.color.holo_green_dark};
    public CreditcenterAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return CreditcenterFragment.newInstance(position,mColors[position]);
    }

    @Override
    public int getCount() {
        return mCount;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "Page" + (position + 1);
    }


//    private String[] mTitles = new String[]{"发布", "参加"};
//
//    public CreditcenterAdapter(FragmentManager fm ) {
//        super(fm);
//    }
//
//    @Override
//    public Fragment getItem(int position) {
//        return CreditcenterFragment.newInstance(position);
//    }
//
//    @Override
//    public Object instantiateItem(ViewGroup container, int position) {
//        CreditcenterFragment fragment = (CreditcenterFragment) super.instantiateItem(container, position);
//        return fragment;
//    }
//
//    @Override
//    public int getItemPosition(@NonNull Object object) {
//        return PagerAdapter.POSITION_NONE;
//    }
//
//    @Override
//    public int getCount() {
//        return mTitles.length;
//    }
//
//    @Override
//    public CharSequence getPageTitle(int position) {
//        return mTitles[position];
//    }

}

package com.yapin.shanduo.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.yapin.shanduo.ui.fragment.HomeActivityFragment;
import com.yapin.shanduo.ui.fragment.HomeTrendFragment;
import com.yapin.shanduo.ui.fragment.LinkFriendFragment;
import com.yapin.shanduo.ui.fragment.LinkGroupFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：L on 2018/5/17 0017 14:32
 */
public class LinkTabAdapter extends FragmentPagerAdapter {

    private List<String> list;
    private List<Fragment> fragments = new ArrayList<>();

    public LinkTabAdapter(FragmentManager fm , List<String> list) {
        super(fm);
        fragments.clear();
        fragments.add(LinkFriendFragment.newInstance());
        fragments.add(LinkGroupFragment.newInstance());
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

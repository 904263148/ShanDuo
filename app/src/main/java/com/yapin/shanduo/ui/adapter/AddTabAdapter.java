package com.yapin.shanduo.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.yapin.shanduo.ui.fragment.AddGroupFragment;
import com.yapin.shanduo.ui.fragment.AddHumanFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：L on 2018/5/23 0023 14:43
 */
public class AddTabAdapter extends FragmentPagerAdapter {

    private List<String> list;
    private List<Fragment> fragments = new ArrayList<>();

    public AddTabAdapter(FragmentManager fm , List<String> list) {
        super(fm);
        fragments.clear();
        fragments.add(AddHumanFragment.newInstance());
        fragments.add(AddGroupFragment.newInstance());
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

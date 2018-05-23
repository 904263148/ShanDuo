package com.yapin.shanduo.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;

import com.yapin.shanduo.ui.fragment.AddActivityFragment;
import com.yapin.shanduo.ui.fragment.HomeFragment;
import com.yapin.shanduo.ui.fragment.LinkManFragment;
import com.yapin.shanduo.ui.fragment.PersonFragment;
import com.yapin.shanduo.ui.fragment.ChatFragment;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter{

    private List<Fragment> fragments = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
        fragments.clear();
        fragments.add(HomeFragment.newInstance());
        fragments.add(ChatFragment.newInstance());
        fragments.add(AddActivityFragment.newInstance());
        fragments.add(LinkManFragment.newInstance());
        fragments.add(PersonFragment.newInstance());

    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return PagerAdapter.POSITION_NONE;
    }


    @Override
    public int getCount() {
        return fragments.size();
    }
}

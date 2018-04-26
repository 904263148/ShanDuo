package com.yapin.shanduo.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationViewPager;
import com.yapin.shanduo.R;
import com.yapin.shanduo.ui.adapter.ViewPagerAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @BindView(R.id.view_pager)
    AHBottomNavigationViewPager viewPager;
    @BindView(R.id.bottom_navigation)
    AHBottomNavigation bottomNavigation;

    private ArrayList<AHBottomNavigationItem> bottomNavigationItems = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initView();
    }

    private void initView(){
        AHBottomNavigationItem item1 = new AHBottomNavigationItem("首页",R.drawable.icon_home, R.color.colorBottomNavigationActiveColored);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem("聊天", R.drawable.icon_chat, R.color.colorBottomNavigationActiveColored);
        AHBottomNavigationItem item5 = new AHBottomNavigationItem("活动" ,R.drawable.icon_add ,R.color.colorBottomNavigationActiveColored);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem("好友", R.drawable.icon_friend, R.color.colorBottomNavigationActiveColored);
        AHBottomNavigationItem item4 = new AHBottomNavigationItem("我的", R.drawable.icon_my, R.color.colorBottomNavigationActiveColored);

        bottomNavigationItems.add(item1);
        bottomNavigationItems.add(item2);
        bottomNavigationItems.add(item5);
        bottomNavigationItems.add(item3);
        bottomNavigationItems.add(item4);

        bottomNavigation.addItems(bottomNavigationItems);
        bottomNavigation.setForceTitlesDisplay(true);
        bottomNavigation.setDefaultBackgroundColor(Color.parseColor("#FEFEFE"));
        bottomNavigation.setAccentColor(Color.parseColor("#1A1B1C"));
        bottomNavigation.setInactiveColor(Color.parseColor("#C0C0C0"));

        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {

                viewPager.setCurrentItem(position,false);
                return true;
            }
        });

        viewPager.setOffscreenPageLimit(5);
        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));

    }

}

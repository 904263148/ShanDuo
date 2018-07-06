package com.yapin.shanduo.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;

import com.flyco.tablayout.SlidingTabLayout;
import com.yapin.shanduo.R;
import com.yapin.shanduo.app.ShanDuoPartyApplication;
import com.yapin.shanduo.ui.adapter.AddTabAdapter;
import com.yapin.shanduo.ui.adapter.LinkTabAdapter;
import com.yapin.shanduo.utils.Constants;
import com.yapin.shanduo.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddHumanGroupActivity extends BaseActivity {

    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.sliding_tab)
    SlidingTabLayout slidingTabLayout;

    private AddTabAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_human_group);

        ButterKnife.bind(this);

        List<String> tabList = new ArrayList<>();
        tabList.add("找人");
        tabList.add("找群");

        adapter = new AddTabAdapter(getSupportFragmentManager(), tabList);
        viewPager.setAdapter(adapter);
        slidingTabLayout.setViewPager(viewPager);
    }

    @OnClick({R.id.iv_back})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.iv_back:
                onBackPressed();
                break;
        }
    }

}

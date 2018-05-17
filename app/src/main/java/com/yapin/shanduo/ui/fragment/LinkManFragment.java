package com.yapin.shanduo.ui.fragment;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tencent.TIMConversationType;
import com.yapin.shanduo.R;
import com.yapin.shanduo.app.ShanDuoPartyApplication;
import com.yapin.shanduo.im.ui.ChatActivity;
import com.yapin.shanduo.ui.activity.EditingformationAcivity;
import com.yapin.shanduo.ui.activity.LoginActivity;
import com.yapin.shanduo.ui.activity.PublishTrendActivity;
import com.yapin.shanduo.ui.activity.RegisterActivity;
import com.yapin.shanduo.ui.adapter.HomeViewPagerAdapter;
import com.yapin.shanduo.ui.adapter.LinkTabAdapter;
import com.yapin.shanduo.ui.adapter.TrendTabAdapter;
import com.yapin.shanduo.utils.StartActivityUtil;
import com.yapin.shanduo.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class LinkManFragment extends Fragment {

    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;

    private Context context;
    private Activity activity;

    private LinkTabAdapter adapter;

    public static LinkManFragment newInstance() {
        LinkManFragment fragment = new LinkManFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_linkman_layout,container,false);
        ButterKnife.bind(this,view);
        initView();
        return view;
    }

    public void initView(){
        context = ShanDuoPartyApplication.getContext();
        activity = getActivity();

        List<String> tabList = new ArrayList<>();
        tabList.add("我的好友");
        tabList.add("我的群组");

        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(new LinkTabAdapter(getChildFragmentManager(), tabList));
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                Utils.setIndicator(tabLayout , 55 , 55);
            }
        });

    }

}

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

import com.yapin.shanduo.R;
import com.yapin.shanduo.app.ShanDuoPartyApplication;
import com.yapin.shanduo.ui.adapter.ActivityTabAdapter;
import com.yapin.shanduo.ui.adapter.ImageHomeAdapter;
import com.yapin.shanduo.utils.Utils;
import com.yapin.shanduo.widget.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeActivityFragment extends Fragment {


    @BindView(R.id.img_view_pager)
    AutoScrollViewPager imgViewPager;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.indicator)
    CirclePageIndicator indicator;

    private Context context;
    private Activity activity;

    private int [] imgs = {R.drawable.good6 , R.drawable.good7 , R.drawable.good8};

    private List<String> list;

    public static HomeActivityFragment newInstance() {
        HomeActivityFragment fragment = new HomeActivityFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public HomeActivityFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_activity, container, false);
        ButterKnife.bind(this,view);
        initView();
        return view;
    }

    public void initView(){
        context = ShanDuoPartyApplication.getContext();
        activity = getActivity();

        imgViewPager.setAdapter(new ImageHomeAdapter(context ,activity ,imgs));
        indicator.setViewPager(imgViewPager);
        imgViewPager.setInterval(4000);
        imgViewPager.setSlideBorderMode(AutoScrollViewPager.SLIDE_BORDER_MODE_TO_PARENT);
//        imgViewPager.startAutoScroll();

        list = new ArrayList<>();
        list.add("热门活动");
        list.add("附近活动");
        list.add("好友活动");

        ActivityTabAdapter adapter = new ActivityTabAdapter(getChildFragmentManager() , list);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                Utils.setIndicator(tabLayout , 30 , 30);
            }
        });
    }

}

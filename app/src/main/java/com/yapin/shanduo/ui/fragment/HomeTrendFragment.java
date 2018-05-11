package com.yapin.shanduo.ui.fragment;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yapin.shanduo.R;
import com.yapin.shanduo.app.ShanDuoPartyApplication;
import com.yapin.shanduo.ui.adapter.MyViewPagerAdapter;
import com.yapin.shanduo.ui.adapter.TrendTabAdapter;
import com.yapin.shanduo.utils.Utils;
import com.yapin.shanduo.widget.CirclePageIndicator;
import com.yapin.shanduo.widget.MyGallyPageTransformer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeTrendFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener , ViewPager.OnPageChangeListener{

    @BindView(R.id.img_view_pager)
    ViewPager imgViewPager;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.indicator)
    CirclePageIndicator indicator;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.app_bar)
    AppBarLayout appBarLayout;

    private Context context;
    private Activity activity;
    private View view;

    private List<String> imgList;
    private List<String> tabList;
    private MyViewPagerAdapter myViewPagerAdapter;

    private TrendTabAdapter adapter;

    public HomeTrendFragment() {
        // Required empty public constructor
    }

    public static HomeTrendFragment newInstance() {
        HomeTrendFragment fragment = new HomeTrendFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttachFragment(Fragment childFragment) {
        super.onAttachFragment(childFragment);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home_trend, container, false);
        ButterKnife.bind(this,view);
        initView();
        return view;
    }

    public void initView(){
        context = ShanDuoPartyApplication.getContext();
        activity = getActivity();

        imgList = new ArrayList<>();
        imgList.add("http://img.ph.126.net/cAhwYnhHyFdr0-eFOTdUGw==/1097752409188465061.jpg");
        imgList.add("http://p1.so.qhmsg.com/t01953e7a5cda90d046.jpg");
        imgList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1490984320392&di=8290126f83c2a2c0d45be41e3f88a6d0&imgtype=0&src=http%3A%2F%2Ffile.mumayi.com%2Fforum%2F201307%2F19%2F152440r9ov9ololkzdcz7d.jpg");
        imgList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1490984407478&di=729b187f4939710e8b2436f9f1306dff&imgtype=0&src=http%3A%2F%2Ffile.mumayi.com%2Fforum%2F201505%2F05%2F172352jrr66rda0dwdwdwz.jpg");

        myViewPagerAdapter = new MyViewPagerAdapter(imgList , context , activity);
        imgViewPager.setAdapter(myViewPagerAdapter);
        imgViewPager.setOffscreenPageLimit(3);
        int pagerWidth = (int) (getResources().getDisplayMetrics().widthPixels * 3.5f / 5.0f);
        Log.d("pageWidth",pagerWidth+"");
        ViewGroup.LayoutParams lp = imgViewPager.getLayoutParams();
        if (lp == null) {
            lp = new ViewGroup.LayoutParams(pagerWidth, ViewGroup.LayoutParams.MATCH_PARENT);
        } else {
            lp.width = pagerWidth;
        }
        imgViewPager.setLayoutParams(lp);
        //setPageMargin表示设置图片之间的间距
        imgViewPager.setPageMargin(5);
        imgViewPager.setPageTransformer(true, new MyGallyPageTransformer());
        imgViewPager.setCurrentItem(1);
        indicator.setViewPager(imgViewPager);

        imgViewPager.addOnPageChangeListener(this);

        tabList = new ArrayList<>();
        tabList.add("附近动态");
        tabList.add("好友动态");

        adapter = new TrendTabAdapter(getChildFragmentManager() , tabList);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(this);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                Utils.setIndicator(tabLayout , 55 , 55);
            }
        });

        ShanDuoPartyApplication application = (ShanDuoPartyApplication)context.getApplicationContext();
        application.appBarLayout = appBarLayout;
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (!refreshLayout.isRefreshing()) {    //如果不在刷新状态
                    //判断是否滑动到最顶部
                    refreshLayout.setEnabled(refreshLayout.getScrollY() == verticalOffset);
                }
            }
        });
        refreshLayout.setColorSchemeResources(R.color.cpb_default_color);
        refreshLayout.setOnRefreshListener(this);

        //广播接收
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager
                .getInstance(getActivity());
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("trendRefreshComplete");
        BroadcastReceiver br = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                Boolean isRefresh = intent.getBooleanExtra("isRefresh" , false);
                refreshLayout.setRefreshing(isRefresh);
            }

        };
        localBroadcastManager.registerReceiver(br, intentFilter);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onRefresh() {
        adapter.notifyDataSetChanged();
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                Utils.setIndicator(tabLayout , 55 , 55);
            }
        });
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {
        refreshLayout.setEnabled(state == ViewPager.SCROLL_STATE_IDLE);
    }
}

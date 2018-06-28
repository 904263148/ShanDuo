package com.yapin.shanduo.ui.fragment;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.flyco.tablayout.SlidingTabLayout;
import com.yapin.shanduo.R;
import com.yapin.shanduo.app.ShanDuoPartyApplication;
import com.yapin.shanduo.presenter.HomeCarouselPresenter;
import com.yapin.shanduo.ui.adapter.MyViewPagerAdapter;
import com.yapin.shanduo.ui.adapter.TrendTabAdapter;
import com.yapin.shanduo.ui.contract.HomeCarouselContract;
import com.yapin.shanduo.ui.inter.OpenPopupWindow;
import com.yapin.shanduo.utils.ApiUtil;
import com.yapin.shanduo.utils.GlideUtil;
import com.yapin.shanduo.utils.Utils;
import com.yapin.shanduo.widget.CirclePageIndicator;
import com.yapin.shanduo.widget.DotView;
import com.yapin.shanduo.widget.MyGallyPageTransformer;
import com.yapin.shanduo.widget.VpSwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeTrendFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener , ViewPager.OnPageChangeListener, HomeCarouselContract.View{

    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.refresh)
    VpSwipeRefreshLayout refreshLayout;
    @BindView(R.id.app_bar)
    AppBarLayout appBarLayout;
    @BindView(R.id.iv_banner)
    ImageView ivBanner;
    @BindView(R.id.sliding_tab)
    SlidingTabLayout slidingTabLayout;

    private Context context;
    private Activity activity;
    private View view;

    private List<String> tabList;

    private TrendTabAdapter adapter;

    private HomeCarouselPresenter presenter;

    private OpenPopupWindow openPopupWindow;

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
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        openPopupWindow = (OpenPopupWindow) activity;
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
        presenter = new HomeCarouselPresenter();
        presenter.init(this);
        return view;
    }

    @Override
    public void initView(){
        context = ShanDuoPartyApplication.getContext();
        activity = getActivity();

        presenter.load();

        tabList = new ArrayList<>();
        tabList.add("附近动态");
        tabList.add("好友动态");

        adapter = new TrendTabAdapter(getChildFragmentManager() , tabList , "");
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(this);
        slidingTabLayout.setViewPager(viewPager);

        ShanDuoPartyApplication application = (ShanDuoPartyApplication)context.getApplicationContext();
        application.appBarLayout = appBarLayout;
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (!refreshLayout.isRefreshing()) {    //如果不在刷新状态
                    //判断是否滑动到最顶部
                    refreshLayout.setEnabled(refreshLayout.getScrollY() == verticalOffset);
                }

                //设置其透明度
                float alpha = 1;
                if(verticalOffset == 0) {
                    //完全不透明
                    alpha = 1;
                }
                if(verticalOffset <= -480){
                    alpha = 0;
                }
                openPopupWindow.onTitleHidden(alpha);

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

        IntentFilter intentFilter1 = new IntentFilter();
        intentFilter1.addAction("trendDeleteComplete");
        BroadcastReceiver br1 = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                onRefresh();
            }

        };
        localBroadcastManager.registerReceiver(br1, intentFilter1);

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

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void showCarousel(List<String> data) {
        setBanner(data.get(0));
    }

    public void setBanner(String url){
        GlideUtil.load(context , activity , ApiUtil.IMG_URL+url , ivBanner , 20);
    }

    @Override
    public void loading() {

    }

    @Override
    public void networkError() {

    }

    @Override
    public void error(String msg) {

    }

    @Override
    public void showFailed(String msg) {

    }

}

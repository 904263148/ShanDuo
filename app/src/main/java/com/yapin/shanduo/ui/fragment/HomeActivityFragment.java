package com.yapin.shanduo.ui.fragment;


import android.annotation.SuppressLint;
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
import android.support.v4.app.FragmentManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.yapin.shanduo.R;
import com.yapin.shanduo.app.ShanDuoPartyApplication;
import com.yapin.shanduo.presenter.HomeCarouselPresenter;
import com.yapin.shanduo.ui.activity.LoginActivity;
import com.yapin.shanduo.ui.adapter.ActivityTabAdapter;
import com.yapin.shanduo.ui.adapter.ImageHomeAdapter;
import com.yapin.shanduo.ui.adapter.MyViewPagerAdapter;
import com.yapin.shanduo.ui.contract.HomeCarouselContract;
import com.yapin.shanduo.ui.inter.OpenPopupWindow;
import com.yapin.shanduo.ui.inter.RefreshAll;
import com.yapin.shanduo.utils.ApiUtil;
import com.yapin.shanduo.utils.GlideUtil;
import com.yapin.shanduo.utils.PrefUtil;
import com.yapin.shanduo.utils.StartActivityUtil;
import com.yapin.shanduo.utils.Utils;
import com.yapin.shanduo.widget.CirclePageIndicator;
import com.yapin.shanduo.widget.DotView;
import com.yapin.shanduo.widget.MyGallyPageTransformer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeActivityFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener , ViewPager.OnPageChangeListener , HomeCarouselContract.View{

    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.app_bar)
    AppBarLayout appBarLayout;
    @BindView(R.id.iv_banner)
    ImageView ivBanner;

    private Context context;
    private Activity activity;

    private List<String> tabList;

    private ActivityTabAdapter adapter;

    private HomeCarouselPresenter presenter;

    private final int OPEN_LOGIN = 1;

    private RefreshAll refreshAll;

    private OpenPopupWindow openPopupWindow;

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
        presenter = new HomeCarouselPresenter();
        presenter.init(this);
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        openPopupWindow = (OpenPopupWindow) activity;
    }

    @Override
    public void initView(){
        context = ShanDuoPartyApplication.getContext();
        activity = getActivity();

        refreshAll = (RefreshAll) activity;

        presenter.load();

        tabList = new ArrayList<>();
        tabList.add("热门活动");
        tabList.add("附近活动");
        tabList.add("好友活动");

        adapter = new ActivityTabAdapter(getChildFragmentManager() , tabList , "");
        viewPager.setAdapter(adapter);
//        viewPager.setOffscreenPageLimit(2);
        viewPager.addOnPageChangeListener(this);

        tabLayout.setupWithViewPager(viewPager);
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                Utils.setIndicator(tabLayout , 30 , 30);
            }
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition() == 2 && TextUtils.isEmpty(PrefUtil.getToken(context))){
                    StartActivityUtil.start(activity ,HomeActivityFragment.this , LoginActivity.class ,OPEN_LOGIN);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        ShanDuoPartyApplication application = (ShanDuoPartyApplication)context.getApplicationContext();
        application.appBarLayout = appBarLayout;
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                Log.d("appbarLayout_x" , verticalOffset+"");
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
        intentFilter.addAction("actRefreshComplete");
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != activity.RESULT_OK) {
            viewPager.setCurrentItem(0);
            return;
        }
        if(requestCode == OPEN_LOGIN){
            refreshAll.refresh();
        }
    }

    @Override
    public void onRefresh() {
//        activityFragment.onRefresh(viewPager.getCurrentItem());
        adapter.notifyDataSetChanged();
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                Utils.setIndicator(tabLayout , 30 , 30);
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

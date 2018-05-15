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


    @BindView(R.id.img_view_pager)
    ViewPager imgViewPager;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.indicator)
    LinearLayout indicator;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.app_bar)
    AppBarLayout appBarLayout;

    private Context context;
    private Activity activity;

    private List<String> tabList;
    private List<String> imgList = new ArrayList<>();
    private MyViewPagerAdapter myViewPagerAdapter;

    private ActivityTabAdapter adapter;

    private List<String> addImgList = new ArrayList<>();

    private HomeCarouselPresenter presenter;

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
    public void initView(){
        context = ShanDuoPartyApplication.getContext();
        activity = getActivity();

        presenter.load();

        tabList = new ArrayList<>();
        tabList.add("热门活动");
        tabList.add("附近活动");
        tabList.add("好友活动");

        adapter = new ActivityTabAdapter(getChildFragmentManager() , tabList);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(2);
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
                    StartActivityUtil.start(activity , LoginActivity.class);
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

    private void pageSelected(int position) {
        if (position == 0) {    //判断当切换到第0个页面时把currentPosition设置为list.size(),即倒数第二个位置，小圆点位置为length-1
            currentPosition = imgList.size();
            dotPosition = imgList.size() - 1;
        } else if (position == imgList.size() + 1) {    //当切换到最后一个页面时currentPosition设置为第一个位置，小圆点位置为0
            currentPosition = 1;
            dotPosition = 0;
        } else {
            currentPosition = position;
            dotPosition = position - 1;
        }
        //  把之前的小圆点设置背景为暗红，当前小圆点设置为红色
        mDotList.get(prePosition).setChecked(false);
        mDotList.get(dotPosition).setChecked(true);
        prePosition = dotPosition;
    }

    //  指示器图片集合
    private List<DotView> mDotList = new ArrayList<>();
    //  指示器圆点半径
    private float indicatorRadius;
    //  圆点位置
    private int dotPosition = 0;
    //  图片上一个位置
    private int prePosition = 0;
    //  图片当前位置
    private int currentPosition;
    //  是否正在循环
    private boolean isLooping;

    Handler mHandler = new Handler();
    Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            if (imgViewPager.getChildCount() > 1) {
                mHandler.postDelayed(this, 3000);
                currentPosition++;
                imgViewPager.setCurrentItem(currentPosition, true);
            }
        }
    };

    //  设置轮播小圆点
    private void setIndicator() {

        indicatorRadius = Utils.dip2px(context , 3);

        // mDotList.clear();
        indicator.removeAllViews();
        //  设置LinearLayout的子控件的宽高，这里单位是像素。
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) indicatorRadius * 2, (int) indicatorRadius * 2);
        params.rightMargin = (int) (indicatorRadius * 2 / 1.5);
        if (imgList.size() > 1) {
            //  for循环创建mUrlList.size()个ImageView（小圆点）
            for (int i = 0; i < imgList.size(); i++) {
                DotView dotView = new DotView(getContext());
                dotView.setLayoutParams(params);
                dotView.setNormalColor(getResources().getColor(R.color.font_color_gray));
                dotView.setCheckedColor(getResources().getColor(R.color.font_black_color));
                dotView.setChecked(false);
                indicator.addView(dotView);
                mDotList.add(dotView);
            }
        }
        //设置第一个小圆点图片背景为红色
        if (imgList.size() > 1) {
            mDotList.get(dotPosition).setChecked(true);
        }
        indicator.setGravity(Gravity.CENTER);
    }

    private void startLoop() {
        if (!isLooping && imgViewPager != null) {
            mHandler.postDelayed(mRunnable, 3000);// 每interval秒执行一次runnable.
            isLooping = true;
        }
    }

    public void stopLoop() {
        if (isLooping && imgViewPager != null) {
            mHandler.removeCallbacks(mRunnable);
            isLooping = false;
        }
    }

    public void setAddImgList(){
        for (int i = 0; i < imgList.size() + 2; i++) {
            if (i == 0) {   //  判断当i=0为该处的mList的最后一个数据作为mListAdd的第一个数据
                addImgList.add(imgList.get(imgList.size() - 1));
            } else if (i == imgList.size() + 1) {   //  判断当i=mList.size()+1时将mList的第一个数据作为mListAdd的最后一个数据
                addImgList.add(imgList.get(0));
            } else {  //  其他情况
                addImgList.add(imgList.get(i - 1));
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
            startLoop();
    }

    @Override
    public void onPause() {
        super.onPause();
        stopLoop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopLoop();
    }

    @Override
    public void showCarousel(List<String> data) {
        imgList.clear();
        imgList.addAll(data);
        addImgList.clear();
        setAddImgList();
        initViewPager();

        startLoop();
    }

    public void initViewPager(){
        myViewPagerAdapter = new MyViewPagerAdapter(addImgList , context , activity);
        imgViewPager.setAdapter(myViewPagerAdapter);

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
        imgViewPager.setPageMargin(2);
        imgViewPager.setOffscreenPageLimit(6);
        imgViewPager.setPageTransformer(true, new MyGallyPageTransformer());
        imgViewPager.setCurrentItem(1);
        setIndicator();

        imgViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                pageSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //  当state为SCROLL_STATE_IDLE即没有滑动的状态时切换页面
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    imgViewPager.setCurrentItem(currentPosition, false);
                }
            }
        });

        imgViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_MOVE:
                        isLooping = true;
                        stopLoop();
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        isLooping = false;
                        startLoop();
                    default:
                        break;
                }
                return false;
            }
        });

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

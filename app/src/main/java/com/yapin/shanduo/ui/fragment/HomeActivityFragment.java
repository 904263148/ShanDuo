package com.yapin.shanduo.ui.fragment;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.yapin.shanduo.R;
import com.yapin.shanduo.app.ShanDuoPartyApplication;
import com.yapin.shanduo.ui.adapter.ActivityTabAdapter;
import com.yapin.shanduo.ui.adapter.ImageHomeAdapter;
import com.yapin.shanduo.ui.adapter.MyViewPagerAdapter;
import com.yapin.shanduo.utils.Utils;
import com.yapin.shanduo.widget.CirclePageIndicator;
import com.yapin.shanduo.widget.MyGallyPageTransformer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeActivityFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{


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

    private List<String> list;
    private MyViewPagerAdapter myViewPagerAdapter;

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

        list = new ArrayList<>();
        list.add("http://img.ph.126.net/cAhwYnhHyFdr0-eFOTdUGw==/1097752409188465061.jpg");
        list.add("http://p1.so.qhmsg.com/t01953e7a5cda90d046.jpg");
        list.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1490984320392&di=8290126f83c2a2c0d45be41e3f88a6d0&imgtype=0&src=http%3A%2F%2Ffile.mumayi.com%2Fforum%2F201307%2F19%2F152440r9ov9ololkzdcz7d.jpg");
        list.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1490984407478&di=729b187f4939710e8b2436f9f1306dff&imgtype=0&src=http%3A%2F%2Ffile.mumayi.com%2Fforum%2F201505%2F05%2F172352jrr66rda0dwdwdwz.jpg");

        myViewPagerAdapter = new MyViewPagerAdapter(list , context , activity);
        imgViewPager.setAdapter(myViewPagerAdapter);
        imgViewPager.setOffscreenPageLimit(3);
        int pagerWidth = (int) (getResources().getDisplayMetrics().widthPixels * 3.0f / 5.0f);
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

    }

    @Override
    public void onRefresh() {

    }
}

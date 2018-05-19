package com.yapin.shanduo.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;

import com.yapin.shanduo.R;
import com.yapin.shanduo.app.ShanDuoPartyApplication;
import com.yapin.shanduo.model.entity.ActivityInfo;
import com.yapin.shanduo.presenter.MyactivityPresenter;
import com.yapin.shanduo.ui.adapter.MyactivityAdapter;
import com.yapin.shanduo.ui.contract.MyactivityContract;
import com.yapin.shanduo.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by dell on 2018/5/18.
 */
//  implements SwipeRefreshLayout.OnRefreshListener , ViewPager.OnPageChangeListener , MyactivityContract.View
public class MyactivitiesActivity extends BaseActivity{

    private Context context;
    private Activity activity;
    private MyactivityPresenter presenter;

    private MyactivityAdapter mAdapter;
    private ViewPager mViewPager;
    private TabLayout mTableLayout;

    @BindView(R.id.my_main_viewpager)
    ViewPager viewPager;
    @BindView(R.id.my_main_tab)
    TabLayout tabLayout;

    private List<String> list;

    private final int OPEN_LOGIN = 1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myactivities);
        context = ShanDuoPartyApplication.getContext();
        activity = this;
        ButterKnife.bind(this);
        presenter = new MyactivityPresenter();
//        presenter.init(MyactivitiesActivity.this);

        mAdapter = new MyactivityAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.my_main_viewpager);
        mViewPager.setAdapter(mAdapter);
        mTableLayout = (TabLayout) findViewById(R.id.my_main_tab);
        mTableLayout.setupWithViewPager(mViewPager);
        mTableLayout.setTabMode(TabLayout.MODE_FIXED);

    }

//
//    @Override
//    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//    }
//
//    @Override
//    public void onPageSelected(int position) {
//
//    }
//
//    @Override
//    public void onPageScrollStateChanged(int state) {
//
//    }
//
//    @Override
//    public void onRefresh() {
//        mAdapter.notifyDataSetChanged();
//        tabLayout.post(new Runnable() {
//            @Override
//            public void run() {
//                Utils.setIndicator(tabLayout , 30 , 30);
//            }
//        });
//    }
//
//    @Override
//    public void initView() {
//        list = new ArrayList<>();
//        list.add("已参加");
//        list.add("已报名");
//        list.add("我的");
//
//        mAdapter = new MyactivityAdapter( list);
//        viewPager.setAdapter(mAdapter);
//        viewPager.setOffscreenPageLimit(2);
//        viewPager.addOnPageChangeListener(this);
//
//        tabLayout.setupWithViewPager(viewPager);
//        tabLayout.post(new Runnable() {
//            @Override
//            public void run() {
//                Utils.setIndicator(tabLayout , 30 , 30);
//            }
//        });
//
//        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
////                if(tab.getPosition() == 2 && TextUtils.isEmpty(PrefUtil.getToken(context))){
////                    StartActivityUtil.start(activity ,MyactivitiesActivity.this , LoginActivity.class ,OPEN_LOGIN);
////                }
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });
//
//
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(resultCode != activity.RESULT_OK) {
//            viewPager.setCurrentItem(0);
//            return;
//        }
//
//    }
//
//
//    @Override
//    public void show(List<ActivityInfo.Act> data, int totalPage) {
//
//    }
//
//    @Override
//    public void loading() {
//
//    }
//
//    @Override
//    public void networkError() {
//
//    }
//
//    @Override
//    public void error(String msg) {
//
//    }
//
//    @Override
//    public void showFailed(String msg) {
//
//    }
//
//    @Override
//    public void onPointerCaptureChanged(boolean hasCapture) {
//
//    }

}

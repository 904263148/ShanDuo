package com.yapin.shanduo.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;

import com.yapin.shanduo.R;
import com.yapin.shanduo.app.ShanDuoPartyApplication;
import com.yapin.shanduo.model.entity.ActivityInfo;
import com.yapin.shanduo.presenter.MyactivityPresenter;
import com.yapin.shanduo.ui.adapter.MyactivityAdapter;
import com.yapin.shanduo.ui.contract.MyactivityContract;
import com.yapin.shanduo.utils.Utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by dell on 2018/5/18.
 */
//  implements SwipeRefreshLayout.OnRefreshListener , ViewPager.OnPageChangeListener , MyactivityContract.View
public class MyactivitiesActivity extends BaseActivity {

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

    @OnClick({R.id.tv_my_finish})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_my_finish:
                finish();
                break;
        }
    }

    public void setIndicator(TabLayout tabs, int leftDip, int rightDip) {
        Class<?> tabLayout = tabs.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        tabStrip.setAccessible(true);
        LinearLayout llTab = null;
        try {
            llTab = (LinearLayout) tabStrip.get(tabs);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
        int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());

        for (int i = 0; i < llTab.getChildCount(); i++) {
            View child = llTab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.leftMargin = left;
            params.rightMargin = right;
            child.setLayoutParams(params);
            child.invalidate();
        }


    }

    @Override
    public void onStart() {
        super.onStart();
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                setIndicator(tabLayout, 40, 40);
            }
        });
    }



}

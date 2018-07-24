package com.yapin.shanduo.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewAnimator;

import com.flyco.tablayout.SlidingTabLayout;
import com.tencent.qcloud.tlslibrary.helper.Util;
import com.yapin.shanduo.R;
import com.yapin.shanduo.app.ShanDuoPartyApplication;
import com.yapin.shanduo.model.entity.CreditItem;
import com.yapin.shanduo.presenter.CreditDetailPresenter;
import com.yapin.shanduo.ui.adapter.CreditTabAdapter;
import com.yapin.shanduo.ui.contract.CreditDetailContract;
import com.yapin.shanduo.utils.ApiUtil;
import com.yapin.shanduo.utils.GlideUtil;
import com.yapin.shanduo.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ScrollingActivity extends AppCompatActivity implements CreditDetailContract.View {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_head)
    ImageView ivHead;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout toolbarLayout;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.tv_score)
    TextView tvScore;
    @BindView(R.id.tv_score_des)
    TextView tvScoreDes;
    @BindView(R.id.tv_kicking)
    TextView tv_kicking;
    @BindView(R.id.textview)
    TextView textview;
    @BindView(R.id.sliding_tab)
    SlidingTabLayout slidingTabLayout;

    private CreditTabAdapter adapter;
    private int page = 1;
    private int pageSize = 10;
    private List<CreditItem> list = new ArrayList<>();
    private CreditDetailPresenter presenter;
    private Context context;
    private Activity activity;

    private String userId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        ButterKnife.bind(this);
        presenter = new CreditDetailPresenter();
        presenter.init(this);
    }

    @Override
    public void initView() {
//        userId = getIntent().getStringExtra("userId");

        textview.setVisibility(View.GONE);
        tv_kicking.setVisibility(View.GONE);

        userId = getIntent().getStringExtra("userId") == null ? "" : getIntent().getStringExtra("userId");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        context = ShanDuoPartyApplication.getContext();
        activity = this;

        List<String> tabList = new ArrayList<>();
        tabList.add("发布");
        tabList.add("参加");

        adapter = new CreditTabAdapter(getSupportFragmentManager(), tabList , userId);
        viewPager.setAdapter(adapter);
        slidingTabLayout.setViewPager(viewPager);

        toolbarLayout.setTitle("");

        presenter.getData(userId , page+"" , pageSize+""  , "1");
    }

    public void setData(){
        GlideUtil.load(context, activity, ApiUtil.IMG_URL + list.get(0).getFather_head(), ivHead);
        tvScore.setText(list.get(0).getFather_reputation()+"");

        tvScoreDes.setText(Utils.getCredit(list.get(0).getFather_reputation()));

    }

    @Override
    public void show(List<CreditItem> data, int totalPage) {
        list.clear();
        list = data;
        setData();
    }

    @OnClick()
    public void onClick(View view){

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

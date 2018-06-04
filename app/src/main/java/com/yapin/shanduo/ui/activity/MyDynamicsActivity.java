package com.yapin.shanduo.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import com.yapin.shanduo.R;
import com.yapin.shanduo.app.ShanDuoPartyApplication;
import com.yapin.shanduo.model.entity.TrendInfo;
import com.yapin.shanduo.presenter.LikePresenter;
import com.yapin.shanduo.presenter.MyDynamicsPresenter;
import com.yapin.shanduo.ui.activity.BaseActivity;
import com.yapin.shanduo.ui.adapter.MyDynamicsAdapter;
import com.yapin.shanduo.ui.adapter.TrendInfoAdapter;
import com.yapin.shanduo.ui.contract.LikeContract;
import com.yapin.shanduo.ui.contract.MyDynamicsContract;
import com.yapin.shanduo.utils.Constants;
import com.yapin.shanduo.utils.PrefUtil;
import com.yapin.shanduo.utils.StartActivityUtil;
import com.yapin.shanduo.utils.ToastUtil;
import com.yapin.shanduo.widget.LoadMoreRecyclerView;
import com.yapin.shanduo.widget.LoadingView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dell on 2018/5/3.
 */

public class MyDynamicsActivity extends BaseActivity implements MyDynamicsContract.View , LoadMoreRecyclerView.OnLoadMoreListener ,MyDynamicsAdapter.OnItemClickListener , MyDynamicsAdapter.OnLikeClickListener ,  LikeContract.View{

    private Context context;
    private Activity activity;

    @BindView(R.id.recycler_my_view)
    LoadMoreRecyclerView recyclerView;
    @BindView(R.id.loading_my_view)
    LoadingView loadingView;
    @BindView(R.id.sr_refresh)
    SwipeRefreshLayout refreshLayout;

    private MyDynamicsAdapter adapter;
    private LinearLayoutManager layoutManager;
    private int position;

    private int page = 1;
    private int pageSize = 10;
    private boolean isRefresh = false;
    private boolean isLoading = false;

    private MyDynamicsPresenter presenter;
    private LikePresenter likePresenter;

    private List<TrendInfo.Trend> list = new ArrayList<>();
    private TrendInfo.Trend footerItem = new TrendInfo.Trend();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mydynamics);
        context = ShanDuoPartyApplication.getContext();
        activity = this;
        ButterKnife.bind(this );
        presenter = new MyDynamicsPresenter();
        presenter.init(this);
        layoutManager = new LinearLayoutManager(context);
        initView();
    }

    @Override
    public void initView() {
        layoutManager = new LinearLayoutManager(context);
        footerItem.setType(Constants.TYPE_FOOTER_LOAD);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new MyDynamicsAdapter(context, activity , list);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
        adapter.setLikeClickListener(this);
        presenter.getdynamics( PrefUtil.getLon(context) , PrefUtil.getLat(context) ,page+"" , pageSize+"");
        recyclerView.setOnLoadMoreListener(this);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setRefreshLoading(true, false);
                page = 1;
                presenter.getdynamics(PrefUtil.getLon(context) , PrefUtil.getLat(context),page+"" , pageSize+"");
            }
        });
    }

    @OnClick({R.id.tv_md_cancel , R.id.tv_md_Release})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_md_cancel:
                finish();
                break;
            case R.id.tv_md_Release:
                StartActivityUtil.start(activity , PublishTrendActivity.class);
                break;
        }
    }


    /**
     * 设置刷新和加载更多的状态
     *
     * @param isRefresh 刷新状态
     * @param isLoading 加载更多状态
     */
    public void setRefreshLoading(boolean isRefresh, boolean isLoading) {
        this.isRefresh = isRefresh;
        this.isLoading = isLoading;

        if (!isRefresh && !isLoading) {
            recyclerView.setLoading(false);
            refreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void success(String data) {
        ToastUtil.showShortToast(context,data);
    }

    @Override
    public void show(List<TrendInfo.Trend> data, int totalPage) {
        if (!isLoading) {
            if (totalPage == 0) {
                loadingView.noData(R.string.tips_no_trend);
            } else {
                loadingView.setGone();
            }
                list.clear();
            list.add(footerItem);
        }
        recyclerView.setPage(page, totalPage);
        footerItem.setType(page < totalPage ? Constants.TYPE_FOOTER_LOAD : Constants.TYPE_FOOTER_FULL);
        list.addAll(list.size() - 1, data);
        adapter.notifyDataSetChanged();
        setRefreshLoading(false, false);
    }

    @Override
    public void loading() {
        if (!isRefresh && !isLoading)
            loadingView.loading();
    }

    @Override
    public void networkError() {
        loadingView.loadError();
        setRefreshLoading(false, false);
    }

    @Override
    public void error(String msg) {
        setRefreshLoading(false, false);
    }

    @Override
    public void showFailed(String msg) {
        loadingView.loadError();
        setRefreshLoading(false, false);
    }

    @Override
    public void onLoadMore() {
        page++;
        setRefreshLoading(false, true);
        presenter.getdynamics(PrefUtil.getLon(context) , PrefUtil.getLat(context),page+"" , pageSize+"");

    }

    @Override
    public void onItemClick(int position) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("trendInfo" , list.get(position));
        StartActivityUtil.start(activity , TrendInfoActivity.class , bundle);
    }

    @Override
    public void onLikeClick(String id) {

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}

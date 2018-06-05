package com.yapin.shanduo.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ListView;

import com.yapin.shanduo.R;
import com.yapin.shanduo.app.ShanDuoPartyApplication;
import com.yapin.shanduo.model.entity.FlickerPurseInfo;
import com.yapin.shanduo.model.entity.TransactionrecordInfo;
import com.yapin.shanduo.presenter.TransactionrecordPresenter;
import com.yapin.shanduo.ui.adapter.MyDynamicsAdapter;
import com.yapin.shanduo.ui.adapter.TransactionrecordAdapter;
import com.yapin.shanduo.ui.contract.LikeContract;
import com.yapin.shanduo.ui.contract.TransactionrecordContract;
import com.yapin.shanduo.utils.Constants;
import com.yapin.shanduo.utils.PrefUtil;
import com.yapin.shanduo.utils.StartActivityUtil;
import com.yapin.shanduo.utils.ToastUtil;
import com.yapin.shanduo.widget.LoadMoreRecyclerView;
import com.yapin.shanduo.widget.LoadingView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dell on 2018/6/3.
 */

public class TransactionrecordActivity extends BaseActivity implements TransactionrecordContract.View ,LoadMoreRecyclerView.OnLoadMoreListener ,TransactionrecordAdapter.OnItemClickListener , TransactionrecordAdapter.OnLikeClickListener{

    private TransactionrecordPresenter presenter;
    private Context context;
    private Activity activity;

//    @BindView(R.id.lv_listview)
//    ListView lv_listview;

    @BindView(R.id.recycler_my_view)
    LoadMoreRecyclerView recyclerView;
    @BindView(R.id.loading_my_view)
    LoadingView loadingView;
    @BindView(R.id.sr_refresh)
    SwipeRefreshLayout refreshLayout;

    private int page = 1;
    private int pageSize = 10;
    private boolean isRefresh = false;
    private boolean isLoading = false;

    //自定义BaseAdapter
    private TransactionrecordAdapter adapter;
    private LinearLayoutManager layoutManager;

    private List<TransactionrecordInfo.Trend> list = new ArrayList<>();
    private TransactionrecordInfo.Trend transactionrecordInfo = new TransactionrecordInfo.Trend();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactionrecord);
        context = ShanDuoPartyApplication.getContext();
        activity = this;
        ButterKnife.bind(this);
        presenter = new TransactionrecordPresenter();
        presenter.init(this);
        layoutManager = new LinearLayoutManager(context);
    }

    @OnClick({R.id.tv_finish})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_finish:
                onBackPressed();
                break;
        }
    }

    @Override
    public void initView() {

        layoutManager = new LinearLayoutManager(context);
        transactionrecordInfo.setType(Constants.TYPE_FOOTER_LOAD);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new TransactionrecordAdapter(context, activity , list);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
        adapter.setLikeClickListener(this);
        presenter.gettrans( page+"" , pageSize+"");
        recyclerView.setOnLoadMoreListener(this);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setRefreshLoading(true, false);
                page = 1;
                presenter.gettrans(page+"" , pageSize+"");
            }
        });

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
    public void show(List<TransactionrecordInfo.Trend> data, int totalPage) {
        if (!isLoading) {
            if (totalPage == 0) {
                loadingView.noData(R.string.tips_no_Record);
            } else {
                loadingView.setGone();
            }
            list.clear();
            list.add(transactionrecordInfo);
        }
        recyclerView.setPage(page, totalPage);
        transactionrecordInfo.setType(page < totalPage ? Constants.TYPE_FOOTER_LOAD : Constants.TYPE_FOOTER_FULL);
        list.addAll(list.size() - 1  , data);
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
        presenter.gettrans(page+"" , pageSize+"");

    }

    @Override
    public void onItemClick(int position) {
//        Bundle bundle = new Bundle();
//        bundle.putParcelable("trendInfo" , list.get(position));
//        StartActivityUtil.start(activity , TrendInfoActivity.class , bundle);
    }

    @Override
    public void onLikeClick(String id) {

    }
}

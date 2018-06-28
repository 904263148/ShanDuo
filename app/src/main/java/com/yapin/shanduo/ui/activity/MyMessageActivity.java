package com.yapin.shanduo.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageButton;

import com.yapin.shanduo.R;
import com.yapin.shanduo.app.ShanDuoPartyApplication;
import com.yapin.shanduo.model.entity.MyMessageInfo;
import com.yapin.shanduo.presenter.MyMessagePresenter;
import com.yapin.shanduo.presenter.TrendReplayPresenter;
import com.yapin.shanduo.ui.adapter.MessageInfoAdapter;
import com.yapin.shanduo.ui.contract.MyMessageContract;
import com.yapin.shanduo.ui.contract.TrendReplayContract;
import com.yapin.shanduo.utils.Constants;
import com.yapin.shanduo.utils.ToastUtil;
import com.yapin.shanduo.utils.Utils;
import com.yapin.shanduo.widget.LoadMoreRecyclerView;
import com.yapin.shanduo.widget.LoadingView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者：L on 2018/6/27 0027 09:01
 */
public class MyMessageActivity extends RightSlidingActivity implements MyMessageContract.View , LoadMoreRecyclerView.OnLoadMoreListener , MessageInfoAdapter.OnItemClickListener, TrendReplayContract.View , SwipeRefreshLayout.OnRefreshListener{

    @BindView(R.id.recycler_view)
    LoadMoreRecyclerView recyclerView;
    @BindView(R.id.loading_view)
    LoadingView loadingView;
    @BindView(R.id.iv_back)
    ImageButton ivBack;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refreshLayout;

    private Context context;
    private Activity activity;

    private int page = 1;
    private int pageSize = 10;
    private boolean isLoading = false;
    private boolean isRefresh = false;

    private MyMessagePresenter presenter;
    private TrendReplayPresenter replayPresenter;
    private MessageInfoAdapter adapter;
    private List<MyMessageInfo.Message> list = new ArrayList<>();
    private LinearLayoutManager layoutManager;
    private MyMessageInfo.Message footerItem = new MyMessageInfo.Message();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_layout);
        ButterKnife.bind(this);
        presenter = new MyMessagePresenter();
        presenter.init(this);
        replayPresenter = new TrendReplayPresenter();
        replayPresenter.init(this);
    }

    @Override
    public void initView() {
        context = ShanDuoPartyApplication.getContext();
        activity = this;

        footerItem.setType_show(Constants.TYPE_FOOTER_LOAD);
        layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new MessageInfoAdapter(context ,activity ,list);
        recyclerView.setAdapter(adapter);
        recyclerView.setOnLoadMoreListener(this);
        adapter.setOnItemClickListener(this);
        refreshLayout.setOnRefreshListener(this);
        recyclerView.setNestedScrollingEnabled(false);

        presenter.getData(page+"" , pageSize+"");

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
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
    public void show(List<MyMessageInfo.Message> data, int totalPage) {
        if (!isLoading) {
            if (totalPage == 0) {
                loadingView.noData(R.string.tips_no_message);
                return;
            } else {
                loadingView.setGone();
            }
            list.clear();
            list.add(footerItem);
        }
        recyclerView.setPage(page, totalPage);
        footerItem.setType_show(page < totalPage ? Constants.TYPE_FOOTER_LOAD : Constants.TYPE_FOOTER_FULL);
        list.addAll(list.size() - 1, data);
        setRefreshLoading(false, false);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void show(String data) {
        ToastUtil.showShortToast(context ,data);
    }

    @Override
    public void loading() {

    }

    @Override
    public void networkError() {
        setRefreshLoading(false, false);
        loadingView.loadError();
    }

    @Override
    public void error(String msg) {
        setRefreshLoading(false, false);
        ToastUtil.showShortToast(context , msg);
    }

    @Override
    public void showFailed(String msg) {
        setRefreshLoading(false, false);
        ToastUtil.showShortToast(context , msg);
    }

    @Override
    public void onLoadMore() {
        page++;
        setRefreshLoading(false, true);
        presenter.getData(page+"" , pageSize+"");
    }

    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void onReplayClick(MyMessageInfo.Message message, String str) {
        if("1".equals(message.getType())){
            replayPresenter.getData(message.getDynamicId() , Utils.stringToUnicode(str) , message.getType() , "" , "");
        }else {
            replayPresenter.getData(message.getDynamicId() , Utils.stringToUnicode(str) , message.getType() , message.getCommentId() , message.getId()+"");
        }
    }

    @Override
    public void onRefresh() {
        setRefreshLoading(true, false);
        page = 1;
        presenter.getData(page+"" , pageSize+"");
    }
}

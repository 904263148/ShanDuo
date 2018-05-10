package com.yapin.shanduo.ui.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yapin.shanduo.R;
import com.yapin.shanduo.app.ShanDuoPartyApplication;
import com.yapin.shanduo.model.entity.ActivityInfo;
import com.yapin.shanduo.presenter.HomeActPresenter;
import com.yapin.shanduo.ui.adapter.ActivityInfoAdapter;
import com.yapin.shanduo.ui.contract.HomeActContract;
import com.yapin.shanduo.utils.Constants;
import com.yapin.shanduo.utils.PrefUtil;
import com.yapin.shanduo.widget.LoadMoreRecyclerView;
import com.yapin.shanduo.widget.LoadingView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivityFragment extends Fragment implements ActivityInfoAdapter.OnItemClickListener ,LoadMoreRecyclerView.OnLoadMoreListener , HomeActContract.View{

    @BindView(R.id.recycler_view)
    LoadMoreRecyclerView recyclerView;
    @BindView(R.id.loading_view)
    LoadingView loadingView;

    private Context context;
    private Activity activity;
    private ActivityInfoAdapter adapter;
    private ShanDuoPartyApplication application;
    private LinearLayoutManager layoutManager;

    private int position;

    private int page = 1;
    private int pageSize = 10;
    private boolean isRefresh = false;
    private boolean isLoading = false;
    private HomeActPresenter presenter;
    private List<ActivityInfo.Act> list = new ArrayList<>();
    private ActivityInfo.Act footerItem = new ActivityInfo.Act();

    private ProgressDialog dialog;

    public static ActivityFragment newInstance(int position) {
        ActivityFragment fragment = new ActivityFragment();
        Bundle args = new Bundle();
        args.putInt("position" , position);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        position = getArguments().getInt("position");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_activity, container, false);
        ButterKnife.bind(this , view);
        presenter= new HomeActPresenter();
        presenter.init(this);
        return view;
    }

    @Override
    public void initView(){
        context = ShanDuoPartyApplication.getContext();
        activity = getActivity();
        layoutManager = new LinearLayoutManager(context);

        footerItem.setType(Constants.TYPE_FOOTER_LOAD);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new ActivityInfoAdapter(context, activity , list);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);

        if(TextUtils.isEmpty(PrefUtil.getToken(context)) && position == 2){
            loadingView.noData(R.string.tips_no_token);
            return;
        }
        presenter.getData((position+1)+"" , "113.93" , "22.54" , page+"" , pageSize+"");
        recyclerView.setOnLoadMoreListener(this);

        dialog = new ProgressDialog(activity);
        dialog.setMessage("加载中...");
        dialog.setCancelable(false);
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
    public void onItemClick(int position) {

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

            //注册广播
            Intent intent = new Intent("actRefreshComplete");
            intent.putExtra("isRefresh",false);
            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
        }
    }

    @Override
    public void onLoadMore() {
        page++;
        setRefreshLoading(false, true);
        presenter.getData((position+1)+"" , "113.93" , "22.54" , page+"" , pageSize+"");
    }

    @Override
    public void show(List<ActivityInfo.Act> data, int totalPage) {
        if (!isLoading) {
            if (totalPage == 0) {
                loadingView.noData(R.string.tips_no_act);
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
        loadingView.loadError();
        setRefreshLoading(false, false);
    }

    @Override
    public void showFailed(String msg) {
        loadingView.loadError();
        setRefreshLoading(false, false);
    }

    public void onRefresh(int position) {
        setRefreshLoading(true, false);
        page = 1 ;
        presenter.getData((position+1)+"" , "113.93" , "22.54" , page+"" , pageSize+"");
    }
}

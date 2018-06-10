package com.yapin.shanduo.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yapin.shanduo.R;
import com.yapin.shanduo.app.ShanDuoPartyApplication;
import com.yapin.shanduo.model.entity.CreditItem;
import com.yapin.shanduo.presenter.CreditDetailPresenter;
import com.yapin.shanduo.ui.adapter.CreditDetailAdapter;
import com.yapin.shanduo.ui.contract.CreditDetailContract;
import com.yapin.shanduo.utils.Constants;
import com.yapin.shanduo.utils.ToastUtil;
import com.yapin.shanduo.widget.LoadMoreRecyclerView;
import com.yapin.shanduo.widget.LoadingView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dell on 2018/6/7.
 */

public class KickingFragment extends Fragment implements CreditDetailContract.View , LoadMoreRecyclerView.OnLoadMoreListener{

    @BindView(R.id.recycler_view)
    LoadMoreRecyclerView recyclerView;
    @BindView(R.id.loading_view)
    LoadingView loadingView;

    private int position = 0;
    private String userId = "";
    private Context context;
    private Activity activity;

    private CreditDetailPresenter presenter;
    private List<CreditItem> list= new ArrayList<>();
    private CreditItem footerItem = new CreditItem();
    private LinearLayoutManager layoutManager;
    private CreditDetailAdapter adapter;

    private int page = 1;
    private int pageSize = 10;

    private boolean isLoading = false;

    public KickingFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static KickingFragment newInstance(int position , String userId) {
        KickingFragment fragment = new KickingFragment();
        Bundle args = new Bundle();
        args.putInt("position" , position);
        args.putString("userId",userId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        position = getArguments().getInt("position");
        userId = getArguments().getString("userId");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_publish_act, container, false);
        ButterKnife.bind(this , view);
        presenter = new CreditDetailPresenter();
        presenter.init(this);
        return view;
    }

    @Override
    public void initView() {
        context = ShanDuoPartyApplication.getContext();
        activity = getActivity();
        layoutManager = new LinearLayoutManager(context);

        footerItem.setType(Constants.TYPE_FOOTER_LOAD);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new CreditDetailAdapter(context, activity , list , position);
        recyclerView.setAdapter(adapter);
        recyclerView.setOnLoadMoreListener(this);
//        recyclerView.setNestedScrollingEnabled(false);

        presenter.getData(userId , page+"" , pageSize+""  , (position+1)+"");
    }

    @Override
    public void show(List<CreditItem> data, int totalPage) {
        if (!isLoading) {
            if (totalPage == 0) {
                loadingView.noData(R.string.tips_no_credit);
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
        setRefreshLoading( false);
    }

    /**
     * 设置刷新和加载更多的状态
     *
     * @param isLoading 加载更多状态
     */
    public void setRefreshLoading(boolean isLoading) {
        this.isLoading = isLoading;
        if (!isLoading) {
            recyclerView.setLoading(false);
        }
    }

    @Override
    public void loading() {

    }

    @Override
    public void networkError() {
        setRefreshLoading( false);
    }

    @Override
    public void error(String msg) {
        setRefreshLoading( false);
        ToastUtil.showShortToast(context, msg);
    }

    @Override
    public void showFailed(String msg) {
        setRefreshLoading( false);
    }

    @Override
    public void onLoadMore() {
        page++;
        setRefreshLoading(true);
        presenter.getData(userId , page+"" , pageSize+""  , (position+1)+"");
    }
}

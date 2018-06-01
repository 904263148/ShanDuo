package com.yapin.shanduo.ui.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
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
import com.yapin.shanduo.presenter.JoinActPresenter;
import com.yapin.shanduo.ui.activity.JoinActActivity;
import com.yapin.shanduo.ui.activity.LoginActivity;
import com.yapin.shanduo.ui.activity.PlaceActivity;
import com.yapin.shanduo.ui.adapter.ActivityInfoAdapter;
import com.yapin.shanduo.ui.contract.HomeActContract;
import com.yapin.shanduo.ui.contract.JoinActContract;
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

public class ActivityFragment extends Fragment implements ActivityInfoAdapter.OnItemClickListener ,LoadMoreRecyclerView.OnLoadMoreListener , HomeActContract.View , JoinActContract.View{

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
    private String userId;

    private int page = 1;
    private int pageSize = 10;
    private boolean isRefresh = false;
    private boolean isLoading = false;
    private HomeActPresenter presenter;
    private List<ActivityInfo.Act> list = new ArrayList<>();
    private ActivityInfo.Act footerItem = new ActivityInfo.Act();

    private ProgressDialog dialog;

    private JoinActPresenter joinActPresenter;

    private View view;

    public static ActivityFragment newInstance(int position , String userId) {
        ActivityFragment fragment = new ActivityFragment();
        Bundle args = new Bundle();
        args.putInt("position" , position);
        args.putString("userId" , userId);
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
        view = inflater.inflate(R.layout.fragment_activity, container, false);
        ButterKnife.bind(this , view);
        presenter= new HomeActPresenter();
        presenter.init(this);
        joinActPresenter = new JoinActPresenter();
        joinActPresenter.init(this);
        return view;
    }

    @Override
    public void initView(){
        context = ShanDuoPartyApplication.getContext();
        activity = getActivity();
        dialog = new ProgressDialog(activity);
        dialog.setMessage("加载中...");
        dialog.setCancelable(false);

        layoutManager = new LinearLayoutManager(context);

        footerItem.setType(Constants.TYPE_FOOTER_LOAD);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new ActivityInfoAdapter(context, activity , list , position);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);

        if(TextUtils.isEmpty(PrefUtil.getToken(context)) && position == 2){
            loadingView.noData(R.string.tips_no_token);
            return;
        }
        presenter.getData((position+1)+"" , PrefUtil.getLon(context) , PrefUtil.getLat(context) , page+"" , pageSize+"" , userId);
        recyclerView.setOnLoadMoreListener(this);

        if(position == 6){
            recyclerView.setNestedScrollingEnabled(false);
        }
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
        Bundle bundle = new Bundle();
        bundle.putParcelable("act" , list.get(position));
        StartActivityUtil.start(activity , this , JoinActActivity.class , bundle);
    }

    @Override
    public void onTextClick(int position, ActivityInfo.Act act , int type) {
        if(TextUtils.isEmpty(PrefUtil.getToken(context))){
            StartActivityUtil.start(activity ,this , LoginActivity.class , Constants.OPEN_LOGIN_ACTIVITY);
            return;
        }
        switch (type){
            case Constants.ACT_JOIN:
                Bundle bundle = new Bundle();
                bundle.putParcelable("act" , list.get(position));
                StartActivityUtil.start(activity , this , JoinActActivity.class , bundle);
                break;
            case Constants.ACT_LOCATION:
                Bundle bundle1 = new Bundle();
                bundle1.putDouble("lat" , act.getLat());
                bundle1.putDouble("lon" , act.getLon());
                bundle1.putString("place" , act.getActivityAddress());
                StartActivityUtil.start(activity , this , PlaceActivity.class , bundle1);
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
        presenter.getData((position+1)+"" , PrefUtil.getLon(context) , PrefUtil.getLat(context) , page+"" , pageSize+"" , userId);
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
        dialog.dismiss();
    }

    @Override
    public void success(String data) {
        dialog.dismiss();
        ToastUtil.showShortToast(context ,data);
    }

    @Override
    public void loading() {
        if (!isRefresh && !isLoading)
            loadingView.loading();
    }

    @Override
    public void networkError() {
        dialog.dismiss();
        loadingView.loadError();
        setRefreshLoading(false, false);
    }

    @Override
    public void joinError(String msg) {
        dialog.dismiss();
        ToastUtil.showShortToast(context , msg);
    }

    @Override
    public void error(String msg) {
        dialog.dismiss();
//        loadingView.loadError();
        setRefreshLoading(false, false);
    }

    @Override
    public void showFailed(String msg) {
        dialog.dismiss();
        loadingView.loadError();
        setRefreshLoading(false, false);
    }

}

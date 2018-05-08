package com.yapin.shanduo.ui.fragment;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.yapin.shanduo.R;
import com.yapin.shanduo.app.ShanDuoPartyApplication;
import com.yapin.shanduo.model.entity.ActivityInfo;
import com.yapin.shanduo.presenter.HomeActPresenter;
import com.yapin.shanduo.ui.adapter.ActivityInfoAdapter;
import com.yapin.shanduo.ui.contract.HomeActContract;
import com.yapin.shanduo.utils.Constants;
import com.yapin.shanduo.widget.LoadMoreRecyclerView;
import com.yapin.shanduo.widget.LoadingView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivityFragment extends Fragment implements ActivityInfoAdapter.OnItemClickListener ,LoadMoreRecyclerView.OnLoadMoreListener , HomeActContract.View{

    @BindView(R.id.recycler_view)
    LoadMoreRecyclerView recyclerView;

    private Context context;
    private Activity activity;
    private ActivityInfoAdapter adapter;
    private ShanDuoPartyApplication application;

    private LinearLayoutManager layoutManager;

    private int position;

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
        initView();
        return view;
    }

    public void initView(){
        context = ShanDuoPartyApplication.getContext();
        activity = getActivity();
        layoutManager = new LinearLayoutManager(context);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new ActivityInfoAdapter(context, activity);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);

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

    @Override
    public void onLoadMore() {

    }

    @Override
    public void show(List<ActivityInfo.Act> data, int totalPage) {

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

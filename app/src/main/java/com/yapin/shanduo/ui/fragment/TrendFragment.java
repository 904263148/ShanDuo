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
import com.yapin.shanduo.ui.adapter.ActivityInfoAdapter;
import com.yapin.shanduo.ui.adapter.TrendInfoAdapter;
import com.yapin.shanduo.utils.Constants;
import com.yapin.shanduo.widget.LoadMoreRecyclerView;
import com.yapin.shanduo.widget.LoadingView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TrendFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TrendFragment extends Fragment {

    @BindView(R.id.recycler_view)
    LoadMoreRecyclerView recyclerView;
    @BindView(R.id.loading_view)
    LoadingView loadingView;

    private Context context;
    private Activity activity;

    private TrendInfoAdapter adapter;
    private LinearLayoutManager layoutManager;

    private int position;

    private int page = 1;
    private int pageSize = 10;
    private boolean isRefresh = false;
    private boolean isLoading = false;

    public TrendFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static TrendFragment newInstance() {
        TrendFragment fragment = new TrendFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_trend, container, false);
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
        adapter = new TrendInfoAdapter(context, activity);
        recyclerView.setAdapter(adapter);
    }

}

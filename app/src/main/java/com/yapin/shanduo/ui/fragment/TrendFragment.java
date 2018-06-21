package com.yapin.shanduo.ui.fragment;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
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
import com.yapin.shanduo.model.entity.TrendInfo;
import com.yapin.shanduo.presenter.HomeActPresenter;
import com.yapin.shanduo.presenter.HomeTrendPresenter;
import com.yapin.shanduo.presenter.LikePresenter;
import com.yapin.shanduo.ui.activity.LoginActivity;
import com.yapin.shanduo.ui.activity.PlaceActivity;
import com.yapin.shanduo.ui.activity.TrendInfoActivity;
import com.yapin.shanduo.ui.activity.UserProfActivity;
import com.yapin.shanduo.ui.adapter.TrendInfoAdapter;
import com.yapin.shanduo.ui.contract.HomeTrendContract;
import com.yapin.shanduo.ui.contract.LikeContract;
import com.yapin.shanduo.utils.Constants;
import com.yapin.shanduo.utils.PrefUtil;
import com.yapin.shanduo.utils.StartActivityUtil;
import com.yapin.shanduo.utils.ToastUtil;
import com.yapin.shanduo.utils.Utils;
import com.yapin.shanduo.widget.LoadMoreRecyclerView;
import com.yapin.shanduo.widget.LoadingView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TrendFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TrendFragment extends Fragment implements HomeTrendContract.View , LoadMoreRecyclerView.OnLoadMoreListener , TrendInfoAdapter.OnItemClickListener , LikeContract.View{

    @BindView(R.id.recycler_view)
    LoadMoreRecyclerView recyclerView;
    @BindView(R.id.loading_view)
    LoadingView loadingView;

    private Context context;
    private Activity activity;

    private TrendInfoAdapter adapter;
    private LinearLayoutManager layoutManager;

    private int position;
    private String userId = "";

    private int page = 1;
    private int pageSize = 10;
    private boolean isRefresh = false;
    private boolean isLoading = false;

    private HomeTrendPresenter presenter;
    private LikePresenter likePresenter;

    private List<TrendInfo.Trend> list = new ArrayList<>();
    private TrendInfo.Trend footerItem = new TrendInfo.Trend();

    private int LikePosition = 0;

    public TrendFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static TrendFragment newInstance(int position , String userId) {
        TrendFragment fragment = new TrendFragment();
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

        View view = inflater.inflate(R.layout.fragment_trend, container, false);
        ButterKnife.bind(this , view);
        presenter = new HomeTrendPresenter();
        presenter.init(this);
        likePresenter = new LikePresenter();
        likePresenter.init(this);
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
        adapter = new TrendInfoAdapter(context, activity , list , position);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);

        page = 1;
        if (ContextCompat.checkSelfPermission(activity, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            //申请READ_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(activity, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                    Constants.REQUEST_CODE_CONTACT);
        }else {
            presenter.getData((position+1)+"" , PrefUtil.getLon(context) , PrefUtil.getLat(context) , page+"" , pageSize+"" , userId);
        }
        recyclerView.setOnLoadMoreListener(this);

        if(position == 3){
            recyclerView.setNestedScrollingEnabled(false);
        }
    }

    /**
     * 外部存储权限申请返回
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Constants.REQUEST_CODE_CONTACT) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission Granted
                Utils.setLocation(context);
                presenter.getData((position+1)+"" , "" , "" , page+"" , pageSize+"" , userId);
            }
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
            Intent intent = new Intent("trendRefreshComplete");
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
    public void success(String data) {
        if(Constants.IS_LIKE.equals(data)){
            ToastUtil.showShortToast(context , R.string.tips_like);
            list.get(LikePosition).setPraise( list.get(LikePosition).getPraise() + 1 );
        }else {
            ToastUtil.showShortToast(context , R.string.tips_unlike);
            if(list.get(LikePosition).getPraise() == 0) return;
            list.get(LikePosition).setPraise( list.get(LikePosition).getPraise() - 1 );
        }
        adapter.notifyDataSetChanged();
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
//        loadingView.loadError();
        if(TextUtils.isEmpty(PrefUtil.getToken(context))){
            loadingView.noData(R.string.tips_no_login);
        }
        setRefreshLoading(false, false);
    }

    @Override
    public void showFailed(String msg) {
        loadingView.loadError();
        setRefreshLoading(false, false);
    }

    @Override
    public void onItemClick(int position) {
        if(TextUtils.isEmpty(PrefUtil.getToken(context))){
            StartActivityUtil.start(activity , this , LoginActivity.class , Constants.OPEN_LOGIN_ACTIVITY);
        }else {
            Bundle bundle = new Bundle();
            bundle.putParcelable("trendInfo" , list.get(position));
            StartActivityUtil.start(activity , TrendInfoActivity.class , bundle);
        }
    }

    @Override
    public void onLikeClick(String id , int position) {
        if(TextUtils.isEmpty(PrefUtil.getToken(context))){
            StartActivityUtil.start(activity , this , LoginActivity.class , Constants.OPEN_LOGIN_ACTIVITY);
        }else {
            LikePosition = position;
            likePresenter.onLike(id);
        }
    }

    @Override
    public void onLocationClick(TrendInfo.Trend trend) {
        Bundle bundle1 = new Bundle();
        bundle1.putDouble("lat" , trend.getLat());
        bundle1.putDouble("lon" , trend.getLon());
        bundle1.putString("place" , trend.getLocation());
        StartActivityUtil.start(activity , this , PlaceActivity.class , bundle1);
    }

    @Override
    public void onHeadClick(int id) {
        Bundle bundle = new Bundle();
        bundle.putString("userId" , id+"");
        StartActivityUtil.start(activity , UserProfActivity.class , bundle);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}

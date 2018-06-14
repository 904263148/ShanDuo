package com.yapin.shanduo.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.zxing.common.StringUtils;
import com.yapin.shanduo.R;
import com.yapin.shanduo.app.ShanDuoPartyApplication;
import com.yapin.shanduo.model.entity.TrendInfo;
import com.yapin.shanduo.presenter.DeletedynamicPresenter;
import com.yapin.shanduo.presenter.LikePresenter;
import com.yapin.shanduo.presenter.MyDynamicsPresenter;
import com.yapin.shanduo.ui.adapter.MyDynamicsAdapter;
import com.yapin.shanduo.ui.contract.DeletedynamicContract;
import com.yapin.shanduo.ui.contract.LikeContract;
import com.yapin.shanduo.ui.contract.MyDynamicsContract;
import com.yapin.shanduo.ui.fragment.CustomBottomSheetDialogFragment;
import com.yapin.shanduo.utils.Constants;
import com.yapin.shanduo.utils.PrefUtil;
import com.yapin.shanduo.utils.StartActivityUtil;
import com.yapin.shanduo.utils.ToastUtil;
import com.yapin.shanduo.widget.LoadMoreRecyclerView;
import com.yapin.shanduo.widget.LoadingView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dell on 2018/5/3.
 */
//,MyDynamicsAdapter.onItemLongClick  MyDynamicsAdapter.OnItemClickListener ,
public class MyDynamicsActivity extends BaseActivity implements MyDynamicsContract.View ,
        LoadMoreRecyclerView.OnLoadMoreListener ,
        MyDynamicsAdapter.OnLikeClickListener ,  LikeContract.View , MyDynamicsAdapter.Onpopupwindow ,
        DeletedynamicContract.View{

    private Context context;
    private Activity activity;

    @BindView(R.id.recycler_my_view)
    LoadMoreRecyclerView recyclerView;
    @BindView(R.id.loading_my_view)
    LoadingView loadingView;
    @BindView(R.id.sr_refresh)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.ll_button)
    LinearLayout ll_button;
    @BindView(R.id.bt_edit)
    Button bt_edit;
    @BindView(R.id.bt_delete)
    Button bt_delete;
    private MyDynamicsAdapter adapter;
    private LinearLayoutManager layoutManager;
    private int position;

    private int page = 1;
    private int pageSize = 10;
    private boolean isRefresh = false;
    private boolean isLoading = false;

    private MyDynamicsPresenter presenter;
    private DeletedynamicPresenter deletedynamicPresenter;
    private LikePresenter likePresenter;
    private CustomBottomSheetDialogFragment fragment;

    private List<TrendInfo.Trend> list = new ArrayList<>();
    private TrendInfo.Trend footerItem = new TrendInfo.Trend();

    /**
     * 是否显示ｃｈｅｃｋｂｏｘ
     */
    private boolean isShowCheck;
    /**
     * 记录选中的ｃｈｅｃｋｂｏｘ
     */
    private List<String> checkList = new ArrayList<String>();
    private String string;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mydynamics);
        context = ShanDuoPartyApplication.getContext();
        activity = this;
        ButterKnife.bind(this );
        presenter = new MyDynamicsPresenter();
        presenter.init(this);
        deletedynamicPresenter = new DeletedynamicPresenter();
        deletedynamicPresenter.init(context , this);
        layoutManager = new LinearLayoutManager(context);
        fragment = new CustomBottomSheetDialogFragment();
        initView();
//        initListener();
    }

    @Override
    public void initView() {
        layoutManager = new LinearLayoutManager(context);
        footerItem.setType(Constants.TYPE_FOOTER_LOAD);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        if (adapter == null) {
            adapter = new MyDynamicsAdapter(context, activity, list);
            recyclerView.setAdapter(adapter);
//        }else {
//            adapter.notifyDataSetChanged();
//        }
//        adapter.setOnItemClickListener(this);
        adapter.setLikeClickListener(this);
        adapter.setOnpopupwindow(this);
//        adapter.setonItemLongClick(this);
//        ll_button.setVisibility(View.GONE);
        adapter.setonItemLongClick(new MyDynamicsAdapter.onItemLongClick() {
            @Override
            public boolean onItemLongClick(View view, int pos , String id) {
                if (isShowCheck) {
                    ll_button.setVisibility(View.GONE);
                    adapter.setShowCheckBox(false);
                    adapter.notifyDataSetChanged();
                    checkList.clear();
                } else {
                    adapter.setShowCheckBox(true);
                    adapter.notifyDataSetChanged();
                    ll_button.setVisibility(View.VISIBLE);
                }
                isShowCheck = !isShowCheck;
                return false;

            }

            @Override
            public void onCheckbox(View view, int pos , String id) {
                if (checkList.contains(String.valueOf(id))) {
                    checkList.remove(String.valueOf(id));
                } else {
                    checkList.add(String.valueOf(id));
                }
            }
        });
        if ( string == "删除成功"){
            adapter.notifyDataSetChanged();
        }
        adapter.notifyDataSetChanged();
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

    /**
     * 点击监听
     */
//    private void initListener() {
//
//    }

    @OnClick({R.id.tv_md_cancel , R.id.tv_md_Release ,R.id.bt_delete})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_md_cancel:
                finish();
                break;
            case R.id.tv_md_Release:
                StartActivityUtil.start(activity , PublishTrendActivity.class);
                break;
            case R.id.bt_delete:
//                String str = String.join("," ,checkList);
                StringBuilder result = new StringBuilder();
                boolean first = true;
                for (String string: checkList) {
                    if(first) {
                        first=false;
                    }else{
                        result.append(",");
                    }
                    result.append(string);
                }
                Log.i("deleteparamsstr", "load:----"+result);
                Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
                deletedynamicPresenter.Deletedynamic(result.toString());
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
        data = string;
//        adapter.notifyDataSetChanged();
        ll_button.setVisibility(View.GONE);
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        adapter.notifyDataSetChanged();
//    }

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

//    @Override
//    public void onItemClick(int position) {
//        Bundle bundle = new Bundle();
//        bundle.putParcelable("trendInfo" , list.get(position));
//        bundle.putInt("id" , 1);
//        StartActivityUtil.start(activity , TrendInfoActivity.class , bundle);
//    }

    @Override
    public void onLikeClick(String id) {

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onpopupwindow(int position, String Id) {
        fragment.show(getSupportFragmentManager() , Id);
        fragment.setType(1 , Id);
    }

//    @Override
//    public void onItemLongClick(View view, int position) {
////        if (actionMode == null) {
////            actionMode = startSupportActionMode(MainActivity.this);
////        }
//    }
}

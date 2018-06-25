package com.yapin.shanduo.ui.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
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
import com.yapin.shanduo.presenter.HomeActPresenter;
import com.yapin.shanduo.presenter.JoinActPresenter;
import com.yapin.shanduo.ui.activity.JoinActActivity;
import com.yapin.shanduo.ui.activity.LoginActivity;
import com.yapin.shanduo.ui.activity.PlaceActivity;
import com.yapin.shanduo.ui.activity.ScrollingActivity;
import com.yapin.shanduo.ui.activity.UserProfActivity;
import com.yapin.shanduo.ui.adapter.ActivityInfoAdapter;
import com.yapin.shanduo.ui.contract.HomeActContract;
import com.yapin.shanduo.ui.contract.JoinActContract;
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

public class ActivityFragment extends Fragment implements ActivityInfoAdapter.OnItemClickListener ,LoadMoreRecyclerView.OnLoadMoreListener , HomeActContract.View , JoinActContract.View{

    @BindView(R.id.recycler_view)
    LoadMoreRecyclerView recyclerView;
    @BindView(R.id.loading_view)
    LoadingView loadingView;

    private Context context;
    private Activity activity;
    private ActivityInfoAdapter adapter;
    private LinearLayoutManager layoutManager;

    private int position;
    private String userId;

    private int joinPosition;

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

    private final int OPEN_LOGIN_BY_JOIN = 1; //item点击和参加活动点击
    private final int OPEN_LOGIN_BY_CREDIT = 2; //信用轨迹点击
    private final int OPEN_LOGIN_BY_PLACE = 3; //地图点击
    private final int OPEN_LOGIN_BY_INFO = 4; //用户头像点击
    private int clickPosition = -1; //用于判断点击下标
    private String clickId; //点击item对应的用户id

    private Dialog loadDialog;//...加载

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
//        dialog = new ProgressDialog(activity);
//        dialog.setMessage("加载中...");
//        dialog.setCancelable(false);

        loadDialog = ToastUtil.showLoading(activity);

        joinPosition = position;
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
        page = 1;
        if (ContextCompat.checkSelfPermission(activity, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            //申请READ_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(activity, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},
                    Constants.REQUEST_CODE_CONTACT);
        }else {
            presenter.getData((position+1)+"" , PrefUtil.getLon(context) , PrefUtil.getLat(context) , page+"" , pageSize+"" , userId);
        }
        recyclerView.setOnLoadMoreListener(this);

        if(position == 6){
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
        clickPosition = position;
        if(TextUtils.isEmpty(PrefUtil.getToken(context))){
            StartActivityUtil.start(activity , this , LoginActivity.class , OPEN_LOGIN_BY_JOIN);
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putParcelable("act" , list.get(clickPosition));
        bundle.putInt("type" , joinPosition);
        StartActivityUtil.start(activity , this , JoinActActivity.class , bundle);
    }

    @Override
    public void onTextClick(int position, ActivityInfo.Act act , int type) {
        clickPosition = position;
        Bundle bundle = new Bundle();
        bundle.clear();
        Class openClass = null;
        switch (type){
            case Constants.ACT_JOIN:
                if(TextUtils.isEmpty(PrefUtil.getToken(context))){
                    StartActivityUtil.start(activity ,this , LoginActivity.class , OPEN_LOGIN_BY_JOIN);
                    return;
                }
                openClass = JoinActActivity.class;
                bundle.putParcelable("act" , list.get(position));
                bundle.putInt("type" , joinPosition);
                break;
            case Constants.ACT_LOCATION:
                if(TextUtils.isEmpty(PrefUtil.getToken(context))){
                    StartActivityUtil.start(activity ,this , LoginActivity.class , OPEN_LOGIN_BY_PLACE);
                    return;
                }
                openClass = PlaceActivity.class;
                bundle.putDouble("lat" , act.getLat());
                bundle.putDouble("lon" , act.getLon());
                bundle.putString("place" , act.getActivityAddress());
                break;
            case Constants.ACT_CREDIT:
                if(TextUtils.isEmpty(PrefUtil.getToken(context))){
                    StartActivityUtil.start(activity ,this , LoginActivity.class , OPEN_LOGIN_BY_CREDIT);
                    return;
                }
                openClass = ScrollingActivity.class;
                bundle.putString("userId" , act.getUserId()+"");
                break;
        }
        StartActivityUtil.start(activity , this , openClass , bundle);
    }

    @Override
    public void onHeadClick(int id) {
        clickId = id +"";
        if(TextUtils.isEmpty(PrefUtil.getToken(context))){
            StartActivityUtil.start(activity , this , LoginActivity.class , OPEN_LOGIN_BY_INFO);
        }else {
            Bundle bundle = new Bundle();
            bundle.putString("userId", clickId);
            StartActivityUtil.start(activity, UserProfActivity.class, bundle);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != activity.RESULT_OK) return;

        Bundle bundle = new Bundle();
        bundle.clear();
        Class openClass = null;
        switch (requestCode){
            case OPEN_LOGIN_BY_JOIN:
                openClass = JoinActActivity.class;
                bundle.putParcelable("act" , list.get(clickPosition));
                bundle.putInt("type" , joinPosition);
                break;
            case OPEN_LOGIN_BY_CREDIT:
                openClass = ScrollingActivity.class;
                bundle.putString("userId" , list.get(clickPosition).getUserId()+"");
                break;
            case OPEN_LOGIN_BY_PLACE:
                openClass = PlaceActivity.class;
                bundle.putDouble("lat" , list.get(clickPosition).getLat());
                bundle.putDouble("lon" , list.get(clickPosition).getLon());
                bundle.putString("place" , list.get(clickPosition).getActivityAddress());
                break;
            case OPEN_LOGIN_BY_INFO:
                openClass = UserProfActivity.class;
                bundle.putString("userId" , clickId);
                break;
        }
        StartActivityUtil.start(activity , this , openClass , bundle);
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
        loadDialog.dismiss();
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
    public void success(String data) {
        ToastUtil.showShortToast(context ,data);
    }

    @Override
    public void loading() {
//        if (!isRefresh && !isLoading)
//            loadDialog.show();
    }

    @Override
    public void networkError() {
        loadDialog.dismiss();
        loadingView.loadError();
        setRefreshLoading(false, false);
    }

    @Override
    public void joinError(String msg) {
        ToastUtil.showShortToast(context , msg);
    }

    @Override
    public void error(String msg) {
        loadDialog.dismiss();
        setRefreshLoading(false, false);
        ToastUtil.showShortToast(context , msg);
    }

    @Override
    public void showFailed(String msg) {
        loadDialog.dismiss();
        setRefreshLoading(false, false);
        ToastUtil.showShortToast(context , msg);
    }

}

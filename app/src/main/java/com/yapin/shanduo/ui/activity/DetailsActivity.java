package com.yapin.shanduo.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yapin.shanduo.R;
import com.yapin.shanduo.app.ShanDuoPartyApplication;
import com.yapin.shanduo.model.entity.ActivityInfo;
import com.yapin.shanduo.model.entity.JoinActUser;
import com.yapin.shanduo.presenter.JoinActPresenter;
import com.yapin.shanduo.presenter.JoinActUserPresenter;
import com.yapin.shanduo.ui.adapter.GridViewAdapter;
import com.yapin.shanduo.ui.contract.JoinActUserContract;
import com.yapin.shanduo.utils.ApiUtil;
import com.yapin.shanduo.utils.Constants;
import com.yapin.shanduo.utils.GlideUtil;
import com.yapin.shanduo.utils.ToastUtil;
import com.yapin.shanduo.widget.LoadingView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dell on 2018/5/31.
 */

public class DetailsActivity extends BaseActivity implements JoinActUserContract.View {

    @BindView(R.id.iv_head)
    ImageView ivHead;
    @BindView(R.id.tv_host)
    TextView tvHost;
    @BindView(R.id.tv_vip)
    TextView tvVip;
    @BindView(R.id.tv_home_age)
    TextView tvHomeAge;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_end_time)
    TextView tvEndTime;
    @BindView(R.id.rl_user_info)
    RelativeLayout rlUserInfo;
    @BindView(R.id.tv_kind)
    TextView tvKind;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.tv_place_tag)
    TextView tvPlaceTag;
    @BindView(R.id.tv_place)
    TextView tvPlace;
    @BindView(R.id.tv_mile)
    TextView tvMile;
    @BindView(R.id.tv_memo)
    TextView tvMemo;
    @BindView(R.id.iv_man)
    ImageView ivMan;
    @BindView(R.id.tv_man_count)
    TextView tvManCount;
    @BindView(R.id.iv_women)
    ImageView ivWomen;
    @BindView(R.id.tv_woman_count)
    TextView tvWomanCount;
    @BindView(R.id.loading_view)
    LoadingView loadingView;
    @BindView(R.id.gridview)
    GridView gridView;

    private Context context;
    private Activity activity;

    private ActivityInfo.Act act = new ActivityInfo.Act();

    private JoinActUserPresenter presenter;

    private GridViewAdapter adapter;

    private List<JoinActUser.ActUser> list = new ArrayList<>();

    private int isJoin = 0 ; // 0 未参加  1 已参加


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        context = ShanDuoPartyApplication.getContext();
        activity = this;
        ButterKnife.bind(this);
        presenter = new JoinActUserPresenter();
        presenter.init(this);

    }

    @Override
    public void initView() {

        act = getIntent().getParcelableExtra("act");

        tvKind.setText(act.getActivityName());
        tvTime.setText(act.getActivityStartTime());
        tvType.setText(act.getMode());
        tvManCount.setText(act.getManNumber());
        tvWomanCount.setText(act.getWomanNumber());
        tvHost.setText(act.getUserName());
        tvMemo.setText(act.getRemarks());
        tvPlace.setText(act.getActivityAddress());
        tvMile.setText(act.getLocation()+"km");
        tvEndTime.setText("报名截止日期:"+act.getActivityCutoffTime());
        GlideUtil.load(context , activity , ApiUtil.IMG_URL+ act.getHeadPortraitId() ,ivHead);

        Drawable drawable = null;
        if ("0".equals(act.getGender())) {
            drawable = activity.getResources().getDrawable(R.drawable.icon_women);
            tvHomeAge.setBackgroundResource(R.drawable.rounded_tv_sex_women);
        } else {
            drawable = activity.getResources().getDrawable(R.drawable.icon_men);
            tvHomeAge.setBackgroundResource(R.drawable.rounded_tv_sex_men);
        }
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        tvHomeAge.setCompoundDrawables(drawable, null, null, null);
        tvHomeAge.setCompoundDrawablePadding(2);
        tvHomeAge.setText(act.getAge()+"");

        int level = act.getVipGrade();
        if(level == 0){
            tvVip.setVisibility(View.GONE);
        }else if(level < 9){
            tvVip.setVisibility(View.VISIBLE);
            tvVip.setText("VIP"+level);
            tvVip.setBackgroundResource(R.drawable.rounded_tv_vip);
        }else {
            tvVip.setVisibility(View.VISIBLE);
            tvVip.setText("SVIP"+(level-10));
            tvVip.setBackgroundResource(R.drawable.rounded_tv_svip);
        }

        adapter = new GridViewAdapter(context , activity , list);
        gridView.setAdapter(adapter);
        presenter.getData(act.getId() , "1" , "10");
        loadingView.loading();
    }

    @OnClick({R.id.iv_back })
    public void onClick(View view){
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;

        }
    }

    @Override
    public void show(List<JoinActUser.ActUser> data, int totalPage) {
        if(totalPage == 0){
            loadingView.noData(R.string.tips_no_user_join);
            return;
        }else {
            loadingView.setGone();
        }
        list.addAll(data);
        adapter.notifyDataSetChanged();
        isJoin = list.get(0).getJoinActivity();

    }


//    @Override
//    public void success(String data) {
//        if("报名成功".equals(data)){
//            isJoin = Constants.JOIN_ACT;
//        }else {
//            isJoin = Constants.UNJOIN_ACT;
//        }
//        ToastUtil.showShortToast(context , data);
//    }

    @Override
    public void loading() {

    }

    @Override
    public void networkError() {
        loadingView.setGone();
    }

//    @Override
//    public void joinError(String msg) {
//        ToastUtil.showShortToast(context , msg);
//    }

    @Override
    public void error(String msg) {
        loadingView.setGone();
        ToastUtil.showShortToast(context ,msg);
    }

    @Override
    public void showFailed(String msg) {
        loadingView.setGone();
    }
}
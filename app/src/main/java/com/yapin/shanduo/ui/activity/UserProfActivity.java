package com.yapin.shanduo.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.yapin.shanduo.R;
import com.yapin.shanduo.app.ShanDuoPartyApplication;
import com.yapin.shanduo.model.entity.ShanDuoUserProf;
import com.yapin.shanduo.model.entity.ShanduoUser;
import com.yapin.shanduo.presenter.UserProfPresenter;
import com.yapin.shanduo.ui.adapter.UserProfTabAdapter;
import com.yapin.shanduo.ui.contract.UserProfContract;
import com.yapin.shanduo.ui.inter.OpenPopupWindow;
import com.yapin.shanduo.utils.ApiUtil;
import com.yapin.shanduo.utils.GlideUtil;
import com.yapin.shanduo.utils.ToastUtil;
import com.yapin.shanduo.utils.Utils;
import com.yapin.shanduo.widget.CircleImageView;
import com.yapin.shanduo.widget.LoadingView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserProfActivity extends BaseActivity implements OpenPopupWindow, PopupWindow.OnDismissListener , UserProfContract.View{

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.iv_head)
    ImageView ivHead;
    @BindView(R.id.tv_home_age)
    TextView tvHomeAge;
    @BindView(R.id.tv_vip)
    TextView tvVip;
    @BindView(R.id.tv_level)
    TextView tvLevel;
    @BindView(R.id.tv_friend_count)
    TextView tvFriendCount;
    @BindView(R.id.tv_act_count)
    TextView tvActCount;
    @BindView(R.id.tv_trend_count)
    TextView tvTrendCount;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.tv_add_friend)
    TextView tvAddFriend;
    @BindView(R.id.rl_bg)
    RelativeLayout rlBg;
    @BindView(R.id.loading_view)
    LoadingView loadingView;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_id)
    TextView tvId;
    @BindView(R.id.iv_head_bg)
    ImageView ivBg;

    private Context context;
    private Activity activity;

    private ImmersionBar mImmersionBar; //沉浸式

    private String userId;

    private UserProfTabAdapter adapter;
    private UserProfPresenter profPresenter;

    private ShanDuoUserProf user = new ShanDuoUserProf();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_prof);
        ButterKnife.bind(this);
        profPresenter = new UserProfPresenter();
        profPresenter.init(this);
    }

    @Override
    public void initView() {
        context = ShanDuoPartyApplication.getContext();
        activity = this;

        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.init();

        userId = getIntent().getStringExtra("userId");

        List<String> tabList = new ArrayList<>();
        tabList.add("TA的活动");
        tabList.add("TA的动态");

        adapter = new UserProfTabAdapter(getSupportFragmentManager(), tabList , userId);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                Utils.setIndicator(tabLayout , 20 , 20);
            }
        });

        profPresenter.getData(userId);
        loadingView.loading();
    }

    @OnClick({R.id.iv_back})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.iv_back:
                onBackPressed();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mImmersionBar != null)
            mImmersionBar.destroy();  //必须调用该方法，防止内存泄漏，不调用该方法，如果界面bar发生改变，在不关闭app的情况下，退出此界面再进入将记忆最后一次bar改变的状态
    }

    @Override
    public void onDismiss() {

    }

    @Override
    public void openPopupWindow(Object object, int type) {

    }

    @Override
    public void show(ShanDuoUserProf data) {
        user = data;
        refreshData();
    }

    public void refreshData(){
        GlideUtil.load(context , activity , ApiUtil.IMG_URL + user.getPicture() , ivHead);
        tvName.setText(user.getName());
        tvId.setText(user.getUserId()+"");
        Drawable drawable = null;
        if ("0".equals(user.getGender())) {
            drawable = activity.getResources().getDrawable(R.drawable.icon_women);
            tvHomeAge.setBackgroundResource(R.drawable.rounded_tv_sex_women);
        } else {
            drawable = activity.getResources().getDrawable(R.drawable.icon_men);
            tvHomeAge.setBackgroundResource(R.drawable.rounded_tv_sex_men);
        }
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        tvHomeAge.setCompoundDrawables(drawable, null, null, null);
        tvHomeAge.setCompoundDrawablePadding(2);
        tvHomeAge.setText(user.getAge() + "");
        
        int level = user.getVip();
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

        tvFriendCount.setText(user.getAttention()+"");
        tvActCount.setText(user.getActivity()+"");
        tvTrendCount.setText(user.getDynamic()+"");

        tvLevel.setText("LV"+user.getLevel());

        if(user.isAttention()){
            tvAddFriend.setText("发消息");
        }else {
            tvAddFriend.setText("加好友");
        }
        loadingView.setGone();
    }

    @Override
    public void loading() {
        loadingView.loading();
    }

    @Override
    public void networkError() {
        loadingView.loadError();
    }

    @Override
    public void error(String msg) {
        loadingView.setGone();
        ToastUtil.showShortToast(context , msg);
    }

    @Override
    public void showFailed(String msg) {
        loadingView.setGone();
        ToastUtil.showShortToast(context , msg);
    }
}

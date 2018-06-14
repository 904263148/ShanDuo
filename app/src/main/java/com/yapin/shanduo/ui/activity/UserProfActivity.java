package com.yapin.shanduo.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gyf.barlibrary.ImmersionBar;
import com.tencent.TIMConversationType;
import com.tencent.TIMFriendResult;
import com.tencent.TIMFriendStatus;
import com.tencent.TIMValueCallBack;
import com.tencent.qcloud.presentation.presenter.FriendshipManagerPresenter;
import com.tencent.qcloud.presentation.viewfeatures.FriendshipManageView;
import com.tencent.qcloud.ui.NotifyDialog;
import com.yapin.shanduo.R;
import com.yapin.shanduo.app.ShanDuoPartyApplication;
import com.yapin.shanduo.im.ui.AddFriendActivity;
import com.yapin.shanduo.im.ui.ChatActivity;
import com.yapin.shanduo.model.entity.ShanDuoUserProf;
import com.yapin.shanduo.model.entity.ShanduoUser;
import com.yapin.shanduo.presenter.AddFriendPresenter;
import com.yapin.shanduo.presenter.DeleteFriendPresenter;
import com.yapin.shanduo.presenter.UserProfPresenter;
import com.yapin.shanduo.ui.adapter.UserProfTabAdapter;
import com.yapin.shanduo.ui.contract.AddFriendContract;
import com.yapin.shanduo.ui.contract.DeleteFriendContract;
import com.yapin.shanduo.ui.contract.UserProfContract;
import com.yapin.shanduo.ui.inter.OpenPopupWindow;
import com.yapin.shanduo.utils.ApiUtil;
import com.yapin.shanduo.utils.Constants;
import com.yapin.shanduo.utils.GlideUtil;
import com.yapin.shanduo.utils.PrefJsonUtil;
import com.yapin.shanduo.utils.PrefUtil;
import com.yapin.shanduo.utils.StartActivityUtil;
import com.yapin.shanduo.utils.ToastUtil;
import com.yapin.shanduo.utils.Utils;
import com.yapin.shanduo.widget.CircleImageView;
import com.yapin.shanduo.widget.LoadingView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserProfActivity extends BaseActivity implements OpenPopupWindow, PopupWindow.OnDismissListener , UserProfContract.View , AddFriendContract.View , FriendshipManageView , DeleteFriendContract.View{

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
    @BindView(R.id.ll_add)
    LinearLayout llAdd;
    @BindView(R.id.tv_delete_friend)
    TextView tvDelete;

    private Context context;
    private Activity activity;

    private ImmersionBar mImmersionBar; //沉浸式

    private String userId;

    private UserProfTabAdapter adapter;
    private UserProfPresenter profPresenter;
    private FriendshipManagerPresenter presenter;
    private AddFriendPresenter addFriendPresenter;
    private DeleteFriendPresenter deleteFriendPresenter;

    private ShanDuoUserProf user = new ShanDuoUserProf();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_prof);
        ButterKnife.bind(this);
        profPresenter = new UserProfPresenter();
        profPresenter.init(this);
        addFriendPresenter = new AddFriendPresenter();
        addFriendPresenter.init(this);
        presenter = new FriendshipManagerPresenter(this);
        deleteFriendPresenter = new DeleteFriendPresenter();
        deleteFriendPresenter.init(this);
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

    @OnClick({R.id.iv_back , R.id.tv_add_friend , R.id.tv_delete_friend})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_add_friend:
                if(user.isAttention()){
                    ChatActivity.navToChat(activity , userId , TIMConversationType.C2C);
                }else {
                    addFriendPresenter.addFriend(userId);
//                    presenter.addFriend(userId, "", "" ,"");
                }
                break;
            case R.id.tv_delete_friend:
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setMessage(R.string.title_delete_friend)
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                return;
                            }
                        }).setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        deleteFriendPresenter.delete(userId , Constants.DELETE_FRIEND);
                    }
                }).create().show();
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
    public void onTitleHidden(float alpha) {

    }

    @Override
    public void show(ShanDuoUserProf data) {
        user = data;
        refreshData();
    }

    public void refreshData(){
        GlideUtil.load(context , activity , ApiUtil.IMG_URL + user.getPicture() , ivHead);
        tvName.setText(Utils.unicodeToString(user.getName()));
        tvId.setText(user.getUserId()+"");
        Drawable drawable = null;
        if ("0".equals(user.getGender())) {
            drawable = activity.getResources().getDrawable(R.drawable.icon_women);
            tvHomeAge.setBackgroundResource(R.drawable.rounded_tv_sex_women);
            rlBg.setBackgroundResource(R.drawable.icon_women_bg);
        } else {
            drawable = activity.getResources().getDrawable(R.drawable.icon_men);
            tvHomeAge.setBackgroundResource(R.drawable.rounded_tv_sex_men);
            rlBg.setBackgroundResource(R.drawable.icon_man_bg);
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
        loadingView.setGone();

        if(userId.equals(PrefJsonUtil.getProfile(context).getUserId())){
            llAdd.setVisibility(View.GONE);
        }else {
            if(user.isAttention()){
                tvAddFriend.setText("发消息");
                tvDelete.setVisibility(View.VISIBLE);
            }else {
                tvAddFriend.setText("加好友");
                tvDelete.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void show(String data) {
        if(Constants.ADD_DIRECTLY.equals(data)){
            ToastUtil.showShortToast(context , R.string.add_friend_added);
            user.setAttention(true);
            tvAddFriend.setText("发消息");
        }else if (Constants.ADD_APPLY.equals(data)) {
            ToastUtil.showShortToast(context , R.string.add_friend_succeed);
        }
    }

    @Override
    public void deleteSuccess(String data) {
//        presenter.delFriend(userId);
        Toast.makeText(this, getResources().getString(R.string.profile_del_succeed), Toast.LENGTH_SHORT).show();
        StartActivityUtil.start(activity , MainActivity.class);
        onBackPressed();
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

    /**
     * 添加好友结果回调
     *
     * @param status 返回状态
     */
    @Override
    public void onAddFriend(TIMFriendStatus status) {
        switch (status){
            case TIM_ADD_FRIEND_STATUS_PENDING:
                Toast.makeText(this, getResources().getString(R.string.add_friend_succeed), Toast.LENGTH_SHORT).show();
                finish();
                break;
            case TIM_FRIEND_STATUS_SUCC:
//                Toast.makeText(this, getResources().getString(R.string.add_friend_added), Toast.LENGTH_SHORT).show();
//                finish();
                addFriendPresenter.addFriend(userId);
                break;
            case TIM_ADD_FRIEND_STATUS_FRIEND_SIDE_FORBID_ADD:
                Toast.makeText(this, getResources().getString(R.string.add_friend_refuse_all), Toast.LENGTH_SHORT).show();
                finish();
                break;
            case TIM_ADD_FRIEND_STATUS_IN_OTHER_SIDE_BLACK_LIST:
                Toast.makeText(this, getResources().getString(R.string.add_friend_to_blacklist), Toast.LENGTH_SHORT).show();
                finish();
                break;
            case TIM_ADD_FRIEND_STATUS_IN_SELF_BLACK_LIST:
                NotifyDialog dialog = new NotifyDialog();
                dialog.show(getString(R.string.add_friend_del_black_list), getSupportFragmentManager(), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FriendshipManagerPresenter.delBlackList(Collections.singletonList(userId), new TIMValueCallBack<List<TIMFriendResult>>() {
                            @Override
                            public void onError(int i, String s) {
                                Toast.makeText(UserProfActivity.this, getResources().getString(R.string.add_friend_del_black_err), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onSuccess(List<TIMFriendResult> timFriendResults) {
                                Toast.makeText(UserProfActivity.this, getResources().getString(R.string.add_friend_del_black_succ), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                break;
            default:
                Toast.makeText(this, getResources().getString(R.string.add_friend_error), Toast.LENGTH_SHORT).show();
                break;
        }

    }

    /**
     * 删除好友结果回调
     *
     * @param status 返回状态
     */
    @Override
    public void onDelFriend(TIMFriendStatus status) {
        switch (status){
            case TIM_FRIEND_STATUS_SUCC:
                Toast.makeText(this, getResources().getString(R.string.profile_del_succeed), Toast.LENGTH_SHORT).show();
                StartActivityUtil.start(activity , MainActivity.class);
                onBackPressed();
                break;
            case TIM_FRIEND_STATUS_UNKNOWN:
                Toast.makeText(this, getResources().getString(R.string.profile_del_fail), Toast.LENGTH_SHORT).show();
                break;
        }
    }

    /**
     * 修改好友分组回调
     *
     * @param status    返回状态
     * @param groupName 分组名
     */
    @Override
    public void onChangeGroup(TIMFriendStatus status, String groupName) {

    }
}

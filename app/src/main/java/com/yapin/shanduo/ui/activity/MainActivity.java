package com.yapin.shanduo.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationViewPager;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.Types.BoomType;
import com.nightonke.boommenu.Types.ButtonType;
import com.nightonke.boommenu.Types.PlaceType;
import com.yapin.shanduo.R;
import com.yapin.shanduo.app.ShanDuoPartyApplication;
import com.yapin.shanduo.model.entity.ActivityInfo;
import com.yapin.shanduo.model.entity.TrendInfo;
import com.yapin.shanduo.ui.adapter.ViewPagerAdapter;
import com.yapin.shanduo.ui.fragment.CustomBottomSheetDialogFragment;
import com.yapin.shanduo.ui.fragment.HomeFragment;
import com.yapin.shanduo.ui.inter.OpenPopupWindow;
import com.yapin.shanduo.ui.inter.RefreshAll;
import com.yapin.shanduo.utils.Constants;
import com.yapin.shanduo.utils.PrefUtil;
import com.yapin.shanduo.utils.StartActivityUtil;
import com.yapin.shanduo.utils.ToastUtil;
import com.yapin.shanduo.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;

public class MainActivity extends AppCompatActivity implements OpenPopupWindow, PopupWindow.OnDismissListener,
        View.OnClickListener, PlatformActionListener , RefreshAll {

    @BindView(R.id.iv_home)
    ImageView ivHome;
    @BindView(R.id.tv_home)
    TextView tvHome;
    @BindView(R.id.ll_home)
    LinearLayout llHome;
    @BindView(R.id.iv_chat)
    ImageView ivChat;
    @BindView(R.id.tv_chat)
    TextView tvChat;
    @BindView(R.id.ll_chat)
    LinearLayout llChat;
    @BindView(R.id.iv_linkman)
    ImageView ivLinkman;
    @BindView(R.id.tv_linkman)
    TextView tvLinkman;
    @BindView(R.id.ll_linkman)
    LinearLayout llLinkman;
    @BindView(R.id.iv_my)
    ImageView ivMy;
    @BindView(R.id.tv_my)
    TextView tvMy;
    @BindView(R.id.ll_my)
    LinearLayout llMy;
    @BindView(R.id.iv_add)
    ImageView ivAdd;
    @BindView(R.id.tv_add)
    TextView tvAdd;

    private Context context;
    private Activity activity;

    private PopupWindow publishPopupWindow;
    private View publishPopView;

    @BindView(R.id.view_pager)
    AHBottomNavigationViewPager viewPager;
    @BindView(R.id.bottom_navigation)
    AHBottomNavigation bottomNavigation;
    @BindView(R.id.share_tag)
    TextView tag;

    private ArrayList<AHBottomNavigationItem> bottomNavigationItems = new ArrayList<>();

    private final int PUBLISH_ACT_OPEN_LOGIN = 1;//
    private final int PUBLISH_TREND_OPEN_LOGIN = 2;//
    private final int OPEN_LOGIN_BY_ACT_SHARE = 4; // ..活动分享点击
    private final int OPEN_LOGIN_BY_TREND_SHARE = 5; // ..动态分享点击
    private Object clickObject = null; // ..分享点击的实体类

    private final int OPEN_OTHER_ACTIVITY = 3;

    private ViewPagerAdapter adapter;

    private CustomBottomSheetDialogFragment fragment;

    private HomeFragment homeFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //设置PopupWindow的View
        publishPopView = LayoutInflater.from(this).inflate(R.layout.publish_popupwindow , null);

        initView();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent == null)
            return;
        setIntent(intent);
        initView();
    }


    public void initView() {
        context = ShanDuoPartyApplication.getContext();
        activity = this;

        Utils.checkPermission(activity);
        Utils.setLocation(context);

        viewPager.setCurrentItem(0, false);
        viewPager.setOffscreenPageLimit(5);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        fragment = new CustomBottomSheetDialogFragment();

        homeFragment = (HomeFragment) adapter.getItem(0);

    }

    @OnClick({R.id.ll_home , R.id.ll_chat , R.id.rl_add ,R.id.ll_linkman , R.id.ll_my})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.ll_home:
                viewPager.setCurrentItem(0, false);
                tvAdd.setTextColor(getResources().getColor(R.color.home_title_color_uncheck));
                ivHome.setImageResource(R.drawable.icon_home);
                tvHome.setTextColor(getResources().getColor(R.color.home_title_select_color));
                ivChat.setImageResource(R.drawable.icon_chat_unselect);
                tvChat.setTextColor(getResources().getColor(R.color.home_title_color_uncheck));
                ivLinkman.setImageResource(R.drawable.icon_friend_unselect);
                tvLinkman.setTextColor(getResources().getColor(R.color.home_title_color_uncheck));
                ivMy.setImageResource(R.drawable.icon_my_unselect);
                tvMy.setTextColor(getResources().getColor(R.color.home_title_color_uncheck));
                break;
            case R.id.ll_chat:
                viewPager.setCurrentItem(1, false);
                tvAdd.setTextColor(getResources().getColor(R.color.home_title_color_uncheck));
                ivHome.setImageResource(R.drawable.icon_home_unselect);
                tvHome.setTextColor(getResources().getColor(R.color.home_title_color_uncheck));
                ivChat.setImageResource(R.drawable.icon_chat);
                tvChat.setTextColor(getResources().getColor(R.color.home_title_select_color));
                ivLinkman.setImageResource(R.drawable.icon_friend_unselect);
                tvLinkman.setTextColor(getResources().getColor(R.color.home_title_color_uncheck));
                ivMy.setImageResource(R.drawable.icon_my_unselect);
                tvMy.setTextColor(getResources().getColor(R.color.home_title_color_uncheck));
                break;
            case R.id.rl_add:
                tvAdd.setTextColor(getResources().getColor(R.color.home_title_select_color));

//                Animation rotateAnimation = AnimationUtils.loadAnimation(this, R.anim.publish_35);
//                rotateAnimation.setFillAfter(true);//设置为true，动画转化结束后被应用
//                ivAdd.startAnimation(rotateAnimation);
//                isPublish = true;
                openPublishPopup();

                break;
            case R.id.ll_linkman:
                viewPager.setCurrentItem(3, false);
                tvAdd.setTextColor(getResources().getColor(R.color.home_title_color_uncheck));
                ivHome.setImageResource(R.drawable.icon_home_unselect);
                tvHome.setTextColor(getResources().getColor(R.color.home_title_color_uncheck));
                ivChat.setImageResource(R.drawable.icon_chat_unselect);
                tvChat.setTextColor(getResources().getColor(R.color.home_title_color_uncheck));
                ivLinkman.setImageResource(R.drawable.icon_friend);
                tvLinkman.setTextColor(getResources().getColor(R.color.home_title_select_color));
                ivMy.setImageResource(R.drawable.icon_my_unselect);
                tvMy.setTextColor(getResources().getColor(R.color.home_title_color_uncheck));
                break;
            case R.id.ll_my:
                viewPager.setCurrentItem(4, false);
                tvAdd.setTextColor(getResources().getColor(R.color.home_title_color_uncheck));
                ivHome.setImageResource(R.drawable.icon_home_unselect);
                tvHome.setTextColor(getResources().getColor(R.color.home_title_color_uncheck));
                ivChat.setImageResource(R.drawable.icon_chat_unselect);
                tvChat.setTextColor(getResources().getColor(R.color.home_title_color_uncheck));
                ivLinkman.setImageResource(R.drawable.icon_friend_unselect);
                tvLinkman.setTextColor(getResources().getColor(R.color.home_title_color_uncheck));
                ivMy.setImageResource(R.drawable.icon_my);
                tvMy.setTextColor(getResources().getColor(R.color.home_title_select_color));
                break;
            case R.id.ll_publish_act:
                if(TextUtils.isEmpty(PrefUtil.getToken(context))){
                    StartActivityUtil.start(activity , LoginActivity.class , PUBLISH_ACT_OPEN_LOGIN);
                }else {
                    StartActivityUtil.start(activity , AddactivityActivity.class , OPEN_OTHER_ACTIVITY);
                }
                break;
            case R.id.ll_publish_trend:
                if(TextUtils.isEmpty(PrefUtil.getToken(context))){
                    StartActivityUtil.start(activity , LoginActivity.class , PUBLISH_TREND_OPEN_LOGIN);
                }else {
                    StartActivityUtil.start(activity , PublishTrendActivity.class , OPEN_OTHER_ACTIVITY);
                }
                break;
            case R.id.iv_dismiss:
                publishPopupWindow.dismiss();
                break;
        }
    }

    @Override
    public void openPopupWindow(Object object , int type) {
        clickObject = object;
        switch (type){
            case Constants.HOME_ACT:
                if(TextUtils.isEmpty(PrefUtil.getToken(context))){
                    StartActivityUtil.start(activity, LoginActivity.class , OPEN_LOGIN_BY_ACT_SHARE);
                    return;
                }
                ActivityInfo.Act act= (ActivityInfo.Act) object;
                fragment.show(getSupportFragmentManager() , act.getUserId()+"");
                fragment.setType(0 , act.getId());
                break;
            case Constants.HOME_TREND:
                if(TextUtils.isEmpty(PrefUtil.getToken(context))){
                    StartActivityUtil.start(activity, LoginActivity.class , OPEN_LOGIN_BY_TREND_SHARE);
                    return;
                }
                TrendInfo.Trend trend = (TrendInfo.Trend) object;
                fragment.show(getSupportFragmentManager() , trend.getUserId()+"");
                fragment.setType(1 , trend.getId());
                break;
        }
    }

    @Override
    public void onTitleHidden(float alpha) {
        homeFragment.setTitleHidden(alpha);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

//        boomMenuButton.init(
//                new Drawable[]{ R.drawable.icon_trend_publish, R.drawable.icon_trend_publish}, // 子按钮的图标Drawable数组，不可以为null
//                new String [] {"" ,""},     // 子按钮的文本String数组，可以为null
//                new int [][] {new int [] {R.color.white ,R.color.bg_color} , new int [] {R.color.white ,R.color.bg_color}},    // 子按钮的背景颜色color二维数组，包括按下和正常状态的颜色，不可为null
//                ButtonType.HAM,     // 子按钮的类型
//                BoomType.PARABOLA,  // 爆炸类型
//                PlaceType.CIRCLE_2_2,  // 排列类型
//                null,               // 展开时子按钮移动的缓动函数类型
//                null,               // 展开时子按钮放大的缓动函数类型
//                null,               // 展开时子按钮旋转的缓动函数类型
//                null,               // 隐藏时子按钮移动的缓动函数类型
//                null,               // 隐藏时子按钮缩小的缓动函数类型
//                null,               // 隐藏时子按钮旋转的缓动函数类型
//                null                // 旋转角度
//        );
    }

    public void openPublishPopup(){
        publishPopupWindow = new PopupWindow(publishPopView, LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        //设置背景,这个没什么效果，不添加会报错
        publishPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        //设置点击弹窗外隐藏自身
        publishPopupWindow.setFocusable(true);
        publishPopupWindow.setOutsideTouchable(true);
        //设置动画
        publishPopupWindow.setAnimationStyle(R.style.PopupWindow);
        //设置位置
        publishPopupWindow.showAtLocation(ivAdd, Gravity.BOTTOM, 0, 0);
        //设置消失监听
        publishPopupWindow.setOnDismissListener(this);
        //设置背景色
        setBackgroundAlpha(1f);

        publishPopView.findViewById(R.id.ll_publish_act).setOnClickListener(this);
        publishPopView.findViewById(R.id.ll_publish_trend).setOnClickListener(this);
        publishPopView.findViewById(R.id.iv_dismiss).setOnClickListener(this);
    }

    //设置屏幕背景透明效果
    public void setBackgroundAlpha(float alpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = alpha;
        getWindow().setAttributes(lp);
    }

    @Override
    public void onDismiss() {
        setBackgroundAlpha(1);
        tvAdd.setTextColor(getResources().getColor(R.color.home_title_color_uncheck));
//        if(isPublish){
//            Animation rotateAnimation = AnimationUtils.loadAnimation(this, R.anim.publish_45);
//            rotateAnimation.setFillAfter(true);//设置为true，动画转化结束后被应用
//            ivAdd.startAnimation(rotateAnimation);
//            isPublish = false;
//        }
    }

    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
        //失败的回调，arg:平台对象，arg1:表示当前的动作，arg2:异常信息
        Log.d("shareSDK" , hashMap.toString());
    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {
        //分享成功的回调
        Log.d("shareSDK" , throwable.toString());
    }

    @Override
    public void onCancel(Platform platform, int i) {
        //取消分享的回调
        Log.d("shareSDK" , "取消了~");
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(publishPopupWindow != null){
            if(publishPopupWindow.isShowing()) {
                publishPopupWindow.dismiss();
                tvAdd.setTextColor(getResources().getColor(R.color.home_title_color_uncheck));
            }
        }
//        setBackgroundAlpha(1);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //设置背景色
//        setBackgroundAlpha(0.5f);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != RESULT_OK){
            return;
        }
        if(publishPopupWindow != null){
            if(publishPopupWindow.isShowing()) {
                publishPopupWindow.dismiss();
            }
        }
        switch (requestCode){
            case PUBLISH_ACT_OPEN_LOGIN:
                StartActivityUtil.start(activity , AddactivityActivity.class);
                break;
            case PUBLISH_TREND_OPEN_LOGIN:
                StartActivityUtil.start(activity , PublishTrendActivity.class);
                break;
            case OPEN_LOGIN_BY_ACT_SHARE:
                ActivityInfo.Act act= (ActivityInfo.Act) clickObject;
                fragment.show(getSupportFragmentManager() , act.getUserId()+"");
                fragment.setType(0 , act.getId());
                break;
            case OPEN_LOGIN_BY_TREND_SHARE:
                TrendInfo.Trend trend = (TrendInfo.Trend) clickObject;
                fragment.show(getSupportFragmentManager() , trend.getUserId()+"");
                fragment.setType(1 , trend.getId());
                break;
        }
    }

    @Override
    public void refresh() {
        adapter.notifyDataSetChanged();
    }

    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            if (publishPopupWindow != null && publishPopupWindow.isShowing()) {
                publishPopupWindow.dismiss();
            } else {

                if ((System.currentTimeMillis() - exitTime) > 2000) {
                    ToastUtil.showShortToast(context, R.string.click_back);
                    exitTime = System.currentTimeMillis();
                } else {
                    finish();
                    System.exit(0);
                }
                return true;
            }
        }

        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            if((System.currentTimeMillis()-exitTime) > 2000){
                ToastUtil.showShortToast(context , R.string.click_back);
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

}

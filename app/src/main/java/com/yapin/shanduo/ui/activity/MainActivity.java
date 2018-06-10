package com.yapin.shanduo.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationViewPager;
import com.tencent.TIMConversation;
import com.tencent.TIMConversationType;
import com.tencent.TIMManager;
import com.tencent.TIMMessage;
import com.tencent.TIMValueCallBack;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.open.utils.Util;
import com.yapin.shanduo.R;
import com.yapin.shanduo.app.ShanDuoPartyApplication;
import com.yapin.shanduo.model.entity.ActivityInfo;
import com.yapin.shanduo.model.entity.SigninInfo;
import com.yapin.shanduo.model.entity.TrendInfo;
import com.yapin.shanduo.presenter.CheckcheckinPresenter;
import com.yapin.shanduo.presenter.SigninPresenter;
import com.yapin.shanduo.ui.adapter.ViewPagerAdapter;
import com.yapin.shanduo.ui.contract.CheckcheckinContract;
import com.yapin.shanduo.ui.contract.SigninContract;
import com.yapin.shanduo.ui.fragment.ChatFragment;
import com.yapin.shanduo.ui.fragment.CustomBottomSheetDialogFragment;
import com.yapin.shanduo.ui.fragment.HomeFragment;
import com.yapin.shanduo.ui.inter.OpenPopupWindow;
import com.yapin.shanduo.ui.inter.RefreshAll;
import com.yapin.shanduo.utils.Constants;
import com.yapin.shanduo.utils.PrefUtil;
import com.yapin.shanduo.utils.StartActivityUtil;
import com.yapin.shanduo.utils.ToastUtil;
import com.yapin.shanduo.utils.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;
import cn.sharesdk.wechat.utils.WXImageObject;
import cn.sharesdk.wechat.utils.WXMediaMessage;

public class MainActivity extends AppCompatActivity implements OpenPopupWindow, PopupWindow.OnDismissListener,
        View.OnClickListener, PlatformActionListener , RefreshAll ,SigninContract.View ,CheckcheckinContract.View{

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

    private boolean isPublish = false;

    private final int PUBLISH_ACT_OPEN_LOGIN = 1;
    private final int PUBLISH_TREND_OPEN_LOGIN = 2;

    private final int OPEN_OTHER_ACTIVITY = 3;

    private ViewPagerAdapter adapter;

    private CustomBottomSheetDialogFragment fragment;

    private HomeFragment homeFragment;

    private SigninPresenter presenter;
    private CheckcheckinPresenter checkcheckinPresenter;

    private SigninInfo signinInfo = new SigninInfo();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        presenter = new SigninPresenter();
        presenter.init(context,this);

        checkcheckinPresenter = new CheckcheckinPresenter();
        checkcheckinPresenter.init(context,this);

        if(TextUtils.isEmpty(PrefUtil.getToken(context))){

        }else {
            presenter.getsignin();
        }

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
    //签到弹窗
    public void initSignin(){
        if (signinInfo.isSignin()==false){
            AlertDialog.Builder builder2 = new AlertDialog.Builder(activity);
            //    通过LayoutInflater来加载一个xml的布局文件作为一个View对象
            View v = LayoutInflater.from(activity).inflate(R.layout.activity_signin, null);
            //    设置我们自己定义的布局文件作为弹出框的Content
            builder2.setView(v);
            final AlertDialog dialog = builder2.show();
            //去除边框
            dialog.getWindow().setLayout(650, LinearLayout.LayoutParams.WRAP_CONTENT);
            TextView tv_number = (TextView) v.findViewById(R.id.tv_number);
            TextView tv_Determine = (TextView) v.findViewById(R.id.tv_Determine);
            TextView tv_level = (TextView) v.findViewById(R.id.tv_level);
            TextView tv_experience = (TextView) v.findViewById(R.id.tv_experience);
            ImageView iv_back = (ImageView) v.findViewById(R.id.iv_back);
            tv_number.setText(signinInfo.getCount()+"");
            String count = tv_number.getText().toString().trim();
            if ("1".equals(count)){
                tv_experience.setText("+10点经验");
            }else if ("2".equals(count)){
                tv_experience.setText("+10闪多豆");
            }else if ("3".equals(count)){
                tv_experience.setText("+15点经验");
            }else if ("4".equals(count)){
                tv_experience.setText("+15闪多豆");
            }else if ("5".equals(count)){
                tv_experience.setText("+20点经验");
            }else if ("6".equals(count)){
                tv_experience.setText("+25点经验");
            }else if ("7".equals(count)){
                tv_experience.setText("+20闪多豆");
            }
                    iv_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            tv_Determine.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    checkcheckinPresenter.setcheck();
                    dialog.dismiss();
                }
            });

        }else if (signinInfo.isSignin() ==  true){

        }
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

//        publishPopView.findViewById(R.id.ll_publish_act).setOnClickListener(this);
//        publishPopView.findViewById(R.id.ll_publish_trend).setOnClickListener(this);

        fragment = new CustomBottomSheetDialogFragment();

        homeFragment = (HomeFragment) adapter.getItem(0);

    }

    @OnClick({R.id.ll_home , R.id.ll_chat , R.id.rl_add ,R.id.ll_linkman , R.id.ll_my})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.ll_home:
                viewPager.setCurrentItem(0, false);
                tvAdd.setTextColor(getResources().getColor(R.color.font_color_gray));
                ivHome.setImageResource(R.drawable.icon_home);
                tvHome.setTextColor(getResources().getColor(R.color.home_title_select_color));
                ivChat.setImageResource(R.drawable.icon_chat_unselect);
                tvChat.setTextColor(getResources().getColor(R.color.font_color_gray));
                ivLinkman.setImageResource(R.drawable.icon_friend_unselect);
                tvLinkman.setTextColor(getResources().getColor(R.color.font_color_gray));
                ivMy.setImageResource(R.drawable.icon_my_unselect);
                tvMy.setTextColor(getResources().getColor(R.color.font_color_gray));
                break;
            case R.id.ll_chat:
                viewPager.setCurrentItem(1, false);
                tvAdd.setTextColor(getResources().getColor(R.color.font_color_gray));
                ivHome.setImageResource(R.drawable.icon_home_unselect);
                tvHome.setTextColor(getResources().getColor(R.color.font_color_gray));
                ivChat.setImageResource(R.drawable.icon_chat);
                tvChat.setTextColor(getResources().getColor(R.color.home_title_select_color));
                ivLinkman.setImageResource(R.drawable.icon_friend_unselect);
                tvLinkman.setTextColor(getResources().getColor(R.color.font_color_gray));
                ivMy.setImageResource(R.drawable.icon_my_unselect);
                tvMy.setTextColor(getResources().getColor(R.color.font_color_gray));
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
                tvAdd.setTextColor(getResources().getColor(R.color.font_color_gray));
                ivHome.setImageResource(R.drawable.icon_home_unselect);
                tvHome.setTextColor(getResources().getColor(R.color.font_color_gray));
                ivChat.setImageResource(R.drawable.icon_chat_unselect);
                tvChat.setTextColor(getResources().getColor(R.color.font_color_gray));
                ivLinkman.setImageResource(R.drawable.icon_friend);
                tvLinkman.setTextColor(getResources().getColor(R.color.home_title_select_color));
                ivMy.setImageResource(R.drawable.icon_my_unselect);
                tvMy.setTextColor(getResources().getColor(R.color.font_color_gray));
                break;
            case R.id.ll_my:
                viewPager.setCurrentItem(4, false);
                tvAdd.setTextColor(getResources().getColor(R.color.font_color_gray));
                ivHome.setImageResource(R.drawable.icon_home_unselect);
                tvHome.setTextColor(getResources().getColor(R.color.font_color_gray));
                ivChat.setImageResource(R.drawable.icon_chat_unselect);
                tvChat.setTextColor(getResources().getColor(R.color.font_color_gray));
                ivLinkman.setImageResource(R.drawable.icon_friend_unselect);
                tvLinkman.setTextColor(getResources().getColor(R.color.font_color_gray));
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
        }
    }

    @Override
    public void openPopupWindow(Object object , int type) {

        switch (type){
            case Constants.HOME_ACT:
                ActivityInfo.Act act= (ActivityInfo.Act) object;
                fragment.show(getSupportFragmentManager() , act.getId());
                fragment.setType(0 , act.getId());
                break;
            case Constants.HOME_TREND:
                TrendInfo.Trend trend = (TrendInfo.Trend) object;
                fragment.show(getSupportFragmentManager() , trend.getId());
                fragment.setType(1 , trend.getId());
                break;
        }


    }

    @Override
    public void onTitleHidden(float alpha) {
        homeFragment.setTitleHidden(alpha);
    }

    public void openPublishPopup(){
        publishPopupWindow = new PopupWindow(publishPopView, LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        //设置背景,这个没什么效果，不添加会报错
        publishPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        //设置点击弹窗外隐藏自身
        publishPopupWindow.setFocusable(true);
        publishPopupWindow.setOutsideTouchable(true);
        //设置动画
        publishPopupWindow.setAnimationStyle(R.style.PopupWindow);
        //设置位置
        publishPopupWindow.showAtLocation(ivAdd, Gravity.BOTTOM, 0, 200);
        //设置消失监听
        publishPopupWindow.setOnDismissListener(this);
        //设置背景色
        setBackgroundAlpha(0.5f);

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


    @Override
    public void success(SigninInfo data) {
        signinInfo = data;
        initSignin();
    }

    @Override
    public void success(String data) {
        ToastUtil.showShortToast(context,data);
    }

    @Override
    public void loading() {

    }

    @Override
    public void networkError() {
        ToastUtil.showShortToast(context,"网络连接异常");
    }

    @Override
    public void error(String msg) {
        ToastUtil.showShortToast(context,msg);
    }

    @Override
    public void showFailed(String msg) {

    }
}

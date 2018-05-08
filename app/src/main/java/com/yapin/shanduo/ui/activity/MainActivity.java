package com.yapin.shanduo.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationViewPager;
import com.yapin.shanduo.R;
import com.yapin.shanduo.app.ShanDuoPartyApplication;
import com.yapin.shanduo.ui.adapter.ViewPagerAdapter;
import com.yapin.shanduo.ui.inter.OpenPopupWindow;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

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

public class MainActivity extends BaseActivity implements OpenPopupWindow , PopupWindow.OnDismissListener , View.OnClickListener , PlatformActionListener{

    private Context context;
    private Activity activity;

    private PopupWindow popupWindow;
    private View popView;

    @BindView(R.id.view_pager)
    AHBottomNavigationViewPager viewPager;
    @BindView(R.id.bottom_navigation)
    AHBottomNavigation bottomNavigation;
    @BindView(R.id.share_tag)
    TextView tag;

    private ArrayList<AHBottomNavigationItem> bottomNavigationItems = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //设置PopupWindow的View
        popView = LayoutInflater.from(this).inflate(R.layout.share_popup, null);

        initView();

        if (Build.VERSION.SDK_INT >= 23) {
            int REQUEST_CODE_CONTACT = 101;
            String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
            //验证是否许可权限
            for (String str : permissions) {
                if (this.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                    //申请权限
                    this.requestPermissions(permissions, REQUEST_CODE_CONTACT);
                    return;
                }
            }
        }

    }

    private void initView(){

        context = ShanDuoPartyApplication.getContext();
        activity = this;

        AHBottomNavigationItem item1 = new AHBottomNavigationItem("首页",R.drawable.icon_home, R.color.colorBottomNavigationActiveColored);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem("聊天", R.drawable.icon_chat, R.color.colorBottomNavigationActiveColored);
        AHBottomNavigationItem item5 = new AHBottomNavigationItem("活动" ,R.drawable.icon_add ,R.color.colorBottomNavigationActiveColored);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem("好友", R.drawable.icon_friend, R.color.colorBottomNavigationActiveColored);
        AHBottomNavigationItem item4 = new AHBottomNavigationItem("我的", R.drawable.icon_my, R.color.colorBottomNavigationActiveColored);

        bottomNavigationItems.add(item1);
        bottomNavigationItems.add(item2);
        bottomNavigationItems.add(item5);
        bottomNavigationItems.add(item3);
        bottomNavigationItems.add(item4);

        bottomNavigation.addItems(bottomNavigationItems);
        bottomNavigation.setForceTitlesDisplay(true);
        bottomNavigation.setDefaultBackgroundColor(Color.parseColor("#FEFEFE"));
        bottomNavigation.setAccentColor(Color.parseColor("#1A1B1C"));
        bottomNavigation.setInactiveColor(Color.parseColor("#C0C0C0"));

        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {

                viewPager.setCurrentItem(position,false);
                return true;
            }
        });

        viewPager.setOffscreenPageLimit(5);
        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));

    }

    @Override
    public void openPopupWindow() {
        //防止重复按按钮
        if (popupWindow != null && popupWindow.isShowing()) {
            return;
        }

        popupWindow = new PopupWindow(popView, LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        //设置背景,这个没什么效果，不添加会报错
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        //设置点击弹窗外隐藏自身
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        //设置动画
        popupWindow.setAnimationStyle(R.style.PopupWindow);
        //设置位置
        popupWindow.showAtLocation(tag, Gravity.BOTTOM|Gravity.LEFT, 50, 70);
        //设置消失监听
        popupWindow.setOnDismissListener(this);
        //设置背景色
        setBackgroundAlpha(0.5f);

        setPopupClick();
    }

    private void setPopupClick(){
        popView.findViewById(R.id.share_friend).setOnClickListener(this);
        popView.findViewById(R.id.share_wechat_coment).setOnClickListener(this);
        popView.findViewById(R.id.share_wechat).setOnClickListener(this);
        popView.findViewById(R.id.share_qq).setOnClickListener(this);
        popView.findViewById(R.id.share_qzone).setOnClickListener(this);
        popView.findViewById(R.id.share_sina).setOnClickListener(this);
        popView.findViewById(R.id.share_report).setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.share_friend:
                break;
            case R.id.share_wechat_coment:
                showShare(WechatMoments.NAME);
                break;
            case R.id.share_wechat:
                showShare(Wechat.NAME);
                break;
            case R.id.share_qq:
                showShare(QQ.NAME);
                break;
            case R.id.share_qzone:
                showShare(QZone.NAME);
                break;
            case R.id.share_sina:
                showShare(SinaWeibo.NAME);
                break;
            case R.id.share_report:
                break;
        }
    }

    private void showShare(String name){
        Platform.ShareParams sp = new Platform.ShareParams();
        sp.setTitle("测试分享的标题");
        sp.setTitleUrl("http://sharesdk.cn"); // 标题的超链接
        sp.setText("测试分享的文本");
        sp.setImageUrl("http://www.baidu.com");
        sp.setSite("发布分享的网站名称");
        sp.setSiteUrl("发布分享网站的地址");

        Platform platform = ShareSDK.getPlatform (name);
        // 设置分享事件回调（注：回调放在不能保证在主线程调用，不可以在里面直接处理UI操作）
        platform.setPlatformActionListener(this);
        // 执行图文分享
        platform.share(sp);
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
    }

    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
        //失败的回调，arg:平台对象，arg1:表示当前的动作，arg2:异常信息
    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {
        //分享成功的回调
    }

    @Override
    public void onCancel(Platform platform, int i) {
        //取消分享的回调
    }
}

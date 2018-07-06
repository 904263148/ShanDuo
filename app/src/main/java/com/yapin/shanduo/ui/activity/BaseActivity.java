package com.yapin.shanduo.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.gyf.barlibrary.ImmersionBar;
import com.yapin.shanduo.utils.ActivityTransitionUtil;

public class BaseActivity extends AppCompatActivity{
    private int isEvent; //右滑关闭页面设置

    private ImmersionBar mImmersionBar; //沉浸式
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mImmersionBar = ImmersionBar.with(this);
//        mImmersionBar.init();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityTransitionUtil.finishActivityTransition(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mImmersionBar != null)
            mImmersionBar.destroy();  //必须调用该方法，防止内存泄漏，不调用该方法，如果界面bar发生改变，在不关闭app的情况下，退出此界面再进入将记忆最后一次bar改变的状态
    }

    public int getIsEvent() {
        return isEvent;
    }

    public void setIsEvent(int isEvent) {
        this.isEvent = isEvent;
    }

    /**
     * 设置toolbar
     *
     * @param toolbar toolbar
     * @param titleId 标题资源
     */
    public void setToolbar(Toolbar toolbar, int titleId) {
        toolbar.setTitle(titleId);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    /**
     * 设置toolbar
     *
     * @param toolbar toolbar
     * @param title 标题内容
     */
    public void setToolbar(Toolbar toolbar, String title) {
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}

package com.yapin.shanduo.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.scwang.smartrefresh.header.FunGameHitBlockHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.yapin.shanduo.R;

/**
 * 作者：L on 2018/6/20 0020 09:42
 */
public class SmartRefreshActivity extends RightSlidingActivity{

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh_layout);

        SmartRefreshLayout refreshLayout = (SmartRefreshLayout) findViewById(R.id.refresh_layout);
        refreshLayout.setRefreshHeader(new FunGameHitBlockHeader(this));

    }
}

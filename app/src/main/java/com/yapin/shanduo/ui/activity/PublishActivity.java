package com.yapin.shanduo.ui.activity;

import android.app.Activity;
import android.os.Bundle;

import com.yapin.shanduo.R;
import com.yapin.shanduo.widget.DashboardView3;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PublishActivity extends BaseActivity{

    @BindView(R.id.dashboard_view_3)
    DashboardView3 dashboardView3;

    private Activity activity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);
        ButterKnife.bind(this);
        activity = this;
    }

    @OnClick(R.id.button)
    public void onClick(){
        dashboardView3.setCreditValueWithAnim(new Random().nextInt(950 - 350) + 350);
    }

}

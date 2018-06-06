package com.yapin.shanduo.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.yapin.shanduo.R;
import com.yapin.shanduo.app.ShanDuoPartyApplication;
import com.yapin.shanduo.utils.StartActivityUtil;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dell on 2018/6/1.
 */

public class AboutflickerActivity extends BaseActivity{

    private Context context;
    private Activity activity;

    private final int BRIEFINTRODUCTION = 1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutflicker);

        context = ShanDuoPartyApplication.getContext();
        activity = this;
        ButterKnife.bind(this);
    }

    @OnClick({R.id.iv_back ,R.id.tv_briefintroduction})
    public void onClick(View view){
        switch (view.getId()) {
            case  R.id.iv_back:
                finish();
                break;
            case R.id.tv_briefintroduction:
                StartActivityUtil.start(activity , BriefintroductionActivity.class ,BRIEFINTRODUCTION);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case BRIEFINTRODUCTION:
                break;
        }
    }
}

package com.yapin.shanduo.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.yapin.shanduo.R;
import com.yapin.shanduo.app.ShanDuoPartyApplication;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dell on 2018/6/5.
 */

public class BriefintroductionActivity extends BaseActivity{

    private Context context;
    private Activity activity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_briefintroduction);

        context = ShanDuoPartyApplication.getContext();
        activity = this;
        ButterKnife.bind(this);
    }

    @OnClick({R.id.iv_back})
    public void onClick(View view){
        switch (view.getId()) {
            case  R.id.iv_back:
                finish();
                break;

        }
    }
}

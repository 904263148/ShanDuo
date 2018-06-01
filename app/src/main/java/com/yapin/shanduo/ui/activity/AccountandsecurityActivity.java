package com.yapin.shanduo.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.yapin.shanduo.R;
import com.yapin.shanduo.app.ShanDuoPartyApplication;
import com.yapin.shanduo.model.LoginPasswordModel;
import com.yapin.shanduo.utils.PrefJsonUtil;
import com.yapin.shanduo.utils.StartActivityUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dell on 2018/5/31.
 */

public class AccountandsecurityActivity extends BaseActivity{

    @BindView(R.id.tv_flicker)
    TextView tv_flicker;
    @BindView(R.id.tv_phone_number)
    TextView tv_phone_number;

    private Context context;
    private Activity activity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accountandsecurity);

        context = ShanDuoPartyApplication.getContext();
        activity = this;
        ButterKnife.bind(this);


        tv_flicker.setText(PrefJsonUtil.getProfile(context).getUserId());
        tv_phone_number.setText(PrefJsonUtil.getProfile(context).getPhone());


    }


    @OnClick({R.id.iv_back , R.id.tv_login_password})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_login_password:
                StartActivityUtil.start(activity , LoginPasswordActivity.class);
                break;
        }
    }



}

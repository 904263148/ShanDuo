package com.yapin.shanduo.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.yapin.shanduo.R;
import com.yapin.shanduo.app.ShanDuoPartyApplication;
import com.yapin.shanduo.utils.ApiUtil;
import com.yapin.shanduo.utils.GlideUtil;
import com.yapin.shanduo.utils.PrefJsonUtil;
import com.yapin.shanduo.utils.PrefUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dell on 2018/5/25.
 */

public class MembercenterActivity extends BaseActivity {

    private Context context;
    private Activity activity;

    @BindView(R.id.ib_Headportrait)
    ImageView ib_Headportrait;
    @BindView(R.id.tv_nickname)
    TextView tv_nickname;
    @BindView(R.id.iv_vip_Headportrait)
    ImageView iv_vip_Headportrait;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_center);
        context = ShanDuoPartyApplication.getContext();
        activity = this;
        ButterKnife.bind(this);
        initView();
    }

    private void initView(){

            tv_nickname.setText(PrefJsonUtil.getProfile(context).getName());
            GlideUtil.load(context ,activity , ApiUtil.IMG_URL + PrefJsonUtil.getProfile(context).getPicture() , ib_Headportrait);
        GlideUtil.load(context ,activity , ApiUtil.IMG_URL + PrefJsonUtil.getProfile(context).getPicture() , iv_vip_Headportrait);

        }


    @OnClick({R.id.iv_back})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
        }
    }



}

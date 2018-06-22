package com.yapin.shanduo.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.tencent.TIMCallBack;
import com.tencent.qcloud.presentation.business.LoginBusiness;
import com.tencent.qcloud.presentation.event.MessageEvent;
import com.tencent.qcloud.tlslibrary.service.TlsBusiness;
import com.yapin.shanduo.R;
import com.yapin.shanduo.app.ShanDuoPartyApplication;
import com.yapin.shanduo.im.model.FriendshipInfo;
import com.yapin.shanduo.im.model.GroupInfo;
import com.yapin.shanduo.im.model.UserInfo;
import com.yapin.shanduo.im.ui.AboutActivity;
import com.yapin.shanduo.utils.Constants;
import com.yapin.shanduo.utils.PrefJsonUtil;
import com.yapin.shanduo.utils.PrefUtil;
import com.yapin.shanduo.utils.StartActivityUtil;
import com.yapin.shanduo.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dell on 2018/5/23.
 */

public class SetupActivity extends BaseActivity{

    private Context context;
    private Activity activity;
    @BindView(R.id.bt_logout)
    Button bt_logout;

    private static final int ACCOUNTANDSECURITY =1;
    private static final int EDITING =2;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);
        context = ShanDuoPartyApplication.getContext();
        activity = this;
        ButterKnife.bind(this );
        if(TextUtils.isEmpty(PrefUtil.getToken(context))){
            bt_logout.setVisibility(View.GONE);
        }else {
            bt_logout.setVisibility(View.VISIBLE);
        }
    }

    @OnClick({R.id.iv_back , R.id.bt_logout,R.id.tv_account ,R.id.tv_about ,R.id.tv_signin})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_signin:    //签到
                StartActivityUtil.start(activity, SiginActivity.class);
                break;
            case R.id.tv_about:     //关于闪多
                StartActivityUtil.start(activity, AboutflickerActivity.class);
                break;
            case R.id.tv_account:       //账户与安全
                if(TextUtils.isEmpty(PrefUtil.getToken(context))){
                    StartActivityUtil.start(activity , LoginActivity.class , EDITING);
                }else {
                    StartActivityUtil.start(activity, AccountandsecurityActivity.class, ACCOUNTANDSECURITY);
                }
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.bt_logout:        //退出登录
                PrefUtil.setToken(context , "");
                PrefJsonUtil.setProfile(context , "");

                LoginBusiness.logout(new TIMCallBack() {
                    @Override
                    public void onError(int i, String s) {
//                        Log.d("TIM_logout",s);
                    }

                    @Override
                    public void onSuccess() {
//                        Log.d("TIM_logout","退出登录成功");
                        TlsBusiness.logout(UserInfo.getInstance().getId());
                        UserInfo.getInstance().setId(null);
                        MessageEvent.getInstance().clear();
                        FriendshipInfo.getInstance().clear();
                        GroupInfo.getInstance().clear();
                        ToastUtil.showShortToast(context , "退出登录成功");
                        bt_logout.setVisibility(View.GONE);
                    }
                });
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != RESULT_OK){
            return;
        }
        switch (requestCode){
            case ACCOUNTANDSECURITY :
                break;

        }
    }
}

package com.yapin.shanduo.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.tencent.TIMCallBack;
import com.tencent.qcloud.presentation.business.LoginBusiness;
import com.tencent.qcloud.presentation.event.MessageEvent;
import com.tencent.qcloud.tlslibrary.service.TlsBusiness;
import com.yapin.shanduo.R;
import com.yapin.shanduo.app.ShanDuoPartyApplication;
import com.yapin.shanduo.im.model.FriendshipInfo;
import com.yapin.shanduo.im.model.GroupInfo;
import com.yapin.shanduo.im.model.UserInfo;
import com.yapin.shanduo.utils.PrefJsonUtil;
import com.yapin.shanduo.utils.PrefUtil;
import com.yapin.shanduo.utils.ToastUtil;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dell on 2018/5/23.
 */

public class SetupActivity extends BaseActivity{

    private Context context;
    private Activity activity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);
        context = ShanDuoPartyApplication.getContext();
        activity = this;
        ButterKnife.bind(this );
    }

    @OnClick({R.id.iv_back , R.id.bt_logout})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.bt_logout:
                PrefUtil.setToken(context , "");
                PrefJsonUtil.setProfile(context , "");

                LoginBusiness.logout(new TIMCallBack() {
                    @Override
                    public void onError(int i, String s) {
                        Log.d("TIM_logout",s);
                    }

                    @Override
                    public void onSuccess() {
                        Log.d("TIM_logout","退出登录成功");
                        TlsBusiness.logout(UserInfo.getInstance().getId());
                        UserInfo.getInstance().setId(null);
                        MessageEvent.getInstance().clear();
                        FriendshipInfo.getInstance().clear();
                        GroupInfo.getInstance().clear();
                        ToastUtil.showShortToast(context , "退出登录成功");
                    }
                });
                break;
        }
    }
}

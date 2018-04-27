package com.yapin.shanduo.model.impl;

import android.content.Context;

import com.yapin.shanduo.app.ShanDuoPartyApplication;
import com.yapin.shanduo.model.LoginLoadModel;
import com.yapin.shanduo.okhttp.JavaOkCallback;
import com.yapin.shanduo.okhttp.OkHttp;
import com.yapin.shanduo.presenter.OnLoadListener;
import com.yapin.shanduo.utils.ApiUtil;
import com.yapin.shanduo.utils.NetWorkUtil;
import com.yapin.shanduo.utils.PrefJsonUtil;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginModelImpl implements LoginLoadModel {
    @Override
    public void load(final OnLoadListener<String> listener, String username, String password) {
        final Context context = ShanDuoPartyApplication.getContext();
        if (!NetWorkUtil.isNetworkAvailable(context)) {
            listener.networkError();
            return;
        }
        Map<String,String> params = new HashMap<>();
        params.put("username",username);
        params.put("password",password);
        OkHttp.get(context, ApiUtil.LOGIN_IN, params, new JavaOkCallback() {
            @Override
            public void onFailure(String msg) {
                if(listener != null)
                    listener.onError(msg);
            }

            @Override
            public void onResponse(String response) {
                try {
                    PrefJsonUtil.setProfile(context , new JSONObject(response).getString("result"));
                    listener.onSuccess("登录成功");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

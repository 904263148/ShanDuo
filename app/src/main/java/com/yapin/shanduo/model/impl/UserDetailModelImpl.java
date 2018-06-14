package com.yapin.shanduo.model.impl;

import android.content.Context;
import android.util.Log;

import com.yapin.shanduo.app.ShanDuoPartyApplication;
import com.yapin.shanduo.model.UserDetailLoadModel;
import com.yapin.shanduo.okhttp.JavaOkCallback;
import com.yapin.shanduo.okhttp.OkHttp;
import com.yapin.shanduo.presenter.OnLoadListener;
import com.yapin.shanduo.utils.ApiUtil;
import com.yapin.shanduo.utils.NetWorkUtil;
import com.yapin.shanduo.utils.PrefJsonUtil;
import com.yapin.shanduo.utils.PrefUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * 作者：L on 2018/6/14 0014 17:44
 */
public class UserDetailModelImpl implements UserDetailLoadModel{

    @Override
    public void load(final OnLoadListener<String> listener) {
        final Context context = ShanDuoPartyApplication.getContext();
        if (!NetWorkUtil.isNetworkAvailable(context)) {
            listener.networkError();
            return;
        }
        Map<String ,String> params = new HashMap<>();
        params.put("token" , PrefUtil.getToken(context));
        OkHttp.post(context, ApiUtil.USER_DETAIL, params, new JavaOkCallback() {
            @Override
            public void onFailure(String msg) {
                Log.d("UserDetailModelImpl" , msg);
                listener.onError(msg);
            }

            @Override
            public void onResponse(String response) {
                Log.d("UserDetailModelImpl" , response);
                try {
                    PrefJsonUtil.setProfile(context , new JSONObject(response).getString("result"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                listener.onSuccess("登录成功");
            }
        });
    }
}

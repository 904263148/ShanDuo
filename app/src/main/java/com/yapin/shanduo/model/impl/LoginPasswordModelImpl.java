package com.yapin.shanduo.model.impl;

import android.content.Context;

import com.yapin.shanduo.app.ShanDuoPartyApplication;
import com.yapin.shanduo.model.LoginPasswordModel;
import com.yapin.shanduo.okhttp.JavaOkCallback;
import com.yapin.shanduo.okhttp.OkHttp;
import com.yapin.shanduo.presenter.OnLoadListener;
import com.yapin.shanduo.utils.ApiUtil;
import com.yapin.shanduo.utils.NetWorkUtil;
import com.yapin.shanduo.utils.PrefJsonUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dell on 2018/5/31.
 */

public class LoginPasswordModelImpl implements LoginPasswordModel {
    @Override
    public void load(final OnLoadListener<String> listener, String password, String newPassword) {
        final Context context = ShanDuoPartyApplication.getContext();
        if (!NetWorkUtil.isNetworkAvailable(context)) {
            listener.networkError();
            return;
        }

        Map<String,String> params = new HashMap<>();
        params.put("token", PrefJsonUtil.getProfile(context).getToken());
        params.put("typeId","2");
        params.put("password",password);
        params.put("newPassword",newPassword);

        OkHttp.post(context, ApiUtil.LOGINPASSWORD, params, new JavaOkCallback() {
            @Override
            public void onFailure(String msg) {
                listener.onError(msg);
            }

            @Override
            public void onResponse(String response) {
                try {
                    PrefJsonUtil.setProfile(context , new JSONObject(response).getString("result"));
                    listener.onSuccess("修改成功");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}

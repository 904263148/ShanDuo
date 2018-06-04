package com.yapin.shanduo.model.impl;

import android.content.Context;

import com.yapin.shanduo.app.ShanDuoPartyApplication;
import com.yapin.shanduo.model.PaymentPasswordModel;
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
 * Created by dell on 2018/6/1.
 */

public class PaymentPasswordModelImpl implements PaymentPasswordModel {
    @Override
    public void load(final OnLoadListener<String> listener, String password) {
        final Context context = ShanDuoPartyApplication.getContext();
        if (!NetWorkUtil.isNetworkAvailable(context)) {
            listener.networkError();
            return;
        }

        Map<String,String> params = new HashMap<>();
        params.put("token", PrefJsonUtil.getProfile(context).getToken());
        params.put("password",password);

        OkHttp.post(context, ApiUtil.VERIFYINGPAYMENT, params, new JavaOkCallback() {
            @Override
            public void onFailure(String msg) {
                listener.onError(msg);
            }

            @Override
            public void onResponse(String response) {
                try {
                    listener.onSuccess(new JSONObject(response).getString("result"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

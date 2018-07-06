package com.yapin.shanduo.model.impl;

import android.content.Context;

import com.google.gson.Gson;
import com.yapin.shanduo.app.ShanDuoPartyApplication;
import com.yapin.shanduo.model.SignModel;
import com.yapin.shanduo.model.entity.SigninInfo;
import com.yapin.shanduo.okhttp.JavaOkCallback;
import com.yapin.shanduo.okhttp.OkHttp;
import com.yapin.shanduo.presenter.OnLoadListener;
import com.yapin.shanduo.utils.ApiUtil;
import com.yapin.shanduo.utils.NetWorkUtil;
import com.yapin.shanduo.utils.PrefUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dell on 2018/6/8.
 */

public class SigninModelImpl implements SignModel {
    @Override
    public void load(final OnLoadListener<SigninInfo> listener) {
        final Context context = ShanDuoPartyApplication.getContext();
        if (!NetWorkUtil.isNetworkAvailable(context)) {
            listener.networkError();
            return;
        }

        Map<String,String> params = new HashMap<>();
        params.put("token", PrefUtil.getToken(context));

        OkHttp.post(context, ApiUtil.CHECK_CHECKIN, params, new JavaOkCallback() {
            @Override
            public void onFailure(String msg) {
                listener.onError(msg);
            }

            @Override
            public void onResponse(String response) {
                SigninInfo info = null;
                try {
                    info = new Gson().fromJson(new JSONObject(response).get("result").toString(),SigninInfo.class);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                listener.onSuccess(info);
//                Log.d("infosignin" , info.toString());

            }
        });
    }
}

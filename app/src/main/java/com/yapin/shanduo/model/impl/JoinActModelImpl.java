package com.yapin.shanduo.model.impl;

import android.content.Context;

import com.yapin.shanduo.app.ShanDuoPartyApplication;
import com.yapin.shanduo.model.JoinActLoadModel;
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
 * 作者：L on 2018/5/10 0010 19:31
 */
public class JoinActModelImpl implements JoinActLoadModel{
    @Override
    public void load(final OnLoadListener<String> listener, String activityId) {
        Context context = ShanDuoPartyApplication.getContext();
        if (!NetWorkUtil.isNetworkAvailable(context)) {
            listener.networkError();
            return;
        }
        Map<String, String> params = new HashMap<>();
        params.put("token", PrefUtil.getToken(context));
        params.put("activityId" , activityId);
        OkHttp.get(context, ApiUtil.JOIN_ACT, params, new JavaOkCallback() {
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

package com.yapin.shanduo.model.impl;

import android.content.Context;

import com.yapin.shanduo.app.ShanDuoPartyApplication;
import com.yapin.shanduo.model.AddactivityModel;
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
 * Created by dell on 2018/5/5.
 */

public class AddactivityModelImpl implements AddactivityModel {
    @Override
    public void load(final OnLoadListener<String> listener, String activityType, String activityStartTime, String activityAddress, String mode, String manNumber, String womanNumber, String remarks, String activityCutoffTime, String lon, String lat) {
        Context context = ShanDuoPartyApplication.getContext();
        if (!NetWorkUtil.isNetworkAvailable(context)) {
            listener.networkError();
            return;
        }

        Map<String,String> params = new HashMap<>();
        params.put("token", PrefJsonUtil.getProfile(context).getToken());
        params.put("activityType",activityType);
        params.put("activityStartTime",activityStartTime);
        params.put("activityAddress",activityAddress);
        params.put("mode",mode);
        params.put("manNumber",manNumber);
        params.put("womanNumber",womanNumber);
        params.put("remarks",remarks);
        params.put("activityCutoffTime",activityCutoffTime);
        params.put("lon",lon);
        params.put("lat",lat);

        OkHttp.post(context, ApiUtil.ADD_ACTIVITY, params, new JavaOkCallback() {
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

package com.yapin.shanduo.model.impl;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.yapin.shanduo.app.ShanDuoPartyApplication;
import com.yapin.shanduo.model.ActivityDetailLoadModel;
import com.yapin.shanduo.model.entity.ActivityInfo;
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
 * 作者：L on 2018/6/22 0022 16:42
 */
public class ActivityDetailModelImpl implements ActivityDetailLoadModel{
    @Override
    public void load(final OnLoadListener<ActivityInfo.Act> listener, String activityId) {
        Context context = ShanDuoPartyApplication.getContext();
        if (!NetWorkUtil.isNetworkAvailable(context)) {
            listener.networkError();
            return;
        }
        Map<String,String> params = new HashMap<>();
        params.put("activityId" , activityId);
        params.put("lat" , PrefUtil.getLat(context));
        params.put("lon" , PrefUtil.getLon(context));
        OkHttp.post(context, ApiUtil.ACTIVITY_INFO, params, new JavaOkCallback() {
            @Override
            public void onFailure(String msg) {
                Log.d("ActivityDetailModelImpl" , msg);
                listener.onError(msg);
            }

            @Override
            public void onResponse(String response) {
                Log.d("ActivityDetailModelImpl" , response);
                try {
                    listener.onSuccess(new Gson().fromJson( new JSONObject(response).get("result").toString() , ActivityInfo.Act.class));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

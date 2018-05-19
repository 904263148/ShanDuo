package com.yapin.shanduo.model.impl;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.yapin.shanduo.app.ShanDuoPartyApplication;
import com.yapin.shanduo.model.MyactivityModel;
import com.yapin.shanduo.model.entity.ActivityInfo;
import com.yapin.shanduo.model.entity.TrendInfo;
import com.yapin.shanduo.okhttp.JavaOkCallback;
import com.yapin.shanduo.okhttp.OkHttp;
import com.yapin.shanduo.presenter.OnMultiLoadListener;
import com.yapin.shanduo.utils.ApiUtil;
import com.yapin.shanduo.utils.NetWorkUtil;
import com.yapin.shanduo.utils.PrefJsonUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2018/5/19.
 */

public class MyactivityModelImpl implements MyactivityModel{


    @Override
    public void load(final OnMultiLoadListener<List<ActivityInfo.Act>> listener,String type, String lon, String lat, String page, String pageSize) {
        final Context context = ShanDuoPartyApplication.getContext();
        if (!NetWorkUtil.isNetworkAvailable(context)) {
            listener.networkError();
            return;
        }

        Map<String,String> params = new HashMap<>();
        params.put("token", PrefJsonUtil.getProfile(context).getToken());
        params.put("type",type);
        params.put("lon",lon);
        params.put("lat",lat);
        params.put("page" , page);
        params.put("pageSize" ,pageSize);

        OkHttp.post(context, ApiUtil.MY_ACTIVITY, params, new JavaOkCallback() {
            @Override
            public void onFailure(String msg) {
                Log.d("trend_result_my" , msg);
                listener.onError(msg);
            }

            @Override
            public void onResponse(String response) {
                ActivityInfo info = null;
                try {
                    info = new Gson().fromJson(new JSONObject(response).get("result").toString(),ActivityInfo.class);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                List<ActivityInfo.Act> list = info.getList();
                listener.onSuccess(list , info.getTotalpage());

            }
        });
    }
}

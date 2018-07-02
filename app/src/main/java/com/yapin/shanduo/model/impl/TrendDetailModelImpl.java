package com.yapin.shanduo.model.impl;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.yapin.shanduo.app.ShanDuoPartyApplication;
import com.yapin.shanduo.model.TrendDetailLoadModel;
import com.yapin.shanduo.model.entity.TrendInfo;
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
 * 作者：L on 2018/7/2 0002 10:44
 */
public class TrendDetailModelImpl implements TrendDetailLoadModel{
    @Override
    public void load(final OnLoadListener<TrendInfo.Trend> listener, String dynamicId) {
        final Context context = ShanDuoPartyApplication.getContext();
        if (!NetWorkUtil.isNetworkAvailable(context)) {
            listener.networkError();
            return;
        }
        Map<String,String> params = new HashMap<>();
        params.put("token", PrefUtil.getToken(context));
        params.put("dynamicId" , dynamicId);
        OkHttp.post(context, ApiUtil.TREND_INFO, params, new JavaOkCallback() {
            @Override
            public void onFailure(String msg) {
                Log.d("TrendDetailModelImpl" ,msg);
                listener.onError(msg);
            }

            @Override
            public void onResponse(String response) {
                Log.d("TrendDetailModelImpl" ,response);
                try {
                    listener.onSuccess(new Gson().fromJson( new JSONObject(response).get("result").toString() , TrendInfo.Trend.class));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

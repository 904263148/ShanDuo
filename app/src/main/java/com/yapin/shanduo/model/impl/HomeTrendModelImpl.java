package com.yapin.shanduo.model.impl;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.yapin.shanduo.app.ShanDuoPartyApplication;
import com.yapin.shanduo.model.HomeTrendLoadModel;
import com.yapin.shanduo.model.entity.ActivityInfo;
import com.yapin.shanduo.model.entity.TrendInfo;
import com.yapin.shanduo.okhttp.JavaOkCallback;
import com.yapin.shanduo.okhttp.OkHttp;
import com.yapin.shanduo.presenter.OnMultiLoadListener;
import com.yapin.shanduo.utils.ApiUtil;
import com.yapin.shanduo.utils.NetWorkUtil;
import com.yapin.shanduo.utils.PrefUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 作者：L on 2018/5/9 0009 19:47
 */
public class HomeTrendModelImpl implements HomeTrendLoadModel{
    @Override
    public void load(final OnMultiLoadListener<List<TrendInfo.Trend>> listener, String typeId, String lon, String lat, String page, String pageSize , String userId) {
        final Context context = ShanDuoPartyApplication.getContext();
        if (!NetWorkUtil.isNetworkAvailable(context)) {
            listener.networkError();
            return;
        }
        Map<String,String> params = new HashMap<>();
        params.put("token" , PrefUtil.getToken(context));
        params.put("typeId" , typeId);
        params.put("lat" , PrefUtil.getLat(context));
        params.put("lon" , PrefUtil.getLon(context));
        params.put("page" , page);
        params.put("pageSize" ,pageSize);
        if("4".equals(typeId)) params.put("userId",userId);
        OkHttp.post(context, ApiUtil.HOME_TREND, params, new JavaOkCallback() {
            @Override
            public void onFailure(String msg) {
                Log.d("trend_result" , msg);
                listener.onError(msg);
            }

            @Override
            public void onResponse(String response) {
                Log.d("trend_result" , response);
                TrendInfo info = new TrendInfo();
                try {
                    info = new Gson().fromJson( new JSONObject(response).get("result").toString() , TrendInfo.class);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                List<TrendInfo.Trend> list = info.getList();
                listener.onSuccess(list , info.getTotalpage());
            }
        });
    }
}

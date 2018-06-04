package com.yapin.shanduo.model.impl;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.yapin.shanduo.app.ShanDuoPartyApplication;
import com.yapin.shanduo.model.SearchActLoadModel;
import com.yapin.shanduo.model.entity.ActivityInfo;
import com.yapin.shanduo.okhttp.JavaOkCallback;
import com.yapin.shanduo.okhttp.OkHttp;
import com.yapin.shanduo.presenter.OnMultiLoadListener;
import com.yapin.shanduo.utils.ApiUtil;
import com.yapin.shanduo.utils.NetWorkUtil;
import com.yapin.shanduo.utils.PrefUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 作者：L on 2018/6/4 0004 12:00
 */
public class SearchActModelImpl implements SearchActLoadModel{
    @Override
    public void load(final OnMultiLoadListener<List<ActivityInfo.Act>> listener, String query, String page , String pageSize) {
        final Context context = ShanDuoPartyApplication.getContext();
        if (!NetWorkUtil.isNetworkAvailable(context)) {
            listener.networkError();
            return;
        }
        Map<String,String> params = new HashMap<>();
        params.put("query" , query);
        params.put("lon" , PrefUtil.getLon(context));
        params.put("lat" ,PrefUtil.getLat(context));
        params.put("page" , page);
        params.put("pageSize" ,pageSize);
        OkHttp.post(context, ApiUtil.QUERY_ACT, params, new JavaOkCallback() {
            @Override
            public void onFailure(String msg) {
                Log.d("SearchActModelImpl" , msg);
                listener.onError(msg);
            }

            @Override
            public void onResponse(String response) {
                Log.d("SearchActModelImpl" , response);
                List<ActivityInfo.Act> list = new ArrayList<>();
                ActivityInfo info = null;
                try {
                    info = new Gson().fromJson( new JSONObject(response).get("result").toString() , ActivityInfo.class);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                list = info.getList();
                listener.onSuccess(list , info.getTotalpage());
            }
        });
    }
}

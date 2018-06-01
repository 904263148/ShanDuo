package com.yapin.shanduo.model.impl;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.yapin.shanduo.app.ShanDuoPartyApplication;
import com.yapin.shanduo.model.JoinActUserLoadModel;
import com.yapin.shanduo.model.entity.ActivityInfo;
import com.yapin.shanduo.model.entity.JoinActUser;
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
 * 作者：L on 2018/5/26 0026 14:41
 */
public class JoinActUserModelImpl implements JoinActUserLoadModel{
    @Override
    public void load(final OnMultiLoadListener<List<JoinActUser.ActUser>> listener, String activityId  , String page , String pageSize) {
        Context context = ShanDuoPartyApplication.getContext();
        if (!NetWorkUtil.isNetworkAvailable(context)) {
            listener.networkError();
            return;
        }
        Map<String, String> params = new HashMap<>();
        params.put("token", PrefUtil.getToken(context));
        params.put("activityId" , activityId);
        params.put("page" , page);
        params.put("pageSize" , pageSize);
        params.put("lat" , PrefUtil.getLat(context));
        params.put("lon" , PrefUtil.getLon(context));
        OkHttp.post(context, ApiUtil.ACT_JOIN_USER, params, new JavaOkCallback() {
            @Override
            public void onFailure(String msg) {
                Log.d("JoinActUserModelImpl" , msg);
                listener.onError(msg);
            }

            @Override
            public void onResponse(String response) {
                Log.d("JoinActUserModelImpl" , response);
                JoinActUser info = null;
                try {
                    info = new Gson().fromJson( new JSONObject(response).get("result").toString() , JoinActUser.class);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                List<JoinActUser.ActUser> list = info.getList();
                listener.onSuccess(list , info.getTotalpage());
            }
        });
    }
}

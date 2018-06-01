package com.yapin.shanduo.model.impl;

import android.content.Context;

import com.google.gson.Gson;
import com.yapin.shanduo.app.ShanDuoPartyApplication;
import com.yapin.shanduo.model.ParticipantevaluationModel;
import com.yapin.shanduo.model.entity.ParticipantevaluationInfo;
import com.yapin.shanduo.okhttp.JavaOkCallback;
import com.yapin.shanduo.okhttp.OkHttp;
import com.yapin.shanduo.presenter.OnLoadListener;
import com.yapin.shanduo.utils.ApiUtil;
import com.yapin.shanduo.utils.NetWorkUtil;
import com.yapin.shanduo.utils.PrefJsonUtil;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2018/5/30.
 */

public class ParticipantevaluationModelImpl implements ParticipantevaluationModel {
    @Override
    public void load(final OnLoadListener<String> listener, String activityId, String data) {
        final Context context = ShanDuoPartyApplication.getContext();
        if (!NetWorkUtil.isNetworkAvailable(context)) {
            listener.networkError();
            return;
        }
        Map<String,String> params = new HashMap<>();
        params.put("token", PrefJsonUtil.getProfile(context).getToken());
        params.put("data",data);
        params.put("activityId",activityId);
        OkHttp.get(context, ApiUtil.PARTICIPANTEVALUATION, params, new JavaOkCallback() {
            @Override
            public void onFailure(String msg) {
                if(listener != null)
                    listener.onError(msg);
            }

            @Override
            public void onResponse(String response) {
                try {
                    listener.onSuccess( new JSONObject(response).getString("result"));

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

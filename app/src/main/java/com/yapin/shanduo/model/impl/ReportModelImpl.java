package com.yapin.shanduo.model.impl;

import android.content.Context;
import android.util.Log;

import com.yapin.shanduo.app.ShanDuoPartyApplication;
import com.yapin.shanduo.model.ReportLoadModel;
import com.yapin.shanduo.okhttp.JavaOkCallback;
import com.yapin.shanduo.okhttp.OkHttp;
import com.yapin.shanduo.presenter.OnLoadListener;
import com.yapin.shanduo.utils.ApiUtil;
import com.yapin.shanduo.utils.NetWorkUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * 作者：L on 2018/6/7 0007 18:12
 */
public class ReportModelImpl implements ReportLoadModel{
    @Override
    public void load(final OnLoadListener<String> listener, String userId, String activityId, String dynamicId, String typeId, String remarks) {
        final Context context = ShanDuoPartyApplication.getContext();
        if (!NetWorkUtil.isNetworkAvailable(context)) {
            listener.networkError();
            return;
        }
        Map<String,String> params = new HashMap<>();
        params.put("userId" , userId);
        params.put("activityId" , activityId);
        params.put("dynamicId" , dynamicId);
        params.put("typeId" ,typeId);
        params.put("remarks" , remarks);
        OkHttp.post(context, ApiUtil.REPORT, params, new JavaOkCallback() {
            @Override
            public void onFailure(String msg) {
                Log.d("ReportModelImpl" , msg);
                listener.onError(msg);
            }

            @Override
            public void onResponse(String response) {
                Log.d("ReportModelImpl" , response);
                try {
                    listener.onSuccess(new JSONObject(response).getString("result"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

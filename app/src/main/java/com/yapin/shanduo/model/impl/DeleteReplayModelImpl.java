package com.yapin.shanduo.model.impl;

import android.content.Context;
import android.util.Log;

import com.yapin.shanduo.app.ShanDuoPartyApplication;
import com.yapin.shanduo.model.DeleteReplayLoadModel;
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
 * 作者：L on 2018/6/14 0014 11:49
 */
public class DeleteReplayModelImpl implements DeleteReplayLoadModel{
    @Override
    public void load(final OnLoadListener<String> listener, String commentId) {
        Context context = ShanDuoPartyApplication.getContext();
        if (!NetWorkUtil.isNetworkAvailable(context)) {
            listener.networkError();
            return;
        }
        Map<String,String> params = new HashMap<>();
        params.put("token" , PrefUtil.getToken(context));
        params.put("commentId" , commentId);
        OkHttp.post(context, ApiUtil.DELETE_REPLAY, params, new JavaOkCallback() {
            @Override
            public void onFailure(String msg) {
                Log.d("DeleteReplayModelImpl" , msg);
                listener.onError(msg);
            }

            @Override
            public void onResponse(String response) {
                Log.d("DeleteReplayModelImpl" , response);
                try {
                    listener.onSuccess(new JSONObject(response).getString("result"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

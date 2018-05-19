package com.yapin.shanduo.model.impl;

import android.content.Context;
import android.util.Log;

import com.yapin.shanduo.app.ShanDuoPartyApplication;
import com.yapin.shanduo.model.TrendReplayLoadModel;
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
 * 作者：L on 2018/5/18 0018 15:08
 */
public class TrendReplayModelImpl implements TrendReplayLoadModel{
    @Override
    public void load(final OnLoadListener<String> listener, String dynamicId, String comment, String typeId, String ommentId, String respondent) {
        Context context = ShanDuoPartyApplication.getContext();
        if (!NetWorkUtil.isNetworkAvailable(context)) {
            listener.networkError();
            return;
        }
        Map<String ,String> params = new HashMap<>();
        params.put("token" , PrefUtil.getToken(context));
        params.put("dynamicId",dynamicId);
        params.put("comment" , comment);
        params.put("typeId" , typeId);
        params.put("commentId" , ommentId);
        params.put("respondent" , respondent);
        OkHttp.post(context, ApiUtil.TREND_REPLAY, params, new JavaOkCallback() {
            @Override
            public void onFailure(String msg) {
                Log.d("trend_replay_result",msg);
                listener.onError(msg);
            }

            @Override
            public void onResponse(String response) {
                Log.d("trend_replay_result",response);
                try {
                    listener.onSuccess(new JSONObject(response).getString("result"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

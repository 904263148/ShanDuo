package com.yapin.shanduo.model.impl;

import android.content.Context;

import com.google.gson.Gson;
import com.yapin.shanduo.app.ShanDuoPartyApplication;
import com.yapin.shanduo.model.UserProfLoadModel;
import com.yapin.shanduo.model.entity.ShanDuoUserProf;
import com.yapin.shanduo.model.entity.ShanduoUser;
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
 * 作者：L on 2018/5/25 0025 16:50
 */
public class UserProfModelImpl implements UserProfLoadModel{
    @Override
    public void load(final OnLoadListener<ShanDuoUserProf> listener, String userId) {
        final Context context = ShanDuoPartyApplication.getContext();
        if (!NetWorkUtil.isNetworkAvailable(context)) {
            listener.networkError();
            return;
        }
        Map<String,String> params = new HashMap<>();
        params.put("token" , PrefUtil.getToken(context));
        params.put("userId" ,userId);
        OkHttp.post(context, ApiUtil.USER_PROF, params, new JavaOkCallback() {
            @Override
            public void onFailure(String msg) {
                listener.onError(msg);
            }

            @Override
            public void onResponse(String response) {
                try {
                    listener.onSuccess(new Gson().fromJson( new JSONObject(response).get("result").toString() , ShanDuoUserProf.class));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

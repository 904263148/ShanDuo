package com.yapin.shanduo.model.impl;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.yapin.shanduo.app.ShanDuoPartyApplication;
import com.yapin.shanduo.model.MyMessageLoadModel;
import com.yapin.shanduo.model.entity.MyMessageInfo;
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
 * 作者：L on 2018/6/27 0027 17:43
 */
public class MyMessageModelImpl implements MyMessageLoadModel{
    @Override
    public void load(final OnMultiLoadListener<List<MyMessageInfo.Message>> listener, String page, String pageSize) {
        final Context context = ShanDuoPartyApplication.getContext();
        if (!NetWorkUtil.isNetworkAvailable(context)) {
            listener.networkError();
            return;
        }
        Map<String,String> params = new HashMap<>();
        params.put("token" , PrefUtil.getToken(context));
        params.put("page" , page);
        params.put("pageSize" ,pageSize);
        OkHttp.post(context, ApiUtil.MY_MESSAGE, params, new JavaOkCallback() {
            @Override
            public void onFailure(String msg) {
                Log.d("MyMessageModelImpl" ,msg);
                listener.onError(msg);
            }

            @Override
            public void onResponse(String response) {
                Log.d("MyMessageModelImpl" ,response);
                MyMessageInfo info = new MyMessageInfo();
                try {
                    info = new Gson().fromJson( new JSONObject(response).get("result").toString() , MyMessageInfo.class);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                List<MyMessageInfo.Message> list = info.getList();
                listener.onSuccess(list , info.getTotalPage());
            }
        });
    }
}

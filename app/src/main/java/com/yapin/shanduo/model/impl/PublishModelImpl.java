package com.yapin.shanduo.model.impl;

import android.content.Context;

import com.yapin.shanduo.app.ShanDuoPartyApplication;
import com.yapin.shanduo.model.PublishLoadModel;
import com.yapin.shanduo.okhttp.JavaOkCallback;
import com.yapin.shanduo.okhttp.OkHttp;
import com.yapin.shanduo.presenter.OnLoadListener;
import com.yapin.shanduo.utils.ApiUtil;
import com.yapin.shanduo.utils.NetWorkUtil;

import java.util.HashMap;
import java.util.Map;

public class PublishModelImpl implements PublishLoadModel{
    @Override
    public void load(OnLoadListener<String> listener, String content, String data) {
        Context context = ShanDuoPartyApplication.getContext();
        if (!NetWorkUtil.isNetworkAvailable(context)) {
            listener.networkError();
            return;
        }
        Map<String , String> params = new HashMap<>();
        params.put("content",content);
        params.put("data",data);
        OkHttp.post(context, ApiUtil.PUBLISH_ACTIVITY, params, new JavaOkCallback() {
            @Override
            public void onFailure(String msg) {

            }

            @Override
            public void onResponse(String response) {

            }
        });

    }
}

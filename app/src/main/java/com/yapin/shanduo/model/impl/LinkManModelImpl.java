package com.yapin.shanduo.model.impl;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.yapin.shanduo.app.ShanDuoPartyApplication;
import com.yapin.shanduo.model.LinkManLoadModel;
import com.yapin.shanduo.model.entity.FriendInfo;
import com.yapin.shanduo.okhttp.JavaOkCallback;
import com.yapin.shanduo.okhttp.OkHttp;
import com.yapin.shanduo.presenter.OnLoadListener;
import com.yapin.shanduo.presenter.OnMultiLoadListener;
import com.yapin.shanduo.utils.ApiUtil;
import com.yapin.shanduo.utils.NetWorkUtil;
import com.yapin.shanduo.utils.PrefUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 作者：L on 2018/5/19 0019 15:16
 */
public class LinkManModelImpl implements LinkManLoadModel{
    @Override
    public void load(final OnLoadListener<List<FriendInfo.fInfo>> listener, String typeId) {
        Context context = ShanDuoPartyApplication.getContext();
        if (!NetWorkUtil.isNetworkAvailable(context)) {
            listener.networkError();
            return;
        }

        Map<String,String> params = new HashMap<>();
        params.put("token" , PrefUtil.getToken(context));
        params.put("typeId" , typeId);
        OkHttp.post(context, ApiUtil.ALL_FRIEND, params, new JavaOkCallback() {
            @Override
            public void onFailure(String msg) {
                Log.d("all_friend_result" ,msg);
                listener.onError(msg);
            }

            @Override
            public void onResponse(String response) {
                Log.d("all_friend_result" ,response);
                try {
                    FriendInfo info = new Gson().fromJson(response , FriendInfo.class);
                    listener.onSuccess(info.getResult());
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

package com.yapin.shanduo.model.impl;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.yapin.shanduo.app.ShanDuoPartyApplication;
import com.yapin.shanduo.model.SearchGroupLoadModel;
import com.yapin.shanduo.model.entity.IMGroupInfo;
import com.yapin.shanduo.okhttp.JavaOkCallback;
import com.yapin.shanduo.okhttp.OkHttp;
import com.yapin.shanduo.presenter.OnLoadListener;
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
 * 作者：L on 2018/6/16 0016 17:21
 */
public class SearchGroupModelImpl implements SearchGroupLoadModel{
    @Override
    public void load(final OnLoadListener<List<IMGroupInfo.GroupInfo>> listener, String name) {
        final Context context = ShanDuoPartyApplication.getContext();
        if (!NetWorkUtil.isNetworkAvailable(context)) {
            listener.networkError();
            return;
        }
        Map<String,String> params = new HashMap<>();
        params.put("token" , PrefUtil.getToken(context));
        params.put("name" , name);
        OkHttp.post(context, ApiUtil.SEARCH_GROUP, params, new JavaOkCallback() {
            @Override
            public void onFailure(String msg) {
                Log.d("SearchGroupModelImpl" , msg);
                listener.onError(msg);
            }

            @Override
            public void onResponse(String response) {
                Log.d("SearchGroupModelImpl" , response);
                List<IMGroupInfo.GroupInfo> list = new ArrayList<>();
                IMGroupInfo info = new IMGroupInfo();
                try {
                    info = new Gson().fromJson( new JSONObject(response).get("result").toString() , IMGroupInfo.class);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                list.addAll(info.getGroupIdList());
                listener.onSuccess(list);
            }
        });
    }
}

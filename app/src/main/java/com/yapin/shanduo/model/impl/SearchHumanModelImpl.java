package com.yapin.shanduo.model.impl;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.yapin.shanduo.app.ShanDuoPartyApplication;
import com.yapin.shanduo.model.SearchHumanLoadModel;
import com.yapin.shanduo.model.entity.TokenInfo;
import com.yapin.shanduo.okhttp.JavaOkCallback;
import com.yapin.shanduo.okhttp.OkHttp;
import com.yapin.shanduo.presenter.OnLoadListener;
import com.yapin.shanduo.presenter.OnMultiLoadListener;
import com.yapin.shanduo.utils.ApiUtil;
import com.yapin.shanduo.utils.NetWorkUtil;
import com.yapin.shanduo.utils.PrefUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 作者：L on 2018/5/24 0024 16:31
 */
public class SearchHumanModelImpl implements SearchHumanLoadModel{
    @Override
    public void load(final OnLoadListener<List<TokenInfo>> listener, String query, String typeId) {
        final Context context = ShanDuoPartyApplication.getContext();
        if (!NetWorkUtil.isNetworkAvailable(context)) {
            listener.networkError();
            return;
        }
        Map<String,String> params = new HashMap<>();
        params.put("token" , PrefUtil.getToken(context));
        params.put("query" , query);
        params.put("typeId" , typeId);
        OkHttp.post(context, ApiUtil.SEARCH_HUMAN, params, new JavaOkCallback() {
            @Override
            public void onFailure(String msg) {
                Log.d("search_human_result" , msg);
                listener.onError(msg);
            }

            @Override
            public void onResponse(String response) {
                Log.d("search_human_result" , response);
                List<TokenInfo> list = new ArrayList<>();
                try {
                    JSONArray array = new JSONObject(response).getJSONArray("result");
                    for (int i = 0 ; i < array.length() ; i++){
                        TokenInfo info = new TokenInfo();
                        info = new Gson().fromJson(array.getString(i) , TokenInfo.class);
                        list.add(info);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                listener.onSuccess(list);
            }
        });
    }
}

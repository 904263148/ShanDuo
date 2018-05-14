package com.yapin.shanduo.model.impl;

import android.content.Context;

import com.yapin.shanduo.app.ShanDuoPartyApplication;
import com.yapin.shanduo.model.HomeCarouselLoadModel;
import com.yapin.shanduo.okhttp.JavaOkCallback;
import com.yapin.shanduo.okhttp.OkHttp;
import com.yapin.shanduo.presenter.OnLoadListener;
import com.yapin.shanduo.utils.ApiUtil;
import com.yapin.shanduo.utils.NetWorkUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 作者：L on 2018/5/12 0012 17:26
 */
public class HomeCarouselModelImpl implements HomeCarouselLoadModel{
    @Override
    public void load(final OnLoadListener<List<String>> listener) {
        final Context context = ShanDuoPartyApplication.getContext();
        if (!NetWorkUtil.isNetworkAvailable(context)) {
            listener.networkError();
            return;
        }
        Map<String,String> params = new HashMap<>();
        OkHttp.get(context, "http://192.168.1.166:8080/shanduoparty/jcarousel/carouselList", params, new JavaOkCallback() {
            @Override
            public void onFailure(String msg) {
                listener.onError(msg);
            }

            @Override
            public void onResponse(String response) {
                JSONArray array = null;
                List<String> list = null;
                try {
                    array = new JSONObject(response).getJSONArray("result");
                    list = new ArrayList<>();
                    for (int i = 0 ; i < array.length() ; i++) {
                        list.add(array.getJSONObject(i).getString("picture"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                listener.onSuccess(list);

            }
        });
    }
}

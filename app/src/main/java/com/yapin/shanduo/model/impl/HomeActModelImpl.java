package com.yapin.shanduo.model.impl;

import android.content.Context;
import android.util.Log;

import com.yapin.shanduo.app.ShanDuoPartyApplication;
import com.yapin.shanduo.model.HomeActLoadModel;
import com.yapin.shanduo.model.entity.ActivityInfo;
import com.yapin.shanduo.okhttp.JavaOkCallback;
import com.yapin.shanduo.okhttp.OkHttp;
import com.yapin.shanduo.presenter.OnLoadListener;
import com.yapin.shanduo.utils.NetWorkUtil;
import com.yapin.shanduo.utils.PrefJsonUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 作者：L on 2018/5/7 0007 19:32
 */
public class HomeActModelImpl implements HomeActLoadModel{

    @Override
    public void load(OnLoadListener<ActivityInfo> listener, String type, String lon, String lat, String page, String pageSize) {
        final Context context = ShanDuoPartyApplication.getContext();
        if (!NetWorkUtil.isNetworkAvailable(context)) {
            listener.networkError();
            return;
        }
        Map<String,String> params = new HashMap<>();
        if(type.equals(3)){
            params.put("token" , PrefJsonUtil.getProfile(context).getToken());
        }
        params.put("lon",lon);
        params.put("lat",lat);
        params.put("page",page);
        params.put("pageSize",pageSize);
        OkHttp.post(context, "http://192.168.1.123:8080/shanduoparty/activity/showHotActivity", params, new JavaOkCallback() {
            @Override
            public void onFailure(String msg) {
                Log.d("act_result",msg);
            }

            @Override
            public void onResponse(String response) {
                Log.d("act_result",response);
            }
        });
    }
}

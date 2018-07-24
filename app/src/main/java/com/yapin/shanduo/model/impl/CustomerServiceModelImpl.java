package com.yapin.shanduo.model.impl;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.yapin.shanduo.app.ShanDuoPartyApplication;
import com.yapin.shanduo.model.CustomerServiceLoadModel;
import com.yapin.shanduo.model.entity.CustomerServiceInfo;
import com.yapin.shanduo.okhttp.JavaOkCallback;
import com.yapin.shanduo.okhttp.OkHttp;
import com.yapin.shanduo.presenter.OnLoadListener;
import com.yapin.shanduo.utils.ApiUtil;
import com.yapin.shanduo.utils.NetWorkUtil;
import com.yapin.shanduo.utils.PrefUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 作者：L on 2018/7/24 0024 09:31
 */
public class CustomerServiceModelImpl implements CustomerServiceLoadModel {
    @Override
    public void load(final OnLoadListener<List<CustomerServiceInfo.Customer>> listener) {
        Context context = ShanDuoPartyApplication.getContext();
        if (!NetWorkUtil.isNetworkAvailable(context)) {
            listener.networkError();
            return;
        }
        Map<String,String> params = new HashMap<>();
        params.put("token" , PrefUtil.getToken(context));
        OkHttp.post(context, ApiUtil.CUSTOMER_SERVICE, params, new JavaOkCallback() {
            @Override
            public void onFailure(String msg) {
                Log.d("CustomerService" , msg);
                listener.onError(msg);
            }

            @Override
            public void onResponse(String response) {
                Log.d("CustomerService" , response);
                try {
                    CustomerServiceInfo info = new Gson().fromJson(response , CustomerServiceInfo.class);
                    listener.onSuccess(info.getResult());
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

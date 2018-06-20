package com.yapin.shanduo.model.impl;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.yapin.shanduo.app.ShanDuoPartyApplication;
import com.yapin.shanduo.model.PayOrderLoadModel;
import com.yapin.shanduo.model.entity.PayOrder;
import com.yapin.shanduo.okhttp.JavaOkCallback;
import com.yapin.shanduo.okhttp.OkHttp;
import com.yapin.shanduo.presenter.OnLoadListener;
import com.yapin.shanduo.utils.ApiUtil;
import com.yapin.shanduo.utils.Constants;
import com.yapin.shanduo.utils.NetWorkUtil;
import com.yapin.shanduo.utils.PrefUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * 作者：L on 2018/6/5 0005 09:36
 */
public class PayOrderModelImpl implements PayOrderLoadModel{
    @Override
    public void load(final OnLoadListener<PayOrder> listener, final String payId, String password, String typeId, String money, String month, String activityId) {
        Context context = ShanDuoPartyApplication.getContext();
        if (!NetWorkUtil.isNetworkAvailable(context)) {
            listener.networkError();
            return;
        }
        final Map<String , String> params = new HashMap<>();
        params.put("token" , PrefUtil.getToken(context));
        params.put("password" , password);
        params.put("payId" , payId);
        params.put("typeId" , typeId);
        params.put("money" , money);
        params.put("month" , month);
        params.put("activityId" , activityId);
        params.put("location" , PrefUtil.getCity(context));
        Log.d("params", "load: --"+params.toString());
        OkHttp.post(context, ApiUtil.GET_ORDER, params, new JavaOkCallback() {
            @Override
            public void onFailure(String msg) {
                Log.d("PayOrderModelImpl" ,msg);
                listener.onError(msg);
            }

            @Override
            public void onResponse(String response) {
                Log.d("PayOrderModelImpl" ,response);

                PayOrder order = new PayOrder();

                String res = null;
                try {
                    res = new JSONObject(response).get("result").toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if(payId == Constants.PAY_WECHAT){
                    order = new Gson().fromJson(res , PayOrder.class);
                }else {
                    order.setOrder(res);
                }

                listener.onSuccess(order);
            }
        });
    }
}

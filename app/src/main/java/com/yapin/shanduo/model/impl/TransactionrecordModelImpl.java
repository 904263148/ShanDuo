package com.yapin.shanduo.model.impl;

import android.content.Context;

import com.google.gson.Gson;
import com.yapin.shanduo.app.ShanDuoPartyApplication;
import com.yapin.shanduo.model.TransactionrecordModel;
import com.yapin.shanduo.model.entity.TransactionrecordInfo;
import com.yapin.shanduo.model.entity.TrendInfo;
import com.yapin.shanduo.okhttp.JavaOkCallback;
import com.yapin.shanduo.okhttp.OkHttp;
import com.yapin.shanduo.presenter.OnMultiLoadListener;
import com.yapin.shanduo.utils.ApiUtil;
import com.yapin.shanduo.utils.NetWorkUtil;
import com.yapin.shanduo.utils.PrefJsonUtil;
import com.yapin.shanduo.utils.PrefUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2018/6/4.
 */

public class TransactionrecordModelImpl implements TransactionrecordModel {
    @Override
    public void load(final OnMultiLoadListener<List<TransactionrecordInfo.Trend>> listener , String page , String pageSize) {
        final Context context = ShanDuoPartyApplication.getContext();
        if (!NetWorkUtil.isNetworkAvailable(context)) {
            listener.networkError();
            return;
        }

        Map<String,String> params = new HashMap<>();
        params.put("token", PrefUtil.getToken(context));
        params.put("page" , page);
        params.put("pageSize" ,pageSize);

        OkHttp.post(context, ApiUtil.TRANSACTION_RECORD, params, new JavaOkCallback() {
            @Override
            public void onFailure(String msg) {
//                Log.d("trend_result_my" , msg);
                listener.onError(msg);
            }

            @Override
            public void onResponse(String response) {
                TransactionrecordInfo info = null;
                try {
                    info = new Gson().fromJson(new JSONObject(response).get("result").toString(),TransactionrecordInfo.class);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                List<TransactionrecordInfo.Trend> list = info.getList();
                listener.onSuccess(list , info.getTotalPage());

            }
        });
    }
}

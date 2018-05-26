package com.yapin.shanduo.model.impl;

import android.content.Context;

import com.yapin.shanduo.app.ShanDuoPartyApplication;
import com.yapin.shanduo.model.ModifyLoadModel;
import com.yapin.shanduo.okhttp.JavaOkCallback;
import com.yapin.shanduo.okhttp.OkHttp;
import com.yapin.shanduo.presenter.OnLoadListener;
import com.yapin.shanduo.utils.ApiUtil;
import com.yapin.shanduo.utils.NetWorkUtil;
import com.yapin.shanduo.utils.PrefJsonUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dell on 2018/4/19.
 */

public class ModifyModelImpI implements ModifyLoadModel {
    @Override
    public void load(final OnLoadListener<String> listener, String name, String gender, String birthday , String emotion, String signature, String hometown, String occupation, String school , String picture , String background) {
        final Context context = ShanDuoPartyApplication.getContext();
        if (!NetWorkUtil.isNetworkAvailable(context)) {
            listener.networkError();
            return;
        }

        Map<String,String> params = new HashMap<>();
        params.put("token", PrefJsonUtil.getProfile(context).getToken());
        params.put("typeId","3");
//        params.put("headPortraitId","22");
        params.put("name",name);
        params.put("birthday",birthday);
        params.put("gender",gender);
        params.put("emotion",emotion);
        params.put("signature",signature);
        params.put("hometown",hometown);
        params.put("occupation",occupation);
        params.put("school",school);
        params.put("picture",picture);
        params.put("background",background);
        OkHttp.post(context, ApiUtil.MODIFY_IN, params, new JavaOkCallback() {
            @Override
            public void onFailure(String msg) {
                listener.onError(msg);
            }

            @Override
            public void onResponse(String response) {
                try {
                    PrefJsonUtil.setProfile(context , new JSONObject(response).getString("result"));
                    listener.onSuccess("修改成功");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}

package com.yapin.shanduo.model.impl;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.yapin.shanduo.app.ShanDuoPartyApplication;
import com.yapin.shanduo.model.GetGroupMebLoadModel;
import com.yapin.shanduo.model.entity.IMGroupInfo;
import com.yapin.shanduo.model.entity.IMGroupUserInfo;
import com.yapin.shanduo.okhttp.JavaOkCallback;
import com.yapin.shanduo.okhttp.OkHttp;
import com.yapin.shanduo.presenter.OnMultiLoadListener;
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
 * 作者：L on 2018/6/19 0019 10:25
 */
public class GetGroupMebModelImpl implements GetGroupMebLoadModel{
    @Override
    public void load(final OnMultiLoadListener<List<IMGroupUserInfo.GroupMebInfo>> listener, String groupId, String page) {
        Context context = ShanDuoPartyApplication.getContext();
        if (!NetWorkUtil.isNetworkAvailable(context)) {
            listener.networkError();
            return;
        }
        Map<String,String> params = new HashMap<>();
        params.put("token" , PrefUtil.getToken(context));
        params.put("groupId" , groupId);
        params.put("page" , page);
        OkHttp.post(context, ApiUtil.GROUP_USER, params, new JavaOkCallback() {
            @Override
            public void onFailure(String msg) {
                Log.d("GetGroupMebModelImpl" ,msg);
                listener.onError(msg);
            }

            @Override
            public void onResponse(String response) {
                Log.d("GetGroupMebModelImpl" , response);
                List<IMGroupUserInfo.GroupMebInfo> list = new ArrayList<>();
                IMGroupUserInfo info = new IMGroupUserInfo();
                try {
                    info = new Gson().fromJson( new JSONObject(response).get("result").toString() , IMGroupUserInfo.class);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                list.addAll(info.getMemberList());
                listener.onSuccess(list , info.getTotalPage());
            }
        });
    }
}

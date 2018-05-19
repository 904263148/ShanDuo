package com.yapin.shanduo.model.impl;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.yapin.shanduo.app.ShanDuoPartyApplication;
import com.yapin.shanduo.model.TrendFirstReplayLoadModel;
import com.yapin.shanduo.model.entity.CommentInfo;
import com.yapin.shanduo.model.entity.TrendInfo;
import com.yapin.shanduo.okhttp.JavaOkCallback;
import com.yapin.shanduo.okhttp.OkHttp;
import com.yapin.shanduo.presenter.OnMultiLoadListener;
import com.yapin.shanduo.utils.ApiUtil;
import com.yapin.shanduo.utils.NetWorkUtil;
import com.yapin.shanduo.utils.PrefUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 作者：L on 2018/5/17 0017 18:12
 */
public class TrendFirstFirstReplayModelImpl implements TrendFirstReplayLoadModel {
    @Override
    public void load(final OnMultiLoadListener<List<CommentInfo.Comment>> listener, String dynamicId  ,final String typeId , String page , String pageSize) {
        Context context = ShanDuoPartyApplication.getContext();
        if (!NetWorkUtil.isNetworkAvailable(context)) {
            listener.networkError();
            return;
        }
        Map<String ,String> params = new HashMap<>();
        params.put("token" , PrefUtil.getToken(context));
        params.put("dynamicId" , dynamicId);
        params.put("typeId",typeId);
        params.put("page" , page);
        params.put("pageSize" , pageSize);
        OkHttp.get(context, ApiUtil.TREND_FIRST_REPLAY, params, new JavaOkCallback() {
            @Override
            public void onFailure(String msg) {
                Log.d("first_replay_result",msg);
                listener.onError(msg);
            }

            @Override
            public void onResponse(String response) {
                Log.d("first_replay_result",response);
                CommentInfo info = new CommentInfo();
                try {
                    info = new Gson().fromJson( new JSONObject(response).get("result").toString() , CommentInfo.class);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                List<CommentInfo.Comment> list = info.getList();
                listener.onSuccess(list , info.getTotalPage());
            }
        });
    }
}

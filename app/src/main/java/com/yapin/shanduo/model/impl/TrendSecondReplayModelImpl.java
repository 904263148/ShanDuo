package com.yapin.shanduo.model.impl;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.yapin.shanduo.app.ShanDuoPartyApplication;
import com.yapin.shanduo.model.TrendSecondReplayLoadModel;
import com.yapin.shanduo.model.entity.CommentInfo;
import com.yapin.shanduo.model.entity.SecondComment;
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
 * 作者：L on 2018/5/19 0019 10:09
 */
public class TrendSecondReplayModelImpl implements TrendSecondReplayLoadModel{
    @Override
    public void load(final OnMultiLoadListener<List<SecondComment.Comments>> listener, String commentId, String typeId, String page, String pageSize) {
        Context context = ShanDuoPartyApplication.getContext();
        if (!NetWorkUtil.isNetworkAvailable(context)) {
            listener.networkError();
            return;
        }
        Map<String ,String> params = new HashMap<>();
        params.put("token" , PrefUtil.getToken(context));
        params.put("commentId" , commentId);
        params.put("typeId",typeId);
        params.put("page" , page);
        params.put("pageSize" , pageSize);
        OkHttp.get(context, ApiUtil.TREND_FIRST_REPLAY, params, new JavaOkCallback() {
            @Override
            public void onFailure(String msg) {
                Log.d("second_replay_result",msg);
                listener.onError(msg);
            }

            @Override
            public void onResponse(String response) {
                Log.d("second_replay_result",response);
                SecondComment info = new SecondComment();
                try {
                    info = new Gson().fromJson( new JSONObject(response).get("result").toString() , SecondComment.class);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                List<SecondComment.Comments> list = info.getList();
                listener.onSuccess(list , info.getTotalPage());
            }
        });
    }
}

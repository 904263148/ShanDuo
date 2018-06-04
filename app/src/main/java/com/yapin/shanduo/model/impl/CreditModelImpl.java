package com.yapin.shanduo.model.impl;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.yapin.shanduo.app.ShanDuoPartyApplication;
import com.yapin.shanduo.model.CreditLoadModel;
import com.yapin.shanduo.model.entity.CreditInfo;
import com.yapin.shanduo.model.entity.CreditItem;
import com.yapin.shanduo.okhttp.JavaOkCallback;
import com.yapin.shanduo.okhttp.OkHttp;
import com.yapin.shanduo.presenter.OnMultiLoadListener;
import com.yapin.shanduo.utils.ApiUtil;
import com.yapin.shanduo.utils.Constants;
import com.yapin.shanduo.utils.NetWorkUtil;
import com.yapin.shanduo.utils.PrefUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 作者：L on 2018/6/1 0001 09:36
 */
public class CreditModelImpl implements CreditLoadModel{
    @Override
    public void load(final OnMultiLoadListener<List<CreditItem>> listener, String userId, String page, String pageSize, String type) {
        Context context = ShanDuoPartyApplication.getContext();
        if (!NetWorkUtil.isNetworkAvailable(context)) {
            listener.networkError();
            return;
        }
        Map<String,String> params = new HashMap<>();
        params.put("token" , PrefUtil.getToken(context));
        params.put("userId" , userId);
        params.put("page" , page);
        params.put("pageSize" , pageSize);
        params.put("type" , type);
        OkHttp.post(context, ApiUtil.CREDIT_DETALILS, params, new JavaOkCallback() {
            @Override
            public void onFailure(String msg) {
                Log.d("CreditModelImpl",msg);
                listener.onError(msg);
            }

            @Override
            public void onResponse(String response) {
                Log.d("CreditModelImpl",response);

                List<CreditItem> list = new ArrayList<>();

                CreditInfo info = null;
                try {
                    info = new Gson().fromJson(new JSONObject(response).get("result").toString(),CreditInfo.class);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                for (CreditInfo.Credit credit: info.getList()){

                    CreditItem creditTop = new CreditItem();
                    creditTop.setType(Constants.TYPE_BOTTOM);
                    if(info.getMap() != null){
                        creditTop.setFather_head(info.getMap().getHead_portrait_id());
                        creditTop.setFather_id(info.getMap().getId());
                        creditTop.setFather_reputation(info.getMap().getReputation());
                    }
                    creditTop.setMode(credit.getMode());
                    creditTop.setActivity_name(credit.getActivity_name());
                    creditTop.setBirthday(credit.getBirthday());
                    creditTop.setPresenter_head(credit.getHead_portrait_id());
                    creditTop.setPresenter_name(credit.getUser_name());
                    creditTop.setGender(credit.getGender());
                    creditTop.setVipGrade(credit.getVipGrade());
                    creditTop.setId(credit.getId());
                    creditTop.setTitle(true);
                    list.add(creditTop);

                    for (CreditItem item : credit.getScoreList()){
                        item.setType(Constants.TYPE_BOTTOM);
                        item.setTitle(false);
                        item.setFather_head(info.getMap().getHead_portrait_id());
                        item.setFather_id(info.getMap().getId());
                        item.setFather_reputation(info.getMap().getReputation());
                        item.setMode(credit.getMode());
                        item.setActivity_name(credit.getActivity_name());
                        item.setBirthday(credit.getBirthday());
                        item.setPresenter_head(credit.getHead_portrait_id());
                        item.setPresenter_name(credit.getUser_name());
                        item.setGender(credit.getGender());
                        item.setVipGrade(credit.getVipGrade());
                        item.setId(credit.getId());
                        item.setBe_evaluated(credit.getBe_evaluated());
                        item.setOthers_score(credit.getOthers_score());
                        list.add(item);
                    }

                }

                listener.onSuccess(list , info.getTotalpage());

            }
        });
    }
}

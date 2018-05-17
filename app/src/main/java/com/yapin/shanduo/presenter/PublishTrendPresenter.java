package com.yapin.shanduo.presenter;

import android.content.Context;

import com.yapin.shanduo.model.PublishTrendLoadModel;
import com.yapin.shanduo.model.impl.PublishTrendLoadModelImpl;
import com.yapin.shanduo.ui.contract.PublishTrendContract;

/**
 * Created by dell on 2018/5/14.
 */

public class PublishTrendPresenter implements PublishTrendContract.Presenter {

    private PublishTrendContract.View view;
    private PublishTrendLoadModel publishTrendLoadModel;
    private Context context;

    public void init(Context context, PublishTrendContract.View view){

        this.context = context;
        this.view = view;
        this.view.initView();
        publishTrendLoadModel = new PublishTrendLoadModelImpl();

    }

    @Override
    public void start() {

    }

    @Override
    public void publish(String content, String picture, String lat, String lon , String location) {
        publishTrendLoadModel.load(new OnLoadListener<String>() {
            @Override
            public void onSuccess(String success) {
                view.success(success);
            }

            @Override
            public void onError(String msg) {
                view.error(msg);
            }

            @Override
            public void networkError() {
                view.networkError();
            }
        },content,picture,lat,lon , location);
    }
}

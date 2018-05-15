package com.yapin.shanduo.presenter;

import android.content.Context;

import com.yapin.shanduo.model.PublishingdynamicsModel;
import com.yapin.shanduo.model.entity.Publishingdynamics;
import com.yapin.shanduo.model.impl.PublishingdynamicsModelImpl;
import com.yapin.shanduo.ui.contract.PublishingdynamicsContract;

/**
 * Created by dell on 2018/5/14.
 */

public class PublishingdynamicsPrsesnter implements PublishingdynamicsContract.Presenter {

    private PublishingdynamicsContract.View view;
    private PublishingdynamicsModel publishingdynamicsModel;
    private Context context;

    public void init(Context context, PublishingdynamicsContract.View view){

        this.context = context;
        this.view = view;
        this.view.initView();
        publishingdynamicsModel = new PublishingdynamicsModelImpl();

    }

    @Override
    public void start() {

    }

    @Override
    public void Publishingdynamics(String content, String picture, String lat, String lon) {
        publishingdynamicsModel.load(new OnLoadListener<String>() {
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
        },content,picture,lat,lon);
    }
}

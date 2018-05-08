package com.yapin.shanduo.presenter;

import android.content.Context;

import com.yapin.shanduo.model.AddactivityModel;
import com.yapin.shanduo.model.impl.AddactivityModelImpl;
import com.yapin.shanduo.ui.contract.AddactivityContract;

/**
 * Created by dell on 2018/5/5.
 */

public class Addactivitypresenter implements AddactivityContract.Presenter{

    private AddactivityModel addactivityModel;
    private AddactivityContract.View view;
    private Context context;

    public void init (Context context, AddactivityContract.View view){
        this.context = context;
        this.view = view;
        this.view.initView();
        addactivityModel = new AddactivityModelImpl();
    }


    @Override
    public void start() {

    }

    @Override
    public void addactivity(String activityType, String activityStartTime, String activityAddress, String mode, String manNumber, String womanNumber, String remarks, String activityCutoffTime, String lon, String lat) {
        addactivityModel.load(new OnLoadListener<String>() {
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
        },activityType,activityStartTime,activityAddress,mode,manNumber,womanNumber,remarks,activityCutoffTime,lon,lat);
    }
}

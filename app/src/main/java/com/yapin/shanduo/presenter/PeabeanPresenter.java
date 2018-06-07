package com.yapin.shanduo.presenter;

import android.content.Context;

import com.yapin.shanduo.model.PeabeanModel;
import com.yapin.shanduo.model.impl.PeabeanModelImpl;
import com.yapin.shanduo.ui.contract.PeabeanContract;

/**
 * Created by dell on 2018/6/6.
 */

public class PeabeanPresenter implements PeabeanContract.Presenter {

    private  PeabeanContract.View view;
    private PeabeanModel peabeanModel;
    private Context context;

    public void init(Context context, PeabeanContract.View view) {

        this.context = context;
        this.view = view;
        this.view.initView();
        peabeanModel = new PeabeanModelImpl();

    }

    @Override
    public void start() {

    }

    @Override
    public void peabean(String activityId ,String typeId) {
        peabeanModel.load(new OnLoadListener<String>() {
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
        },activityId ,typeId);
    }
}

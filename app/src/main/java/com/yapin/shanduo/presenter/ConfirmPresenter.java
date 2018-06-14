package com.yapin.shanduo.presenter;

import android.content.Context;

import com.yapin.shanduo.model.ConfirmModel;
import com.yapin.shanduo.model.impl.ConfirmModelImpl;
import com.yapin.shanduo.ui.contract.ConfirmContract;

/**
 * Created by dell on 2018/6/6.
 */

public class ConfirmPresenter implements ConfirmContract.Presenter {

    private  ConfirmContract.View view;
    private ConfirmModel confirmModel;
    private Context context;

    public void init(Context context, ConfirmContract.View view) {

        this.context = context;
        this.view = view;
        this.view.initView();
        confirmModel = new ConfirmModelImpl();
    }

    @Override
    public void start() {

    }

    @Override
    public void deleteconfirm(String activityId) {
        confirmModel.load(new OnLoadListener<String>() {
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
        },activityId);
    }
}

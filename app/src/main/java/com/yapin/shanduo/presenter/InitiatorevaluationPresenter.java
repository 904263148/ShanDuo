package com.yapin.shanduo.presenter;

import android.content.Context;

import com.yapin.shanduo.model.InitiatorevaluationModel;
import com.yapin.shanduo.model.impl.InitiatorevaluationModelImpl;
import com.yapin.shanduo.ui.contract.InitiatorevaluationContract;

/**
 * Created by dell on 2018/5/29.
 */

public class InitiatorevaluationPresenter implements InitiatorevaluationContract.Presenter {

    private InitiatorevaluationContract.View view;
    private InitiatorevaluationModel loadModel;
    private Context context;

    public void init(Context context, InitiatorevaluationContract.View view) {
        this.context = context;
        this.view = view;
        this.view.initView();
        loadModel = new InitiatorevaluationModelImpl();
    }

    @Override
    public void start() {

    }

    @Override
    public void initiat(String score, String evaluationcontent ,  String activityId) {
        view.loading();
        loadModel.load(new OnLoadListener<String>() {
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
        },score ,evaluationcontent  ,activityId);
    }
}

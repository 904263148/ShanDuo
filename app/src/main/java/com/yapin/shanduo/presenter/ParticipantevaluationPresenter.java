package com.yapin.shanduo.presenter;

import android.content.Context;

import com.yapin.shanduo.model.ParticipantevaluationModel;
import com.yapin.shanduo.model.impl.ParticipantevaluationModelImpl;
import com.yapin.shanduo.ui.contract.ParticipantevaluationContract;

/**
 * Created by dell on 2018/5/30.
 */

public class ParticipantevaluationPresenter implements ParticipantevaluationContract.Presenter{

    private ParticipantevaluationContract.View view;
    private ParticipantevaluationModel loadModel;
    private Context context;

    public void init(Context context, ParticipantevaluationContract.View view) {
        this.context = context;
        this.view = view;
        this.view.initView();
        loadModel = new ParticipantevaluationModelImpl();
    }


    @Override
    public void start() {

    }

    @Override
    public void initiat(String activityId, String data) {
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
        },activityId , data);

    }
}

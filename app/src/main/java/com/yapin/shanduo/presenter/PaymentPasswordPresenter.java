package com.yapin.shanduo.presenter;

import android.content.Context;

import com.yapin.shanduo.model.ParticipantevaluationModel;
import com.yapin.shanduo.model.PaymentPasswordModel;
import com.yapin.shanduo.model.impl.ParticipantevaluationModelImpl;
import com.yapin.shanduo.model.impl.PaymentPasswordModelImpl;
import com.yapin.shanduo.ui.contract.PaymentPasswordContract;

/**
 * Created by dell on 2018/6/1.
 */

public class PaymentPasswordPresenter implements PaymentPasswordContract.Presenter {

    private  PaymentPasswordContract.View view;
    private PaymentPasswordModel paymentPasswordModel;
    private Context context;

    public void init(Context context, PaymentPasswordContract.View view) {

        this.context = context;
        this.view = view;
        this.view.initView();
        paymentPasswordModel = new PaymentPasswordModelImpl();

    }

    @Override
    public void start() {

    }

    @Override
    public void paymentpassword(String password) {
        paymentPasswordModel.load(new OnLoadListener<String>() {
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
        },password);
    }
}

package com.yapin.shanduo.presenter;

import android.content.Context;

import com.yapin.shanduo.model.VerificationModel;
import com.yapin.shanduo.model.impl.VerificationModelImpl;
import com.yapin.shanduo.ui.contract.VerificationConract;

/**
 * Created by dell on 2018/6/8.
 */

public class VerificationPresenter implements VerificationConract.Presenter {

    private  VerificationConract.View view;
    private VerificationModel verificationModel;
    private Context context;

    public void init(Context context, VerificationConract.View view) {

        this.context = context;
        this.view = view;
        this.view.initView();
        verificationModel = new VerificationModelImpl();

    }

    @Override
    public void start() {

    }

    @Override
    public void getverification(String typeId, String phone, String code) {
        verificationModel.load(new OnLoadListener<String>() {
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
        },typeId , phone , code );
    }
}

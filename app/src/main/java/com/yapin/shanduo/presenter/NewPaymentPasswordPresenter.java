package com.yapin.shanduo.presenter;

import android.content.Context;

import com.yapin.shanduo.model.NewPaymentPasswordModel;
import com.yapin.shanduo.model.impl.NewPaymentPasswordModelImpl;
import com.yapin.shanduo.ui.contract.NewPaymentPasswordContract;

/**
 * Created by dell on 2018/6/1.
 */

public class NewPaymentPasswordPresenter implements NewPaymentPasswordContract.Presenter {

    private  NewPaymentPasswordContract.View view;
    private NewPaymentPasswordModel newPaymentPasswordModel;
    private Context context;

    public void init(Context context, NewPaymentPasswordContract.View view) {

        this.context = context;
        this.view = view;
        this.view.initView();
        newPaymentPasswordModel = new NewPaymentPasswordModelImpl();

    }

    @Override
    public void start() {

    }

    @Override
    public void newpaymentpassword(String typeId , String code ,String password, String newPassword) {
        newPaymentPasswordModel.load(new OnLoadListener<String>() {
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
        },typeId , code ,password ,newPassword);
    }
}

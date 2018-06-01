package com.yapin.shanduo.presenter;

import android.content.Context;

import com.yapin.shanduo.model.LoginPasswordModel;
import com.yapin.shanduo.model.impl.LoginPasswordModelImpl;
import com.yapin.shanduo.ui.contract.LoginPasswordContract;

/**
 * Created by dell on 2018/5/31.
 */

public class LoginPasswordPresenter implements LoginPasswordContract.Presenter {

    private  LoginPasswordContract.View view;
    private LoginPasswordModel loginPasswordModel;
    private Context context;

    public void init(Context context, LoginPasswordContract.View view) {

        this.context = context;
        this.view = view;
        this.view.initView();
        loginPasswordModel = new LoginPasswordModelImpl();

    }

    @Override
    public void start() {

    }

    @Override
    public void loginpassword(String password, String newPassword) {
        loginPasswordModel.load(new OnLoadListener<String>() {
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
        },password , newPassword);
    }

}

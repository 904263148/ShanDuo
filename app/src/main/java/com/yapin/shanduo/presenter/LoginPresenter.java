package com.yapin.shanduo.presenter;

import android.content.Context;

import com.yapin.shanduo.model.LoginLoadModel;
import com.yapin.shanduo.model.impl.LoginModelImpl;
import com.yapin.shanduo.ui.contract.LoginContract;

public class LoginPresenter implements LoginContract.Presenter{
    private LoginContract.View view;
    private LoginLoadModel loadModel;
    private Context context;

    public void init(Context context, LoginContract.View view) {
        this.context = context;
        this.view = view;
        this.view.initView();
        loadModel = new LoginModelImpl();
    }

    @Override
    public void login(String username, String password) {
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
        } , username , password);
    }

    @Override
    public void start() {

    }
}

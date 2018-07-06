package com.yapin.shanduo.presenter;

import android.content.Context;

import com.yapin.shanduo.model.SignModel;
import com.yapin.shanduo.model.entity.SigninInfo;
import com.yapin.shanduo.model.impl.SigninModelImpl;
import com.yapin.shanduo.ui.contract.SigninContract;

/**
 * Created by dell on 2018/6/8.
 */

public class SigninPresenter implements SigninContract.Presenter {

    private  SigninContract.View view;
    private SignModel signinModel;
    private Context context;

    public void init(Context context, SigninContract.View view) {
        this.view = view;
        view.initView();
        signinModel = new SigninModelImpl();
    }

    @Override
    public void start() {

    }

    @Override
    public void getsignin() {
        signinModel.load(new OnLoadListener<SigninInfo>() {
            @Override
            public void onSuccess(SigninInfo success) {
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
        });
    }
}

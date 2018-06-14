package com.yapin.shanduo.presenter;

import com.yapin.shanduo.model.UserDetailLoadModel;
import com.yapin.shanduo.model.impl.UserDetailModelImpl;
import com.yapin.shanduo.ui.contract.UserDetailContract;

/**
 * 作者：L on 2018/6/14 0014 17:51
 */
public class UserDetailPresenter implements UserDetailContract.Presenter{

    private UserDetailContract.View view;
    private UserDetailLoadModel loadModel;

    public void init(UserDetailContract.View view){
        this.view = view;
        loadModel = new UserDetailModelImpl();
    }

    @Override
    public void start() {
        loadModel.load(new OnLoadListener<String>() {
            @Override
            public void onSuccess(String success) {
                view.dataSuccess(success);
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

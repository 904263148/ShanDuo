package com.yapin.shanduo.presenter;

import com.yapin.shanduo.model.UserProfLoadModel;
import com.yapin.shanduo.model.entity.ShanDuoUserProf;
import com.yapin.shanduo.model.impl.UserProfModelImpl;
import com.yapin.shanduo.ui.contract.UserProfContract;

/**
 * 作者：L on 2018/5/25 0025 16:54
 */
public class UserProfPresenter implements UserProfContract.Presenter{

    private UserProfContract.View view;
    private UserProfLoadModel loadModel;

    public void init(UserProfContract.View view){
        this.view = view;
        loadModel = new UserProfModelImpl();
        view.initView();
    }

    @Override
    public void getData(String userId) {
        loadModel.load(new OnLoadListener<ShanDuoUserProf>() {
            @Override
            public void onSuccess(ShanDuoUserProf success) {
                view.show(success);
            }

            @Override
            public void onError(String msg) {
                view.error(msg);
            }

            @Override
            public void networkError() {
                view.networkError();
            }
        } , userId);
    }
}

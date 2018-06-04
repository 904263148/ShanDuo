package com.yapin.shanduo.presenter;

import com.yapin.shanduo.model.CheckGroupLoadModel;
import com.yapin.shanduo.model.impl.CheckGroupModelImpl;
import com.yapin.shanduo.ui.contract.CheckGroupContract;

/**
 * 作者：L on 2018/6/1 0001 17:48
 */
public class CheckGroupPresenter implements CheckGroupContract.Presenter{

    private CheckGroupContract.View view;
    private CheckGroupLoadModel loadModel;

    public void init(CheckGroupContract.View view){
        this.view = view;
        loadModel = new CheckGroupModelImpl();
        view.initView();
    }

    @Override
    public void checkGroup(String groupType) {
        loadModel.load(new OnLoadListener<String>() {
            @Override
            public void onSuccess(String success) {
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
        } , groupType);
    }
}

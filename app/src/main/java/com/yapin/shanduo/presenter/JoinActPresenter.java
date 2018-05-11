package com.yapin.shanduo.presenter;

import com.yapin.shanduo.model.JoinActLoadModel;
import com.yapin.shanduo.model.impl.JoinActModelImpl;
import com.yapin.shanduo.ui.contract.JoinActContract;

/**
 * 作者：L on 2018/5/11 0011 10:13
 */
public class JoinActPresenter implements JoinActContract.Presenter{

    private JoinActContract.View view;
    private JoinActLoadModel loadModel;

    public void init(JoinActContract.View view){
        loadModel = new JoinActModelImpl();
        this.view = view;
        view.initView();
    }

    @Override
    public void join(String activityId) {
        loadModel.load(new OnLoadListener<String>() {
            @Override
            public void onSuccess(String success) {
                view.success(success);
            }

            @Override
            public void onError(String msg) {
                view.joinError(msg);
            }

            @Override
            public void networkError() {
                view.networkError();
            }
        } , activityId);

    }
}

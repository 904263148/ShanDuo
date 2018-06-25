package com.yapin.shanduo.presenter;

import com.yapin.shanduo.model.ActivityDetailLoadModel;
import com.yapin.shanduo.model.entity.ActivityInfo;
import com.yapin.shanduo.model.impl.ActivityDetailModelImpl;
import com.yapin.shanduo.ui.contract.ActivityDetailContract;

/**
 * 作者：L on 2018/6/22 0022 16:46
 */
public class ActivityDetailPresenter implements ActivityDetailContract.Presenter{

    private ActivityDetailContract.View view;
    private ActivityDetailLoadModel loadModel;

    public void init(ActivityDetailContract.View view){
        this.view = view;
        loadModel = new ActivityDetailModelImpl();
    }

    @Override
    public void getData(String activityId) {
        loadModel.load(new OnLoadListener<ActivityInfo.Act>() {
            @Override
            public void onSuccess(ActivityInfo.Act success) {
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
        } , activityId);
    }
}

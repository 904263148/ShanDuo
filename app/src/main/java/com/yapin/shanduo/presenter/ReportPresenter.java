package com.yapin.shanduo.presenter;

import com.yapin.shanduo.model.ReportLoadModel;
import com.yapin.shanduo.model.impl.ReportModelImpl;
import com.yapin.shanduo.ui.contract.ReportContract;

/**
 * 作者：L on 2018/6/7 0007 18:15
 */
public class ReportPresenter implements ReportContract.Presenter{

    private ReportContract.View view;
    private ReportLoadModel loadModel;

    public void init(ReportContract.View view){
        this.view = view;
        loadModel = new ReportModelImpl();
        view.initView();
    }

    @Override
    public void getData(String userId, String activityId, String dynamicId, String typeId, String remarks) {
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
        } , userId , activityId , dynamicId , typeId , remarks);
    }
}

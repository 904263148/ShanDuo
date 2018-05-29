package com.yapin.shanduo.presenter;

import com.yapin.shanduo.model.JoinActUserLoadModel;
import com.yapin.shanduo.model.entity.JoinActUser;
import com.yapin.shanduo.model.impl.JoinActUserModelImpl;
import com.yapin.shanduo.ui.contract.JoinActUserContract;

import java.util.List;

/**
 * 作者：L on 2018/5/26 0026 14:46
 */
public class JoinActUserPresenter implements JoinActUserContract.Presenter{

    private JoinActUserContract.View view;
    private JoinActUserLoadModel loadModel;

    public void init(JoinActUserContract.View view){
        this.view = view;
        loadModel = new JoinActUserModelImpl();
        view.initView();
    }

    @Override
    public void getData(String activityId , String page , String pageSize) {
        loadModel.load(new OnMultiLoadListener<List<JoinActUser.ActUser>>() {
            @Override
            public void onSuccess(List<JoinActUser.ActUser> success, int totalPage) {
                view.show(success , totalPage);
            }

            @Override
            public void onError(String msg) {
                view.error(msg);
            }

            @Override
            public void networkError() {
                view.networkError();
            }
        } , activityId , page , pageSize);
    }
}

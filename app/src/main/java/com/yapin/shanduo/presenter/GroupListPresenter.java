package com.yapin.shanduo.presenter;

import com.yapin.shanduo.model.GroupListLoadModel;
import com.yapin.shanduo.model.entity.IMGroupInfo;
import com.yapin.shanduo.model.impl.GroupListModelImpl;
import com.yapin.shanduo.ui.contract.GroupListContract;

import java.util.List;

/**
 * 作者：L on 2018/6/16 0016 11:57
 */
public class GroupListPresenter implements GroupListContract.Presenter{

    private GroupListContract.View view;
    private GroupListLoadModel loadModel;

    public void init(GroupListContract.View view){
        this.view = view;
        loadModel = new GroupListModelImpl();
        view.initView();
    }

    @Override
    public void getData() {
        loadModel.load(new OnLoadListener<List<IMGroupInfo.GroupInfo>>() {
            @Override
            public void onSuccess(List<IMGroupInfo.GroupInfo> success) {
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
        });
    }
}

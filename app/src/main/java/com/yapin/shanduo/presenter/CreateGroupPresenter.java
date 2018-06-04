package com.yapin.shanduo.presenter;

import com.yapin.shanduo.model.CreateGroupLoadModel;
import com.yapin.shanduo.model.impl.CreateGroupModelImpl;
import com.yapin.shanduo.ui.contract.CreateGroupContract;

/**
 * 作者：L on 2018/6/1 0001 19:34
 */
public class CreateGroupPresenter implements CreateGroupContract.Presenter{

    private CreateGroupContract.View view;
    private CreateGroupLoadModel loadModel;

    public void init(CreateGroupContract.View view){
        this.view = view;
        loadModel = new CreateGroupModelImpl();
    }

    @Override
    public void createGroup(String typeId, String groupId, String groupType) {
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
        } , typeId , groupId , groupType);
    }
}

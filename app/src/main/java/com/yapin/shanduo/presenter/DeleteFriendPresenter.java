package com.yapin.shanduo.presenter;

import com.yapin.shanduo.model.DeleteFriendLoadModel;
import com.yapin.shanduo.model.impl.DeleteFriendModelImpl;
import com.yapin.shanduo.ui.contract.DeleteFriendContract;

/**
 * 作者：L on 2018/6/6 0006 15:58
 */
public class DeleteFriendPresenter implements DeleteFriendContract.Presenter{

    private DeleteFriendContract.View view;
    private DeleteFriendLoadModel loadModel;

    public void init(DeleteFriendContract.View view){
        this.view = view;
        loadModel = new DeleteFriendModelImpl();
    }

    @Override
    public void delete(String attention, String typeId) {
        loadModel.load(new OnLoadListener<String>() {
            @Override
            public void onSuccess(String success) {
                view.deleteSuccess(success);
            }

            @Override
            public void onError(String msg) {
                view.error(msg);
            }

            @Override
            public void networkError() {
                view.networkError();
            }
        } , attention , typeId);
    }
}

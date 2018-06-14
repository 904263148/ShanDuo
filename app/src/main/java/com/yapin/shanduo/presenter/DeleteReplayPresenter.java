package com.yapin.shanduo.presenter;

import com.yapin.shanduo.model.DeleteReplayLoadModel;
import com.yapin.shanduo.model.impl.DeleteReplayModelImpl;
import com.yapin.shanduo.ui.contract.DeleteReplayContract;

/**
 * 作者：L on 2018/6/14 0014 11:53
 */
public class DeleteReplayPresenter implements DeleteReplayContract.Presenter{

    private DeleteReplayContract.View view;
    private DeleteReplayLoadModel loadModel;

    public void init(DeleteReplayContract.View view){
        this.view = view;
        loadModel = new DeleteReplayModelImpl();
    }

    @Override
    public void delete(String commentId) {
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
        } , commentId);
    }
}

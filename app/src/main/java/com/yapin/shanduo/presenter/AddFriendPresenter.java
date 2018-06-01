package com.yapin.shanduo.presenter;

import com.yapin.shanduo.model.AddFriendLoadModel;
import com.yapin.shanduo.model.impl.AddFriendModelImpl;
import com.yapin.shanduo.ui.contract.AddFriendContract;

/**
 * 作者：L on 2018/5/29 0029 16:36
 */
public class AddFriendPresenter implements AddFriendContract.Presenter{

    private AddFriendContract.View view;
    private AddFriendLoadModel loadModel;

    public void init(AddFriendContract.View view){
        this.view = view;
        loadModel = new AddFriendModelImpl();
    }

    @Override
    public void addFriend(String attention) {
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
        } , attention);
    }
}

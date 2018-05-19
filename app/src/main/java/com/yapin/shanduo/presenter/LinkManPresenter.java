package com.yapin.shanduo.presenter;

import com.yapin.shanduo.model.LinkManLoadModel;
import com.yapin.shanduo.model.entity.FriendInfo;
import com.yapin.shanduo.model.impl.LinkManModelImpl;
import com.yapin.shanduo.ui.contract.LinkManContract;

import java.util.List;

/**
 * 作者：L on 2018/5/19 0019 15:28
 */
public class LinkManPresenter implements LinkManContract.Presenter{

    private LinkManContract.View view;
    private LinkManLoadModel loadModel;

    public void init(LinkManContract.View view){
        this.view = view;
        loadModel = new LinkManModelImpl();
        view.initView();
    }

    @Override
    public void getData(String typeId) {
        loadModel.load(new OnLoadListener<List<FriendInfo.fInfo>>() {
            @Override
            public void onSuccess(List<FriendInfo.fInfo> success) {
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
        } , typeId);
    }
}

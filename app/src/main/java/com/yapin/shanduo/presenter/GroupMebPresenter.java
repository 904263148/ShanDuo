package com.yapin.shanduo.presenter;

import com.yapin.shanduo.model.GetGroupMebLoadModel;
import com.yapin.shanduo.model.entity.IMGroupUserInfo;
import com.yapin.shanduo.model.impl.GetGroupMebModelImpl;
import com.yapin.shanduo.ui.contract.GroupMebContract;

import java.util.List;

/**
 * 作者：L on 2018/6/19 0019 10:30
 */
public class GroupMebPresenter implements GroupMebContract.Presenter{

    private GroupMebContract.View view;
    private GetGroupMebLoadModel loadModel;

    public void init(GroupMebContract.View view){
        this.view = view;
        loadModel = new GetGroupMebModelImpl();
        view.initView();
    }

    @Override
    public void getData(String groupId, String page) {
        loadModel.load(new OnMultiLoadListener<List<IMGroupUserInfo.GroupMebInfo>>() {
            @Override
            public void onSuccess(List<IMGroupUserInfo.GroupMebInfo> success, int totalPage) {
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
        } , groupId , page);
    }
}

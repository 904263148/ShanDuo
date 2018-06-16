package com.yapin.shanduo.presenter;

import com.yapin.shanduo.model.EditGroupNameLoadModel;
import com.yapin.shanduo.model.impl.EditGroupNameModelImpl;
import com.yapin.shanduo.ui.contract.EditGroupNameContact;

/**
 * 作者：L on 2018/6/16 0016 16:37
 */
public class EditGroupPresenter implements EditGroupNameContact.Presenter{

    private EditGroupNameContact.View view;
    private EditGroupNameLoadModel loadModel;

    public void init(EditGroupNameContact.View view){
        this.view = view;
        loadModel = new EditGroupNameModelImpl();
    }

    @Override
    public void editName(String name, String groupId) {
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
        } , name , groupId);
    }
}

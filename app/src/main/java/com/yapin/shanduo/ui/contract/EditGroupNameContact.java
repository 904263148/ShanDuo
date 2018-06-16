package com.yapin.shanduo.ui.contract;

import com.yapin.shanduo.base.BaseView;

/**
 * 作者：L on 2018/6/16 0016 16:35
 */
public interface EditGroupNameContact {

    interface View extends BaseView {
        void show(String data);

        void loading();

        void networkError();

        void error(String msg);

        void showFailed(String msg);
    }

    interface Presenter{
        void editName(String name , String groupId);
    }
}

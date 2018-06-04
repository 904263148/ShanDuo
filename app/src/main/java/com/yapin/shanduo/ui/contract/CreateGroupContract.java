package com.yapin.shanduo.ui.contract;

import com.yapin.shanduo.base.BaseView;

/**
 * 作者：L on 2018/6/1 0001 19:32
 */
public interface CreateGroupContract {

    interface View extends BaseView {
        void success(String data);

        void loading();

        void networkError();

        void error(String msg);

        void showFailed(String msg);

    }

    interface Presenter{
        void createGroup(String typeId, String groupId, String groupType);
    }
}

package com.yapin.shanduo.ui.contract;

import com.yapin.shanduo.base.BaseView;

/**
 * 作者：L on 2018/6/6 0006 15:57
 */
public interface DeleteFriendContract {

    interface View extends BaseView {

        void deleteSuccess(String data);

        void loading();

        void networkError();

        void error(String msg);

        void showFailed(String msg);

    }

    interface Presenter{
        void delete(String attention , String typeId);
    }
}

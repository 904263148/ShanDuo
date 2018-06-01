package com.yapin.shanduo.ui.contract;

import com.yapin.shanduo.base.BaseView;

/**
 * 作者：L on 2018/5/29 0029 16:35
 */
public interface AddFriendContract {

    interface View extends BaseView {
        void show(String data);

        void loading();

        void networkError();

        void error(String msg);

        void showFailed(String msg);
    }

    interface Presenter{
        void addFriend(String attention);
    }

}

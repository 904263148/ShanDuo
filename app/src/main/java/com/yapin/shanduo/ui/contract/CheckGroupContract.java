package com.yapin.shanduo.ui.contract;

import com.yapin.shanduo.base.BaseView;

/**
 * 作者：L on 2018/6/1 0001 17:47
 */
public interface CheckGroupContract {

    interface View extends BaseView {
        void show(String data);

        void loading();

        void networkError();

        void error(String msg);

        void showFailed(String msg);

    }

    interface Presenter{
        void checkGroup(String groupType);
    }

}

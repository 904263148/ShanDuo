package com.yapin.shanduo.ui.contract;

import com.yapin.shanduo.base.BasePresenter;
import com.yapin.shanduo.base.BaseView;

/**
 * 作者：L on 2018/6/14 0014 17:50
 */
public interface UserDetailContract {

    interface View extends BaseView {

        void dataSuccess(String data);

        void loading();

        void networkError();

        void error(String msg);

        void showFailed(String msg);
    }

    interface Presenter extends BasePresenter {
    }
}

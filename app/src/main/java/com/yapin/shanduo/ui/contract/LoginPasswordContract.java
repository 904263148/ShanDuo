package com.yapin.shanduo.ui.contract;

import com.yapin.shanduo.base.BasePresenter;
import com.yapin.shanduo.base.BaseView;

/**
 * Created by dell on 2018/5/31.
 */

public interface LoginPasswordContract {

    interface View extends BaseView {

        void success(String data);

        void loading();

        void networkError();

        void error(String msg);

        void showFailed(String msg);
    }

    interface Presenter extends BasePresenter {
        void loginpassword(String password , String newPassword);
    }

}

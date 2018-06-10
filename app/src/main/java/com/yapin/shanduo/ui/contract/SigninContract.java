package com.yapin.shanduo.ui.contract;

import com.yapin.shanduo.base.BasePresenter;
import com.yapin.shanduo.base.BaseView;
import com.yapin.shanduo.model.entity.SigninInfo;

/**
 * Created by dell on 2018/6/8.
 */

public interface SigninContract {

    interface View extends BaseView {

        void success(SigninInfo data);

        void loading();

        void networkError();

        void error(String msg);

        void showFailed(String msg);
    }

    interface Presenter extends BasePresenter {
        void getsignin( );
    }
}

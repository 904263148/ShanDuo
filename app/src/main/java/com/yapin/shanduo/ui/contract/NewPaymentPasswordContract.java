package com.yapin.shanduo.ui.contract;

import com.yapin.shanduo.base.BasePresenter;
import com.yapin.shanduo.base.BaseView;

/**
 * Created by dell on 2018/6/1.
 */

public interface NewPaymentPasswordContract {

    interface View extends BaseView {

        void success(String data);

        void loading();

        void networkError();

        void error(String msg);

        void showFailed(String msg);
    }

    interface Presenter extends BasePresenter {
        void newpaymentpassword(String typeId , String code ,String password ,String newPassword );
    }
}

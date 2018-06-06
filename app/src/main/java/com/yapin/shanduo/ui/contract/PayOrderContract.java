package com.yapin.shanduo.ui.contract;

import com.yapin.shanduo.base.BasePresenter;
import com.yapin.shanduo.base.BaseView;
import com.yapin.shanduo.model.entity.PayOrder;

/**
 * 作者：L on 2018/6/5 0005 10:15
 */
public interface PayOrderContract {

    interface View extends BaseView {

        void success(PayOrder data);

        void loading();

        void networkError();

        void error(String msg);

        void showFailed(String msg);
    }

    interface Presenter{
        void payOrder( String payId, String password, String typeId, String money, String month, String activityId);
    }
}

package com.yapin.shanduo.ui.contract;

import com.yapin.shanduo.base.BaseView;
import com.yapin.shanduo.model.entity.CustomerServiceInfo;

import java.util.List;

/**
 * 作者：L on 2018/7/24 0024 11:09
 */
public interface CustomerServiceContract {

    interface View extends BaseView {
        void show(List<CustomerServiceInfo.Customer> data);

        void loading();

        void networkError();

        void error(String msg);

        void showFailed(String msg);
    }

    interface Presenter{
        void load();
    }

}

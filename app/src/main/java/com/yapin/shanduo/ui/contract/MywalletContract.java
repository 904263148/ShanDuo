package com.yapin.shanduo.ui.contract;

import com.yapin.shanduo.base.BasePresenter;
import com.yapin.shanduo.base.BaseView;
import com.yapin.shanduo.model.entity.FlickerPurseInfo;
import com.yapin.shanduo.model.entity.TransactionrecordInfo;

import java.util.List;

/**
 * Created by dell on 2018/6/4.
 */

public interface MywalletContract {
    interface View extends BaseView {

        void success(FlickerPurseInfo data);

        void loading();

        void networkError();

        void error(String msg);

        void showFailed(String msg);
    }

    interface Presenter {
        void mywallet();
    }
}

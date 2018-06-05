package com.yapin.shanduo.ui.contract;

import com.yapin.shanduo.base.BaseView;
import com.yapin.shanduo.model.entity.FlickerPurseInfo;
import com.yapin.shanduo.model.entity.NumberInfo;

/**
 * Created by dell on 2018/6/5.
 */

public interface NumberContract {

    interface View extends BaseView {

        void success(NumberInfo data);

        void loading();

        void networkError();

        void error(String msg);

        void showFailed(String msg);
    }

    interface Presenter {
        void getnumber();
    }

}

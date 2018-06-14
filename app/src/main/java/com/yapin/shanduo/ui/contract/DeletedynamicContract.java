package com.yapin.shanduo.ui.contract;

import com.yapin.shanduo.base.BasePresenter;
import com.yapin.shanduo.base.BaseView;

/**
 * Created by dell on 2018/6/14.
 */

public interface DeletedynamicContract {
    interface View extends BaseView {

        void success(String data);

        void loading();

        void networkError();

        void error(String msg);

        void showFailed(String msg);
    }

    interface Presenter extends BasePresenter {
        void Deletedynamic(String dynamicIds );
    }
}

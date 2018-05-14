package com.yapin.shanduo.ui.contract;

import com.yapin.shanduo.base.BasePresenter;
import com.yapin.shanduo.base.BaseView;

/**
 * Created by dell on 2018/5/5.
 */

public interface AddactivityContract {

    interface View extends BaseView {

        void success(String data);

        void loading();

        void networkError();

        void error(String msg);

        void showFailed(String msg);
    }

    interface Presenter extends BasePresenter {
        void addactivity(String activityType, String activityStartTime, String activityAddress, String mode, String manNumber, String womanNumber, String remarks, String activityCutoffTime, String lon, String lat,String detailedAddress);
    }
}

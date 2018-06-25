package com.yapin.shanduo.ui.contract;

import com.yapin.shanduo.base.BaseView;
import com.yapin.shanduo.model.entity.ActivityInfo;

import java.util.List;

/**
 * 作者：L on 2018/6/22 0022 16:45
 */
public interface ActivityDetailContract {

    interface View extends BaseView {

        void show(ActivityInfo.Act data);

        void loading();

        void networkError();

        void error(String msg);

        void showFailed(String msg);

    }

    interface Presenter{
        void getData(String activityId);
    }
}

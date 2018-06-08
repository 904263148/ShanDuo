package com.yapin.shanduo.ui.contract;

import com.yapin.shanduo.base.BaseView;
import com.yapin.shanduo.model.entity.ActivityInfo;

import java.util.List;

/**
 * 作者：L on 2018/6/7 0007 18:15
 */
public interface ReportContract {

    interface View extends BaseView {

        void show(String success);

        void loading();

        void networkError();

        void error(String msg);

        void showFailed(String msg);

    }

    interface Presenter{
        void getData(String userId , String activityId , String dynamicId , String typeId , String remarks);
    }
}

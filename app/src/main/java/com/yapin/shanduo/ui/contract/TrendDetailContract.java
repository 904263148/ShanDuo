package com.yapin.shanduo.ui.contract;

import com.yapin.shanduo.base.BaseView;
import com.yapin.shanduo.model.entity.TrendInfo;

import java.util.List;

/**
 * 作者：L on 2018/7/2 0002 10:48
 */
public interface TrendDetailContract {

    interface View extends BaseView {

        void show(TrendInfo.Trend data);

        void loading();

        void networkError();

        void error(String msg);

        void showFailed(String msg);

    }

    interface Presenter{
        void getData(String dynamicId);
    }

}

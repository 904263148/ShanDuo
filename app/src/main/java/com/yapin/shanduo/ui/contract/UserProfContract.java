package com.yapin.shanduo.ui.contract;

import com.yapin.shanduo.base.BaseView;
import com.yapin.shanduo.model.entity.ShanDuoUserProf;

/**
 * 作者：L on 2018/5/25 0025 16:53
 */
public interface UserProfContract {

    interface View extends BaseView {

        void show(ShanDuoUserProf data);

        void loading();

        void networkError();

        void error(String msg);

        void showFailed(String msg);

    }

    interface Presenter{
        void getData(String userId);
    }

}

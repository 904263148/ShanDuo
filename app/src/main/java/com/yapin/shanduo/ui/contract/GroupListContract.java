package com.yapin.shanduo.ui.contract;

import com.yapin.shanduo.base.BaseView;
import com.yapin.shanduo.model.entity.IMGroupInfo;

import java.util.List;

/**
 * 作者：L on 2018/6/16 0016 11:55
 */
public interface GroupListContract {

    interface View extends BaseView {

        void show(List<IMGroupInfo.GroupInfo> data);

        void loading();

        void networkError();

        void error(String msg);

        void showFailed(String msg);

    }

    interface Presenter{
        void getData();
    }
}

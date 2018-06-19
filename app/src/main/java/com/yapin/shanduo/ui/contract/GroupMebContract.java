package com.yapin.shanduo.ui.contract;

import com.yapin.shanduo.base.BaseView;
import com.yapin.shanduo.model.entity.IMGroupUserInfo;

import java.util.List;

/**
 * 作者：L on 2018/6/19 0019 10:29
 */
public interface GroupMebContract {

    interface View extends BaseView {

        void show(List<IMGroupUserInfo.GroupMebInfo> data , int totalPage);

        void loading();

        void networkError();

        void error(String msg);

        void showFailed(String msg);

    }

    interface Presenter{
        void getData(String groupId , String page);
    }

}

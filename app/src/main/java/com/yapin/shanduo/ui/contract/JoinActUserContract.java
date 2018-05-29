package com.yapin.shanduo.ui.contract;

import com.yapin.shanduo.base.BaseView;
import com.yapin.shanduo.model.entity.JoinActUser;

import java.util.List;

/**
 * 作者：L on 2018/5/26 0026 14:45
 */
public interface JoinActUserContract {

    interface View extends BaseView {

        void show(List<JoinActUser.ActUser> data , int totalPage);

        void loading();

        void networkError();

        void error(String msg);

        void showFailed(String msg);

    }

    interface Presenter{
        void getData(String activityId , String page , String pageSize);
    }

}

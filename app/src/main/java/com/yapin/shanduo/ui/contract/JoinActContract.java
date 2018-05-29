package com.yapin.shanduo.ui.contract;

import com.yapin.shanduo.base.BaseView;

/**
 * 作者：L on 2018/5/11 0011 10:11
 */
public interface JoinActContract {

    interface View extends BaseView{
        void success(String data);

        void loading();

        void networkError();

        void joinError(String msg);

        void showFailed(String msg);
    }

    interface Presenter{
        void join(String activityId , String type);
    }

}

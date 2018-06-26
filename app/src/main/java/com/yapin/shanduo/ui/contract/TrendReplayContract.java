package com.yapin.shanduo.ui.contract;

import com.yapin.shanduo.base.BaseView;

/**
 * 作者：L on 2018/5/18 0018 15:15
 */
public interface TrendReplayContract {

    interface View extends BaseView {

        void show(String data);

        void loading();

        void networkError();

        void error(String msg);

        void showFailed(String msg);

    }

    interface Presenter{
        void getData(String dynamicId, String comment, String typeId, String ommentId, String replyCommentId);
    }
}

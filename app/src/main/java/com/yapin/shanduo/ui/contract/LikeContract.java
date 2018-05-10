package com.yapin.shanduo.ui.contract;

import com.yapin.shanduo.base.BaseView;

/**
 * 作者：L on 2018/5/10 0010 17:55
 */
public interface LikeContract {

    interface View extends BaseView{
        void success(String data);

        void loading();

        void networkError();

        void error(String msg);

        void showFailed(String msg);
    }

    interface Presenter{
        void onLike(String dynamicId);
    }
}

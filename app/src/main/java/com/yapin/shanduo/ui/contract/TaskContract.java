package com.yapin.shanduo.ui.contract;

import com.yapin.shanduo.base.BasePresenter;
import com.yapin.shanduo.base.BaseView;
import com.yapin.shanduo.model.entity.SigninInfo;
import com.yapin.shanduo.model.entity.TaskInfo;

/**
 * Created by dell on 2018/6/21.
 */

public interface TaskContract {
    interface View extends BaseView {

        void success(TaskInfo data);

        void loading();

        void networkError();

        void error(String msg);

        void showFailed(String msg);
    }

    interface Presenter extends BasePresenter {
        void gettask();
    }
}

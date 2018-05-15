package com.yapin.shanduo.ui.contract;

import com.yapin.shanduo.base.BaseView;

import java.util.List;

public interface UploadContract {

    interface View extends BaseView{
        void uploadSuccess(String imgIds);

        void loading();

        void networkError();

        void error(String msg);

        void showFailed(String msg);
    }

    interface Presenter{
        void upload(List<String> paths);
    }

}

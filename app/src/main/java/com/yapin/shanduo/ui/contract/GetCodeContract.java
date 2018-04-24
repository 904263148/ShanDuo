package com.yapin.shanduo.ui.contract;

import com.yapin.shanduo.base.BaseView;
import com.yapin.shanduo.presenter.OnLoadListener;

public interface GetCodeContract {

    interface View extends BaseView{
        void show(String data);

        void loading(int type);

        void networkError(int type);

        void error(int type,String msg);

        void showFailed(int type,String msg);

    }

    interface Presenter{
        void getCode(String phone , String typeId);
    }
}

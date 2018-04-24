package com.yapin.shanduo.ui.contract;

import com.yapin.shanduo.base.BaseView;

public interface RegisterContract {

    interface View extends BaseView{
        void success(String data);

        void loading(int type);

        void networkError(int type);

        void error(int type,String msg);

        void showFailed(int type,String msg);
    }

    interface Presenter {
        void register(  String phone , String code ,String password );
    }

}

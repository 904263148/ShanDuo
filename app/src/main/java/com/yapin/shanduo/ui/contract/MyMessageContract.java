package com.yapin.shanduo.ui.contract;

import com.yapin.shanduo.base.BaseView;
import com.yapin.shanduo.model.entity.MyMessageInfo;

import java.util.List;

/**
 * 作者：L on 2018/6/27 0027 17:47
 */
public interface MyMessageContract {

    interface View extends BaseView {

        void show(List<MyMessageInfo.Message> data , int totalPage);

        void loading();

        void networkError();

        void error(String msg);

        void showFailed(String msg);

    }

    interface Presenter{
        void getData( String page, String pageSize);
    }

}

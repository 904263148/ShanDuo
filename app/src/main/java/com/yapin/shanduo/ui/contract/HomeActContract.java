package com.yapin.shanduo.ui.contract;

import com.yapin.shanduo.base.BaseView;
import com.yapin.shanduo.model.entity.ActivityInfo;

import java.util.List;

/**
 * 作者：L on 2018/5/8 0008 09:32
 */
public interface HomeActContract {

    interface View extends BaseView{

        void show(List<ActivityInfo.Act> data , int totalPage);

        void loading();

        void networkError();

        void error(String msg);

        void showFailed(String msg);

    }

    interface Presenter{
        void getData(String type, String lon, String lat, String page, String pageSize);
    }

}

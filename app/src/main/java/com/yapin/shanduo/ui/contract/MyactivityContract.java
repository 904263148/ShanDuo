package com.yapin.shanduo.ui.contract;

import com.yapin.shanduo.base.BaseView;
import com.yapin.shanduo.model.entity.ActivityInfo;
import com.yapin.shanduo.model.entity.TrendInfo;

import java.util.List;

/**
 * Created by dell on 2018/5/19.
 */

public interface MyactivityContract {

    interface View extends BaseView {

        void show(List<ActivityInfo.Act> data , int totalPage);

        void loading();

        void networkError();

        void error(String msg);

        void showFailed(String msg);
    }
    interface Presenter {
        void getmyactivity(String type , String lon , String lat, String page, String pageSize);
    }

}

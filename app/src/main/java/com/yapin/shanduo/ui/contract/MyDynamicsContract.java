package com.yapin.shanduo.ui.contract;

import com.yapin.shanduo.base.BasePresenter;
import com.yapin.shanduo.base.BaseView;
import com.yapin.shanduo.model.entity.TrendInfo;

import java.util.List;

/**
 * Created by dell on 2018/5/15.
 */

public interface MyDynamicsContract {

    interface View extends BaseView {

        void show(List<TrendInfo.Trend> data , int totalPage);

        void loading();

        void networkError();

        void error(String msg);

        void showFailed(String msg);
    }
    interface Presenter {
        void getdynamics( String lon , String lat, String page, String pageSize);
    }
}

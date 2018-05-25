package com.yapin.shanduo.ui.contract;

import com.yapin.shanduo.base.BaseView;
import com.yapin.shanduo.model.entity.ActivityInfo;
import com.yapin.shanduo.model.entity.TrendInfo;

import java.util.List;

/**
 * 作者：L on 2018/5/10 0010 09:00
 */
public interface HomeTrendContract {

    interface View extends BaseView{

        void show(List<TrendInfo.Trend> data , int totalPage);

        void loading();

        void networkError();

        void error(String msg);

        void showFailed(String msg);

    }

    interface Presenter{
        void getData(String typeId, String lon, String lat, String page, String pageSize , String userId);
    }

}

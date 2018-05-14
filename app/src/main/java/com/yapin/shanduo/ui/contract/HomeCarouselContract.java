package com.yapin.shanduo.ui.contract;

import com.yapin.shanduo.base.BaseView;
import com.yapin.shanduo.model.entity.ActivityInfo;

import java.util.List;

/**
 * 作者：L on 2018/5/12 0012 17:45
 */
public interface HomeCarouselContract {

    interface View extends BaseView{
        void showCarousel(List<String> data);

        void loading();

        void networkError();

        void error(String msg);

        void showFailed(String msg);
    }

    interface Presenter {
        void load();
    }

}

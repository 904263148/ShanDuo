package com.yapin.shanduo.ui.contract;

import com.yapin.shanduo.base.BaseView;
import com.yapin.shanduo.model.entity.TokenInfo;

import java.util.List;

/**
 * 作者：L on 2018/5/24 0024 16:54
 */
public interface SearchHumanContract {

    interface View extends BaseView{

        void show(List<TokenInfo> data);

        void loading();

        void networkError();

        void error(String msg);

        void showFailed(String msg);

    }

    interface Presenter{
        void getData(String query, String typeId);
    }

}

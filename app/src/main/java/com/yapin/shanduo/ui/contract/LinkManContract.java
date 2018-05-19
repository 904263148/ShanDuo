package com.yapin.shanduo.ui.contract;

import com.yapin.shanduo.base.BasePresenter;
import com.yapin.shanduo.base.BaseView;
import com.yapin.shanduo.model.entity.FriendInfo;

import java.util.List;

/**
 * 作者：L on 2018/5/19 0019 15:26
 */
public interface LinkManContract {

    interface View extends BaseView{

        void success(List<FriendInfo.fInfo> data);

        void loading();

        void networkError();

        void error(String msg);

        void showFailed(String msg);
    }

    interface Presenter {
        void getData(String typeId);
    }
}

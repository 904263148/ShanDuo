package com.yapin.shanduo.ui.contract;

import com.yapin.shanduo.base.BaseView;
import com.yapin.shanduo.model.entity.FlickerPurseInfo;
import com.yapin.shanduo.model.entity.GetVipLevelInfo;

/**
 * Created by dell on 2018/6/25.
 */

public interface GetVipLevelContract {
    interface View extends BaseView {

        void success(GetVipLevelInfo data);

        void loading();

        void networkError();

        void error(String msg);

        void showFailed(String msg);
    }

    interface Presenter {
        void getvipLevel();
    }
}

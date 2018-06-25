package com.yapin.shanduo.presenter;

import android.content.Context;

import com.yapin.shanduo.model.GetVipLevelModel;
import com.yapin.shanduo.model.entity.FlickerPurseInfo;
import com.yapin.shanduo.model.entity.GetVipLevelInfo;
import com.yapin.shanduo.model.impl.GetVipLevelModelImpl;
import com.yapin.shanduo.ui.contract.GetVipLevelContract;

/**
 * Created by dell on 2018/6/25.
 */

public class GetVipLevelPresenter implements GetVipLevelContract.Presenter {

    private  GetVipLevelContract.View view;
    private GetVipLevelModel getVipLevelModel;

    public void init(Context context, GetVipLevelContract.View view) {
        this.view = view;
        view.initView();
        getVipLevelModel = new GetVipLevelModelImpl();
    }

    @Override
    public void getvipLevel() {
        getVipLevelModel.load(new OnLoadListener <GetVipLevelInfo>() {
            @Override
            public void onSuccess(GetVipLevelInfo success) {
                view.success(success);
            }

            @Override
            public void onError(String msg) {
                view.error(msg);
            }

            @Override
            public void networkError() {
                view.networkError();
            }
        });
    }
}

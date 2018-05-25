package com.yapin.shanduo.presenter;

import android.content.Context;

import com.yapin.shanduo.model.HomeActLoadModel;
import com.yapin.shanduo.model.entity.ActivityInfo;
import com.yapin.shanduo.model.impl.HomeActModelImpl;
import com.yapin.shanduo.ui.contract.HomeActContract;

import java.util.List;

/**
 * 作者：L on 2018/5/8 0008 09:50
 */
public class HomeActPresenter implements HomeActContract.Presenter{

    private HomeActContract.View view;
    private HomeActLoadModel loadModel;

    public void init(HomeActContract.View view){
        this.view = view;
        loadModel = new HomeActModelImpl();
        view.initView();
    }

    @Override
    public void getData(String type, String lon, String lat, String page, String pageSize , String userId) {
        view.loading();
        loadModel.load(new OnMultiLoadListener<List<ActivityInfo.Act>>() {
            @Override
            public void onSuccess(List<ActivityInfo.Act> success, int totalPage) {
                view.show(success , totalPage);
            }

            @Override
            public void onError(String msg) {
                view.error(msg);
            }

            @Override
            public void networkError() {
                view.networkError();
            }
        }, type, lon, lat, page, pageSize , userId);
    }
}

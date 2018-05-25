package com.yapin.shanduo.presenter;

import android.content.Context;

import com.yapin.shanduo.model.HomeTrendLoadModel;
import com.yapin.shanduo.model.entity.TrendInfo;
import com.yapin.shanduo.model.impl.HomeTrendModelImpl;
import com.yapin.shanduo.ui.contract.HomeTrendContract;

import java.util.List;

/**
 * 作者：L on 2018/5/10 0010 09:12
 */
public class HomeTrendPresenter implements HomeTrendContract.Presenter{

    private HomeTrendContract.View view;
    private HomeTrendLoadModel loadModel;

    public void init(HomeTrendContract.View view){
        this.view = view;
        loadModel = new HomeTrendModelImpl();
        view.initView();
    }


    @Override
    public void getData(String typeId, String lon, String lat, String page, String pageSize , String userId) {
        loadModel.load(new OnMultiLoadListener<List<TrendInfo.Trend>>() {
            @Override
            public void onSuccess(List<TrendInfo.Trend> success, int totalPage) {
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
        } , typeId , lon , lat , page , pageSize , userId);
    }
}

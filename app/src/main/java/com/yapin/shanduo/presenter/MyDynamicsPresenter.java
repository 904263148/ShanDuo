package com.yapin.shanduo.presenter;

import com.yapin.shanduo.model.MyDynamicsModel;
import com.yapin.shanduo.model.entity.TrendInfo;
import com.yapin.shanduo.model.impl.MyDynamicsModelIml;
import com.yapin.shanduo.ui.contract.MyDynamicsContract;

import java.util.List;

/**
 * Created by dell on 2018/5/15.
 */

public class MyDynamicsPresenter implements MyDynamicsContract.Presenter {

    private MyDynamicsContract.View view;
    private MyDynamicsModel myDynamicsModel;

    public void init(MyDynamicsContract.View view){
        this.view = view;
        myDynamicsModel = new MyDynamicsModelIml();
        view.initView();

    }
    @Override
    public void getdynamics(String lon , String lat, String page, String pageSize) {
        myDynamicsModel.load(new OnMultiLoadListener<List<TrendInfo.Trend>>() {
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
        },lat , lon ,  page , pageSize);
    }
}

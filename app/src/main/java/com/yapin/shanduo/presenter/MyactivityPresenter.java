package com.yapin.shanduo.presenter;

import com.yapin.shanduo.model.MyactivityModel;
import com.yapin.shanduo.model.entity.ActivityInfo;
import com.yapin.shanduo.model.impl.MyactivityModelImpl;
import com.yapin.shanduo.ui.contract.MyactivityContract;

import java.util.List;

/**
 * Created by dell on 2018/5/19.
 */

public class MyactivityPresenter implements MyactivityContract.Presenter {

    private MyactivityContract.View view;
    private MyactivityModel myactivityModel;

    public void init(MyactivityContract.View view){
        this.view = view;
        myactivityModel = new MyactivityModelImpl();
        view.initView();

    }

    @Override
    public void getmyactivity(String type ,String lon, String lat, String page, String pageSize) {

        myactivityModel.load(new OnMultiLoadListener<List<ActivityInfo.Act>>() {
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
        },type ,lat , lon ,  page , pageSize);


    }
}

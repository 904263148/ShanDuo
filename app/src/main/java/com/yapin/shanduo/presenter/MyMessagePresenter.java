package com.yapin.shanduo.presenter;

import com.yapin.shanduo.model.MyMessageLoadModel;
import com.yapin.shanduo.model.entity.MyMessageInfo;
import com.yapin.shanduo.model.impl.MyMessageModelImpl;
import com.yapin.shanduo.ui.contract.MyMessageContract;

import java.util.List;

/**
 * 作者：L on 2018/6/27 0027 17:48
 */
public class MyMessagePresenter implements MyMessageContract.Presenter{

    private MyMessageContract.View view;
    private MyMessageLoadModel loadModel;

    public void init(MyMessageContract.View view){
        this.view = view;
        loadModel = new MyMessageModelImpl();
        view.initView();
    }

    @Override
    public void getData(String page, String pageSize) {
        loadModel.load(new OnMultiLoadListener<List<MyMessageInfo.Message>>() {
            @Override
            public void onSuccess(List<MyMessageInfo.Message> success, int totalPage) {
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
        } , page , pageSize);
    }
}

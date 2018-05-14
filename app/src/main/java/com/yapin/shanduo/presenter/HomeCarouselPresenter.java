package com.yapin.shanduo.presenter;

import com.yapin.shanduo.model.HomeCarouselLoadModel;
import com.yapin.shanduo.model.impl.HomeCarouselModelImpl;
import com.yapin.shanduo.ui.contract.HomeCarouselContract;

import java.util.List;

/**
 * 作者：L on 2018/5/12 0012 17:50
 */
public class HomeCarouselPresenter implements HomeCarouselContract.Presenter{

    private HomeCarouselContract.View view;
    private HomeCarouselLoadModel loadModel;

    public void init(HomeCarouselContract.View view){
        this.view = view;
        loadModel = new HomeCarouselModelImpl();
        view.initView();
    }

    @Override
    public void load() {
        loadModel.load(new OnLoadListener<List<String>>() {
            @Override
            public void onSuccess(List<String> success) {
                view.showCarousel(success);
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

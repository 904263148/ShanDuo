package com.yapin.shanduo.presenter;

import com.yapin.shanduo.model.TrendDetailLoadModel;
import com.yapin.shanduo.model.entity.TrendInfo;
import com.yapin.shanduo.model.impl.TrendDetailModelImpl;
import com.yapin.shanduo.ui.contract.TrendDetailContract;

/**
 * 作者：L on 2018/7/2 0002 10:55
 */
public class TrendDetailPresenter implements TrendDetailContract.Presenter{

    private TrendDetailContract.View view;
    private TrendDetailLoadModel loadModel;

    public void init(TrendDetailContract.View view){
        this.view = view;
        loadModel = new TrendDetailModelImpl();
    }

    @Override
    public void getData(String dynamicId) {
        loadModel.load(new OnLoadListener<TrendInfo.Trend>() {
            @Override
            public void onSuccess(TrendInfo.Trend success) {
                view.show(success);
            }

            @Override
            public void onError(String msg) {
                view.error(msg);
            }

            @Override
            public void networkError() {
                view.networkError();
            }
        } , dynamicId);
    }
}

package com.yapin.shanduo.presenter;

import com.yapin.shanduo.model.PayOrderLoadModel;
import com.yapin.shanduo.model.entity.PayOrder;
import com.yapin.shanduo.model.impl.PayOrderModelImpl;
import com.yapin.shanduo.ui.contract.PayOrderContract;

/**
 * 作者：L on 2018/6/5 0005 10:16
 */
public class PayOrderPresenter implements PayOrderContract.Presenter{

    private PayOrderContract.View view;
    private PayOrderLoadModel loadModel;

    public void init(PayOrderContract.View view){
        this.view = view;
        loadModel = new PayOrderModelImpl();
    }

    @Override
    public void payOrder(String payId, String password, String typeId, String money, String month, String activityId) {
        loadModel.load(new OnLoadListener<PayOrder>() {
            @Override
            public void onSuccess(PayOrder success) {
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
        } , payId , password , typeId , money , month , activityId);
    }
}

package com.yapin.shanduo.presenter;

import com.yapin.shanduo.model.CreditLoadModel;
import com.yapin.shanduo.model.entity.CreditItem;
import com.yapin.shanduo.model.impl.CreditModelImpl;
import com.yapin.shanduo.ui.contract.CreditDetailContract;

import java.util.List;

/**
 * 作者：L on 2018/6/1 0001 11:37
 */
public class CreditDetailPresenter implements CreditDetailContract.Presenter{

    private CreditDetailContract.View view;
    private CreditLoadModel loadModel;

    public void init(CreditDetailContract.View view){
        this.view = view;
        loadModel = new CreditModelImpl();
        view.initView();
    }

    @Override
    public void getData(String userId, String page, String pageSize, String type) {
        loadModel.load(new OnMultiLoadListener<List<CreditItem>>() {
            @Override
            public void onSuccess(List<CreditItem> success, int totalPage) {
                view.show(success ,totalPage);
            }

            @Override
            public void onError(String msg) {
                view.error(msg);
            }

            @Override
            public void networkError() {
                view.networkError();
            }
        } , userId , page , pageSize ,type);
    }
}

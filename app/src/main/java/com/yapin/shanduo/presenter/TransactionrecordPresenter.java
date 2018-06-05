package com.yapin.shanduo.presenter;

import com.yapin.shanduo.model.TransactionrecordModel;
import com.yapin.shanduo.model.entity.TransactionrecordInfo;
import com.yapin.shanduo.model.impl.TransactionrecordModelImpl;
import com.yapin.shanduo.ui.contract.TransactionrecordContract;

import java.util.List;

/**
 * Created by dell on 2018/6/4.
 */

public class TransactionrecordPresenter implements TransactionrecordContract.Presenter {

    private TransactionrecordContract.View view;
    private TransactionrecordModel transactionrecordModel;

    public void init(TransactionrecordContract.View view){
        this.view = view;
        transactionrecordModel = new TransactionrecordModelImpl();
        view.initView();

    }

    @Override
    public void gettrans(String page, String pageSize) {
        transactionrecordModel.load(new OnMultiLoadListener<List<TransactionrecordInfo.Trend>>() {
            @Override
            public void onSuccess(List<TransactionrecordInfo.Trend> success, int totalPage) {
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
        }, page , pageSize);
    }
}

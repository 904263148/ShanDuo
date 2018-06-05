package com.yapin.shanduo.presenter;

import android.content.Context;

import com.yapin.shanduo.model.MywalletModel;
import com.yapin.shanduo.model.entity.FlickerPurseInfo;
import com.yapin.shanduo.model.entity.TransactionrecordInfo;
import com.yapin.shanduo.model.impl.MywalletModelImpl;
import com.yapin.shanduo.ui.contract.MywalletContract;

import java.util.List;

/**
 * Created by dell on 2018/6/4.
 */

public class MywalletPresenter implements MywalletContract.Presenter {

    private  MywalletContract.View view;
    private MywalletModel mywalletModel;

    public void init(Context context, MywalletContract.View view) {
        this.view = view;
        view.initView();
        mywalletModel = new MywalletModelImpl();
    }


    @Override
    public void mywallet() {
        mywalletModel.load(new OnLoadListener <FlickerPurseInfo>() {
            @Override
            public void onSuccess(FlickerPurseInfo success) {
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
        });
    }
}

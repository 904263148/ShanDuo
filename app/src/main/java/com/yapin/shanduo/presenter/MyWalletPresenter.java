package com.yapin.shanduo.presenter;

import android.content.Context;

import com.yapin.shanduo.model.MyWalletModel;
import com.yapin.shanduo.model.entity.FlickerPurseInfo;
import com.yapin.shanduo.model.impl.MyWalletModelImpl;
import com.yapin.shanduo.ui.contract.MywalletContract;

/**
 * Created by dell on 2018/6/4.
 */

public class MyWalletPresenter implements MywalletContract.Presenter {

    private  MywalletContract.View view;
    private MyWalletModel myWalletModel;

    public void init(Context context, MywalletContract.View view) {
        this.view = view;
        view.initView();
        myWalletModel = new MyWalletModelImpl();
    }


    @Override
    public void mywallet() {
        myWalletModel.load(new OnLoadListener <FlickerPurseInfo>() {
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

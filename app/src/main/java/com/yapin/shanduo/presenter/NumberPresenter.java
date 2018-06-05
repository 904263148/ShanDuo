package com.yapin.shanduo.presenter;

import android.content.Context;

import com.yapin.shanduo.model.NumberModel;
import com.yapin.shanduo.model.entity.NumberInfo;
import com.yapin.shanduo.model.impl.NumberModelImpl;
import com.yapin.shanduo.ui.contract.NumberContract;

/**
 * Created by dell on 2018/6/5.
 */

public class NumberPresenter implements NumberContract.Presenter {

    private  NumberContract.View view;
    private NumberModel numberModel;

    public void init(Context context, NumberContract.View view) {
        this.view = view;
        view.initView();
        numberModel = new NumberModelImpl();
    }

    @Override
    public void getnumber() {
        numberModel.load(new OnLoadListener<NumberInfo>() {
            @Override
            public void onSuccess(NumberInfo success) {
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

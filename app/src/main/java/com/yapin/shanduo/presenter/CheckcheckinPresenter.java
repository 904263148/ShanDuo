package com.yapin.shanduo.presenter;

import android.content.Context;

import com.yapin.shanduo.model.CheckcheckinModel;
import com.yapin.shanduo.model.impl.CheckcheckinModelImpl;
import com.yapin.shanduo.ui.contract.CheckcheckinContract;

/**
 * Created by dell on 2018/6/8.
 */

public class CheckcheckinPresenter implements CheckcheckinContract.Presenter {

    private  CheckcheckinContract.View view;
    private CheckcheckinModel checkcheckinModel;
    private Context context;

    public void init(Context context, CheckcheckinContract.View view) {
        this.view = view;
        view.initView();
        checkcheckinModel = new CheckcheckinModelImpl();
    }

    @Override
    public void start() {

    }

    @Override
    public void setcheck() {
        checkcheckinModel.load(new OnLoadListener<String>() {
            @Override
            public void onSuccess(String success) {
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

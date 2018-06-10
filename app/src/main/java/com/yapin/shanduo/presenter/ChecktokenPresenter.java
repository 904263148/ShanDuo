package com.yapin.shanduo.presenter;

import android.content.Context;

import com.yapin.shanduo.model.ChecktokenModel;
import com.yapin.shanduo.model.impl.ChecktokenModelImpl;
import com.yapin.shanduo.ui.contract.ChecktokenContract;

/**
 * Created by dell on 2018/6/9.
 */

public class ChecktokenPresenter implements ChecktokenContract.Presenter {

    private  ChecktokenContract.View view;
    private ChecktokenModel checktokenModel;
    private Context context;

    public void init(Context context, ChecktokenContract.View view) {

        this.context = context;
        this.view = view;
        this.view.initView();
        checktokenModel = new ChecktokenModelImpl();

    }

    @Override
    public void start() {

    }

    @Override
    public void getchecktoken() {
        checktokenModel.load(new OnLoadListener<String>() {
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

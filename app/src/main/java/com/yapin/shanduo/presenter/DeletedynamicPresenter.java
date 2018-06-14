package com.yapin.shanduo.presenter;

import android.content.Context;

import com.yapin.shanduo.model.DeletedynamicModel;
import com.yapin.shanduo.model.impl.DeletedynamicModelImpl;
import com.yapin.shanduo.ui.contract.DeletedynamicContract;

/**
 * Created by dell on 2018/6/14.
 */

public class DeletedynamicPresenter implements DeletedynamicContract.Presenter {

    private  DeletedynamicContract.View view;
    private DeletedynamicModel deletedynamicModel;
    private Context context;

    public void init(Context context, DeletedynamicContract.View view) {

        this.context = context;
        this.view = view;
        this.view.initView();
        deletedynamicModel = new DeletedynamicModelImpl();

    }

    @Override
    public void start() {

    }

    @Override
    public void Deletedynamic(String dynamicIds) {
        deletedynamicModel.load(new OnLoadListener<String>() {
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
        },dynamicIds );
    }
}

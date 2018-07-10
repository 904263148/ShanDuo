package com.yapin.shanduo.presenter;

import android.content.Context;

import com.yapin.shanduo.model.UploadLoadModel;
import com.yapin.shanduo.model.impl.UploadModelImpl;
import com.yapin.shanduo.ui.contract.UploadContract;

import java.util.List;

public class UploadPresenter implements UploadContract.Presenter{

    private Context context;
    private UploadLoadModel loadModel;
    private UploadContract.View view;

    public void init(Context context , UploadContract.View view){
        this.context = context ;
        this.view = view ;
        loadModel = new UploadModelImpl();
    }

    @Override
    public void upload(List<String> paths) {
        view.loading();
        loadModel.load(new OnLoadListener<String>() {
            @Override
            public void onSuccess(String data) {
                view.uploadSuccess(data);
            }

            @Override
            public void onError(String msg) {
                view.error(msg);
            }

            @Override
            public void networkError() {
                view.networkError();
            }
        } , paths);
    }
}

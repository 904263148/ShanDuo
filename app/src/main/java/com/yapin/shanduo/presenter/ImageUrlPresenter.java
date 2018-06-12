package com.yapin.shanduo.presenter;

import android.content.Context;

import com.yapin.shanduo.model.UploadLoadModel;
import com.yapin.shanduo.model.impl.ImageUrlModelImpl;
import com.yapin.shanduo.model.impl.UploadModelImpl;
import com.yapin.shanduo.ui.contract.UploadContract;

import java.util.List;

public class ImageUrlPresenter implements UploadContract.Presenter{

    private ImageUrlModelImpl loadModel;
    private UploadContract.View view;

    public void init(UploadContract.View view){
        this.view = view ;
        loadModel = new ImageUrlModelImpl();
    }

    @Override
    public void upload(List<String> paths) {
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

package com.yapin.shanduo.presenter;

import android.content.Context;

import com.yapin.shanduo.model.LikeLoadModel;
import com.yapin.shanduo.model.impl.LikeModelImpl;
import com.yapin.shanduo.ui.contract.LikeContract;

/**
 * 作者：L on 2018/5/10 0010 17:58
 */
public class LikePresenter implements LikeContract.Presenter{

    private LikeContract.View view;
    private LikeLoadModel loadModel;

    public void init(LikeContract.View view){
        this.view = view;
        loadModel = new LikeModelImpl();
    }


    @Override
    public void onLike(String dynamicId) {
        loadModel.load(new OnLoadListener<String>() {
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
        } , dynamicId);
    }
}

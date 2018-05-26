package com.yapin.shanduo.presenter;

import android.content.Context;

import com.yapin.shanduo.model.ModifyLoadModel;
import com.yapin.shanduo.model.impl.ModifyModelImpI;
import com.yapin.shanduo.ui.contract.ModifyContract;

/**
 * Created by dell on 2018/4/19.
 */

public class ModifyPresenter implements ModifyContract.Presenter{


    private  ModifyContract.View view;
    private ModifyLoadModel modifyLoadModel;
    private Context context;

    public void init(Context context, ModifyContract.View view) {

        this.context = context;
        this.view = view;
        this.view.initView();
        modifyLoadModel = new ModifyModelImpI();

    }

    @Override
    public void modify(String name, String gender,String birthday ,String emotion,String signature,String hometown,String occupation,String school  , String picture , String background) {
        modifyLoadModel.load(new OnLoadListener<String>() {
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
        } ,name,gender, birthday,emotion,signature,hometown,occupation,school , picture , background);
    }

    @Override
    public void start() {

    }


}

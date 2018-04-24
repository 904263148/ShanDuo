package com.yapin.shanduo.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.yapin.shanduo.R;
import com.yapin.shanduo.model.RegisterLoadModel;
import com.yapin.shanduo.model.impl.RegisterModelImpl;
import com.yapin.shanduo.ui.contract.RegisterContract;
import com.yapin.shanduo.utils.Utils;

public class RegisterPresenter implements RegisterContract.Presenter{

    private Context context;
    private RegisterContract.View view;
    private RegisterLoadModel loadModel;

    public void init(Context context , RegisterContract.View view){
        this.context = context;
        this.view = view;
        loadModel = new RegisterModelImpl();
    }

    @Override
    public void register(String phone, String code, String password) {
        if(TextUtils.isEmpty(phone)){
            view.showFailed(2 , context.getString(R.string.toast_phone_null));
            return;
        }
        if( !Utils.checkCellphone(phone) ){
            view.showFailed(2 ,context.getString(R.string.toast_phone_wrong));
            return;
        }
        if(TextUtils.isEmpty(code)){
            view.showFailed(2 ,context.getString(R.string.toast_code_null));
            return;
        }
        if(TextUtils.isEmpty(password)){
            view.showFailed(2 ,context.getString(R.string.toast_pwd_null));
            return;
        }
        if(password.length() < 8){
            view.showFailed(2 ,context.getString(R.string.toast_pwd_wrong));
            return;
        }
        view.loading(2);
        loadModel.load(new OnLoadListener<String>() {
            @Override
            public void onSuccess(String success) {
                view.success(success);
            }

            @Override
            public void onError(String msg) {
                view.error(2 ,msg);
            }

            @Override
            public void networkError() {
                view.networkError(2);
            }
        }, phone , code , password );
    }
}

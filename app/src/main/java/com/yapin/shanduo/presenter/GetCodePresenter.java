package com.yapin.shanduo.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.yapin.shanduo.R;
import com.yapin.shanduo.model.GetCodeModel;
import com.yapin.shanduo.model.impl.GetCodeModelImpl;
import com.yapin.shanduo.ui.contract.GetCodeContract;
import com.yapin.shanduo.utils.Constants;
import com.yapin.shanduo.utils.ToastUtil;
import com.yapin.shanduo.utils.Utils;

public class GetCodePresenter implements GetCodeContract.Presenter{

    private Context context;
    private GetCodeContract.View view;
    private GetCodeModel codeModel;

    public void init(Context context , GetCodeContract.View view){
        this.context = context;
        this.view = view;
        view.initView();
        codeModel = new GetCodeModelImpl();
    }

    @Override
    public void getCode(String phone, String typeId) {
        if(TextUtils.isEmpty(phone)){
            view.showFailed( Constants.IS_CODE , context.getString(R.string.toast_phone_null));
            return;
        }
        if( !Utils.checkCellphone(phone) ){
            view.showFailed(Constants.IS_CODE , context.getString(R.string.toast_phone_wrong));
            return;
        }
        view.loading(Constants.IS_CODE);
        codeModel.load(new OnLoadListener<String>() {
            @Override
            public void onSuccess(String success) {
                view.show(success);
            }

            @Override
            public void onError(String msg) {
                view.error(Constants.IS_CODE ,msg);
            }

            @Override
            public void networkError() {
                view.networkError(Constants.IS_CODE);
            }
        } , phone , typeId);
    }
}

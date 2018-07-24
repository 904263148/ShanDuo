package com.yapin.shanduo.presenter;

import com.yapin.shanduo.model.CustomerServiceLoadModel;
import com.yapin.shanduo.model.entity.CustomerServiceInfo;
import com.yapin.shanduo.model.impl.CustomerServiceModelImpl;
import com.yapin.shanduo.ui.contract.CustomerServiceContract;

import java.util.List;

/**
 * 作者：L on 2018/7/24 0024 11:11
 */
public class CustomerServicePresenter implements CustomerServiceContract.Presenter{

    private CustomerServiceContract.View view;
    private CustomerServiceLoadModel loadModel;

    public void init(CustomerServiceContract.View view){
        this.view = view;
        loadModel = new CustomerServiceModelImpl();
        view.initView();
    }

    @Override
    public void load() {
        loadModel.load(new OnLoadListener<List<CustomerServiceInfo.Customer>>() {
            @Override
            public void onSuccess(List<CustomerServiceInfo.Customer> success) {
                view.show(success);
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

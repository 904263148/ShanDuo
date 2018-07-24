package com.yapin.shanduo.model;

import com.yapin.shanduo.model.entity.CustomerServiceInfo;
import com.yapin.shanduo.presenter.OnLoadListener;

import java.util.List;

/**
 * 作者：L on 2018/7/24 0024 09:29
 */
public interface CustomerServiceLoadModel {

    void load(OnLoadListener<List<CustomerServiceInfo.Customer>> listener);

}

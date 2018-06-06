package com.yapin.shanduo.model;

import com.yapin.shanduo.model.entity.PayOrder;
import com.yapin.shanduo.presenter.OnLoadListener;

/**
 * 作者：L on 2018/6/5 0005 09:26
 */
public interface PayOrderLoadModel {

    void load(OnLoadListener<PayOrder> listener , String payId , String password , String typeId , String money , String month , String activityId);
}

package com.yapin.shanduo.model;

import com.yapin.shanduo.presenter.OnLoadListener;

/**
 * Created by dell on 2018/6/1.
 */

public interface NewPaymentPasswordModel {
    void load(OnLoadListener<String> listener, String password , String newPassword);
}

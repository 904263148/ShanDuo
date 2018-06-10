package com.yapin.shanduo.model;

import com.yapin.shanduo.presenter.OnLoadListener;

/**
 * Created by dell on 2018/6/8.
 */

public interface VerificationModel {
    void load(OnLoadListener<String> listener, String typeId , String phone , String code);
}

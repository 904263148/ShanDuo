package com.yapin.shanduo.model;

import com.yapin.shanduo.presenter.OnLoadListener;

public interface RegisterLoadModel {

    void load(OnLoadListener<String> listener , String phone , String code ,String password );

}

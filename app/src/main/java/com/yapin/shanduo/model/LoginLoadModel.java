package com.yapin.shanduo.model;

import com.yapin.shanduo.presenter.OnLoadListener;

public interface LoginLoadModel {

    void load(OnLoadListener<String> listener, String username, String password);

}

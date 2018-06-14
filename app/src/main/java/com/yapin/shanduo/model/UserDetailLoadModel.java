package com.yapin.shanduo.model;

import com.yapin.shanduo.presenter.OnLoadListener;

/**
 * 作者：L on 2018/6/14 0014 17:44
 */
public interface UserDetailLoadModel {

    void load(OnLoadListener<String> listener);
}

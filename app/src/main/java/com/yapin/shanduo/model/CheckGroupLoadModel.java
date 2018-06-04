package com.yapin.shanduo.model;

import com.yapin.shanduo.presenter.OnLoadListener;

/**
 * 作者：L on 2018/6/1 0001 17:35
 */
public interface CheckGroupLoadModel {

    void load(OnLoadListener<String> listener ,String groupType);
}

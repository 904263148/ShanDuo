package com.yapin.shanduo.model;

import com.yapin.shanduo.presenter.OnLoadListener;

/**
 * 作者：L on 2018/6/6 0006 15:52
 */
public interface DeleteFriendLoadModel {

    void load(OnLoadListener<String> listener , String attention , String typeId);
}
